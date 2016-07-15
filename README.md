Book Examples
=============

Source code for the code examples in the Book of Vaadin and some more.

Requirements:

  * Java 8 JDK installed
  * Maven 3 or higher installed

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


3. Building and Deployment
-----------

 To build the project after cloning the repository:

 	$ cd book-examples
	$ mvn install

 After building the project you can deploy it locally with:

 	$ mvn jetty:run

 You should then be able to access the application at:

    http://localhost:8080/book/

4. Development
----------------

 Things you should notice:

 * The examples are formatted by hand, so you **must not** have any automatic
   code formatting enabled in your Eclipse default settings.

5. Contributions
----------------

 Contributions must go through the Gerrit code review system.

  * You **must** install the `commit-msg` hook as instructed
  * You **must** push to review as instructed
