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
package net.bioclipse.qsar;

public interface QSARConstants {

  public static final String BO_NAMESPACE = "http://www.blueobelisk.org/" +
  "ontologies/chemoinformatics-algorithms/#";
    
	//Preferences
	public static final String QSAR_PROVIDERS_ORDER_PREFERENCE = "QSARProvderOrder";
	public static final String QSAR_ONTOLOGY_FILES_PREFERENCE = "QSARontologyFiles";
	public static final String PREFS_SEPERATOR = ";";
	public static final String PREFS_INTERNAL_SEPERATOR = "|";
	
	//Properties
	public static final String QSAR_RESPONSE_PROPERTY="qsar.response";

	//Matches extension point
	public static final String DESCRIPTOR_EXTENSION_POINT = 
		"net.bioclipse.qsar.descriptorProvider";
	public static final Object PROVIDER_ELEMENT_NAME = "provider";
	public static final String DESCRIMPL_ELEMENT_NAME = "descriptorImpl";
	public static final String PARAMETER_ELEMENT_NAME = "parameter";
  public static final String PARAMETER_LISTED_VALUES = "listedvalue";

	 //Matches extension point
  public static final String RESPONSEUNITS_EXTENSION_POINT = 
    "net.bioclipse.qsar.responseUnit";
  public static final String RESPONSEUNITS_ELEMENT_NAME = "responseUnit";
  
  public static final String MISSING_VALUE_STRING = "NaN";
  public static final String QSAR_UNITS_PREFERENCE = "QsarUnits";

  public static final Object DISCOVERY_EP = "discovery";


}
