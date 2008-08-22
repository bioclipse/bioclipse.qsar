package net.bioclipse.cdk.qsar.test;

import static org.junit.Assert.*;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.domain.SmilesMolecule;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.business.QsarManager;
import net.bioclipse.qsar.descriptor.IDescriptorResult;
import net.bioclipse.qsar.descriptor.model.Descriptor;
import net.bioclipse.qsar.descriptor.model.DescriptorCategory;
import net.bioclipse.qsar.descriptor.model.DescriptorProvider;

import org.junit.Test;

public class TestCDKQsar {

	IQsarManager qsar;
	private String cdkProviderID="net.bioclipse.cdk.descriptorprovider";
	String categoryID="net.bioclipse.qsar.cdk.molecular";
	
	public TestCDKQsar() {
		
		//Unnecessary to use OSGI.
		qsar=new QsarManager();
	}
	
	

	@Test
	public void testGetCategories(){
		
		//Get category IDs
		List<String> lst = qsar.getCategories();
		assertNotNull(lst);
		
		assertTrue(lst.contains(categoryID));
		
		DescriptorCategory cat=qsar.getCategoryByID(categoryID);
		assertNotNull(cat);
		assertEquals(categoryID, cat.getId());
		
		List<DescriptorCategory> lstFull = qsar.getFullCategories();
		assertNotNull(lstFull);
		assertTrue(lstFull.contains(cat));

	}

	@Test
	public void testGetProviders(){

		//Get provider by ID
		DescriptorProvider provider=qsar.getProviderByID(cdkProviderID);
		assertNotNull(provider);

		//Get provider classes
		List<DescriptorProvider> lstFull = qsar.getFullProviders();
		assertNotNull(lstFull);
		assertTrue(lstFull.contains(provider));

	}

	@Test
	public void testGetDescriptors(){

		//Matches plugin.xml, just test some classes
     	String xlogpID="org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor";
		String bpolID="org.openscience.cdk.qsar.descriptors.molecular.BPolDescriptor";

		//Get provider by ID
		DescriptorProvider provider=qsar.getProviderByID(cdkProviderID);
		assertNotNull(provider);

		List<String> descIDs=qsar.getDescriptors(cdkProviderID);
		List<String> descIDsInCat=qsar.getDescriptors(cdkProviderID, categoryID);
		List<Descriptor> descs=qsar.getDescriptors(provider);
		
		//Check list of IDs and list of classes equal size
		assertEquals(descIDs.size(), descs.size());
		assertEquals(descIDsInCat.size(), descs.size());
		assertEquals(descIDsInCat.size(), descIDs.size());
		
		assertTrue(descIDs.contains(xlogpID));
		assertTrue(descIDs.contains(bpolID));
		
	}

	@Test
		public void testGetDescriptorsByID(){
		//Matches plugin.xml
		String bpolID="org.openscience.cdk.qsar.descriptors.molecular.BPolDescriptor";

		//Get decriptor by hardcoded id
		Descriptor desc=qsar.getDescriptor(bpolID);
		assertNotNull(desc);
		assertNull(desc.getParameters());
		assertFalse(desc.isRequires3D());
		assertEquals(categoryID, desc.getCategory().getId());
		assertEquals(cdkProviderID, desc.getProvider().getId());
	}

	@Test
	public void testGetDescriptorsByIDWithParameters(){
		//Matches plugin.xml
     	String xlogpID="org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor";

		//Get decriptor by hardcoded id with parameters
		Descriptor desc=qsar.getDescriptor(xlogpID);
		assertNotNull(desc);
		assertNotNull(desc.getParameters());

		for (String key: desc.getParameters().keySet()){
			System.out.println("Param: " + key + " = " + desc.getParameters().get(key));
		}

		assertTrue(desc.getParameters().keySet().contains("checkAromaticity"));
		assertTrue(desc.getParameters().keySet().contains("salicylFlag"));
		
		assertEquals("true", desc.getParameters().get("checkAromaticity"));
		assertEquals("true", desc.getParameters().get("salicylFlag"));

	}
	
	
	
	@Test
	public void testCalculateBpolFromSmiles() throws BioclipseException{

		IMolecule mol=new SmilesMolecule("C1CNCCC1CC(COC)CCNC");
		String bpolID="org.openscience.cdk.qsar.descriptors.molecular.BPolDescriptor";
		
		List<IDescriptorResult> resList = qsar.calculate(mol, bpolID);

		//We know only one result as we only asked for one descriptor
		assertEquals(1, resList.size());
		IDescriptorResult dres1=resList.get(0);
		assertNotNull(dres1);
		assertNull(dres1.getErrorMessage());
		assertEquals(bpolID, dres1.getDescriptorId());

		System.out.println("Mol: " + mol.getSmiles() + 
				" ; Desc: " + dres1.getDescriptorId() +": ");
		for (int i=0; i<dres1.getValues().length;i++){
			System.out.println("    " + dres1.getLabels()[i] 
			                                     + "=" + dres1.getValues()[i] ); 
		}
		
		assertEquals("bpol", dres1.getLabels()[0]);
		assertEquals(4.556, dres1.getValues()[0]);
		
		
	}

	@Test
	public void testCalculateXlogPFromSmiles() throws BioclipseException{

		IMolecule mol=new SmilesMolecule("C1CNCCC1CC(COC)CCNC");
     	String xlogpID="org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor";
		
		List<IDescriptorResult> resList = qsar.calculate(mol, xlogpID);

		//We know only one result as we only asked for one descriptor
		assertEquals(1, resList.size());
		IDescriptorResult dres1=resList.get(0);
		assertNotNull(dres1);
		assertNull(dres1.getErrorMessage());
		assertEquals(xlogpID, dres1.getDescriptorId());

		System.out.println("Mol: " + mol.getSmiles() + 
				" ; Desc: " + dres1.getDescriptorId() +": ");
		for (int i=0; i<dres1.getValues().length;i++){
			System.out.println("    " + dres1.getLabels()[i] 
			                                     + "=" + dres1.getValues()[i] ); 
		}
		
		assertEquals("XLogP", dres1.getLabels()[0]);
		assertEquals(0.184, dres1.getValues()[0]);
		
		
	}

