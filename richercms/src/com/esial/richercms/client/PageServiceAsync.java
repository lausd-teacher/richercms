package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PageServiceAsync {
	public void addPage(String browser_title,String page_title, String url_name,String 
			description, String content, AsyncCallback<Void> async);
}
