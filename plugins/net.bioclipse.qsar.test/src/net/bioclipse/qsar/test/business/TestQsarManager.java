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
package net.bioclipse.qsar.test.business;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.domain.SMILESMolecule;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.business.QsarManager;
import net.bioclipse.qsar.descriptor.IDescriptorResult;
import net.bioclipse.qsar.descriptor.model.Descriptor;
import net.bioclipse.qsar.descriptor.model.DescriptorCalculationResult;
import net.bioclipse.qsar.descriptor.model.DescriptorImpl;
import net.bioclipse.qsar.descriptor.model.DescriptorCategory;
import net.bioclipse.qsar.descriptor.model.DescriptorParameter;
import net.bioclipse.qsar.descriptor.model.DescriptorProvider;
import net.bioclipse.qsar.descriptor.model.ResponseUnit;
import net.bioclipse.qsar.init.Activator;
import net.bioclipse.qsar.util.QsarAdapterFactory;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.junit.Test;

public class TestQsarManager {

	private static final String BO_NAMESPACE = "http://www.blueobelisk.org/" +
	"ontologies/chemoinformatics-algorithms";

	IQsarManager qsar;
	
	public TestQsarManager() {
		
		//Unnecessary to use OSGI.
		qsar=new QsarManager();
		
		//Initialize from ontology and EP
		qsar.getFullCategories();
		qsar.getFullDescriptors();
		
	}
	
  @Test
  public void testSetings(){
      assertNotNull( "Must run as PluginTest.", Activator.PLUGIN_ID );
      
  }
  
  @Test
  public void testAddRemoveResponseValues(){

      QsarAdapterFactory factory=new QsarAdapterFactory();
      EditingDomain editingDomain=new AdapterFactoryEditingDomain(factory, new BasicCommandStack());

      QsarType qsarModel=QsarFactory.eINSTANCE.createQsarType();

      assertEquals( 0, qsarModel.getResponseunit().size() );
      List<ResponseUnit> list=new ArrayList<ResponseUnit>();
      
      ResponseUnit unit1=new ResponseUnit("ic50", "half maximal inhibitory concentration (IC50)");
      unit1.setShortname( "IC50" );
      unit1.setDescription( "Measure of the effectiveness of a compound in inhibiting biological or biochemical function");
      unit1.setUrl( "http://en.wikipedia.org/wiki/IC50" );
      list.add( unit1 );
      
      qsar.addResponseUnitToModel( qsarModel, editingDomain, list );
      assertEquals( 1, qsarModel.getResponseunit().size() );
      qsar.removeResponseUnitsFromModel( qsarModel, editingDomain, qsarModel.getResponseunit() );
      assertEquals( 0, qsarModel.getResponseunit().size() );
      
  }

	
	@Test
	public void testGetPreferences(){

    String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain";

		IEclipsePreferences prefs = new DefaultScope().getNode(Activator.PLUGIN_ID);
		assertNotNull(prefs);
		String prefsString=prefs.get(QSARConstants.QSAR_PROVIDERS_ORDER_PREFERENCE, null);
		assertNotNull(prefsString);

		System.out.println("Got prefs string: " + prefsString);
		assertFalse( prefsString.equals( "ERROR" ) );
		
		assertTrue(prefsString.contains("descriptorProvider1"));
		assertTrue(prefsString.contains("descriptorProvider2"));

		DescriptorImpl impl=qsar.getPreferredImpl(descriptorID);
		assertNotNull(impl);
	}

	@Test
	public void testGetCategoriesAndFullCategories(){

		//Get category IDs
		List<String> lst = qsar.getCategories();
		assertNotNull(lst);
		assertTrue(lst.size()>=2);

		System.out.println("=======================");
		System.out.println("Category IDs:");
		for (String str : lst){
			System.out.println("  - " + str);
		}
		System.out.println("=======================");

		//Get category classes
		List<DescriptorCategory> lstFull = qsar.getFullCategories();
		assertNotNull(lstFull);
		
		//Should have same size as IDs
		assertTrue(lstFull.size()==(lst.size()));

	}
	
