package net.bioclipse.qsar.ui.editors;

import java.util.ArrayList;
import java.util.List;

import net.bioclipse.qsar.descriptor.model.Descriptor;

import org.eclipse.help.IContext;
import org.eclipse.help.IHelpResource;

public class DescriptorContext implements IContext {

	private Descriptor desc;

	public DescriptorContext(Descriptor desc) {
		this.desc=desc;
	}

	public IHelpResource[] getRelatedTopics() {

		List<IHelpResource> retlist=new ArrayList<IHelpResource>();

		if (desc.getId().indexOf("#")>=0){
			
			final String shortPart=desc.getId().substring(desc.getId().lastIndexOf("#")+1);
			
			IHelpResource res=new IHelpResource(){

				public String getHref() {
						return "net.bioclipse.qsar.ui/html/descriptors.html#" + shortPart;
				}
				///help/topic/net.bioclipse.qsar.ui/html/descriptors.html
				public String getLabel() {
					return "Descriptor: " + shortPart;
				}
				
			};
			retlist.add(res);
		}

		IHelpResource res=new IHelpResource(){

			public String getHref() {
					return "net.bioclipse.qsar.ui/html/descriptors.html";
			}
			///help/topic/net.bioclipse.qsar.ui/html/descriptors.html
			public String getLabel() {
				return "Descriptor Dictionary";
			}
			
		};
		
		retlist.add(res);

		return retlist.toArray(new IHelpResource[0]);
	}

	public String getText() {
		return "Definition:\n" + desc.getDefinition() + "\n\nDescription:\n" + desc.getDescription();
	}

}
