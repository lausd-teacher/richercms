package com.sfeir.richercms.page.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SuppressWarnings("serial")
public class BeanArboPage implements Serializable{
	
    private Long id;
	List<BeanTranslationPage> translation;
	private Date publicationStart;
	private Date publicationFinish;
	private Date creationDate; 
	private Long TemplateId;
	private List<Long> tagsId;
	private Long idUserInModif;
	private Long parentId; // Parent id
	
	public BeanArboPage() {
		super();
		this.id = null;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
		this.creationDate = new Date();
		this.idUserInModif = null;
		this.tagsId = new ArrayList<Long>();
		this.TemplateId = null;
		this.parentId = null;
	}
	
	public BeanArboPage(Long id) {
		super();
		this.id = id;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
		this.creationDate = new Date();
		this.idUserInModif = null;
		this.tagsId = new ArrayList<Long>();
		this.TemplateId = null;
		this.parentId = null;
	}
	
	public BeanArboPage(Long id, Date publicationStart, Date publicationFinish, Date creationDate,
			Long templateId, List<Long> tagList, Long parentId) {
		super();
		this.id = id;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = publicationFinish;
		this.publicationStart = publicationStart;
		this.creationDate = creationDate;
		this.idUserInModif = null;
		this.TemplateId = templateId;
		this.tagsId = tagList;
		this.parentId = parentId;
	}

	
	public BeanArboPage(Long id, List<BeanTranslationPage> translation,
			List<Long> idLinkedImages, Date publicationStart,
			Date publicationFinish, Date creationDate, Long templateId,
			List<Long> tagsId, Long idUserInModif, Long parentId) {
		super();
		this.id = id;
		this.translation = translation;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.creationDate = creationDate;
		this.TemplateId = templateId;
		this.tagsId = tagsId;
		this.idUserInModif = idUserInModif;
		this.parentId = parentId;
	}
	
	public BeanArboPage(Date publicationStart, Date publicationFinish,
			List<BeanTranslationPage> translation) {
		
		super();
		this.id = null;
		this.translation = translation;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.creationDate = new Date();
		this.TemplateId = null;
		this.tagsId = new ArrayList<Long>();
		this.TemplateId = null;
		this.idUserInModif = null;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<BeanTranslationPage> getTranslation() {
		return translation;
	}
	
	public void setTranslation(List<BeanTranslationPage> translation) {
		this.translation = translation;
	}

	public Date getPublicationStart() {
		return publicationStart;
	}

	public void setPublicationStart(Date publicationStart) {
		this.publicationStart = publicationStart;
	}

	public Date getPublicationFinish() {
		return publicationFinish;
	}

	public void setPublicationFinish(Date publicationFinish) {
		this.publicationFinish = publicationFinish;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getIdUserInModif() {
		return idUserInModif;
	}

	public void setIdUserInModif(Long idUserInModif) {
		this.idUserInModif = idUserInModif;
	}

	public Long getTemplateId() {
		return TemplateId;
	}

	public void setTemplateId(Long templateId) {
		TemplateId = templateId;
	}

	public List<Long> getTagsId() {
		return tagsId;
	}

	public void setTagsId(List<Long> tagsId) {
		this.tagsId = tagsId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