	 @Test
	  public void testGetStandardResponseValues(){

	    //Get category IDs
	    List<String> lst = qsar.getResponseUnits();
	    assertNotNull(lst);
	    assertTrue(lst.size()>=4);

	    System.out.println("=======================");
	    System.out.println("Response IDs:");
	    for (String str : lst){
	      System.out.println("  - " + str);
	    }
	    System.out.println("=======================");

	    //Get category classes
	    List<ResponseUnit> lstFull = qsar.getFullResponseUnits();
	    assertNotNull(lstFull);
	    
	    //Should have same size as IDs
	    assertTrue(lstFull.size()==(lst.size()));

	  }

	@Test
	public void testGetCategoryByID(){

		String cat1id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#hybridDescriptor";
		String cat2id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#topologicalDescriptor";

		//Get category IDs
		DescriptorCategory cat1 = qsar.getCategoryByID(cat1id);
		assertNotNull(cat1);
		assertEquals(cat1id, cat1.getId());

		DescriptorCategory cat2 = qsar.getCategoryByID(cat2id);
		assertNotNull(cat2);
		assertEquals(cat2id, cat2.getId());

	}

	@Test
	public void testGetProvidersAndFullProviders(){

		//Get provider IDs
		List<String> lst = qsar.getProviders();
		assertNotNull(lst);
		assertTrue(lst.size()>=2);

		System.out.println("=======================");
		System.out.println("Provider IDs:");
		for (String str : lst){
			System.out.println("  - " + str);
		}
		System.out.println("=======================");

		//Get provider classes
		List<DescriptorProvider> lstFull = qsar.getFullProviders();
		assertNotNull(lstFull);
		
		//Should have same size as IDs
		assertTrue(lstFull.size()==(lst.size()));

	}

	@Test
	public void testGetProvidersByID(){

		String providerID="net.bioclipse.qsar.test.descriptorProvider1";
		String expectedNamespace="net.bioclipse.qsar.test.provider1.namespace";
		
		DescriptorProvider prov = qsar.getProviderByID(providerID);
		assertNotNull(prov);
		assertEquals(prov.getId(), providerID);
		assertNotNull(prov.getShortName());
		assertEquals("provider1", prov.getShortName());
		assertEquals(expectedNamespace, prov.getNamesapce());
	}
	
	@Test
	public void testGetProvidersByDescriptorAndProvider() throws BioclipseException{

		String providerID="net.bioclipse.qsar.test.descriptorProvider1";
		String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#BCUT";
        String expectedImplID="net.bioclipse.qsar.test.descriptor1";

		DescriptorImpl impl = qsar.getDescriptorImpl(descriptorID, providerID);
		assertEquals(expectedImplID, impl.getId());
		
	}
	

	
	
	@Test
	public void testGetDescriptorsAndFullDescriptors(){

		List<String> desc=qsar.getDescriptorIDs();
		assertNotNull(desc);
		assertTrue(desc.size()>0);

		List<Descriptor> descList=qsar.getFullDescriptors();
		assertNotNull(descList);
		assertTrue(descList.size()>0);
		
		assertEquals(desc.size(), descList.size());

	}
	
	@Test
	public void testGetDescriptorByID(){
		
		String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#aromaticAtomsCount";
		String cat1id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#hybridDescriptor";
		String cat2id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#constitutionalDescriptor";
		String cat3id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#electronicDescriptor";
		
		Descriptor desc=qsar.getDescriptorByID(descriptorID);
		assertNotNull(desc);
		assertEquals(BO_NAMESPACE, desc.getNamesapce());

		
		DescriptorCategory cat1 = qsar.getCategoryByID(cat1id);
		DescriptorCategory cat2 = qsar.getCategoryByID(cat2id);
		DescriptorCategory cat3 = qsar.getCategoryByID(cat3id);
		
		assertNotNull(desc.getCategories());
		assertFalse(desc.getCategories().contains(cat1));
		assertTrue(desc.getCategories().contains(cat2));
		assertFalse(desc.getCategories().contains(cat3));
		
		assertNotNull(desc.getDate());
		
		assertNotNull(desc.getDefinition());
		
	}

	
	
