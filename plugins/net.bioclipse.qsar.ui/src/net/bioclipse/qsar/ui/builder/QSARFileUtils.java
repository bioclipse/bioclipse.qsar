package net.bioclipse.qsar.ui.builder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.bioclipse.qsar.DocumentRoot;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.util.QsarResourceFactoryImpl;
import net.sf.bibtexml.BibtexmlPackage;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;


public class QSARFileUtils {

    private static final Logger logger = Logger.getLogger(QSARFileUtils.class);

    /**
     * Read in project file and parse it with EMF
     * @param file 
     * @return QsarType model object
     */
    public static QsarType readModelFromProjectFile(IFile file) {

        // Register the package -- only needed for stand-alone!
        @SuppressWarnings("unused")
        QsarPackage qsarPackage=QsarPackage.eINSTANCE;

        // Register the package -- only needed for stand-alone!
        @SuppressWarnings("unused")
        BibtexmlPackage bibPackage=BibtexmlPackage.eINSTANCE;

        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register the appropriate resource factory to handle all file extensions.
        //
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
        (Resource.Factory.Registry.DEFAULT_EXTENSION, 
         new QsarResourceFactoryImpl());

        // Register the package to ensure it is available during loading.
        //
        resourceSet.getPackageRegistry().put
        (QsarPackage.eNS_URI, 
         QsarPackage.eINSTANCE);

        // Register the package to ensure it is available during loading.
        //
        resourceSet.getPackageRegistry().put
        (BibtexmlPackage.eNS_URI, 
         BibtexmlPackage.eINSTANCE);

        EcoreUtil.resolveAll( resourceSet );


        logger.debug("Model file to read: " 
                     + file.getRawLocation().toOSString());

        // Get the URI of the model file.
        //        URI fileURI = URI.createFileURI(qsarfile.getRawLocation().toOSString());

        URI uri=URI.createPlatformResourceURI(file.getFullPath().toString(), true);

        // Demand load the resource for this file.
        try{
            Resource resource = resourceSet.getResource(uri, true);
            DocumentRoot root=(DocumentRoot) resource.getContents().get(0);
            QsarType qsarType=root.getQsar();

            return qsarType;
        }catch (Exception e){
            logger.error("Could not read file:" + file.getName() 
                         + " in project: " + file.getProject() 
                         + " because error: " + e.getMessage());
        }

        return null;
    }
    
    /**
     * Save the model to file
     * @param qsarModel 
     * @return QsarType model object
     */
    public static void saveModelToFile(QsarType qsarModel, IFile file) {

        //We need a documentroot for serialization
        DocumentRoot root=QsarFactory.eINSTANCE.createDocumentRoot();
        root.setQsar( qsarModel );

        ResourceSet resourceSet=new ResourceSetImpl();
        //        URI fileURI;
        try {
            //For now, only one QSAR file per project
            //            IFile qsarfile = getProject().getFile("qsar.xml");
            //            fileURI = URI.createFileURI(qsarfile.getRawLocation().toOSString());
            URI uri=URI.createPlatformResourceURI(file.getFullPath().toString(), true);

            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xml", new QsarResourceFactoryImpl());

            Resource resource=resourceSet.createResource(uri);
            resource.getContents().add(root);

            //Serialize with extra options
            Map opts=new HashMap();
            opts.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
            opts.put(XMLResource.OPTION_ENCODING, "UTF-8");

            //Save to file
            resource.save(opts);

            file.refreshLocal( 0, new NullProgressMonitor());
            
            logger.debug( "Wrote QSAR file: " + uri );

            //Serialize to byte[] and print to sysout
            //            ByteArrayOutputStream os=new ByteArrayOutputStream();
            //            resource.save(os, opts);
            //            System.out.println(new String(os.toByteArray()));

        } catch (IOException e) {
            e.printStackTrace();
        } catch ( CoreException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
