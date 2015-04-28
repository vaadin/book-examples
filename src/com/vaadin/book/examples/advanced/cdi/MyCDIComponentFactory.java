package com.vaadin.book.examples.advanced.cdi;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;

import com.vaadin.cdi.internal.CDIUtil;
import com.vaadin.ui.Component;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.declarative.DesignContext;

// BEGIN-EXAMPLE: advanced.cdi.navigation
/*
 *  This custom ComponentFactory allows the Design 
 *  to inject CDI bean components to a design root.
 */
@Singleton
@Startup
class MyCDIComponentFactory extends Design.DefaultComponentFactory {
    private static final long serialVersionUID = -8749567585647004426L;

    @PostConstruct
    public void setup() {
        Design.setComponentFactory(this);
    }
    
    @Override
    public Component createComponent(String fullyQualifiedClassName,
                                     DesignContext context) {
        Class<?> cls = resolveComponentClass(fullyQualifiedClassName,
                                             context);

        // Try to get the component with CDI
        BeanManager beanManager = CDIUtil.lookupBeanManager();
        Set<Bean<?>> beans = beanManager.getBeans(cls,
                               new AnnotationLiteral<Any>() {
            private static final long serialVersionUID = -8450753359910452915L;
        });
        Bean<?> bean = (Bean<?>) beanManager.resolve(beans);
        if (bean == null)
            return super.createComponent(fullyQualifiedClassName,
                                         context);

        return (Component) beanManager.getReference(bean, cls,
                           beanManager.createCreationalContext(bean));
    }
}
// END-EXAMPLE: advanced.cdi.navigation
