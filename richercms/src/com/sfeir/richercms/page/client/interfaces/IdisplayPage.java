package com.sfeir.richercms.page.client.interfaces;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.PageConstants;


/**
 * Allows the presenter to communicate with the view
 * MainPage <=> MainPagePresenter
 * @author homberg.g
 *
 */
public interface IdisplayPage extends LazyView {

	Widget asWidget();
	
	/**
	 * Load the NavigationPanel in the right place Panel
	 * @param navPanel
	 */
	public void setNavPanel(INavigationPanel navPanel);

	/**
	 * Load the InformationPanel in the right place Panel
	 * @param listPanel
	 */
	public void setInfoPanel(IInformationPanel listPanel);

	/**
	 * Load the TinyMCEPanel in the right place Panel
	 * @param tinyMcePanel
	 */
	public void setTinyMcePanel(ITinyMCEPanel tinyMcePanel);

	/**
	 * Load the ValidationPanel in the right place Panel
	 * @param validationPanel
	 */
	public void setValidationPanel(IValidationPanel validationPanel);
	
	/**
	 * Load the ReorderPanel into the layout of the InformationPanel
	 * @param reorderPanel : the panel
	 */
	public void displayReorderPanel(IReorderPagePanel reorderPanel);
	
	/**
	 * Display standard panel if it was instanciate after.
	 * This function is using for re-display panel after a specific panels (reorderPanel, ...)
	 */
	public void displayNormalPanel();
	
	/**
	 * Display page view with all standard panel
	 * This function need all panel instanciate after this call.
	 * This function is using for re-display pageView after a specific tool(ImageTool, ...)
	 */
	public void reDisplayPageView();
	
	/**
	 * Load the imageManager's Panel into the layout of the InformationPanel
	 * @param imageMPanel : the panel
	 */
	public void displayImagePanel(IImageManager imageMPanel);
	
	/**
	 * Load the UserManager's Panel into the dispositionPanel Container (all screen)
	 * @param userManagerPanel
	 */
	public void displayUserManager(IUserManager userManagerPanel);
	
	/**
	 * Load the TagManager's Panel into the dispositionPanel container (all screen)
	 * @param tagManager
	 */
	public void displayTagManager(ITagManager tagManager);
	
	/**
	 * Load the TemplateManager's Panel into the dispositionPanel container (all screen)
	 * @param templateManager
	 */
	public void displayTemplateManager(ITemplateManager templateManager);
	
	/**
	 * Add a language in the listBox
	 * @param name : the display name of the language 
	 * @param key : the key, needed to retrive the page translation in the dataBase
	 * @param defaultLg : if is set : the language was select by default and a string is added in the name to specify it.
	 */
	public void addLanguageInListBox(String name, String key, boolean defaultLg);
	
	/**
	 * When a new language is selected in the listBox
	 * @return the Event
	 */
	public HasChangeHandlers onChangeSelectedLg();
	
	/**
	 * When user click on the logOut Anchor
	 * @return the Event
	 */
	public HasClickHandlers onLogOutClick();
	
	/**
	 * When user click on the up button to resize the spliter
	 * in default place.
	 * @return the Event
	 */
	public HasClickHandlers upSpliterEvent();

	/**
	 * When user click on the down button to minimize the bottom part 
	 * of the spliter.
	 * @return the Event
	 */
	public HasClickHandlers downSpliterEvent();
	
	/**
	 * Return the key of the language, its the key of the associated language in the datastore.
	 * @return the key of the selected Language
	 */
	public String getKeyOfSelectedLg();

	/**
	 * get the number of language in the lisBox
	 * @return size of the language listBox
	 */
	public int countTranslation();
	
	/**
	 * Return the position in the listBox of the selected Language
	 * in order to display the good translation
	 * @return : index of the selected language
	 */
	public int getIndexOfCurrentLg();
	
	/**
	 * Set the index of the languages listBox to the default value
	 */
	public void setIndexOfLgToDefault();
	
	/**
	 * Set the display languages
	 * @param index : language index in the list
	 */
	public void setLanguageListIndex(int index);
	
	/**
	 * Disable the listBox containing language
	 */
	public void disableLanguageBox();
	
	/**
	 * Enable the listBox containing language
	 */
	public void enableLanguageBox();
	
	/**
	 * Show the popUp who request the user 
	 * to waited during saving a page in the database
	 */
	public void showWaitPopUp();
	
	/**
	 * Hide the popUp who request the user 
	 * to waited during saving a page in the database
	 */
	public void hideWaitPopUp();
	
	/**
	 * Add a new line in the State popUp
	 * @param text : the description of the new state
	 * @param state : wait = 0 | success = 1 | fail = 2
	 */
	public void addLineInPopUp(String text, int state);
	
	
	/**
	 * Return the MainConstants. Use this for use translationSystem
	 * @return the MainConstants
	 */
	public PageConstants getConstants();
	
	/**
	 * Add a specific command when the ImageToolEntry is clicked
	 * in the menuBar
	 * @param cmd
	 */
	public void setImageToolCommand(Command cmd);
	
	/**
	 * Add a specific command when the PageToolEntry is clicked
	 * in the menuBar
	 * @param cmd
	 */
	public void setPageToolCommand(Command cmd);
	
	/**
	 * Add a specific command when the userSettingsEntry is clicked
	 * in the menuBar
	 * @param cmd
	 */
	public void setUserSettingsCommand(Command cmd);
	
	/**
	 * Add a specific command when the tagSettingsEntry is clicked
	 * in the menuBar 
	 * @param cmd
	 */
	public void setTagSettingsCommand(Command cmd);
	
	/**
	 * Add a specific command when the templateSettingsEntry is clicked
	 * in the menuBar 
	 * @param cmd
	 */
	public void setTemplateSettingsCommand(Command cmd);
	
	/**
	 * Set the url of the logOut Anchor.
	 * this URL is set in the beanUser, set during the connection.
	 * Only this BeanUser contain the good Url to disconnect user
	 * @param url : the url
	 */
	public void setLogOutAnchor(String url);
	
	/**
	 * set value of nickname
	 * @param name : pseudo
	 */
	public void setPseudo(String name);
	
	/**
	 * Down the topBottomSpliter and display just the top part
	 */
	public void downRightSpliter();
	
	/**
	 * Up the topBottomSpliter and resize it
	 * to default place.
	 */
	public void upRightSpliter();
	
	/**
	 * Display the admin menu
	 * @param adminMenu
	 */
	public void initAdminMenu(boolean adminMenu);
}
