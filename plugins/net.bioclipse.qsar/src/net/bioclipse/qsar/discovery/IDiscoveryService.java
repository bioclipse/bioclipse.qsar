package net.bioclipse.qsar.discovery;

import java.util.List;

import net.bioclipse.qsar.descriptor.model.DescriptorProvider;

public interface IDiscoveryService {
	
	public List<DescriptorProvider> discoverProvidersAndImpls();
	public String getName();

}
