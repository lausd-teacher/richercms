package wizardConfig.client.view;

import wizardConfig.client.wizardConfigConstants;
import wizardConfig.client.Interface.IdisplayPage1;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * First configuration view (explication + UIlanguage choice)
 * @author homberg.g
 *
 */
public class Page1View  extends DockLayoutPanel implements IdisplayPage1
{
	//gestion des langues
	private wizardConfigConstants constants = GWT.create(wizardConfigConstants.class);
	
	//widget de la fenetre
	private Button btnSuivant = new Button(constants.buttonNext());
	private Label langue = new Label(constants.LabelLangue());
	private ListBox listeLangue = new ListBox();
	
	// panel de la fenetre
	private HorizontalPanel PanelLangue = new HorizontalPanel();
	private VerticalPanel PanelCentral = new VerticalPanel();
	//private DockLayoutPanel panelPrincipal = new DockLayoutPanel(Unit.PX); // layout principal
	
	/**
	 * build the view and place widgets
	 */
	public Page1View()
	{
		super(Unit.PX);
		this.listeLangue.addItem("English");
		this.listeLangue.addItem("Francais");
		this.listeLangue.addItem("Deutsch");
		this.listeLangue.setVisibleItemCount(1);


		this.PanelLangue.add(langue);
		this.PanelLangue.add(listeLangue);
		

		this.PanelCentral.add(PanelLangue);
		this.PanelCentral.add(new HTML(constants.summary()));
		
		//Layout principal
	    this.addNorth(new HTML(constants.titlePage1()), 50);
	    this.addSouth(btnSuivant, 30);
	    this.add(PanelCentral);

	    //hauteur de la page
	    this.setHeight("300px");

	    // recuperation du layout root + ajout de notre panel
	    //RootLayoutPanel panelRoot = RootLayoutPanel.get();
	    //panelRoot.add(this);

	}
	
	public Widget asWidget() 
	{
		return this;
	}

	/**
	 * evt when the button next is clicked
	 */
	public HasClickHandlers getNextButton() 
	{
		return this.btnSuivant;
	}

	/**
	 * evt when the list change
	 */
	public HasChangeHandlers getSelectedLanguage() 
	{
		return this.listeLangue;
	}

	/**
	 * set the current language
	 */
	public void setSelectedLanguage(int idLanguage) 
	{
		this.listeLangue.setItemSelected(idLanguage, true);
	}


	/**
	 * get the index of the current language
	 */
	public int getIndexLanguage() 
	{
		return this.listeLangue.getSelectedIndex();
	}
}
