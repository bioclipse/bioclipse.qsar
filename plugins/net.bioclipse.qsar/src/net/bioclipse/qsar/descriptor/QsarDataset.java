package net.bioclipse.qsar.descriptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.DenseDataset;
import net.bioclipse.core.domain.IMolecule;

public class QsarDataset extends DenseDataset{

	Map<IMolecule, List<IDescriptorResult>> qsarResults;

	public Map<IMolecule, List<IDescriptorResult>> getQsarResults() {
		return qsarResults;
	}

	public QsarDataset(Map<IMolecule, List<IDescriptorResult>> resultMap) {
		super();
		
		qsarResults=resultMap;

		colHeaders=new ArrayList<String>();
		rowHeaders=new ArrayList<String>();
		values=new ArrayList<List<Float>>();

		//Descriptor names on column headers
		//Loop over all descriptors in get first mol (all are same)
		IMolecule fmol = (IMolecule) resultMap.keySet().toArray()[0];
		for (IDescriptorResult dres : resultMap.get( fmol )){
			for (String label : dres.getLabels()){
				colHeaders.add(label);
			}
		}
		//        if (null!=responseProperty)
		//            row=row+responseProperty;

		//Debug out
		for (IMolecule mol : resultMap.keySet()){

			List<Float> rowValues=new ArrayList<Float>();

			rowHeaders.add("Molecule-" + mol);
			List<IDescriptorResult> lst = resultMap.get( mol );
			for (IDescriptorResult dres : lst){
				for (Float value : dres.getValues()){
					rowValues.add(value);
				}
			}
			values.add(rowValues);
		}	
		
	}
	
	public void setNameProperty(String nameProperty){
		ICDKManager cdk = Activator.getDefault().getJavaCDKManager();
		rowHeaders = new ArrayList<String>();
		for (IMolecule mol : qsarResults.keySet()){
			String name =null;
			try {
				ICDKMolecule cdkmol = cdk.asCDKMolecule(mol);
				name = (String) cdkmol.getProperty(nameProperty, null);
			} catch (BioclipseException e) {
			}
			if (name==null)
				rowHeaders.add("Molecule-" + mol);
			else
				rowHeaders.add(name);
			
		}
	}

	/**
	 * Fill the response values by a named property
	 */
	public void setResponseProperty(String responseProperty){

		ICDKManager cdk = Activator.getDefault().getJavaCDKManager();
		this.responseProperty=responseProperty;
		responseValues = new ArrayList<String>();
		
		for (IMolecule mol : qsarResults.keySet()){
			
			String response =null;
			try {
				ICDKMolecule cdkmol = cdk.asCDKMolecule(mol);
				response = (String) cdkmol.getProperty(responseProperty, null);
			} catch (BioclipseException e) {
			}
			if (response==null)
				responseValues.add("NaN");
			else
				responseValues.add(response);

		}
	}
	
	/**
	 * Returns at <code>String</code> that simulates a matrix in its appearance
	 * when printed in e.g. the JavaScript console. I.e. the values is separated 
	 * with a comma and a tab on each row. If it has headers those are also 
	 * printed, the column headers are separated with a tab and the row
	 * headers are printed in front of the values of its row.
	 * 
	 * @return The dataSet as a matrix.
	 */
	public String toMatrix() {
        StringBuffer matrix = new StringBuffer();
        
        int rhLen = 0;
        if (!rowHeaders.isEmpty()) {            
            for (String header:rowHeaders) {
                if (header.length()>rhLen)
                    rhLen = header.length();
            }
            for (int i=0;i<rhLen;i++)
                matrix.append( '\u0020' );
            
            matrix.append( '\t' );
        }
        
        if (!colHeaders.isEmpty()) {
            for(String header:colHeaders) {
                matrix.append( header );                
                matrix.append( '\t' );
            }
            matrix.replace( matrix.length()-1, matrix.length(), "\n" );
        }
        
        Iterator<String> rowHeaderItr = rowHeaders.iterator();
        String header = "";
        for (List<Float> row:values) {
            if (rowHeaderItr.hasNext()) {
                header = rowHeaderItr.next();
                matrix.append( header );
                if (header.length()<rhLen) {
                    for (int i=header.length();i<rhLen;i++)
                        matrix.append( '\u0020' );
                }
                matrix.append( '\t' );
            }
            for (Float value:row) {
                matrix.append( value );
                matrix.append( '\u002C' );
                matrix.append( '\t' );
            }
            matrix.replace( matrix.length()-2, matrix.length(), "\n" );
        }
        
        return matrix.toString();
    }

}
