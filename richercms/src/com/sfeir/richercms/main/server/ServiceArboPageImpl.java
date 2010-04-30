package com.sfeir.richercms.main.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.main.client.ArboPageService;
import com.sfeir.richercms.main.server.business.ArboPage;
import com.sfeir.richercms.main.server.business.Root;
import com.sfeir.richercms.main.server.business.RootArbo;
import com.sfeir.richercms.main.server.business.TranslationPage;
import com.sfeir.richercms.main.shared.BeanArboPage;
import com.sfeir.richercms.main.shared.BeanTranslationPage;
import com.sfeir.richercms.server.PMF;

@SuppressWarnings("serial")
public class ServiceArboPageImpl  extends RemoteServiceServlet implements ArboPageService {

	
	private static final PersistenceManagerFactory Pmf = PMF.get();
	private RootArbo root = null;
	
	private PersistenceManager getPersistenceManager() {
		return Pmf.getPersistenceManager();
	}
	
	public void addArboPage(BeanArboPage newArboPage, String parentKey) {
		
		PersistenceManager pm = getPersistenceManager();
		
    	Transaction tx = pm.currentTransaction();
		 try {
		    	tx.begin();
		    		ArboPage parentPage = pm.getObjectById(ArboPage.class, parentKey);
		    		ArboPage nAP = this.BeanToArboPage(newArboPage);
		    		pm.makePersistent(nAP);
		    		parentPage.getIdChildArboPage().add(nAP.getEncodedKey());
				tx.commit();
			 }
		 finally{
			if (tx.isActive()) {
			    tx.rollback();
			}
		 }
		
	}

	public void deleteArboPage(String key) {
		
		PersistenceManager pm = getPersistenceManager();
		
    	Transaction tx = pm.currentTransaction();
		 try {
		    	tx.begin();
		    	ArboPage arboPage = pm.getObjectById(ArboPage.class, key);
					pm.deletePersistent(arboPage);
				tx.commit();
			 }
		 finally{
			if (tx.isActive()) {
			    tx.rollback();
			}
		 }
	}

	public BeanArboPage getArboPage(String key) {
		ArboPage arboPage = null;
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
		 try {

		    	tx.begin();
		    	arboPage = pm.getObjectById(ArboPage.class, key);
			 	tx.commit();

			 }finally {
				if (tx.isActive()) {
				    tx.rollback();
				}
			 }
		 return this.arboPageToBean(arboPage);
	}

	@Override
	public List<BeanArboPage> getChildPages(String ParentKey) {
		ArboPage parentArboPage = null;
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
    	ArrayList<BeanArboPage> lst = new ArrayList<BeanArboPage>();
		 try {
		    	tx.begin();
		    		parentArboPage = pm.getObjectById(ArboPage.class, ParentKey);
				 	ArboPage childArboPage;
					for (String key : parentArboPage.getIdChildArboPage()){
						childArboPage = pm.getObjectById(ArboPage.class, key);
						lst.add(this.arboPageToBean(childArboPage));
					}
				tx.commit();
			 
		 }finally {
				if (tx.isActive()) {
				    tx.rollback();
				}
		 }
		 return lst;
	}

	@SuppressWarnings("unchecked")
	public BeanArboPage getMainArboPage() {
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
    	try {
	    	tx.begin();
		        Query q = pm.newQuery(Root.class);
		        List<RootArbo> roots = (List<RootArbo>) q.execute();
		        if(roots.size() == 0){
		        	this.root = new RootArbo();
		        	List<TranslationPage> lst1 = new ArrayList<TranslationPage>();
		        	TranslationPage tp = new TranslationPage();
		        	tp.setPageTitle("main");
			    	lst1.add(tp);
			    	this.root.setTranslation(lst1);
		        	pm.makePersistent(this.root);
		        }else {
		        	this.root = roots.get(0);
		        }
	        tx.commit();
        } finally {
			if (tx.isActive()) {
			    tx.rollback();
			}
        	pm.close();
        }
		return this.arboRootToBean(this.root);
	}

	@Override
	public void updateArboPage(BeanArboPage p) {
		// TODO Auto-generated method stub
		
	}
	
	public BeanArboPage arboPageToBean(ArboPage ap){
		BeanArboPage bap = new BeanArboPage(ap.getEncodedKey());
		ArrayList<BeanTranslationPage> lst = new ArrayList<BeanTranslationPage>();
		for(TranslationPage tp : ap.getTranslation()){
			lst.add(translationPageToBean(tp));}
		bap.setTranslation(lst);
		return bap;
	}
	
	public BeanArboPage arboRootToBean(RootArbo ar){
		BeanArboPage bap = new BeanArboPage(ar.getEncodedKey());
		ArrayList<BeanTranslationPage> lst = new ArrayList<BeanTranslationPage>();
		for(TranslationPage tp : ar.getTranslation()){
			lst.add(translationPageToBean(tp));}
		bap.setTranslation(lst);
		return bap;
	}
	
	public BeanTranslationPage translationPageToBean(TranslationPage tp) {
		return new BeanTranslationPage(tp.getEncodedKey(), tp.getBrowserTitle(),tp.getPageTitle(), tp.getUrlName(),
				 tp.getDescription(), tp.getKeyWord(), tp.getPublicationStart(),
				 tp.getPublicationFinish(), tp.getContent());
	}
	
	public ArboPage BeanToArboPage(BeanArboPage bAP){
		ArboPage ap = new ArboPage();
		ArrayList<TranslationPage> lst = new ArrayList<TranslationPage>();
		
		for(BeanTranslationPage bTp : bAP.getTranslation()){
			lst.add(BeanToTranslationPage(bTp));}
		ap.setTranslation(lst);
		return ap;
	}
	
	public TranslationPage BeanToTranslationPage(BeanTranslationPage bTp){
		return new TranslationPage(bTp.getBrowserTitle(),bTp.getPageTitle(), bTp.getUrlName(),
				bTp.getDescription(), bTp.getKeyWord(), bTp.getPublicationStart(),
				bTp.getPublicationFinish(), bTp.getContent());
	}

}
