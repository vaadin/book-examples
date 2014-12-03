<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>This is a JSP page</title>

  <!-- Define the application configuration -->
  <script type="text/javascript">
    var vaadin = {
      vaadinConfigurations: {
        helloworld: {
          appUri:'/book-examples/helloworld',
          pathInfo: '/',
          themeUri:'/book-examples/VAADIN/themes/book-examples',
          versionInfo : {}}
      }};
  </script>
  
  <!-- Load the widget set, that is, the Client-Side Engine -->
  <script language='javascript' src='/book-examples/VAADIN/widgetsets/com.vaadin.book.widgetset.BookExamplesWidgetSet/com.vaadin.book.widgetset.BookExamplesWidgetSet.nocache.js'></script>

  <!-- Load the style sheet -->
  <link rel="stylesheet"
        type="text/css"
        href="/book-examples/VAADIN/themes/book-examples/styles.css"/>

</head>
<body>
  <!-- GWT requires an invisible history frame is needed for -->
  <!-- page/fragment history in the browser                  -->
  <iframe tabIndex="-1" id="__gwt_historyFrame" style="position:absolute;width:0;height:0;border:0;overflow:hidden;" src="javascript:false"></iframe>

  <h1>This is a JSP page</h1>
  
  <p>Time is now <%= new java.util.Date() %>.</p>
  
  <p>And below is a Vaadin app.</p>
  
  <!-- So here comes the div element in which the Vaadin -->
  <!-- application is embedded.                          -->
  <div id="helloworld" style="border: 2px solid green;"></div>
  
  <p><%
  for (int i=0; i<3; i++)
      out.println("Isn't it nice?");
    %></p>
  
</body>
</html>