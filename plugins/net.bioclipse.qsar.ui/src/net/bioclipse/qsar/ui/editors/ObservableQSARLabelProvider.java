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
package net.bioclipse.qsar.ui.editors;


import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.ParameterType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ui.Activator;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author ola
 *
 */
public class ObservableQSARLabelProvider extends ObservableMapLabelProvider{

    private Image errImg;
    private Image molImg;
    private Image molListImg;

    public ObservableQSARLabelProvider(IObservableMap[] observeMaps) {
        super(observeMaps);
        errImg=Activator.getImageDescriptor("icons/warn2.gif").createImage();
        molImg=Activator.getImageDescriptor("icons/benzene.gif").createImage();
        molListImg=Activator.getImageDescriptor("icons/table16-with-molecule.png").createImage();
    }

    @Override
    public Image getColumnImage(Object element, int columnIndex) {

        if (columnIndex!=0) return null;

        if (element instanceof DescriptorType) {
            //			return Activator.getImageDescriptor("icons/descriptor.png").createImage();
        }
        else if (element instanceof ResourceType) {
            ResourceType rt=(ResourceType)element;
            if (rt.isContainsErrors()){
                return errImg;
            }
            if (rt.getStructure()!=null && rt.getStructure().size()>1)
                return molListImg;
            return molImg;
        }

        return null;

    }

    @Override
    public String getColumnText(Object element, int columnIndex) {

        String label= super.getColumnText(element, columnIndex);

        if (element instanceof DescriptorType) {
            if (label.indexOf('#')>0){
                label=label.substring(label.lastIndexOf('#')+1, label.length());
            }

            DescriptorType desc = (DescriptorType) element;

            String cpstr="";
            if (desc.getParameter()!=null && desc.getParameter().size()>0){
                for (ParameterType param : desc.getParameter()){
                    String pstr=param.getKey() + "=" + param.getValue()+", ";
                    cpstr=cpstr+pstr;
                }
                cpstr=cpstr.substring(0,cpstr.length()-2);
            }

            if (cpstr.length()>1){
                label=label + " [" + cpstr + "]";
            }

        }

        if (element instanceof ResourceType) {
            ResourceType resource=(ResourceType)element;

            if (columnIndex==4){
                //status
                if (resource.isContainsErrors())
                    return "Contains errors";
                else
                    return "OK";
            }

            int problems=0;
            //Find all problems
            //        for (StructureType structure : resource.getStructure()){
            //            for (String problem : structure.getProblem()){
            //                problems++;
            //            }
            //        }

            if (problems>0 && columnIndex==0)
                return super.getColumnText( element, columnIndex ) + " [" + problems + " problems]";

        }

        return label;

    }



}
