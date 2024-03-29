package com.sfeir.richercms.page.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.IImageManager;
import com.sfeir.richercms.page.client.view.custom.PopUpImagePreview;
import com.sfeir.richercms.page.client.view.custom.thumb.ThumbAndBtn;

/**
 * ImageManager panel in the PageView, use add image using the path of Page
 * @author homberg.g
 *
 */
public class ImageManager extends ResizeComposite implements IImageManager {
	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	
	private LayoutPanel panel;
	private static final int thumbsWidth = 150;
	private static final int thumbsHeight = 150;
	private static final String urlActionUP = GWT.getModuleName() + "/upload"; // up a file
	private static final String thumbnailUrl = GWT.getModuleName() + 
						"/thumbnail?width="+thumbsWidth+"&height="+thumbsHeight; // display a thumbnail
	private FileUpload uploadField = new FileUpload();
	private FlexTable thumbsPanel = null;
	private Button btnSend = null;
	private Hidden path = null;
	private FormPanel form = null;
	private PopUpImagePreview popUpImg = null;
	private Label thumbtitle = null;
	private Label uptitle = null;
	
	private int currentLine = 0;
	private int currentCol = 0;
	private int nbColMax = 6;
	
	public Widget asWidget() {	
		return this;
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		this.panel = new LayoutPanel();
		VerticalPanel submitPanel = new VerticalPanel();
		this.path = new Hidden();
		this.thumbsPanel = new FlexTable();
		this.thumbsPanel.setCellPadding(5);
		
		this.thumbtitle = new Label(this.constants.ThumbTitle());
		this.thumbtitle.setStyleName("informationTitle");
		this.panel.add(this.thumbtitle);
		this.panel.setWidgetTopHeight(this.thumbtitle,0,Unit.PX,50,Unit.PX);
		
		ScrollPanel scrollThumb = new ScrollPanel();
		scrollThumb.setWidget(this.thumbsPanel);
		scrollThumb.addStyleName("thumbsPanel");
		this.panel.add(scrollThumb);
		this.panel.setWidgetTopHeight(scrollThumb,50,Unit.PX,450,Unit.PX);
		
		this.uptitle = new Label(this.constants.UploadTitle());
		this.uptitle.setStyleName("informationTitle");
		this.panel.add(this.uptitle);
		this.panel.setWidgetTopHeight(this.uptitle,520,Unit.PX,50,Unit.PX);
		
		this.btnSend = new Button(this.constants.BtnUpload());
		
		// Creation du panel contenant le formulaire
		this.form = new FormPanel();
		// Definition de l'action du formulaire (url du servlet)
		
		form.setAction(urlActionUP);
		// encodage pour envoi de fichier
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		// definition de la methode POST pour l'envoi
		form.setMethod(FormPanel.METHOD_POST);
		// Creation du panel contenant tous les element du formulaire
		//form.setWidget(panel);
		
		
		this.uploadField.setTitle(this.constants.imageTitle());
		this.uploadField.setName("uploadFormElement");
		
		submitPanel.add(this.path);
		submitPanel.add(this.uploadField);
		this.form.setWidget(submitPanel);
		
		this.panel.add(form);
		this.panel.setWidgetTopHeight(this.form,620,Unit.PX,50,Unit.PX);
		// Ajout d'un bouton de soumission pour le formulaire
		this.panel.add(this.btnSend);
		this.panel.setWidgetTopHeight(this.btnSend,650,Unit.PX,50,Unit.PX);
		
		this.initWidget(this.panel);
	}
		
	@SuppressWarnings("static-access")
	public HasClickHandlers addThumbnail(String p) {
		ThumbAndBtn thumb = new ThumbAndBtn(this.thumbnailUrl+"&path="+p,null,p,thumbsWidth,thumbsHeight);

		if(this.currentCol == this.nbColMax){
			this.currentCol = 0;
			this.currentLine ++;
		}
		
		this.thumbsPanel.setWidget(this.currentLine, this.currentCol, thumb);
		
		this.currentCol++;
		
		//handle the click on the delete btn
		return thumb.btnClickEvent();
	}
	
	public HasMouseDownHandlers onThumbClick() {
		ThumbAndBtn thumb = (ThumbAndBtn)this.thumbsPanel.getWidget(this.currentLine, this.currentCol-1);
		return thumb.imageClickEvent();
	}
	
	public HasClickHandlers onSendBtnclick() {
		return this.btnSend;
	}
	
	public FormPanel getFormEvent() {
		return this.form;
	}
	
	public void submitForm() {
		this.form.submit();
	}
	
	public void clearThumbNails() {
		this.thumbsPanel.clear();
		this.currentLine = 0;
		this.currentCol = 0;
	}
	
	public PageConstants getConstants() {
		return this.constants;
	}


	public String getCurrentPath() {
		return this.path.getName();
	}

	public void setCurrentPath(String path) {
		this.path.setName(path);
	}
	
	public void showPopUpImgPreview(String path){
		if(popUpImg!=null)
			popUpImg.hide();
		
		popUpImg = new PopUpImagePreview(path);
		popUpImg.center();
	}
	
	public void deleteThumb(Element thumb){
		thumb.getParentNode().getParentNode()
			.getParentNode().removeFromParent();
	}
	
	public void setTitle(String title) {
		this.thumbtitle.setText(this.constants.ThumbTitle()+" "+title);
	}
}
