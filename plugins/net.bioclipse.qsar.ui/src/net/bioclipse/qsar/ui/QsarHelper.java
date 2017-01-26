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
package net.bioclipse.qsar.ui;

import java.util.List;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.StructureType;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;


public class QsarHelper {

    private static final Logger logger = Logger.getLogger(QsarHelper.class);

    public static void setChangedInPreference( DescriptorType desc, IProject project, boolean newValue ) {


        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        node.putBoolean( project.getName()+"_"+desc.getId(), newValue);
        try {
            node.flush();
        } catch ( BackingStoreException e ) {
            e.printStackTrace();
        }
    }

    public static void setChangedInPreference( StructureType structure, IProject project, boolean newValue) {

        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        node.putBoolean( project.getName()+"_"+structure.getId(), newValue);
        try {
            node.flush();
        } catch ( BackingStoreException e ) {
            e.printStackTrace();
        }

    }

    /**
     * Set the autobuild preference for a project
     * @param structure
     * @param project
     * @param newValue
     */
    public static void setAutoBuild( IProject project, boolean newValue) {

        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        node.putBoolean( project.getName()+"_autobuild", newValue);
        try {
            node.flush();
        } catch ( BackingStoreException e ) {
            e.printStackTrace();
        }

    }

    /**
     * Read prefs to determine if autobuild is on. Default is false
     * @return
     */
    public static boolean isAutoBuild( IProject project) {
        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        return node.getBoolean( project.getName()+"_autobuild", false);
    }


    /**
     * Read prefs to determine if this descriptor is dirty. Default is TRUE.
     * @return
     */
    public static boolean isDirtyInPreference( DescriptorType desc, IProject project) {
        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        //        System.out.println(" READ PREFERENCE: " + project.getName()+"_"+desc.getId() +" = " + node.getBoolean( project.getName()+"_"+desc.getId(), true));
        return node.getBoolean( project.getName()+"_"+desc.getId(), true);
    }

    /**
     * Read prefs to determine if this structure is dirty. Default is TRUE.
     * @return
     */
    public static boolean isDirtyInPreference( StructureType structure, IProject project) {
        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        //        System.out.println(" READ PREFERENCE: " + project.getName()+"_"+structure.getId() +" = " + node.getBoolean( project.getName()+"_"+structure.getId(), true));
        return node.getBoolean( project.getName()+"_"+structure.getId(), true);
    }

    @Deprecated
    public static void updateTransientProperties( QsarType qsarModel, IProject project) {

        //If exists in prefs, add them
        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);

        ICDKManager cdk=Activator.getDefault().getJavaCDKManager();

        //NoMols is easy, just count number of structures
        for (ResourceType resource : qsarModel.getStructurelist().getResources()){
            if (resource.getStructure()!=null){
                resource.setNoMols( resource.getStructure().size() );
            }

            int pno2D=node.getInt( project.getName()+"_"+resource.getId()+"_no2d", -1);
            int pno3D=node.getInt( project.getName()+"_"+resource.getId()+"_no3d", -1);
            if (pno2D<0 || pno3D<0){

                //At least one missing attribute, parse and read mols
                //Load molecules into file
                try {
                    List<ICDKMolecule> mollist = cdk.loadMolecules(resource.getFile());
                    if (mollist!=null){

                        //Count no of 2D and 3D
                        int no2d=0;
                        int no3d=0;
                        for (ICDKMolecule mol : mollist){
                            if (cdk.has2d( mol ))
                                no2d++;
                            if (cdk.has3d( mol ))
                                no3d++;
                        }

                        resource.setNo2d( no2d );
                        resource.setNo3d( no3d );
                        
                        //Also store as preferences
                        node.putInt( project.getName()+"_"+resource.getId()+"_no2d", no2d);
                        node.putInt( project.getName()+"_"+resource.getId()+"_no3d", no3d);
                        
//                        System.out.println("* No2D and no3D calculated and stored as prefs");

                    }
                } catch ( Exception e ) {
                    logger.error("Could not parse molecule file: " + resource.getFile());
                }
            }else{
                resource.setNo2d( pno2D );
                resource.setNo3d( pno3D );
//                System.out.println("* No2D and no3D read from prefs; no calc required");
            }

        }


    }

    public static void setBuildStatus( IProject project, String status ) {

        //If exists in prefs, add them
        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        node.put( project.getName()+"_status", status);

    }

    public static String getBuildStatus(IProject project) {

        //If exists in prefs, add them
        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        return node.get( project.getName()+"_status", "UNKNOWN");

    }

    public static void setBuildTime( IProject project, String time ) {

        //If exists in prefs, add them
        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        node.put( project.getName()+"_time", time);
        
    }

    public static String getBuildTime( IProject project) {

        //If exists in prefs, add them
        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        return node.get( project.getName()+"_time", "N/A");
        
    }

    public static void setAllDirty( QsarType qsarModel, IProject project ) {

        IEclipsePreferences node = InstanceScope.INSTANCE.getNode(net.bioclipse.qsar.ui.Activator.PLUGIN_ID);

        //Set all structures to dirty
        for (ResourceType res : qsarModel.getStructurelist().getResources()){
            for (StructureType structure : res.getStructure()){
                node.putBoolean( project.getName()+"_"+structure.getId(), true);
            }
        }
        
        for (DescriptorType desc : qsarModel.getDescriptorlist().getDescriptors()){
            node.putBoolean( project.getName()+"_" + desc.getId(), true);
        }
        
        try {
            node.flush();
        } catch ( BackingStoreException e ) {
            e.printStackTrace();
        }
    }

}
