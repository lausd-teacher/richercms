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
    <link type="text/css" rel="stylesheet" href="Image.css">
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
    
    <script src="tiny_mce_jquery_3.3.9.1/jquery-1.4.2.min.js" type='text/javascript'></script>
    <script src="tiny_mce_jquery_3.3.9.1/jquery.tinymce.js" type='text/javascript'></script>
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
