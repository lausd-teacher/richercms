package com.sfeir.richercms.main.shared;

import java.io.Serializable;


@SuppressWarnings("serial")
public class BeanPage implements Serializable {
	
	private String browserTitle;
	private String pageTitle;
	private String urlName;
	private String description;
	private String keyWord;
	private String publicationStart;
	private String publicationFinish;
	private String content;
	
	public BeanPage() {
		super();
	}

	public BeanPage(String browserTitle, String pageTitle, String urlName,
			String description, String keyWord, String publicationStart,
			String publicationFinish, String content) {
		super();
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.content = content;
	}

	public String getBrowserTitle() {
		return browserTitle;
	}
	
	public void setBrowserTitle(String browserTitle) {
		this.browserTitle = browserTitle;
	}
	
	public String getPageTitle() {
		return pageTitle;
	}
	
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	public String getUrlName() {
		return urlName;
	}
	
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public String getPublicationStart() {
		return publicationStart;
	}
	
	public void setPublicationStart(String publicationStart) {
		this.publicationStart = publicationStart;
	}
	
	public String getPublicationFinish() {
		return publicationFinish;
	}
	
	public void setPublicationFinish(String publicationFinish) {
		this.publicationFinish = publicationFinish;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

}
