package com.sfeir.richercms.wizard.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.wizard.client.wizardConfigConstants;
import com.sfeir.richercms.wizard.client.Interface.IdisplayPage1;

/**
 * First configuration view (explication + UIlanguage choice)
 * @author homberg.g
 *
 */
public class Page1View  extends ResizeComposite implements IdisplayPage1 {
	
	//gestion des langues
	private wizardConfigConstants constants = GWT.create(wizardConfigConstants.class);
	
	//widget de la fenetre
	private Button btnNext = null;
	private Label language = null;
	private ListBox languageList = null;
	
	// panel de la fenetre
	private HorizontalPanel languagePanel = null;
	private LayoutPanel centralPanel = null;
	

	public Page1View() {
		super();
	}
	
	/**
	 * return the view
	 * @return this
	 */
	public Widget asWidget() {	
		return this;
	}

	/**
	 * evt when the button next is clicked
	 */
	public HasClickHandlers getNextButton() {
		
		return this.btnNext;
	}

	/**
	 * evt when the list change
	 */
	public HasChangeHandlers getSelectedLanguage() {
		
		return this.languageList;
	}

	/**
	 * set the current language
	 */
	public void setSelectedLanguage(int idLanguage) {
		
		this.languageList.setItemSelected(idLanguage, true);
	}


	/**
	 * get the index of the current language
	 */
	public int getIndexLanguage() {
		
		return this.languageList.getSelectedIndex();
	}
	
	/**
	 * Modify the "Locale" variable with the new countryCode
	 * @param countryCode
	 */
	public void reload(String countryCode) {
		
		UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
		urlBuilder.setParameter("locale", countryCode);
		Window.Location.replace(urlBuilder.buildString());
	}
	
	
	/**
	 * call by the mvp4g framework to instanciate view (lazy method)
	 */
	public void createView() 
	{
		this.btnNext = new Button(constants.buttonNext());
		this.language = new Label(constants.LabelLangue());
		this.languageList = new ListBox();
		this.languagePanel = new HorizontalPanel();
		this.centralPanel = new LayoutPanel();
		
		LayoutPanel mainContent = new LayoutPanel();
		Label title = new Label(constants.titlePage1());
		//is wrapped by the composite (super)
		CenterLayoutPanel mainPanel = new CenterLayoutPanel(800, 400, title, mainContent);
		
		//List => centralPanel
		this.languageList.addItem("English");
		this.languageList.addItem("Français");
		this.languageList.addItem("Deutsch");
		this.languageList.setVisibleItemCount(1);
		//languagePanel.setBorderWidth(3);
		this.languagePanel.add(language);
		this.languagePanel.add(languageList);
		centralPanel.add(languagePanel);
		
		//Summary => centralPanel
		HTML summary = new HTML(constants.summary());
		centralPanel.add(summary);
		
		//position of the widgets in the centralPanel
		centralPanel.setWidgetTopHeight(languagePanel, 0, Style.Unit.PCT, 10, Style.Unit.PCT);
		centralPanel.setWidgetTopHeight(summary, 10, Style.Unit.PCT, 90, Style.Unit.PCT);
		//centralPanel.setWidth("50%");
		
		//centralPanel => root 80%
		mainContent.add(centralPanel);
		mainContent.setWidgetTopHeight(centralPanel, 0, Style.Unit.PCT, 80, Style.Unit.PCT);
	    
		// Next button
		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("buttonPanel");
		buttonPanel.add(btnNext);
		mainContent.add(buttonPanel);
		mainContent.setWidgetBottomHeight(buttonPanel, 0, Style.Unit.PX, 28, Style.Unit.PX);
		
		initWidget(mainPanel);
	}
}
