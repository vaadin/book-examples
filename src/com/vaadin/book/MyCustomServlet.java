package com.vaadin.book;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;

public class MyCustomServlet extends VaadinServlet
    implements SessionInitListener, SessionDestroyListener {
    private static final long serialVersionUID = -2419953872823694670L;
    private static final transient Logger logger = Logger.getLogger(MyCustomServlet.class.getName());
    
    public MyCustomServlet() {
        LogFormatter formatter = new LogFormatter();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        logger.addHandler(handler);
    }
    
    @Override
    protected void servletInitialized()
            throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event)
            throws ServiceException {
        logger.info("Session started: " +
            event.getSession().hashCode());
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        logger.info("Session destroyed: " +
            event.getSession().hashCode());
    }
    
    // BEGIN-EXAMPLE: advanced.urifragment.basic
    /** Provide crawlable content */
    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {
        String fragment = request
            .getParameter("_escaped_fragment_");
        if (fragment != null) {
            response.setContentType("text/html");
            Writer writer = response.getWriter();
            writer.append("<html><body>"+
                "<p>Here is some crawlable "+
                "content about " + fragment + "</p>");
            
            // A list of all crawlable pages
            String items[] = {"mercury", "venus",
                              "earth", "mars"};
            writer.append("<p>Index of all content:</p><ul>");
            for (String item: items) {
                String url = request.getContextPath() +
                    request.getServletPath() +
                    request.getPathInfo() + "#!" + item;
                writer.append("<li><a href='" + url + "'>" +
                              item + "</a></li>");
            }
            writer.append("</ul></body>");
        } else
            super.service(request, response);
    }
    // END-EXAMPLE: advanced.urifragment.basic
}
