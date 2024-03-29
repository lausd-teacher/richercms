package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.UserInfoServiceAsync;
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.ImageAndId;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IUserManager;
import com.sfeir.richercms.page.client.view.UserManager;
import com.sfeir.richercms.page.client.view.custom.ConfirmationBox;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.shared.BeanUser;


/**
 * Presenter of the user manager view
 * All interaction with eventBus, datastore and event handling
 * are coded here
 * @author homberg.g
 */
@Presenter( view = UserManager.class)
public class UserManagerPresenter extends LazyPresenter<IUserManager, PageEventBus>{


	private UserInfoServiceAsync rpcUser = null;
	private ArboPageServiceAsync rpcPage = null;
	
	public void bindView() {
		
		// add user formular handle
		this.view.onAddNewUserClick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.showInformationPopUp();
				if(view.getNewEmail().length() == 0){
					eventBus.addErrorLinePopUp(view.getConstants().MsgWarnMailEmpty());
					eventBus.hideInformationPopUp();
				}else {
					eventBus.addWaitLinePopUp(view.getConstants().MsgAddUserInProg());
					rpcUser.addUser(view.getNewEmail(), new AsyncCallback<Long>() {
						public void onFailure(Throwable caught) {
							eventBus.addErrorLinePopUp(view.getConstants().MsgErrorAddUser());
							eventBus.hideInformationPopUp();
						}
						public void onSuccess(Long result) {
							if(result == null){
								eventBus.addErrorLinePopUp(view.getConstants().MsgUserAlreadyInDB());
							}else {
								eventBus.addSuccessPopUp(view.getConstants().MsgUserAddSuccess());
								fetchUserTable();
								view.clearAddUserTextBox();
							}
							eventBus.hideInformationPopUp();
						}
					});
				}
			}
		});
	}
	
	public void onStartUserManager() {
		this.eventBus.displayUserManager(this.view);
		fetchUserTable();
	}
	
	private void fetchUserTable() {
		this.view.clearUserTable();
		this.eventBus.showInformationPopUp();
		this.eventBus.addWaitLinePopUp(this.view.getConstants().MsgLoadUser());
		this.rpcPage.getAllLockedPages(new AsyncCallback<List<BeanArboPage>>() {
			public void onFailure(Throwable caught) {}
			public void onSuccess(List<BeanArboPage> lokedPage) {
				makeTable(lokedPage);
			}
		});
	}
	
	/**
	 * Function call by fetchUserTable to fetch the table
	 * @param lokedPage
	 */
	private void makeTable(final List<BeanArboPage> lokedPages){
		
		this.rpcUser.getUsers(new AsyncCallback<List<BeanUser>>() {
			public void onSuccess(List<BeanUser> result) {	
				
				// boucle sur les user
				for(final BeanUser usr : result){
					String state = view.getConstants().offLine();
					
					if(usr.isLoggedIn())
						state = view.getConstants().onLine();
					
					//add new line	+ handle deleteEvent
					view.addLine(usr.getEmailAddress(),usr.getNickname(), state).addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							deleteUser(usr);
						}
					});
					
					// add Yes admin RadioButton + handle event
					view.addAdminWidgetYes(usr.isAdmin()).addValueChangeHandler(new ValueChangeHandler<Boolean>() {
						public void onValueChange(ValueChangeEvent<Boolean> event) {
							rpcUser.UserAdminChange(usr.getId(), true, new AsyncCallback<Void>() {
								public void onSuccess(Void result) {}
								public void onFailure(Throwable caught) {}
							});
						}
					});
					
					// add No admin RadioButton + handle event
					view.addAdminWidgetNo(usr.isAdmin()).addValueChangeHandler(new ValueChangeHandler<Boolean>() {
						public void onValueChange(ValueChangeEvent<Boolean> event) {
							rpcUser.UserAdminChange(usr.getId(), false, new AsyncCallback<Void>() {
								public void onSuccess(Void result) {}
								public void onFailure(Throwable caught) {}
							});
						}
					});
					
					// take all lockedPage by current User
					for(BeanArboPage lokedPage : lokedPages){
						
						if(lokedPage.getIdUserInModif().intValue() == usr.getId().intValue()){
							//add all locked page in the line + handle event
							view.addLockedPage(lokedPage.getId(),lokedPage.getUrlName()).
									addClickHandler(new ClickHandler() {
										public void onClick(ClickEvent event) {	
											ImageAndId img = (ImageAndId)event.getSource();
											unlockPageRPC(img.getId());
									}});
						}
					}
				}
				eventBus.addSuccessPopUp(view.getConstants().MsgLoadUserSuccess());
				eventBus.hideInformationPopUp();
			}
			public void onFailure(Throwable caught) {
				eventBus.addErrorLinePopUp(view.getConstants().MsgLoadUserError());
				eventBus.hideInformationPopUp();
			}
		});
	}
	
	private void deleteUser(final BeanUser usr){
		ConfirmationBox confirmPopUp = new ConfirmationBox("ATTENTION",
				this.view.getConstants().ConfirmCancelMsg());

		confirmPopUp.getClickOkEvt().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rpcPage.unlockAllUserPage(usr.getId(), new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(Void result) {
					}
				});
				rpcUser.deleteUser(usr.getId(), new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
					}
				public void onSuccess(Void result) {
					fetchUserTable();
				}
				});
			}
		});
	}
	
	private void unlockPageRPC(Long pageId) {
		this.rpcPage.unlockThisPage(pageId, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {}
			public void onSuccess(Void result) {
				fetchUserTable();
			}
		});
	}
	

	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
	
	/**
	 * used by the framework to instantiate rpcLanguage
	 * @param rpcUser
	 */
	@InjectService
	public void setUserInfoService( UserInfoServiceAsync rpcUser ) {
		this.rpcUser = rpcUser;
	}
}
