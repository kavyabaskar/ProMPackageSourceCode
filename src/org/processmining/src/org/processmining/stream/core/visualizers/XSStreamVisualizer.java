package org.processmining.stream.core.visualizers;

import javax.swing.JComponent;

import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSStream;
import org.processmining.stream.core.visualizers.abstr.AbstractXSRunnableXSVisualizableVisualizer;

@Plugin(name = "XSStream Visualizer", parameterLabels = { "XSStream" }, returnLabels = {
		"XSStream Visualizer" }, returnTypes = { JComponent.class })
@Visualizer
public class XSStreamVisualizer extends AbstractXSRunnableXSVisualizableVisualizer<XSStream<?>> {

	public XSStreamVisualizer(XSStream<?> visualized) {
		super(visualized);
	}

	@UITopiaVariant(affiliation = "Eindhoven University of Technology", author = "S.J. van Zelst", email = "s.j.v.zelst@tue.nl")
	@PluginVariant(variantLabel = "XSStream Visualizer", requiredParameterLabels = { 0 })
	@Visualizer
	public static JComponent visualize(final PluginContext context,
			final XSStream<? extends XSDataPacket<?, ?>> stream) {
		return XSStreamVisualizer.visualize(stream);
	}

	public static JComponent visualize(final XSStream<? extends XSDataPacket<?, ?>> stream) {
		XSStreamVisualizer visualizer = new XSStreamVisualizer(stream);
		return visualizer.show();
	}

}