	@Test
	public void testGetDescriptorByCategory(){
		
		String cat1id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#hybridDescriptor";
		String cat2id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#geometricalDescriptor";

		String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#momentOfInertia";

		DescriptorCategory cat1 = qsar.getCategoryByID(cat1id);
		assertNotNull( cat1 );
		DescriptorCategory cat2 = qsar.getCategoryByID(cat2id);
    assertNotNull( cat2 );
		Descriptor desc=qsar.getDescriptorByID(descriptorID);
		
//		List<Descriptor> descList1 = qsar.getDescriptorsInCategory(cat1);
//    assertNotNull(descList1);
//    assertTrue(descList1.contains(desc));
//    List<String> descList1_1 = qsar.getDescriptorsInCategory(cat1id);
//    assertNotNull(descList1_1);
//    assertEquals( descList1.size(), descList1_1.size() );
    
		List<Descriptor> descList2 = qsar.getDescriptorsInCategory(cat2);
    assertNotNull(descList2);
    assertTrue(descList2.contains(desc));
    List<String> descList2_2 = qsar.getDescriptorsInCategory(cat2id);
    assertNotNull(descList2_2);
    assertEquals( descList2.size(), descList2_2.size() );

	}

  @Test
  public void testGetDescriptorImplsByProvider() throws BioclipseException{

    //Matches plugin.xml
    String providerID="net.bioclipse.qsar.test.descriptorProvider1";
    String providerID2="net.bioclipse.qsar.test.descriptorProvider2";
    
    String descriptorImplID="net.bioclipse.qsar.test.descriptor3";

    assertEquals(2, qsar.getDescriptorImplsByProvider(providerID).size());
    assertEquals(4, qsar.getDescriptorImplsByProvider(providerID2).size());
    
    assertTrue(qsar.existsDescriptorImpl(descriptorImplID));
  }


	@Test
	public void testGetDescriptorImpls(){

		String descriptorImplID="net.bioclipse.qsar.test.descriptor3";
		
		List<DescriptorImpl> descs = qsar.getFullDescriptorImpls();
		assertNotNull(descs);
		assertTrue(descs.size()>0);
		
		DescriptorImpl impl=qsar.getDescriptorImplByID(descriptorImplID);
		assertNotNull(impl);
		
		assertTrue(descs.contains(impl));
		
	}
	

	
	@Test
	public void testGetDescriptorImplNotInOntology(){

		System.out.println("=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.");
		System.out.println("Impl not in onology:");
		System.out.println("=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.");
		for (DescriptorImpl impl : qsar.getFullDescriptorImpls()){
			if (qsar.getDescriptorIDs().contains(impl.getDefinition())){
				//All is well
			}
			else{
				System.out.println("=.= Descriptor impl: " + impl.getName() + " with def: " + impl.getDefinition());
				
			}
			
		}
		System.out.println("=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.");
		
		
	}

	
	

	@Test
	public void testGetDescriptorIDsImplsFromDescriptor() throws BioclipseException{

		String expectedImpl="net.bioclipse.qsar.test.descriptor3";
        String expectedImpl2="net.bioclipse.qsar.test.descriptor3D";
        String expectedImpl3="net.bioclipse.qsar.test.descriptor1";

        String descriptor="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain";

		List<String> descImplIDs = qsar.getDescriptorImpls(descriptor);
		assertNotNull(descImplIDs);

		System.out.println("================");
		System.out.println(" ** Descriptor: " + descriptor + " has the following implementations:");
		for (String dimpl : descImplIDs){
			System.out.println("  -  " + dimpl);
		}
		System.out.println("================");

		assertTrue(descImplIDs.contains(expectedImpl));
		assertTrue(descImplIDs.contains(expectedImpl2));
		assertFalse(descImplIDs.contains(expectedImpl3));

		DescriptorImpl impl1=qsar.getDescriptorImplByID(expectedImpl);
		DescriptorImpl impl2=qsar.getDescriptorImplByID(expectedImpl2);
		DescriptorImpl impl3=qsar.getDescriptorImplByID(expectedImpl3);
		
		List<DescriptorImpl> descImplIDsFull = qsar.getDescriptorImplsForDescriptor(descriptor);
		assertNotNull(descImplIDsFull);
		assertTrue( descImplIDs.size()>0 );
		assertEquals(descImplIDs.size(), descImplIDsFull.size());

		assertTrue(descImplIDsFull.contains(impl1));
		assertTrue(descImplIDsFull.contains(impl2));
		assertFalse(descImplIDsFull.contains(impl3));

	}
	

