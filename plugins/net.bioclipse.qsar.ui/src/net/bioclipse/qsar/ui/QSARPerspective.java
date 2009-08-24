package net.bioclipse.qsar.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * 
 * @author ola
 *
 */
public class QSARPerspective implements IPerspectiveFactory {

    IPageLayout storedLayout;

    /**
     * This perspective's ID
     */
    public static final String ID_PERSPECTIVE =
        "net.bioclipse.qsar.ui.QSARPerspective";

    public static final String ID_NAVIGATOR = 
        "net.bioclipse.navigator";

    public static final String ID_JAVSCRIPT_CONSOLE = 
        "net.bioclipse.scripting.ui.views.JsConsoleView";

    /**
     * Create initial layout
     */
    public void createInitialLayout(IPageLayout layout) {
        defineActions(layout);
        defineLayout(layout);
    }

    private void defineLayout( IPageLayout layout ) {

        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(true);
        layout.setFixed(false);
        layout.addPerspectiveShortcut(ID_PERSPECTIVE);

        //Add layouts for views
        IFolderLayout left_folder_layout =
            layout.createFolder(
                    "explorer",
                    IPageLayout.LEFT,
                    0.20f,
                    editorArea);

        IFolderLayout bottom_folder_layout =
            layout.createFolder(
                    "bottom",
                    IPageLayout.BOTTOM,
                    0.70f,
                    editorArea);

        IFolderLayout right_bottom_folder_layout =
            layout.createFolder(
                    "rightbottom",
                    IPageLayout.RIGHT,
                    0.70f,
                    "bottom");

//        IFolderLayout right_folder_layout =
//            layout.createFolder(
//                    "right",
//                    IPageLayout.RIGHT,
//                    0.70f,
//                    editorArea);

        //Add views
        left_folder_layout.addView(ID_NAVIGATOR);
        bottom_folder_layout.addView(ID_JAVSCRIPT_CONSOLE);
        right_bottom_folder_layout.addView(IPageLayout.ID_PROGRESS_VIEW);
//        right_folder_layout.addView(IPageLayout.ID_OUTLINE);

    }

    private void defineActions( IPageLayout layout ) {


        //Add ShowView shortcuts
        layout.addShowViewShortcut(ID_NAVIGATOR);    
        layout.addShowViewShortcut(ID_JAVSCRIPT_CONSOLE);    
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);    
        layout.addShowViewShortcut(IPageLayout.ID_PROGRESS_VIEW);    
        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);    
        layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);    
        
    }
}