	@Test
	public void testCalculateBCUTFromSmiles() throws BioclipseException{

		IMolecule mol=new SmilesMolecule("C1CNCCC1CC(COC)CCNC");
     	String bcutID="org.openscience.cdk.qsar.descriptors.molecular.BCUTDescriptor";
		
		List<IDescriptorResult> resList = qsar.calculate(mol, bcutID);

		//We know only one result as we only asked for one descriptor
		assertEquals(1, resList.size());
		IDescriptorResult dres1=resList.get(0);
		assertNotNull(dres1);
		assertNull(dres1.getErrorMessage());
		assertEquals(bcutID, dres1.getDescriptorId());

		System.out.println("Mol: " + mol.getSmiles() + 
				" ; Desc: " + dres1.getDescriptorId() +": ");
		for (int i=0; i<dres1.getValues().length;i++){
			System.out.println("    " + dres1.getLabels()[i] 
			                                     + "=" + dres1.getValues()[i] ); 
		}

		assertEquals(6, dres1.getValues().length);
		assertEquals(6, dres1.getLabels().length);
		
		assertEquals("BCUTw-1l", dres1.getLabels()[0]);
		assertEquals(11.993387, dres1.getValues()[0]);

		assertEquals("BCUTw-1h", dres1.getLabels()[1]);
		assertEquals(15.994919, dres1.getValues()[1]);
		assertEquals("BCUTc-1l", dres1.getLabels()[2]);
		assertEquals(0.89, dres1.getValues()[2]);
		assertEquals("BCUTc-1h", dres1.getLabels()[3]);
		assertEquals(1.1102645, dres1.getValues()[3]);
		assertEquals("BCUTp-1l", dres1.getLabels()[4]);
		assertEquals(4.6727624, dres1.getValues()[4]);
		assertEquals("BCUTp-1h", dres1.getLabels()[5]);
		assertEquals(9.596294, dres1.getValues()[5]);
		
	}

	@Test
	public void testCalculateMultipleMolMultipleDescriptor() throws BioclipseException{
		
		IMolecule mol1=new SmilesMolecule("C1CNCCC1CC(COC)CCNC");
		IMolecule mol2=new SmilesMolecule("C1CCCCC1CC(CC)CCCCCOCCCN");
		String bpolID="org.openscience.cdk.qsar.descriptors.molecular.BPolDescriptor";
     	String xlogpID="org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor";
		
		List<IMolecule> mols=new ArrayList<IMolecule>();
		List<String> descs=new ArrayList<String>();
		
		mols.add(mol1);
		mols.add(mol2);
		descs.add(bpolID);
		descs.add(xlogpID);
		
		Map<IMolecule, List<IDescriptorResult>> res = qsar.calculate(mols, descs);
		assertNotNull(res);
		
		List<IDescriptorResult> res1=res.get(mol1);
		List<IDescriptorResult> res2=res.get(mol2);

		assertEquals(2, res1.size());
		assertEquals(2, res2.size());

		IDescriptorResult dres1=res1.get(0);
		IDescriptorResult dres11=res1.get(1);
		IDescriptorResult dres2=res2.get(0);
		IDescriptorResult dres22=res2.get(1);

		assertNull(dres1.getErrorMessage());
		assertNull(dres11.getErrorMessage());
		assertNull(dres2.getErrorMessage());
		assertNull(dres22.getErrorMessage());

		System.out.println("Mol: " + mol1.getSmiles() + 
				" ; Desc: " + dres1.getDescriptorId() +": ");
		for (int i=0; i<dres1.getValues().length;i++){
			System.out.println("    " + dres1.getLabels()[i] 
			                                     + "=" + dres1.getValues()[i] ); 
		}
		
		System.out.println("Mol: " + mol1.getSmiles() + 
				" ; Desc: " + dres11.getDescriptorId() +": ");
		for (int i=0; i<dres11.getValues().length;i++){
			System.out.println("    " + dres11.getLabels()[i] 
			                                     + "=" + dres11.getValues()[i] ); 
		}

		System.out.println("Mol: " + mol2.getSmiles() + 
				" ; Desc: " + dres2.getDescriptorId() +": ");
		for (int i=0; i<dres2.getValues().length;i++){
			System.out.println("    " + dres2.getLabels()[i] 
			                                     + "=" + dres2.getValues()[i] ); 
		}
		
		System.out.println("Mol: " + mol2.getSmiles() + 
				" ; Desc: " + dres22.getDescriptorId() +": ");
		for (int i=0; i<dres22.getValues().length;i++){
			System.out.println("    " + dres22.getLabels()[i] 
			                                     + "=" + dres22.getValues()[i] ); 
		}

		assertEquals("bpol", dres1.getLabels()[0]);
		assertEquals(4.556, dres1.getValues()[0]);
		assertEquals("XLogP", dres11.getLabels()[0]);
		assertEquals(0.184, dres11.getValues()[0]);

		assertEquals("bpol", dres2.getLabels()[0]);
		assertEquals(2.576, dres2.getValues()[0]);
		assertEquals("XLogP", dres22.getLabels()[0]);
		assertEquals(0.04, dres22.getValues()[0]);
		
	}

}
