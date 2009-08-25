package net.bioclipse.qsar.business;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.qsar.descriptor.model.Descriptor;
import net.bioclipse.qsar.descriptor.model.DescriptorCategory;
import net.bioclipse.qsar.descriptor.model.DescriptorModel;
import net.bioclipse.rdf.Activator;
import net.bioclipse.rdf.business.IRDFManager;
import net.bioclipse.rdf.business.IRDFStore;


public class OntologyHelper {

    private static final Logger logger = Logger.getLogger(OntologyHelper.class);

    private static final String BO_NAMESPACE = "http://www.blueobelisk.org/" +
    "ontologies/chemoinformatics-algorithms";

    /**
     * Parse OWL using RDF plugin and build descriptor hierarchy
     * @return
     * @throws CoreException 
     * @throws BioclipseException 
     * @throws IOException 
     */
    public static DescriptorModel buildDescriptorHierarchy() throws IOException, BioclipseException, CoreException{

        //The new model
        DescriptorModel descriptorModel=new DescriptorModel();

        //Use hardcoded owl
        String owlFile="/ontology/descriptor-algorithms.owl";
        Bundle bundle = Platform.getBundle(net.bioclipse.qsar.init.Activator.PLUGIN_ID); 

        URL url=bundle.getEntry(owlFile);
        //        System.out.println("URL: " + url);
        URL furl=FileLocator.toFileURL(url);
        logger.debug("BODOntology as fileURL: " + furl);

        return addDescriptorHierarchy(descriptorModel, furl);
    }

    public static DescriptorModel addDescriptorHierarchy(
        DescriptorModel descriptorModel, URL url) throws
        IOException, BioclipseException, CoreException {

        //We need a new list of descriptor categories
        List<DescriptorCategory> categories = descriptorModel.getCategories();
        if (categories == null)
            categories = new ArrayList<DescriptorCategory>();

        IRDFManager rdf=Activator.getDefault().getJavaManager();
        IRDFStore owl = rdf.createStore();

        rdf.importURL(owl, url.toString());

        // list all descriptor categories
        List<List<String>> cats = rdf.sparql(owl,
          "PREFIX qsar: <http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#> " +
          "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
          "SELECT ?id ?label ?definition WHERE { " +
          "  ?id qsar:isClassifiedAs qsar:descriptorCategories; " +
          "      rdfs:label ?label; " +
          "      qsar:definition ?definition. " +
          "}"
        );
        // iterate over all categories child of the category 'descriptorCategories'
        for (int i=0; i<cats.size(); i++) {
            List<String> cat = cats.get(i);
            

            // the next follows the ?id ?label ?definition order in the SPARQL
            String identifier = cat.get(0);
            String label = cat.get(1);
            String definition = cat.get(2);
//            logger.debug("Category: " + label + "\n");

            //Remove the starting qsar:
            identifier=identifier.substring( 5 );
            identifier=BO_NAMESPACE + "/#" + identifier;

            //Create model object and store in list
            DescriptorCategory dcat=new DescriptorCategory(identifier, label);
            
            /*
            FIXME: Egonw, see bug 1566
            dcat.setDate( TODO );
            */
            categories.add( dcat );
        }

        // because we may be adding from a new OWL file, we have to also
        // now earlier added categories, so we reiterate
        for (DescriptorCategory dcat : categories) {
            String identifier = dcat.getId();
            // list of descriptors for this category; it takes advantage from the fact
            // that the identifier is namespace prefix, and we use the same here
            String sparql = "PREFIX qsar: <http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#> " +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
            "SELECT ?s ?label WHERE { " + 
            "  ?s ?p <" + identifier + ">;" +
            "     rdfs:label ?label." +
            "}";
            List<List<String>> descriptors = rdf.sparql(owl, sparql);
            if (descriptors != null) {
                for (int j=0; j<descriptors.size(); j++) {
                    List<String> descriptor = descriptors.get(j);
                    String descriptorID = descriptor.get(0);
                    String label = descriptor.get(1);
//                    logger.debug("  " + descriptorID + " = " + label + "\n");
                    
                    //Remove the starting qsar:
                    descriptorID=descriptorID.substring( 5 );
                    
                    Descriptor desc = new Descriptor(BO_NAMESPACE + "/#" + descriptorID,label);
                    desc.setNamesapce(BO_NAMESPACE);
                    desc.addCategory(dcat);

                    /*
                      
                     FIXME: Egonw, see bug 1566
                    desc.setDate( TODO );
                    desc.setDescription( TODO );
                    */
                    
                    dcat.addDescriptor(desc);
                    
                }
            }
        }

        descriptorModel.setCategories(categories);

        return descriptorModel;
    }


}
