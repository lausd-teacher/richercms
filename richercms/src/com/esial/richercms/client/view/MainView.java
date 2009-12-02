package com.esial.richercms.client.view;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainView {
	
	private VerticalPanel content;
	private TabPanel tabPanel;
	private HashMap<String, FlowPanel> tabsContent;
	private HorizontalPanel horizontalPanel;
	
	public MainView() {
		super();
		//Tabs creation and insertion
		tabsContent=createTabs();
		tabPanel=insertTabsInPanel();
		tabPanel.selectTab(0);
		
		content=new VerticalPanel();
		content.add(tabPanel);
		horizontalPanel=new HorizontalPanel();
		
		// Create and add a tree with a few items in it.
		Tree tree = createTree();
		horizontalPanel.add(tree);
		
		//Add a text area and set size
		RichTextArea richTextArea=new RichTextArea();
		richTextArea.setSize("500px", "400px");
		horizontalPanel.add(richTextArea);
		
		FlowPanel siteDock = tabsContent.get("Site");
		siteDock.add(horizontalPanel);
		
		//content.add(horizontalPanel);
	}
	
	protected Tree createTree() {
		Tree tree=new Tree();
		tree.addItem("elem1");
		tree.addItem("elem2");
		tree.addItem("elem3");
		return tree;
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
		tContent.put("Site", new FlowPanel());
		tContent.put("Elements", new FlowPanel());
		tContent.put("Modules", new FlowPanel());
		tContent.put("Utilisateurs", new FlowPanel());
		tContent.put("Rapports", new FlowPanel());
		return tContent;
	}

	public VerticalPanel getContent() {
		return content;
	}

	public void setContent(VerticalPanel content) {
		this.content = content;
	}

}
