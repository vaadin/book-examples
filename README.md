Book Examples
=============

 Requirements:

  * Java 8 JDK installed in Eclipse
  * Apache TomEE 1.7 or newer
  * Vaadin Plugin for Eclipse

1. Cloning
----------

 You have two options:

 a) To clone the repository read-only from Github:

    $ git clone https://github.com/vaadin/book-examples

 b) To clone the repository from the read-write code review repository:

    $ git clone ssh://<username>@dev.vaadin.com:29418/book-examples.git

 Where <username> is your username in the review system.

 See the contribution instructions for information about registering:
 https://vaadin.com/wiki/-/wiki/Main/Contributing+Code
   

2. Importing
------------

 1. Create TomEE server in Eclipse
   - The project may like to have this before importing
 1.1. In the Servers view, right-click -> New -> Server
 1.2. In the "New Server" wizard
   - Select "Apache" -> "Tomcat 7.0 Server"
   - Set Server name: "Apache TomEE 1.7 at localhost"
   - Click Next
 1.3. In the Tomcat Servet step
   - Name: "Apache TomEE 1.7 at localhost"
   - Installation Directory: "/opt/apache-tomee-webprofile-1.7.1"
   - JRE: Workbench default or Java 8 JRE
   - Finish
 2. Import project
 2.1. File -> Import -> Existing projects intro workspace
   - Select the cloned project directory
   - Finish

3. Building
-----------

 1. To compile the main widget set, select
   Java Resources -> src/com.vaadin.book.widgetset/BookExamplesWidgetSet.gwt.xml
 2. Click "Compile Widgetset" in the toolbar (requires Vaadin Plugin for Eclipse)

 You can also compile the themes here, or let them be compiled on-the-fly.

4. Deployment
-------------

 Just add the project to the previously created TomEE server.

 You should then be able to run the application with:

    http://localhost:8080/book-examples-vaadin7/book
