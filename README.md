Book Examples
=============

Source code for the code examples in the Book of Vaadin and some more.

Requirements:

  * Eclipse IDE for Java EE Developers
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

 Where &lt;username&gt; is your username in the review system.

 See the contribution instructions for information about registering:
 https://vaadin.com/wiki/-/wiki/Main/Contributing+Code. Also note the
 required repository configuration for submitting code for review.

2. Importing
------------

You should create the server before importing, as the project files may refer
to it as a compilation target. Not sure if it is relevant

1. Create TomEE server in Eclipse
  1. In the **Servers** view, right-click &#8594; **New** &#8594; **Server**
  2. In the "New Server" wizard:
    - Select **Apache** &#8594;  **Tomcat 7.0 Server**
    - Set **Server name**: "Apache TomEE 1.7 at localhost"
    - Click **Next**
  3. In the **Tomcat Servet** step:
    - **Name**: "Apache TomEE 1.7 at localhost"
    - **Installation Directory**: "/opt/apache-tomee-webprofile-1.7.1"
    - **JRE**: Workbench default or Java 8 JRE
    - Click **Finish**
2. Import project
  1. **File** &#8594; **Import** &#8594; **Existing projects intro workspace**
  2. In the import wizard:
    - Select the cloned project directory
    - Click **Finish**

3. Building
-----------

 1. Some Vaadin add-ons may require a snapshot version, which must be built and
    installed to the local Maven repository.

    For example, for Vaadin Charts:
```
    $ git clone https://github.com/vaadin/charts
    $ cd charts
    $ mvn install -DskipTests
```
 2. Refresh Ivy
   * Right-click on project, select **Ivy** &#8594; **Refresh**

 3. Install license keys for commercial Vaadin add-ons (Charts, etc.)

 4. Compile the widget set
   1. Select **Java Resources** &#8594; src/com.vaadin.book.widgetset/BookExamplesWidgetSet.gwt.xml
   2. Click **Compile Widgetset** in the toolbar (requires Vaadin Plugin for Eclipse)

 You can also compile the themes here, or let them be compiled on-the-fly.

4. Deployment
-------------

 Just add the project to the previously created TomEE server.

 You should then be able to run the application with:

    http://localhost:8080/book-examples-vaadin7/book

5. Development
----------------

 Things you should notice:

 * The examples are formatted by hand, so you **must not** have any automatic
   code formatting enabled in your Eclipse default settings.

6. Contributions
----------------

 Contributions must go through the Gerrit code review system.

  * You **must** install the `commit-msg` hook as instructed
  * You **must** push to review as instructed
