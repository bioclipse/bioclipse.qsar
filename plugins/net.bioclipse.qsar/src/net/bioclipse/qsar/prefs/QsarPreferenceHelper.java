/*******************************************************************************
 * Copyright (c) 2009 Ola Spjuth.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ola Spjuth - initial API and implementation
 ******************************************************************************/
package net.bioclipse.qsar.prefs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.business.QsarHelper;
import net.bioclipse.qsar.descriptor.model.ResponseUnit;
import net.bioclipse.qsar.init.Activator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * 
 * @author ola
 *
 */
public class QsarPreferenceHelper {

    private static final Logger logger = Logger.getLogger(
                                                    QsarPreferenceHelper.class);


    static Map<String, String> providerNameToID = new HashMap<String, String>();

    public static String getAvailableProvidersFromEP(){

        //Initialize implementations via extension points
        IExtensionRegistry registry = Platform.getExtensionRegistry();

        if ( registry == null )
            throw new RuntimeException("Registry is null, no services can " +
            "be read. Workbench not started?");
        // it likely means that the Eclipse workbench has not
        // started, for example when running tests

        /*
         * service objects
         */
        IExtensionPoint serviceObjectExtensionPoint = registry
        .getExtensionPoint(QSARConstants.DESCRIPTOR_EXTENSION_POINT);

        IExtension[] serviceObjectExtensions
        = serviceObjectExtensionPoint.getExtensions();

        List<String> providerNames=new ArrayList<String>();

        for(IExtension extension : serviceObjectExtensions) {
            for( IConfigurationElement element
                    : extension.getConfigurationElements() ) {

                if (element.getName().equals(QSARConstants
                                                       .PROVIDER_ELEMENT_NAME)){

                    String providerID=element.getAttribute("id");
                    String providerName=element.getAttribute("name");
                    providerNames.add(providerName);
                    providerNameToID.put(providerName, providerID);

                }
            }
        }
        if (providerNames.size()>0){
        	int ix = providerNames.indexOf("Chemistry Development Kit");
        	if (ix>0){
        		//put this first
        		String cache=providerNames.get(0);
        		providerNames.set(0, "Chemistry Development Kit");
        		providerNames.set(ix, cache);
        	}
            String[] providersAsArray=providerNames.toArray(new String[0]);
            return createQsarPreferenceStringFromItems(
                                                       providersAsArray);
        }

        return "ERROR";
    }


    public static String[] parseQsarPreferenceString(String stringList) {
        StringTokenizer st = 
            new StringTokenizer(stringList, QSARConstants.PREFS_SEPERATOR);
        ArrayList v = new ArrayList();
        while (st.hasMoreElements()) {
            v.add(st.nextElement());
        }
        return (String[])v.toArray(new String[v.size()]);
    }


    public static String createQsarPreferenceStringFromItems(String[] items) {

        StringBuffer path = new StringBuffer("");//$NON-NLS-1$

        for (int i = 0; i < items.length; i++) {
            path.append(items[i]);
            path.append(QSARConstants.PREFS_SEPERATOR);
        }
        return path.toString();
    }

    public static String getProviderID(String providerName){
        return providerNameToID.get(providerName);
    }


    public static String createQsarPreferenceStringFromResponseUnit(
                                                            ResponseUnit unit) {

        StringBuffer ret = new StringBuffer("");

        ret.append(unit.getId()+QSARConstants.PREFS_INTERNAL_SEPERATOR);
        ret.append(unit.getName()+QSARConstants.PREFS_INTERNAL_SEPERATOR);
        ret.append(unit.getShortname()+QSARConstants.PREFS_INTERNAL_SEPERATOR);
       ret.append(unit.getDescription()+QSARConstants.PREFS_INTERNAL_SEPERATOR);
        ret.append(unit.getUrl()+QSARConstants.PREFS_INTERNAL_SEPERATOR);

        return ret.toString();
    }


    /**
     * Input: concatenated info like ID¤name¤shortname¤...
     * Output: ResponseUnit or null if not valid
     * 
     * @param stringParts
     * @return
     */
    public static ResponseUnit parseQsarPreferenceStringIntoResponseUnit(
                                                             String unitString){
        StringTokenizer st = 
            new StringTokenizer(unitString, 
                                QSARConstants.PREFS_INTERNAL_SEPERATOR);

        if (st.countTokens()<3)
            return null;

        String newId=(String) st.nextElement();
        String newName=(String) st.nextElement();
        String newShortname=(String) st.nextElement();

        ResponseUnit unit=new ResponseUnit(newId, newName);
        unit.setShortname( newShortname );
        
        if (st.hasMoreElements())
            unit.setDescription( (String) st.nextElement() );
        if (st.hasMoreElements())
            unit.setUrl( (String) st.nextElement() );

        return unit;

    }


    /**
     * Reads preference string and parses into list of ResponeUnits
     * @return list of ResponeUnits
     */
    public static List<ResponseUnit> getAvailableUnitsFromPrefs() {

        List<ResponseUnit> retlist=new ArrayList<ResponseUnit>();
        
        
        IPreferenceStore store =
            Activator.getDefault().getPreferenceStore();

        String completePref = store.getString( 
                                          QSARConstants.QSAR_UNITS_PREFERENCE );


        if (completePref==null)
            return retlist;

        String[] unitStrings = QsarPreferenceHelper
        .parseQsarPreferenceString( completePref );

        //Parse individual strings into response units
        for (String unitString : unitStrings){
            ResponseUnit unit = QsarPreferenceHelper
                                   .parseQsarPreferenceStringIntoResponseUnit( 
                                                                   unitString );
            if (unit!=null)
                retlist.add(unit);
            else{
                logger.error("Could not parse string: '" + unitString 
                             + "' into a ResponseUnit.");
            }
        }

        return retlist;
    }


    /**
     * Read all descr def files stored as strings and return list of URLs
     * @return
     */
    public static List<URL> getAvailableDescriptorDefinitionFilesFromPrefs() {

        IPreferenceStore store =
            Activator.getDefault().getPreferenceStore();

        String completePref = store.getString( 
                                          QSARConstants.QSAR_ONTOLOGY_FILES_PREFERENCE );

        String[] urlStrings = parseQsarPreferenceString( completePref );
        
        List<URL> urls=new ArrayList<URL>();
        for (String urlstr : urlStrings){
            try {
                urls.add( new URL(urlstr) );
            } catch ( MalformedURLException e ) {
                logger.error( "Could not convert prefs string to url: " + urlstr );
            }
        }
        
        return urls;
    }



}
