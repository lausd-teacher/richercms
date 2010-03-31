package wizardConfig.client.view;

import wizardConfig.client.wizardConfigConstants;
import wizardConfig.client.Interface.IdisplayPage1;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * First configuration view (explication + UIlanguage choice)
 * @author homberg.g
 *
 */
public class Page1View  extends LayoutPanel implements IdisplayPage1 {
	
	//gestion des langues
	private wizardConfigConstants constants = GWT.create(wizardConfigConstants.class);
	
	//widget de la fenetre
	private Button btnNext = new Button(constants.buttonNext());
	private Label language = new Label(constants.LabelLangue());
	private ListBox languageList = new ListBox();
	
	// panel de la fenetre
	private HorizontalPanel languagePanel = new HorizontalPanel();
	private LayoutPanel centralPanel = new LayoutPanel();
	//private DockLayoutPanel panelPrincipal = new DockLayoutPanel(Unit.PX); // layout principal
	

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
	 * call by the mvp4g framework to instanciate view (lazy method)
	 */
	public void createView() 
	{
		//Title => root 10%
		HTML title = new HTML(constants.titlePage1());
		this.add(title);
		this.setWidgetTopHeight(title, 0, Style.Unit.PCT, 10, Style.Unit.PCT);
		
		//List => centralPanel
		this.languageList.addItem("English");
		this.languageList.addItem("Francais");
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
		centralPanel.setWidgetTopHeight(languagePanel, 0, Style.Unit.PCT, 4, Style.Unit.PCT);
		centralPanel.setWidgetTopHeight(summary, 10, Style.Unit.PCT, 90, Style.Unit.PCT);
		//centralPanel.setWidth("50%");
		
		//centralPanel => root 80%
	    this.add(centralPanel);
	    this.setWidgetTopHeight(centralPanel, 10, Style.Unit.PCT, 80, Style.Unit.PCT);
	    
		//Next button => root 3%
		btnNext.setWidth("10%");
		this.add(btnNext);
		this.setWidgetTopHeight(btnNext, 85, Style.Unit.PCT, 3, Style.Unit.PCT);
		
	}
}
