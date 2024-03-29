package com.sfeir.richercms.page.client.view;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.client.tools.HtmlTools;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.tinyMCE.TinyMCE;

/**
 * @author homberg.g
 * 
 * TinyMCEPanel : display a richEditor. It was used in the PageView
 */
public class TinyMCEPanel extends ResizeComposite implements ITinyMCEPanel {

	private TinyMCE tmce = new TinyMCE();
	private ScrollPanel  displayPanel = null;
	private LayoutPanel main = null;
	private boolean isViewer = true;
	/**
	 * Constructor
	 * @param height of the tinyEditor in pixels
	 */
	public TinyMCEPanel() {
		super();
		//System.out.println("TinyMCEPanel création");
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		this.main = new LayoutPanel();
		this.main.setStyleName("tinyMceContainer");
		this.displayEditor(""); // first initialization
		this.initWidget(main);
		//System.out.println("TinyMCEPanel createView");
	}
	

	public void onResize() {
		//System.out.println("TinyMCEPanel onResize");
		Widget parent = getParent();
		if (parent!=null) {
			//System.out.println("TinyMCEPanel : " + parent.getOffsetHeight() + " -> " + getOffsetHeight());
			if (tmce!=null) {
				tmce.onResize();
			}
		}
	}

	public void ShowEditor() {
		//System.out.println("TinyMCEPanel ShowEditor");
		this.tmce.show();
	}
	
	public void HideEditor() {
		//System.out.println("TinyMCEPanel HideEditor");
		this.tmce.hide();
	}
	
	public void clear() {
		//System.out.println("TinyMCEPanel clear");
		this.tmce.setVisible(true);
	}
	
	public String getContent() {
		//System.out.println("TinyMCEPanel getContent");
		if(this.main.getWidget(0).equals(this.displayPanel))
			return this.clearDiv(this.displayPanel.getWidget().toString());
		else
			return this.tmce.getText();
	}
	
	public void setContent(String content) {
		//System.out.println("TinyMCEPanel setContent");
		this.tmce.setText(content);
	}
	
	public void displayEditor(String html) {
		//System.out.println("TinyMCEPanel displayEditor" + html);
		if (!isViewer) {
			this.tmce.hide();
			this.tmce.setInitialized(false);
		}
		this.main.clear();
		this.main.add(tmce);
		isViewer = false;
		this.setContent(html);
	}
	
	public void displayViewer(String html) {
		//System.out.println("TinyMCEPanel displayViewer");
		this.tmce.hide();
		this.tmce.setInitialized(false);
		this.main.clear();
		this.displayPanel = new ScrollPanel();
		HTMLPanel HTMLP = new HTMLPanel(HtmlTools.changeUrl(html));
		this.displayPanel.add(HTMLP);
		this.main.add(this.displayPanel);
		isViewer = true;
	}
	
	
	/**
	 * Remove the div that includes the content after a displaying in the HTMLPanel
	 * @param content : the content with the div
	 * @return the same content without unnecessary div
	 */
	private String clearDiv(String content) {
		//System.out.println("TinyMCEPanel clearDiv");
		String newContent = content.replaceAll("^<div[^<]*>", "");
		String newContent2 = newContent.replaceAll("</div>$", "");
		return newContent2.replaceAll("<a href=\"/site/", "<a href=\"/");
	}
	
	public void addEventBusInTiny(PageEventBus eventBus) {
		//System.out.println("TinyMCEPanel addEventBusInTiny");
		this.tmce.setEventBus(eventBus);
	}
}