  @Test
  public void testExistsDescriptor(){
      String descriptor1="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain";
      String descriptor2="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChainWEEE";

      boolean exists=qsar.existsDescriptor( descriptor1 );
      assertTrue( exists );
      boolean noexists=qsar.existsDescriptor( descriptor2 );
      assertFalse( noexists );
     
  }



	@Test
		public void testGetDescriptorImplsByID(){
		//Matches plugin.xml
		String descriptorImplID="net.bioclipse.qsar.test.descriptor3";
//		String cat1id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#molecularDescriptor";

		//Get decriptor by hardcoded id
		DescriptorImpl desc=qsar.getDescriptorImplByID(descriptorImplID);
		assertNotNull(desc);
		assertNull(desc.getParameters());
		assertFalse(desc.isRequires3D());
		assertEquals("descriptorProvider2", desc.getProvider().getName());
		assertEquals("http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain", desc.getDefinition());
		assertEquals("net.bioclipse.qsar.test.description3", desc.getDescription());
	}

	

	@Test
	public void testGetDescriptorImplsByIDRequire3D(){
		//Matches plugin.xml
		String descriptorID3D="net.bioclipse.qsar.test.descriptor3D";
//		String cat2id="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#constitutionalDescriptor";

		//Get decriptor by hardcoded id requiring 3D
		DescriptorImpl desc=qsar.getDescriptorImplByID(descriptorID3D);
		assertNotNull(desc);
		assertNull(desc.getParameters());
		assertTrue(desc.isRequires3D());
		assertEquals("descriptorProvider2", desc.getProvider().getName());
		assertEquals("http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain", desc.getDefinition());
		assertEquals("net.bioclipse.qsar.test.description3D", desc.getDescription());
	}

	@Test
	public void testGetDescriptorImplsByIDWithParameters(){
		//Matches plugin.xml
		String descriptorIDParams="net.bioclipse.qsar.test.descriptorParams";

		//Get decriptor by hardcoded id with parameters
		DescriptorImpl desc=qsar.getDescriptorImplByID(descriptorIDParams);
		assertNotNull(desc);
		assertNotNull(desc.getParameters());
		
		assertEquals(2, desc.getParameters().size());

		List<String> paramKeys=new ArrayList<String>();
		List<String> paramVals=new ArrayList<String>();
		List<String> paramDesc=new ArrayList<String>();
		for (DescriptorParameter param: desc.getParameters()){
			System.out.println("Param: " + param.getKey() + " = " + param.getDefaultvalue() + " ; " + param.getDescription());
			paramKeys.add(param.getKey());
			paramVals.add(param.getDefaultvalue());
			paramDesc.add(param.getDescription());
		}
		
		assertEquals(paramKeys.get(0), "bogusParameter");
		assertEquals(paramKeys.get(1), "bogusParameter2");
		
		assertEquals(paramVals.get(0), "true");
		assertEquals(paramVals.get(1), "101.67");
		
		assertEquals(paramDesc.get(0), "description1");
		assertEquals(paramDesc.get(1), "description2");

	}
	
  @Test
  public void testGetPreferredImpl(){
      String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#BCUT";
      String expectedImplID="net.bioclipse.qsar.test.descriptor1";

      DescriptorImpl impl = qsar.getPreferredImpl( descriptorID );
      assertNotNull( impl );
      assertEquals( expectedImplID,impl.getId());

  }

  @Test
  public void testDescriptorImpl(){
      String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#BCUT";
      String expectedImplID="net.bioclipse.qsar.test.descriptor1";

      DescriptorImpl impl = qsar.getPreferredImpl( descriptorID );
      assertNotNull( impl );
      assertEquals( expectedImplID,impl.getId());

  }

	
	
