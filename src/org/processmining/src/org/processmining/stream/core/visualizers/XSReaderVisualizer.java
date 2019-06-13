package org.processmining.stream.core.visualizers;

import javax.swing.JComponent;

import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSReader;
import org.processmining.stream.core.visualizers.abstr.AbstractXSRunnableXSVisualizableVisualizer;

@Plugin(name = "XSReader Visualizer", parameterLabels = { "XSReader" }, returnLabels = {
		"XSReader Visualizer" }, returnTypes = { JComponent.class })
@Visualizer
public class XSReaderVisualizer
		extends AbstractXSRunnableXSVisualizableVisualizer<XSReader<? extends XSDataPacket<?, ?>, ?>> {

	public XSReaderVisualizer(final XSReader<? extends XSDataPacket<?, ?>, ?> reader) {
		super(reader);
	}

	@UITopiaVariant(affiliation = "Eindhoven University of Technology", author = "S.J. van Zelst", email = "s.j.v.zelst@tue.nl")
	@PluginVariant(variantLabel = "XSReader Visualizer", requiredParameterLabels = { 0 })
	@Visualizer
	public static <T extends XSDataPacket<?, ?>> JComponent visualize(final PluginContext context,
			final XSReader<T, ?> reader) {
		return XSReaderVisualizer.visualize(reader);
	}

	public static <T extends XSDataPacket<?, ?>> JComponent visualize(final XSReader<T, ?> reader) {
		XSReaderVisualizer visualizer = new XSReaderVisualizer(reader);
		return visualizer.show();
	}

}
