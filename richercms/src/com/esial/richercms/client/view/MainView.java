package com.esial.richercms.client.view;

import java.util.HashMap;
import java.util.Iterator;

import com.esial.richercms.client.Richercms;
import com.esial.richercms.client.UserInfo;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;

public class MainView {

	private FlowPanel content;
	private TabPanel tabPanel;
	private HashMap<String, FlowPanel> tabsContent;
	private HorizontalPanel loginPanel = new HorizontalPanel();
	private Label loginLabel = new Label(Richercms.getInstance().getCmsConstants().signInto());
	private Anchor signInLink = new Anchor(Richercms.getInstance().getCmsConstants().signIn());
	private Anchor signOutLink = new Anchor(Richercms.getInstance().getCmsConstants().signOut());

	//private String email;

	public MainView(UserInfo loginInfo) {
		super();
		if(loginInfo.isLoggedIn()) {
			//Tabs creation and insertion
			tabsContent=createTabs();
			tabPanel=insertTabsInPanel();
			tabPanel.selectTab(0);

			content=new FlowPanel();
			content.add(tabPanel);

			FlowPanel siteDock = tabsContent.get(Richercms.getInstance().getCmsConstants().site());
			siteDock.add(new SiteView());
			FlowPanel adminDock = tabsContent.get(Richercms.getInstance().getCmsConstants().admin());
			adminDock.add(new AdminView());
			signOutLink.setHref(loginInfo.getLogoutUrl());
			loginPanel.add(new Label(loginInfo.getEmailAddress()));
			if(loginInfo.isAdmin()) loginPanel.add(new Label(" (admin) "));
			loginPanel.add(signOutLink);
			content.insert(loginPanel, 0);

			tabPanel.selectTab(0);
		} else {
			content=new FlowPanel();
			signInLink.setHref(loginInfo.getLoginUrl());
			loginPanel.add(loginLabel);
			loginPanel.add(signInLink);
			content.add(loginPanel);
		}
	}

	private TabPanel insertTabsInPanel() {
		TabPanel tPanel=new TabPanel();
		Iterator<String> it=tabsContent.keySet().iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			tPanel.add(tabsContent.get(string), string);	
		}
		return tPanel;
	}

	private  HashMap<String, FlowPanel> createTabs() {
		HashMap<String, FlowPanel> tContent=new HashMap<String, FlowPanel>();
		tContent.put(Richercms.getInstance().getCmsConstants().site(), new FlowPanel());
		tContent.put(Richercms.getInstance().getCmsConstants().admin(), new FlowPanel());
		return tContent;
	}

	public FlowPanel getContent() {
		return content;
	}

	public void setContent(FlowPanel content) {
		this.content = content;
	}

}