	 /*====================================================
   * Calculations below
   * ====================================================
   */

	
	@Test
	public void testCalculateSingleMolSingleDescriptor() throws BioclipseException{

		IMolecule mol=new SMILESMolecule("C1CCCCC1CC(CC)CC");
		String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain";
		
		IDescriptorResult res=qsar.calculate(mol, descriptorID);
		assertNotNull(res);
		assertNull("Error message was not null but: " + res.getErrorMessage(), res.getErrorMessage());
		assertEquals(descriptorID, res.getDescriptor().getOntologyid());
		assertEquals(3, res.getLabels().length);
		assertEquals(3, res.getValues().length);

		//Dummycalculator returns 15.2456 as first result in array
		assertEquals(new Float(15.2456), res.getValues()[0]);
		
	}

	@Test
	public void testCalculateDescriptorWithError() throws BioclipseException{

		IMolecule mol=new SMILESMolecule("C1CCCCC1CC(CC)CC");
		String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#aromaticBondsCount";

		IDescriptorResult res=qsar.calculate(mol, descriptorID);

		assertNotNull(res);
		
		System.out.println("Error message: " + res.getErrorMessage());
		assertEquals("Failed to calculate descriptor '" +
				descriptorID + "'. Reason: Unknown", 
				res.getErrorMessage());

	}


	@Test
	public void testCalculateMultipleMolMultipleDescriptor() throws BioclipseException{
		
		IMolecule mol1=new SMILESMolecule("C1CCCCC1CC(CC)CC");
		IMolecule mol2=new SMILESMolecule("C1CCCCC1CC(CC)CCCCCO");
//		String descriptorID="net.bioclipse.qsar.test.descriptor3";
//		String descriptorID2="net.bioclipse.qsar.test.descriptor2";
		String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain";
		String descriptorID2="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#BCUT";
	
		List<IMolecule> mols=new ArrayList<IMolecule>();
		List<String> descs=new ArrayList<String>();
		
		mols.add(mol1);
		mols.add(mol2);
		descs.add(descriptorID);
		descs.add(descriptorID2);

		DescriptorCalculationResult calres = qsar.calculate(mols, descs);
		Map<? extends IMolecule, List<IDescriptorResult>> res = calres.getResultMap();
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

		System.out.println("Mol: " +
				mol1.toSMILES());
		System.out.println("Desc " + dres1.getDescriptor().getOntologyid() +": " + 
				dres1.getLabels()[0] + "=" + dres1.getValues()[0] +", " + 
				dres1.getLabels()[1] + "=" + dres1.getValues()[1] +", " + 
				dres1.getLabels()[2] + "=" + dres1.getValues()[2] +", ");

		System.out.println("Mol: " + 
		                   mol1.toSMILES());
		System.out.println("Desc " + dres11.getDescriptor().getOntologyid() +": " + 
				dres11.getLabels()[0] + "=" + dres11.getValues()[0] +", " + 
				dres11.getLabels()[1] + "=" + dres11.getValues()[1] +", " + 
				dres11.getLabels()[2] + "=" + dres11.getValues()[2] +", ");

		System.out.println("Mol: " + 
		                   mol2.toSMILES());
		System.out.println("Desc " + dres2.getDescriptor().getOntologyid() +": " + 
				dres2.getLabels()[0] + "=" + dres2.getValues()[0] +", " + 
				dres2.getLabels()[1] + "=" + dres2.getValues()[1] +", " + 
				dres2.getLabels()[2] + "=" + dres2.getValues()[2] +", ");

		System.out.println("Mol: " + 
                       mol2.toSMILES());
		System.out.println("Desc " + dres22.getDescriptor().getOntologyid() +": " + 
				dres22.getLabels()[0] + "=" + dres22.getValues()[0] +", " + 
				dres22.getLabels()[1] + "=" + dres22.getValues()[1] +", " + 
				dres22.getLabels()[2] + "=" + dres22.getValues()[2] +", ");

		assertEquals(3, dres1.getLabels().length);
		assertEquals(3, dres1.getValues().length);
		assertEquals("label1", dres1.getLabels()[0]);
		assertEquals("label2", dres1.getLabels()[1]);
		assertEquals("label3", dres1.getLabels()[2]);
		assertEquals(new Float(15.2456), dres1.getValues()[0]);
		assertEquals(new Float(47.01), dres1.getValues()[1]);
		assertEquals(new Float(-6.44), dres1.getValues()[2]);
		

	}
	
