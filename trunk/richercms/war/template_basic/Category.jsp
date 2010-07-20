<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.sfeir.richercms.site.template.template_basic.*" %>
<%@ page import = "com.sfeir.richercms.page.shared.*, java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<link href="/template_basic/style.css" rel="stylesheet" type="text/css" />
	
	<% // initialize template class
		String siteSufix = "/site";
		BeanArboPage p = (BeanArboPage)request.getAttribute("page");
		TemplateBasic template = new TemplateBasic(p);
	%>
</head>
<body>
	<!-- header -->
    <div id="logo"><a href="<%= siteSufix+template.getPath()%>"><%=template.getPageTitle()%></a></div>
    <div id="header">
    	<div id="left_header"></div>
        <div id="right_header"></div>
  </div> 
  <div id="menu">
        	<ul>
        		<% 
        			List<LinkPage> categs = template.getAllCategory();
	        	    for (LinkPage categ : categs) {
	        	        %>
	        	        <li><a href="<%= siteSufix+categ.getPath()%>"><%= categ.getName()%></a></li>
	        	        <%
	        	    }
        		%>
          </ul>
      </div>
    <!--end header -->
    <!-- main -->
    <div id="content">
    	<div id="content_top">
        	<div id="content_top_left"></div>
            <div id="content_top_right"></div>
        </div>
      <div id="content_body">
       	  <div id="sidebar">
            <div id="sidebar_top"></div>
            <div id="sidebar_body">
            <h1>Sub Categories</h1>
              <ul>
                <li><a href="#">List item</a> (1)</li>
              </ul>
              <h1>Article</h1>
              <ul>
                <% 
        			List<LinkPage> articles = template.getChildArticle();
	        	    for (LinkPage article : articles) {
	        	        %>
	        	        <li><a href="<%= siteSufix+article.getPath()%>"><%= article.getName()%></a></li>
	        	        <%
	        	    }
        		%>
              </ul>
              </div>
                <div id="sidebar_bottom"></div>
          </div>
            <div id="text">
            <div id="text_top">
            	<div id="text_top_left"></div>
                <div id="text_top_right"></div>
            </div>
            <div id="text_body">
              <h1><span>Welcome</span></h1>
                <p><strong>Darkit </strong> is a free template released by <a href="http://www.realitysoftware.ca">Reality Software</a> under the <a href="http://creativecommons.org/licenses/by/3.0/">Creative Commons Attribution 3.0</a> license, which means you can use it in any way you want provided you keep the link to the author intact.</p>
                <ul>
                  <li>Lorem ipsum dolor sit amet.</li>
                  <li>Consectetuer adipiscing elit.</li>
                  <li>Maecenas ac lacus. Etiam quis ante.</li>
                  <li>Nullam accumsan metus sit amet est.</li>
                  <li>Nullam diam. Nunc ac ipsum at nisl pretium congue.</li>
                </ul>
                <h1><span>Lorem ipsum dolor</span></h1>
              <p><strong>Lorem ipsum</strong> dolor sit amet, consectetuer adipiscing elit. Phasellus ornare condimentum sem. Nullam a eros. Vivamus vestibulum hendrerit arcu. Integer a orci. Morbi nonummy semper est. Donec at risus sed velit porta dictum. Maecenas condimentum orci aliquam nunc. Morbi nonummy tellus in nibh. Suspendisse orci eros, dapibus at, ultrices at, egestas ac, tortor. Suspendisse fringilla est id erat. Praesent et libero. Proin nisi felis, euismod a, tempus varius, elementum vel, nisl. Fusce non magna sit amet enim suscipit feugiat. Fusce et leo. Morbi nonummy tellus in nibh. Suspendisse orci eros, dapibus at, ultrices at, egestas ac, tortor. Suspendisse fringilla est id erat. Praesent et libero. Proin nisi felis, euismod a, tempus varius, elementum vel, nisl. Fusce non magna sit amet enim suscipit feugiat. Fusce et leo.</p>
                            <div id="foot_text">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nunc nec mi quis felis ullamcorper adipiscing. Integer elementum tellus id purus. Vestibulum diam. 
Cras congue nulla ac turpis ultrices ullamcorper. Mauris lobortis. Quisque consectetuer massa eu enim tristique accumsan. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nunc nec mi quis felis ullamcorper adipiscing. Integer elementum tellus id purus. Vestibulum diam.</div>
            </div>
                <div id="text_bottom">
                	<div id="text_bottom_left"></div>
                    <div id="text_bottom_right"></div>
                </div>
          </div>
      </div>
        <div id="content_bottom">
        	<div id="content_bottom_left"></div>
            <div id="content_bottom_right"></div>
        </div>
    </div>
    <!-- end main -->
    <!-- footer -->
    <div id="footer">
    <div id="left_footer">&copy; Copyright 2008 <b>Darkit</b> design </div>
    <div id="right_footer">

<!-- Please do not change or delete this link. Read the license! Thanks. :-) -->
Design by <a href="http://www.realitysoftware.ca" title="Website Design">Reality Software</a>

    </div>
    </div>
    <!-- end footer -->
</body>
</html>