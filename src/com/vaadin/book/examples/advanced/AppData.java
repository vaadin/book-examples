package com.vaadin.book.examples.advanced;


// BEGIN-EXAMPLE: advanced.global.threadlocal
/** Holds data for one user session. */
/* TODO Vaadin 7: Is this anymore useful?
public class AppDataSerializable {
    private static final long serialVersionUID = -1408116804626243626L;

    private Locale locale;   // Current locale
    private ResourceBundle bundle;
    
    private String userData; // Trivial data model for the user
    
    private Application app; // For distinguishing between apps

    private static ThreadLocal<AppData> instance =
        new ThreadLocal<AppData>();
    
    public AppData(Application app) {
        this.app = app;

        // It's usable from now on in the current request
        instance.set(this);
    }

    @Override
    public void transactionStart(Application application,
                                 Object transactionData) {
        // Set this data instance of this application
        // as the one active in the current thread. 
        if (this.app == application)
            instance.set(this);
    }

    @Override
    public void transactionEnd(Application application,
                               Object transactionData) {
        // Clear the reference to avoid potential issues
        if (this.app == application)
            instance.set(null);
    }

    public static void initLocale(Locale locale,
                                  String bundleName) {
        instance.get().locale = locale;
        instance.get().bundle =
            ResourceBundle.getBundle(bundleName, locale);
    }
    
    public static Locale getLocale() {
        return instance.get().locale;
    }

    public static String getMessage(String msgId) {
        return instance.get().bundle.getString(msgId);
    }

    public static String getUserData() {
        return instance.get().userData;
    }

    public static void setUserData(String userData) {
        instance.get().userData = userData;
    }
}
*/
// END-EXAMPLE: advanced.global.threadlocal