	 @Test
	  public void testCalculateMolDescMap() throws BioclipseException{
	    
	    IMolecule mol1=new SMILESMolecule("C1CCCCC1CC(CC)CC");
	    IMolecule mol2=new SMILESMolecule("C1CCCCC1CC(CC)CCCCCO");
	    String descriptorID="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain";
	    String descriptorID2="http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#BCUT";

	    Descriptor desc1=qsar.getDescriptorByID( descriptorID );
      Descriptor desc2=qsar.getDescriptorByID( descriptorID2 );
      DescriptorImpl impl1 = qsar.getPreferredImpl( descriptorID );
      DescriptorImpl impl2 = qsar.getPreferredImpl( descriptorID2 );
	    
      DescriptorType dtype1 = qsar.createDescriptorType( null, null, desc1, impl1, null );
      DescriptorType dtype2 = qsar.createDescriptorType( null, null, desc2, impl2, null );
      
      Map<IMolecule, List<DescriptorType>> moldescmap=new HashMap<IMolecule, List<DescriptorType>>();
      List<DescriptorType> list1=new ArrayList<DescriptorType>();
      list1.add( dtype1 );
      list1.add( dtype2 );
      moldescmap.put( mol1, list1 );
      List<DescriptorType> list2=new ArrayList<DescriptorType>();
      list2.add( dtype2 );
      moldescmap.put( mol2, list2 );

      Map<? extends IMolecule, List<IDescriptorResult>> res = qsar.doCalculation( moldescmap, new NullProgressMonitor() );
	    assertNotNull("QSAR calculation returned NULL", res);
	    
	    List<IDescriptorResult> res1=res.get(mol1);
	    List<IDescriptorResult> res2=res.get(mol2);

	    assertEquals(2, res1.size());
	    assertEquals(1, res2.size());

	    IDescriptorResult dres1=res1.get(0);
	    IDescriptorResult dres11=res1.get(1);
	    IDescriptorResult dres2=res2.get(0);

	    assertNull(dres1.getErrorMessage());
	    assertNull(dres11.getErrorMessage());
	    assertNull(dres2.getErrorMessage());

	    System.out.println("Mol: " + 
	                       mol1.toSMILES());
	    System.out.println("Desc " + dres1.getDescriptor().getOntologyid() +": " + 
	        dres1.getLabels()[0] + "=" + dres1.getValues()[0] +", " + 
	        dres1.getLabels()[1] + "=" + dres1.getValues()[1] +", " + 
	        dres1.getLabels()[2] + "=" + dres1.getValues()[2] +", ");

	    System.out.println("Mol: " + 
                         mol1.toSMILES());
	    System.out.println("Desc " + dres11.getDescriptor().getOntologyid() +": " + 
	        dres11.getLabels()[0] + "=" + dres11.getValues()[0] +", " + 
	        dres11.getLabels()[1] + "=" + dres11.getValues()[1] +", " + 
	        dres11.getLabels()[2] + "=" + dres11.getValues()[2] +", ");

	    System.out.println("Mol: " + 
                         mol2.toSMILES());
	    System.out.println("Desc " + dres2.getDescriptor().getOntologyid() +": " + 
	        dres2.getLabels()[0] + "=" + dres2.getValues()[0] +", " + 
	        dres2.getLabels()[1] + "=" + dres2.getValues()[1] +", " + 
	        dres2.getLabels()[2] + "=" + dres2.getValues()[2] +", ");

	    assertEquals(3, dres1.getLabels().length);
	    assertEquals(3, dres1.getValues().length);
	    assertEquals("label1", dres1.getLabels()[0]);
	    assertEquals("label2", dres1.getLabels()[1]);
	    assertEquals("label3", dres1.getLabels()[2]);
	    assertEquals(new Float(15.2456), dres1.getValues()[0]);
	    assertEquals(new Float(47.01), dres1.getValues()[1]);
	    assertEquals(new Float(-6.44), dres1.getValues()[2]);
	    

	  }


}
