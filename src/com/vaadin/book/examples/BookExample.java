package com.vaadin.book.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class BookExample extends CaptionedExampleItem {
    private static final long serialVersionUID = 8205608518115765928L;

    /** Example bundle class that contains the example */
    public Class<?> exclass;

    /** Current instance of the bundle */
    AnyBookExampleBundle instance = null;

    /** A more or less short description of the example */
    protected String description;

    /** All Java source fragments associated with the example */
    private List<SourceFragment> sourceFragments;

    /** All CSS source fragments associated with the example */
    private List<SourceFragment> cssFragments;

    boolean loaded = false;

    /**
     * Creates a book example object.
     * 
     * You must read the example data with {@link BookExample#readExample(File)
     * readExample()} before you can access the data.
     * 
     * @param exampleId
     * @param shortName
     * @param exclass
     */
    public BookExample(String exampleId, String shortName, Class<?> exclass) {
        super(exampleId, shortName);

        this.exclass = exclass;
    }

    public String getDescription() {
        return description;
    }

    public List<SourceFragment> getSourceFragments() {
        return sourceFragments;
    }

    public List<SourceFragment> getCssFragments() {
        return cssFragments;
    }

    /**
     * Loads the example.
     * 
     * Reads the source and CSS files and extracts any source fragments
     * associated with the example.
     **/
    synchronized public void loadExample(File baseDirectory) {
        if (loaded)
            return;

        try {
            // Use "_" instead of "-" in descriptions
            String javaContext = context.replace('-', '_');

            // Try to get old-style description
            java.lang.reflect.Field descField = null;
            try {
                descField = exclass.getField(javaContext + "Description");
                description = (String) descField.get(null);
            } catch (NoSuchFieldException e) {
                // These are OK and expected for most examples
            }
            
            // Try to get a description given with @Description annotation
            try {
                Method method = exclass.getMethod(context, VerticalLayout.class);
                Description descAnnotation = method.getDeclaredAnnotation(Description.class);
                if (descAnnotation != null)
                    description = descAnnotation.value();
            } catch (NoSuchMethodException e) {
                // It fails for all private methods in the old-style example bundles
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Read the source code
        String classname = exclass.getName();
        sourceFragments = readSourcesFromClass(classname, context, exampleId);

        // Load associated CSS
        // TODO Reading it here causes parsing it for every example!
        String csspath = baseDirectory.toString() + "/VAADIN/themes/book-examples/book-examples.scss";
        File cssfile = new File(csspath);
        FileInputStream cssins;
        try {
            cssins = new FileInputStream(cssfile);

            // TODO Assume that there is only one CSS fragment
            cssFragments = readSourceFromStream(cssins, exampleId, csspath);
            // System.out.println("Read " + cssFragments.size() + " CSS fragments");
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open book-examples.scss: " + e.getMessage());
        }

        loaded = true;
    }

    public Component invokeExample() {
        try {
            // Instantiate and initialize the example
            instance = (AnyBookExampleBundle) exclass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // See if we can call the particular example directly
        if (instance instanceof BookExampleBundle) {
            // Handles init by itself
            ((BookExampleBundle)instance).init(context);
            return (Component) instance;
        } else {
            try {
                final Method method = instance.getClass().getMethod(context, VerticalLayout.class);
                
                VerticalLayout layout = new VerticalLayout();
                method.invoke(instance, layout);
                return layout;
            } catch (NoSuchMethodException e) {
                return new Label("Example library problem: there is no such example \"" + context + "\" in the " + exclass.getSimpleName() + " bundle. It must be public, get a VerticalLayout as parameter, etc.");
            } catch (Exception e) {
                String cause = "<b>Example failed because:</b><br/>";

                // Find the final cause
                for (Throwable t = e; t != null; t = t.getCause()) { // We're at final cause
                    cause += t.getClass().getName();
                    if (t.getMessage() != null)
                        cause += " - " + t.getMessage();
                    cause += "<br/>";
                }
                
                e.printStackTrace();
                
                return new Label(cause, ContentMode.HTML);
            }
        }
    }

    /**
     * Reads source file for a given class and extracts examples for the given
     * location.
     * 
     * The source file can contain one or more example fragments
     * 
     * @param className
     * @param context
     * @param exampleId
     *            the dot-delimited example identifier
     * @return
     */
    private List<SourceFragment> readSourcesFromClass(String className,
        String context, String exampleId) {
        // Some exceptions for specific special examples
        if ("com.vaadin.book.examples.intro.HelloWorldExample".equals(className))
            className = "com.vaadin.book.applications.HelloWorld";

        // Try Java
        String srcname = "/" + className.replace('.', '/') + ".java";
        InputStream ins = getClass().getResourceAsStream(srcname);
        
        // Try Scala
        if (ins == null) {
            srcname = "/" + className.replace('.', '/') + ".scala";
            BookExamplesUI.getLogger().info("Trying to open " + srcname + " for reading.");
            ins = getClass().getResourceAsStream(srcname);
            if (ins == null)
                BookExamplesUI.getLogger().warning("Couldn't even open " + srcname + " for reading.");
        }

        if (ins != null)
            return readSourceFromStream(ins, exampleId, srcname);
        else
            BookExamplesUI.getLogger().warning("Error: unable to open " + srcname + " for reading.");

        // Empty list
        return new ArrayList<SourceFragment>();
    }

    /**
     * Reads a stream that contains source content.
     * 
     * If the source content has multiple fragments or recursive file inclusion
     * statements, the return value will contain more than one fragment.
     **/
    private List<SourceFragment> readSourceFromStream(InputStream ins,
        String exampleId, String srcname) {
        // Read the proper source fragment from the stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        StringBuffer src = new StringBuffer();
        int state = 0;
        int lineNo = 0;

        Pattern beginPattern = Pattern.compile("\\s*/[/*] BEGIN-EXAMPLE:\\s+" + exampleId.replace(".", "\\.") + "\\s*(\\*/)?\\s*$");
        Pattern endPattern   = Pattern.compile("\\s*/[/*] END-EXAMPLE:\\s+" + exampleId.replace(".", "\\.") + "\\s*(\\*/)?\\s*$");
        
        List<SourceFragment> fragments = new ArrayList<SourceFragment>();
        SourceFragment fragment = new SourceFragment(srcname);
        try {
            for (String line = reader.readLine(); null != line; line = reader.readLine()) {
                line = line.replace("\t", "    ");
                lineNo++;

                switch (state) {
                case 0: // Not reading a fragment
                    if (beginPattern.matcher(line).matches()) {
                        state = 1;
                        fragment.setFragmentPos(lineNo);
                    } else if (line.indexOf("EXAMPLE-REF:") != -1) {
                        // Reference to another source file
                        Pattern p = Pattern.compile("\\s*// EXAMPLE-REF:\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"); //
                        Matcher m = p.matcher(line);
                        if (m.matches() && m.groupCount() == 3 && m.group(1).equals(
                            exampleId)) {
                            String classname = m.group(2);
                            String reflocation = m.group(3);
                            List<SourceFragment> subs = readSourcesFromClass(
                                classname, null, reflocation);
                            if (subs != null)
                                for (SourceFragment subfragment : subs)
                                    fragments.add(subfragment);
                        }
                    } else if (line.indexOf("EXAMPLE-FILE:") != -1) {
                        // Reference to another source file
                        Pattern p = Pattern.compile("\\s*// EXAMPLE-FILE:\\s+(\\S+)\\s+(\\S+)\\s*"); //
                        Matcher m = p.matcher(line);
                        if (m.matches() && m.groupCount() == 2 && m.group(1).equals(
                            exampleId)) {
                            String filename = m.group(2);
                            SourceFragment newfragment = readFileFromClassPath(filename);
                            if (newfragment != null)
                                fragments.add(newfragment);
                        }
                    } else if (line.indexOf("EXAMPLE-APPFILE:") != -1) {
                        // Reference to another source file
                        Pattern p = Pattern.compile("\\s*// EXAMPLE-APPFILE:\\s+(\\S+)\\s+(\\S+)\\s*"); //
                        Matcher m = p.matcher(line);
                        if (m.matches() && m.groupCount() == 2 && m.group(1).equals(
                            exampleId)) {
                            String filename = m.group(2);
                            SourceFragment newfragment = readFileFromApplication(filename);
                            if (newfragment != null)
                                fragments.add(newfragment);
                        }
                    }
                    break;
                case 1: // Reading fragment
                    if (endPattern.matcher(line).matches()) {
                        state = 0;
                    } else if (line.indexOf("END-EXAMPLE: ") != -1)
                        ; // Skip non-matching fragment tags
                    else if (line.indexOf("BEGIN-EXAMPLE: ") != -1)
                        ; // Skip non-matching fragment tags
                    else if (line.indexOf("EXAMPLE-REF:") != -1) {
                        // Reference to another source file
                        Pattern p = Pattern.compile("\\s*// EXAMPLE-REF:\\s+(\\S+)(\\s+\\S+)?\\s*"); //
                        Matcher m = p.matcher(line);
                        if (m.matches() && m.groupCount() >= 1) {
                            String classname = m.group(1);

                            String reflocation; // Optional
                            if (m.groupCount() == 2 && m.group(2) != null)
                                reflocation = m.group(2).trim();
                            else
                                reflocation = exampleId;

                            List<SourceFragment> subs = readSourcesFromClass(
                                classname, null, reflocation);
                            if (subs != null)
                                for (SourceFragment subfragment : subs)
                                    fragments.add(subfragment);
                        }
                    } else if (line.indexOf("// FORUM:") != -1) {
                        // Pick up Forum link
                        String link = line.substring(line.indexOf(":") + 1).trim();
                        fragment.getForumLinks().add(link);
                    } else if (line.indexOf("// BOOK:") != -1) {
                        // Pick up Book link
                        String link = line.substring(line.indexOf(":") + 1).trim();
                        fragment.getBookRefs().add(link);
                    } else if (line.indexOf("// KB:") != -1) {
                        // Pick up Book link
                        String ref = line.substring(line.indexOf(":") + 1).trim();
                        fragment.getKbRefs().add(fragment.new Ref(ref));
                    } else if (line.indexOf("serialVersionUID") != -1)
                        state = 3;
                    else {
                        src.append(line);
                        src.append("\n");
                    }
                    break;
                case 3: // Skip one line
                    if (line.indexOf("END-EXAMPLE: " + exampleId) != -1)
                        state = 0;
                    else
                        state = 1;
                    break;
                default:
                    break;
                }
            }
        } catch (IOException e) {
            BookExamplesUI.getLogger().info("Could not read example source code because: " + e.getMessage());
            return null;
        }

        // Post-process
        String srcCode = src.toString();
        if (srcCode.length() == 0) {
            // Return list because may have found referenced examples
            return fragments;
        }

        // Shorten to minimum indentation
        int minindent = 999;
        String lines[] = srcCode.split("\n");
        for (int i = 0; i < lines.length; i++) {
            int spacecount = 0;
            for (; spacecount < lines[i].length(); spacecount++)
                if (lines[i].charAt(spacecount) != ' ')
                    break;
            if (spacecount < lines[i].length() && spacecount < minindent)
                minindent = spacecount;
        }
        int srcWidth = 0;
        if (minindent < 999) {
            StringBuffer shortsrc = new StringBuffer();
            for (int i = 0; i < lines.length; i++) {
                // The line can be shorter if it's all space
                String line;
                if (lines[i].length() > minindent)
                    line = lines[i].substring(minindent);
                else
                    line = lines[i];

                // Calculate width of the source fragment
                if (line.length() > srcWidth)
                    srcWidth = line.length();

                shortsrc.append(line + "\n");
            }
            srcCode = shortsrc.toString();
        }

        fragment.setSrcCode(srcCode);
        fragment.setSrcWidth(srcWidth);

        fragments.add(fragment);
        return fragments;
    }

    SourceFragment readFragmentFromStream(InputStream ins, String filename) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        StringBuffer src = new StringBuffer();
        int maxlength = 0;
        try {
            for (String line = reader.readLine(); null != line; line = reader.readLine()) {
                line = line.replace("\t", "    ");
                src.append(line + "\n");
                if (line.length() > maxlength)
                    maxlength = line.length();
            }
        } catch (IOException e) {
            Notification.show("Error while reading source file: " + filename);
        }
        SourceFragment fragment = new SourceFragment(filename);
        fragment.setSrcCode(src.toString());
        fragment.setSrcWidth(maxlength);
        return fragment;
    }

    /** Reads an file from class path */
    SourceFragment readFileFromClassPath(String filename) {
        InputStream ins = getClass().getResourceAsStream(filename);
        if (ins == null) {
            Notification.show("Invalid source file name: " + filename);
        } else
            return readFragmentFromStream(ins, filename);
        return null;
    }

    SourceFragment readFileFromApplication(String filename) {
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        String absfilename = basepath + "/" + filename;
        FileInputStream ins;
        try {
            ins = new FileInputStream(absfilename);
            return readFragmentFromStream(ins, absfilename);
        } catch (FileNotFoundException e) {
            Notification.show("Invalid source file name: " + absfilename);
        }
        return null;
    }
}
