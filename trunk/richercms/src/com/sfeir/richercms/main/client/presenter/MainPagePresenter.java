package com.sfeir.richercms.main.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.ArboPageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.interfaces.IValidationPanel;
import com.sfeir.richercms.main.client.interfaces.IdisplayMainPage;
import com.sfeir.richercms.main.client.view.MainPageView;
import com.sfeir.richercms.main.shared.BeanArboPage;
import com.sfeir.richercms.wizard.client.LanguageServiceAsync;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

@Presenter( view = MainPageView.class)
public class MainPagePresenter extends LazyPresenter<IdisplayMainPage, MainEventBus> {
	
	private ArboPageServiceAsync rpcPage = null;
	private LanguageServiceAsync rpcLanguage = null;
	private BeanArboPage editingPage = null;
	private String key = null; // field used to save the key of the current Page
	private int AddOrModify = 0; //0 => add, 1=> modify
	
	public MainPagePresenter() {
		super();
	}
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
		view.onChangeSelectedLg().addChangeHandler(new ChangeHandler(){
			public void onChange(ChangeEvent event) {	
				eventBus.changeTranslation(view.getIndexOfCurrentLg());
			}
	    	});
	}
	
	
	private void addPage() {
		this.rpcPage.addArboPage(this.editingPage, this.key, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				eventBus.AddNewChildInTree(); //reload the new tree
				//on redonne la possibilité de changer de traduction
				view.enableLanguageBox();
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : AddPage");
				p.show();}
		});
	}
	
	private void modifyPage() {
		
		//this.editingPage.setKey(this.key);
		this.editingPage.setEncodedKey(this.key);
		this.rpcPage.updateArboPage(this.editingPage, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				//reload the new tree
				eventBus.reloadCurrentPageInTree(editingPage.getTranslation().get(0).getUrlName());
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Modify Page");
				p.show();}
		});
	}
	
	private void fetchLanguageListBox() {
		
		this.rpcLanguage.getLangues( new AsyncCallback<List<BeanLanguageDetails>>() {
	    	public void onSuccess(List<BeanLanguageDetails> result) {
	    		for(int i = 0 ; i<result.size(); i++) 
		    		for(BeanLanguageDetails lg : result) {
		    			if(lg.getTranslationID() == i) // sort language by her translationID
		    			{
		    				if( i == 0) // add the default language
		    					view.addLanguageInListBox(lg.getLangue(), "", true);
		    				else //other language
		    					view.addLanguageInListBox(lg.getLangue(), "", false);
		    				break;
		    			}
		    				
		    		}
	    	}
			public void onFailure(Throwable caught) {
	        	PopUpMessage p = new PopUpMessage("Error retrieving language");
	        	p.show();}
		});
	}
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onAddPage(String key){
		this.AddOrModify = 0;
		this.key = key;
		this.view.disableLanguageBox();
		this.view.setIndexOfLgToDefault();
	}
	
	public void onModifyPage(String key)
	{
		this.AddOrModify = 1;
		this.key = key;
		this.view.enableLanguageBox();
	}
	
	public void onCancelPage() {
		this.view.enableLanguageBox();
	}
	
	public void onChangeNavPanel(INavigationPanel navPanel) {
		this.view.setNavPanel(navPanel);
	}
	
	public void onChangeInfoPanel(IInformationPanel infoPanel) {
		this.view.setInfoPanel(infoPanel);
	}
	
	public void onChangeEditorPanel(ITinyMCEPanel tinyMcePanel) {
		this.view.setTinyMcePanel(tinyMcePanel);
	}
	
	public void onChangeValidationPanel(IValidationPanel validationPanel) {
		this.view.setValidationPanel(validationPanel);
	}
	
	public void onStartMain() {
		this.fetchLanguageListBox();
		this.eventBus.startPanels();
		eventBus.changeBody(view.asWidget());
	}
	
	public void onSavePage() {
		this.eventBus.callInfo();
	}

	public void onSendInfo(BeanArboPage information) {
		this.editingPage = new BeanArboPage();
		this.editingPage.setPublicationStart(information.getPublicationStart());
		this.editingPage.setPublicationFinish(information.getPublicationFinish());
		this.editingPage.setTranslation(information.getTranslation());
		this.eventBus.callContent();
	}
	
	public void onSendContent(String content) {
		this.editingPage.getTranslation().get(view.getIndexOfCurrentLg()).setContent(content);
		switch(this.AddOrModify) {
			case 0 :
				this.addPage();
				break;
			case 1 :
				this.modifyPage();
				break;
			default :
				break;
		}
	}
	
	public void onSetTranslationKeyInLanguage(String TranslationKey) {
		/*this.rpcLanguage.setTranslationKey(view.getKeyOfSelectedLg(),TranslationKey, new AsyncCallback<Void>() {
	    	public void onSuccess(Void result) {}
			public void onFailure(Throwable caught) {
	        	PopUpMessage p = new PopUpMessage("Error retrieving on setting translationKey");
	        	p.show();}
		});*/
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
	 * @param rpcLanguage
	 */
	@InjectService
	public void setLanguageService( LanguageServiceAsync rpcLanguage ) {
		this.rpcLanguage = rpcLanguage;
	}
}
