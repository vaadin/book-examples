package com.vaadin.book.examples.advanced.spring;

import java.util.logging.Logger;

import javax.enterprise.inject.spi.InjectionPoint;

public class SpringProducers {
    // TODO Spring @Produces
    Logger createLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger( injectionPoint.getMember().getDeclaringClass().getName() );    
    }
}
