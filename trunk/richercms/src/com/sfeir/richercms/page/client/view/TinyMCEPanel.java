package com.sfeir.richercms.page.client.view;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.tinyMCE.TinyMCE;

/**
 * @author homberg.g
 * 
 * TinyMCEPanel : display a richEditor. It was used in the PageView
 */
public class TinyMCEPanel extends ResizeComposite implements ITinyMCEPanel {

	private TinyMCE tmce = null;
	private ScrollPanel  displayPanel = null;
	private LayoutPanel main = null;
	/**
	 * Constructor
	 * @param height of the tinyEditor in pixels
	 */
	public TinyMCEPanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		this.main = new LayoutPanel();
		this.main.setStyleName("tinyMceContainer");
		this.displayEditor(""); // first initialization
		this.initWidget(main);
	}
	

	public void onResize() {
		Widget parent = getParent();
		if (parent!=null) {
			System.out.println("TinyMCEPanel : " + parent.getOffsetHeight() + " -> " + getOffsetHeight());
			if (tmce!=null) {
				tmce.onResize();
			}
		}
	}

	public void ShowEditor() {
		this.tmce.enable();
	}
	
	public void HideEditor() {
		this.tmce.disable();
	}
	
	public void clear() {
		this.tmce.setVisible(true);
	}
	
	public String getContent() {
		if(this.main.getWidget(0).equals(this.displayPanel))
			return this.clearDiv(this.displayPanel.getWidget().toString());
		else
			return this.tmce.getText();
	}
	
	public void setContent(String content) {
		this.tmce.setText(content);
	}
	
	public void enableEditor() {
		this.tmce.enable();
	}
	
	public void disableEditor() {
		this.tmce.disable();
	}
	

	
	public void displayEditor(String html) {
		this.main.clear();
		this.tmce = new TinyMCE();
		this.main.add(tmce);
		this.setContent(html);
		onResize();
	}
	
	public void displayViewer(String html) {
		this.main.clear();
		this.displayPanel = new ScrollPanel();
		HTMLPanel HTMLP = new HTMLPanel(html);
		this.displayPanel.add(HTMLP);
		this.main.add(this.displayPanel);
	}
	
	/**
	 * Remove the div that includes the content after a displaying in the HTMLPanel
	 * @param content : the content with the div
	 * @return the same content without unnecessary div
	 */
	private String clearDiv(String content) {
		String newContent = content.replaceAll("^<div[^<]*>", "");
		String newContent2 = newContent.replaceAll("</div>$", "");
		return newContent2;
	}
	
	public void addEventBusInTiny(PageEventBus eventBus) {
		this.tmce.setEventBus(eventBus);
	}
}