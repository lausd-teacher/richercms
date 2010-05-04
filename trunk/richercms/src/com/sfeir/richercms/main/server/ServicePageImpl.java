package com.sfeir.richercms.main.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.main.client.PageService;
import com.sfeir.richercms.main.server.business.Page;
import com.sfeir.richercms.main.server.business.Root;
import com.sfeir.richercms.main.shared.BeanPage;
import com.sfeir.richercms.server.PMF;


/**
 * Implementation of all WebPages Services.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class ServicePageImpl extends RemoteServiceServlet implements PageService {
	
	private static final PersistenceManagerFactory Pmf = PMF.get();
	private Root root = null;
	
	private PersistenceManager getPersistenceManager() {
		return Pmf.getPersistenceManager();
	}

	
	@SuppressWarnings("unchecked")
	public List<BeanPage>  getPages() {
		
		//this.addBasesPage();
		
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
        ArrayList<BeanPage> lst = new ArrayList<BeanPage>();
	    try {
	    	tx.begin();
		        Query q = pm.newQuery(Root.class);
		        List<Root> roots = (List<Root>) q.execute();
		        
		        if(roots.size() == 0){
		        	this.root = new Root();
			    	List<Page> lst1 = new ArrayList<Page>();
			    	Page p = new Page(); p.setPageTitle("Main");
			    	pm.makePersistent(p);
			    	lst1.add(p);
			    	this.root.setPages(lst1);
		        	pm.makePersistent(this.root);
		        	lst.add(this.pageToBean(p));
		        } else {
		        	this.root = roots.get(0);
		        	for (Page page : this.root.getPages())
		        		lst.add(this.pageToBean(page));
		        }
	        tx.commit();
	        

        } finally {
			if (tx.isActive()) {
			    tx.rollback();
			}
        	//pm.close();
        }
        
        return lst;
	}
	
	@SuppressWarnings("unchecked")
	public BeanPage getMainPage(String translationKey) {
		
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
    	BeanPage mainPage = null;
	    try {
	    	tx.begin();
		        Query q = pm.newQuery(Root.class);
		        List<Root> roots = (List<Root>) q.execute();
		    tx.commit();
		        
		        if(roots.size() == 0){	
		        	tx.begin();
			        	this.root = new Root();
				    	List<Page> lst1 = new ArrayList<Page>();
				    	Page p = new Page(); p.setPageTitle("Main");
				    	lst1.add(p);
				    	this.root.setPages(lst1);
			        	pm.makePersistent(this.root);
		        	tx.commit();
		        	mainPage = this.pageToBean(p);

		        } else {
		        	this.root = roots.get(0);
		        	if(translationKey != null) {
			        	for (Page page : this.root.getPages())
			        		if(page.getEncodedKey().equals(translationKey)) {
			        			mainPage = this.pageToBean(page);
			        			break;
			        		}
		        	}else{
		        		tx.begin();
		        			Page p = duplicateMainPage(this.root.getPages().get(0), pm);
		        			pm.makePersistent(p);
		        			this.root.getPages().add(p);
		        			pm.makePersistent(this.root);
		        			
		        			p = this.encodingKey(p);
				    	tx.commit();
	        			mainPage = this.pageToBean(p);
		        	}
		        }       

        } finally {
			if (tx.isActive()) {
			    tx.rollback();
			}
        }
        
        return mainPage;
	}
	
	private Page encodingKey(Page p){
		Page tmpPage = p;
		int i = 1234;
		Key k;
		
		for(Page child : tmpPage.getSubPages()) {
			 k = KeyFactory.createKey(tmpPage.getEncodedKey(),i);
			 child.setEncodedKey(KeyFactory.keyToString(k));
			 for(Page subChild : child.getSubPages()){
				 k = KeyFactory.createKey(child.getEncodedKey(),i);
				 subChild.setEncodedKey(KeyFactory.keyToString(k));
				 i = i+1;
			 }
			 i = i+1;
		}
		return tmpPage;
	}
	
	private Page duplicateMainPage(Page p, PersistenceManager pm) {
		Page Duplicata = new Page(p.getBrowserTitle(),p.getPageTitle(), p.getUrlName(),
				 p.getDescription(), p.getKeyWord(), p.getPublicationStart(),
				 p.getPublicationFinish(), p.getContent());
		
		if((p.getSubPages()!= null) && (p.getSubPages().size() != 0)) {
			ArrayList<Page>lst = new ArrayList<Page>();
			for(Page childPage : p.getSubPages()) {
				lst.add(duplicateMainPage(childPage,pm));
			}
			Duplicata.setSubPages(lst);

			return Duplicata;
		}else {
			return Duplicata;
		}
	}
	
	public void addPage(BeanPage newPage) {
		int i = 1;
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
	    try {
	    	
	    	tx.begin();
		    	Page parentPage = pm.getObjectById(Page.class, newPage.getKey());
	    		Page p = this.BeanToPage(newPage);
	    		i = i + parentPage.getSubPages().size();
	    		Key k = new KeyFactory.Builder(Page.class.getSimpleName(), parentPage.getEncodedKey())
	    			.addChild(Page.class.getSimpleName(), i).getKey();
	    		
				p.setEncodedKey(KeyFactory.keyToString(k));
				
				parentPage.getSubPages().add(p);
    		tx.commit();
		    		
		    } finally {
				if (tx.isActive()) {
				    tx.rollback();
				}
		    }
	}
	
	public void updatePage(BeanPage p) {
		
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
		 try {

		    	tx.begin();
					Page page = pm.getObjectById(Page.class, p.getKey());
					page.setBrowserTitle(p.getBrowserTitle());
					page.setContent(p.getContent());
					page.setDescription(p.getDescription());
					page.setKeyWord(p.getKeyWord());
					page.setPageTitle(p.getPageTitle());
					page.setPublicationFinish(p.getPublicationFinish());
					page.setPublicationStart(p.getPublicationStart());
					page.setUrlName(p.getUrlName());
				tx.commit();
			 }
		 finally{
				if (tx.isActive()) {
				    tx.rollback();
				}
		 }
	}
	
	public void deletePage(String key) {
		
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
		 try {
		    	tx.begin();
					Page page = pm.getObjectById(Page.class, key);
					pm.deletePersistent(page);
				tx.commit();
			 }
		 finally{
			if (tx.isActive()) {
			    tx.rollback();
			}
		 }
	}
	
	public BeanPage getPage(String key) {
		
		Page page = null;
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
		 try {

		    	tx.begin();
			 		page = pm.getObjectById(Page.class, key);
			 	tx.commit();

			 }finally {
				if (tx.isActive()) {
				    tx.rollback();
				}
			 }
		 return this.pageToBean(page);
	}
	
	
	/**
	 * make a Bean Page with all information necessary to display correctly a Page
	 * @return the corresponding BeanPage
	 */
	public BeanPage pageToBean(Page p) {
		
		BeanPage bp = new BeanPage(p.getEncodedKey(), p.getBrowserTitle(),p.getPageTitle(), p.getUrlName(),
				 p.getDescription(), p.getKeyWord(), p.getPublicationStart(),
				 p.getPublicationFinish(), p.getContent());
		
		if((p.getSubPages()!= null) && (p.getSubPages().size() != 0)) {
			ArrayList<BeanPage>lst = new ArrayList<BeanPage>();
			for(Page childPage : p.getSubPages()) {
				lst.add(pageToBean(childPage));
			}
			bp.setSubPages(lst);
			return bp;
		}else {
			return bp;
		}
	}
	
	public Page BeanToPage(BeanPage bean) {
		return new Page(bean.getBrowserTitle(),bean.getPageTitle(), bean.getUrlName(),
				bean.getDescription(), bean.getKeyWord(), bean.getPublicationStart(),
				bean.getPublicationFinish(), bean.getContent());

	}
}