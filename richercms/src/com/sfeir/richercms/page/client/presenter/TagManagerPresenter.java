package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.page.client.TagServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.ITagManager;
import com.sfeir.richercms.page.client.view.TagManager;
import com.sfeir.richercms.page.shared.BeanTag;

/**
 * Presenter of the tag manager view
 * All interaction with eventBus, datastore and event handling
 * are coded here
 * @author homberg.g
 */
@Presenter( view = TagManager.class)
public class TagManagerPresenter extends LazyPresenter<ITagManager, PageEventBus>{

	private TagServiceAsync rpcTag = null;
	private Long currentTagId; // use for apply modification on the current tag
	
	public void bindView() {
		this.view.clickOnAddTag().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final BeanTag bean = new BeanTag(view.getNewTagName(),
						view.getNewShortLib(),
						view.getNewDescription(),
						view.isTextual());
				
				if(testTag(bean))
					rpcTag.addTag(bean, new AsyncCallback<Long>() {
						public void onFailure(Throwable caught) {}
						public void onSuccess(Long result) {
							if(result != null){
								view.hideAddLine();
								bean.setId(result);
								addTag(bean);
							}else {
								PopUpMessage popUp = new PopUpMessage(view.getConstants().msgErrorAddTag());
								popUp.show();
							}
						}
					});
			}
		});
		
		this.view.clickOnApplyModif().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.hideAddLine();
				BeanTag bean = new BeanTag(currentTagId,
								   view.getModifyTagName(),
								   view.getModifyShortLib(),
								   view.getModifyDescription(),
								   view.isModifyTextual());
				if(testTag(bean))
					rpcTag.updateTag(bean, new AsyncCallback<Boolean>() {
						public void onFailure(Throwable caught) {}
						public void onSuccess(Boolean result) {
							//if update was possible
							if(result.booleanValue()) {
								view.hideModifyFields(false);
							}else{
								PopUpMessage popUp = new PopUpMessage(view.getConstants().msgErrorModifyTag());
								popUp.show();
							}
						}
					});
				
			}
		});
		
		this.view.clickOnCancelModif().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {	
				view.hideModifyFields(true);
			}
		});
		
		this.view.clickOnCancelAddTag().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.hideAddLine();
			}
		});
		
		this.view.clickOnAddNewTag().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.showAddLine();
			}
		});
	}
	
	public void onStartTagManager() {
		this.fetchTagTable();
		eventBus.displayTagManager(this.view);
	}
	
	/**
	 * fill the tag table with all tag
	 */
	private void fetchTagTable(){
		this.view.clearTagTable();
		this.eventBus.showInformationPopUp();
		this.eventBus.addWaitLinePopUp(view.getConstants().MsgLoadTag());
		this.rpcTag.getAllTags(new AsyncCallback<List<BeanTag>>() {
			public void onSuccess(List<BeanTag> result) {
				eventBus.addSuccessPopUp(view.getConstants().MsgLoadTagSuccess());
				eventBus.addWaitLinePopUp(view.getConstants().MsgLoadTagTable());
				for(final BeanTag bean : result){
					addTag(bean);
				}
				eventBus.addSuccessPopUp(view.getConstants().MsgLoadTagTableOk());
				eventBus.hideInformationPopUp();
			}
			public void onFailure(Throwable caught) {
				eventBus.addErrorLinePopUp(view.getConstants().MsgLoadTagFail());
				eventBus.hideInformationPopUp();
			}
		});
	}
	
	/**
	 * Add new tag in table
	 * @param bean : corresponding new table
	 */
	private void addTag(final BeanTag bean) {
		
		final int lineNumb = view.addLine(bean.getTagName(),
				bean.getShortLib(),
				bean.getDescription(),
				bean.isTextual());
		view.getCurDeleteClick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.hideAddLine();
				deleteTag(bean.getId());
				view.deleteLine(event.getRelativeElement());
			}
		});
		
		view.getCurModifyClick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				currentTagId = bean.getId();
				view.DisplayModifyFields(lineNumb);
			}
		});
	}
	
	/**
	 * Delete a specific tag into datastore
	 * @param id : tag id
	 */
	private void deleteTag(Long id){
		this.rpcTag.deleteTag(id, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {}
			public void onSuccess(Void result) {}
		});
	}
	
	/**
	 * test bean's fields and 
	 * display an errorMessage if obligation fields are empty
	 * @param bean
	 * @return true if bean are correct, false either
	 */
	private boolean testTag(BeanTag bean){
		if(bean.getTagName().length() == 0 || bean.getShortLib().length() == 0){
			PopUpMessage popUp = new PopUpMessage(view.getConstants().msgErrorEmptyTag());
			popUp.show();
			return false;
		}
			
		return true;
	}
	
	/**
	 * used by the framework to instantiate rpcTag
	 * @param rpcUser
	 */
	@InjectService
	public void setTagService( TagServiceAsync rpcTag ) {
		this.rpcTag = rpcTag;
	}
}
