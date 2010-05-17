<!doctype html>
<!-- The DOCTYPE declaration above will set the    -->
<!-- browser's rendering engine into               -->
<!-- "Standards Mode". Replacing this declaration  -->
<!-- with a "Quirks Mode" doctype may lead to some -->
<!-- differences in layout.                        -->

<html>
  <head>
  
  <!-- Taking into account the language of the browser -->
  <meta name="gwt:property" content="locale=<%=request.getLocale().getLanguage()%>">
	
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <!--                                                               -->
    <!-- Consider inlining CSS to reduce the number of requested files -->
    <!--                                                               -->
    <link type="text/css" rel="stylesheet" href="RicherCMS.css">
    <link type="text/css" rel="stylesheet" href="Wizard.css">
    <link type="text/css" rel="stylesheet" href="Main.css">
    <link type="text/css" rel="stylesheet" href="DndStyle.css">

    <!--                                           -->
    <!-- Any title is fine                         -->
    <!--                                           -->
    <title>RicherCMS</title>
    
    <!--                                           -->
    <!-- This script loads your compiled module.   -->
    <!-- If you add any GWT meta tags, they must   -->
    <!-- be added before this line.                -->
    <!--                                           -->
    <script type="text/javascript" language="javascript" src="RicherCMS/RicherCMS.nocache.js"></script>
    
    <script src='tiny_mce_3.2.7/tiny_mce_src.js' type='text/javascript'></script>
    <script language="javascript" type="text/javascript">
    tinyMCE.init({
        mode : "textareas",
        theme : "advanced",
        plugins : "table,advhr,advimage,advlink,emotions,insertdatetime,preview,searchreplace,"+
                  "print,contextmenu,inlinepopups",
        file_browser_callback : "tinyMCE.org_richercms_call.loadFileName",
        theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
        theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
        theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
        theme_advanced_toolbar_location : "top",
        theme_advanced_toolbar_align : "left",
        theme_advanced_path_location : "bottom",
        plugin_insertdate_dateFormat : "%Y/%m/%d",
        plugin_insertdate_timeFormat : "%H:%M:%S",
        extended_valid_elements : "a[name|href|target|title|onclick],img[class|style|src|border=0|alt|title|hspace"+
                                  "|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|"+
                                  "noshade],font[face|size|color|style],span[class|align|style]"
    });

    function richerCmsFileBrowser(field_name, url, type, win) {
    	// Do custom browser logic
    	// win.document.forms[0].elements[field_name].value = 'Ma valeur sur le type : ' + type;
    	//alert('ici' + tinyMCE.org_richercms_call.loadFileName);
    	win.document.forms[0].elements[field_name].value = tinyMCE.org_richercms_call.loadFileName();
    }

    </script>
    
  </head>

  <!--                                           -->
  <!-- The body can have arbitrary html, or      -->
  <!-- you can leave the body empty if you want  -->
  <!-- to create a completely dynamic UI.        -->
  <!--                                           -->
  <body>

    <!-- OPTIONAL: include this if you want history support -->
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
    
    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
      </div>
    </noscript>
  </body>
</html>
