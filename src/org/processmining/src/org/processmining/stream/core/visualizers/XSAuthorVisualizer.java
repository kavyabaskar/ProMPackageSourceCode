package org.processmining.stream.core.visualizers;

import javax.swing.JComponent;

import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.stream.core.interfaces.XSAuthor;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.visualizers.abstr.AbstractXSRunnableXSVisualizableVisualizer;

@Plugin(name = "XSAuthor Visualizer", parameterLabels = { "XSAuthor" }, returnLabels = {
		"XSAuthor Visualizer" }, returnTypes = { JComponent.class })
@Visualizer
public class XSAuthorVisualizer
		extends AbstractXSRunnableXSVisualizableVisualizer<XSAuthor<? extends XSDataPacket<?, ?>>> {

	public XSAuthorVisualizer(final XSAuthor<? extends XSDataPacket<?, ?>> author) {
		super(author);
	}

	@UITopiaVariant(affiliation = "Eindhoven University of Technology", author = "S.J. van Zelst", email = "s.j.v.zelst@tue.nl")
	@PluginVariant(variantLabel = "XSAuthor Visualizer", requiredParameterLabels = { 0 })
	@Visualizer
	public static <T extends XSDataPacket<?, ?>> JComponent visualize(final PluginContext context,
			final XSAuthor<T> author) {
		return XSAuthorVisualizer.visualize(author);
	}

	public static <T extends XSDataPacket<?, ?>> JComponent visualize(final XSAuthor<T> author) {
		XSAuthorVisualizer visualizer = new XSAuthorVisualizer(author);
		return visualizer.show();
	}

}
