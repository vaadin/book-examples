package com.vaadin.book.examples.advanced.spring;

import javax.servlet.annotation.WebServlet;

import com.vaadin.spring.server.SpringVaadinServlet;

@WebServlet(value = "/myspringuis/*", asyncSupported = true)
public class MySpringServlet extends SpringVaadinServlet {

}
