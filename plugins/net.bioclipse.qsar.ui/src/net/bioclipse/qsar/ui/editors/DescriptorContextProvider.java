package net.bioclipse.qsar.ui.editors;

import net.bioclipse.qsar.descriptor.model.Descriptor;

import org.eclipse.help.IContext;
import org.eclipse.help.IContextProvider;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class DescriptorContextProvider implements IContextProvider {

	public IContext getContext(Object target) {

		if (target instanceof Tree) {
			Tree tree=(Tree) target;
			if (tree.getSelection()!=null && tree.getSelection().length>0){
				TreeItem a = tree.getSelection()[0];
				if (a.getData() instanceof Descriptor) {
					Descriptor desc= (Descriptor) a.getData();
					return new DescriptorContext(desc);
				}
			}
		}

		if (target instanceof Descriptor) {
			Descriptor desc= (Descriptor) target;
			return new DescriptorContext(desc);
		}
		return null;
	}

	public int getContextChangeMask() {
		return SELECTION;
	}

	public String getSearchExpression(Object target) {
		return null;
	}

}
