package com.vaadin.book.examples.misc;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class I18nPrototype extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 6359251510840841085L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    // BEGIN-EXAMPLE: misc.prototypes.i18n.basic
    public class TranslatableTextField extends TextField {
        private static final long serialVersionUID = 413745914234666630L;
        
        TranslatedText captionTranslation;

        public TranslatableTextField(String caption) {
            super(caption);
        }

        public TranslatableTextField(TranslatedText caption) {
            captionTranslation = caption;
        }
        
        @Override
        public void attach() {
            super.attach();
            localeChange();
        }
        
        void localeChange() {
            getLocale();
        }
    }
    
    public class TranslatedText {
        private String   keyString;
        private int      keyValue;
        private Object[] parameters;
        
        public TranslatedText(String key) {
            this.keyString = key;
        }
        
        public TranslatedText(String key, Object...parameters) {
            this.keyString = key;
            this.parameters = parameters;
        }
        
        public TranslatedText(int key) {
            this.keyValue   = key;
        }
        
        public TranslatedText(int key, Object...parameters) {
            this.keyValue   = key;
            this.parameters = parameters;
        }
        
        public String getKeyString() {
            return keyString;
        }

        public int getKeyValue() {
            return keyValue;
        }
    }

    public class TranslatedPluralText extends TranslatedText {
        private String   key2;
        private Object[] parameters;

        public TranslatedPluralText(String key1, String key2, Object...parameters) {
            super(key1, parameters);
            
            this.key2 = key2;
            this.parameters = parameters;
        }
    }
    
    void basic(VerticalLayout layout) {
        {
            // Basic use
            TranslatableTextField tf = new TranslatableTextField(new TranslatedText("Hello"));
            layout.addComponent(tf);
        }

        {
            // Parameterized calls
            String name   = "Venus";
            int    number = 2;
            TranslatableTextField tf = new TranslatableTextField(new TranslatedText("Planet %1 is number %2.", name, number));
            layout.addComponent(tf);
        }

        {
            // Singular and plural forms
            for (int i=0; i<5; i++) {
                TranslatableTextField tf = new TranslatableTextField(new TranslatedPluralText("%1 line", "%1 lines", i));
                layout.addComponent(tf);
            }
        }
    }
    // END-EXAMPLE: misc.prototypes.i18n.basic
}
