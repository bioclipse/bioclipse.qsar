package net.bioclipse.cdk.qsar.test;

import java.io.InputStream;

import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.io.CMLReader;
import org.openscience.cdk.io.ReaderFactory;
import org.openscience.cdk.io.formats.CMLFormat;
import org.openscience.cdk.io.formats.IChemFormatMatcher;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

public class testCDKAT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

        InputStream ins = testCDKAT.class.getResourceAsStream("/testFiles/0037.cml");
        
        CMLReader reader=new CMLReader(ins);
        
        IChemFile chemFile = new org.openscience.cdk.ChemFile();


        //Read file
        try {
            chemFile=(IChemFile)reader.read(chemFile);
        } catch (CDKException e) {
        	e.printStackTrace();
        }
        
        IAtomContainer container = ChemFileManipulator.getAllAtomContainers(chemFile).get(0);
        
		try {

			CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(container.getBuilder());
	        for (IAtom atom : container.atoms()) {
	            IAtomType matched = matcher.findMatchingAtomType(container, atom);
	            if (matched != null){
	            	AtomTypeManipulator.configure(atom, matched);
	            }else{
	            	System.out.println("Could not find matching atom type for atom: " + atom);
	            }
	        }
	        
			CDKHydrogenAdder hAdder = CDKHydrogenAdder.getInstance(container.getBuilder());
			hAdder.addImplicitHydrogens(container);
			AtomContainerManipulator.convertImplicitToExplicitHydrogens(container);

		} catch (Exception e1) {
			System.out.println("Error addding hydrogens : " + e1.getMessage());
		}



	}

}
