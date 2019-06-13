package org.processmining.kavyabaskar.plugins;

import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.eventstream.core.interfaces.XSEventStream;
import org.processmining.eventstream.readers.acceptingpetrinet.XSEventStreamToAcceptingPetriNetReader;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.kavyabaskar.algorithms.CompareStreamInductiveMinerStreamAlphaMinerAPNXSEventReaderImpl;
import org.processmining.kavyabaskar.parameters.CompareStreamInductiveMinerStreamAlphaMinerParameters;
import org.processmining.stream.core.enums.CommunicationType;
import org.processmining.streamabstractrepresentation.plugins.AbstractUncoupledStreamMinerDefaultDialogsPlugin;


@Plugin(name = "Compare Accepting Petri Net(s) (Inductive Miner and Alpha Miner)", parameterLabels = { "Event Stream", "Parameters" }, 
	    returnLabels = { "Inductive Miner and Alpha Miner (Event Stream)" }, returnTypes = { XSEventStreamToAcceptingPetriNetReader.class }, userAccessible = true)
public class CompareStreamInductiveMinerStreamAlphaMinerAcceptingPetriNetPlugin extends AbstractUncoupledStreamMinerDefaultDialogsPlugin<XSEventStreamToAcceptingPetriNetReader, CompareStreamInductiveMinerStreamAlphaMinerParameters> {

	/**
	 * The plug-in variant that runs in any context and requires a parameters.
	 * 
	 * @param context The context to run in.
	 * @param input1 The first input.
	 * @param input2 The second input.
	 * @param parameters The parameters to use.
	 * @return The output.
	 */
	@UITopiaVariant(affiliation = "Eindhoven University of Technology", author = "K. Baskar", email = "k.baskar@student.tue.nl")
	@PluginVariant(variantLabel = "Compare Accepting Petri Net(s) (Inductive Miner and Alpha Miner)", requiredParameterLabels = { 0 })
	public XSEventStreamToAcceptingPetriNetReader apply1(final UIPluginContext context, final XSEventStream stream) {
		// Apply the algorithm depending on whether a connection already exists.
	    return super.apply(context, stream);
	}
	
	/*for 2nd miner
	public XSEventStreamToAcceptingPetriNetReader apply2(final UIPluginContext context, final XSEventStream stream) {
		// Apply the algorithm depending on whether a connection already exists.
	    return super.apply2(context, stream);
	}
	*/
	
	/**
	 * The plug-in variant that runs in any context and uses the default parameters.
	 * 
	 * @param context The context to run in.
	 * @param input1 The first input.
	 * @param input2 The second input.
	 * @return The output.
	 */
	@UITopiaVariant(affiliation = "Eindhoven University of Technology", author = "K. Baskar", email = "k.baskar@student.tue.nl")
	@PluginVariant(variantLabel = "Compare Accepting Petri Net(s) (Inductive Miner and Alpha Miner), Default Parameters", requiredParameterLabels = { 0 })
	public XSEventStreamToAcceptingPetriNetReader applyDefault1(final PluginContext context,
			final XSEventStream stream) {
		// Get the default parameters.
	    //YourParameters parameters = new YourParameters(input1, input2);
		// Apply the algorithm depending on whether a connection already exists.
		return super.apply(context, stream, getFreshParameters(context));
	}
	
	/*for 2nd miner
	public XSEventStreamToAcceptingPetriNetReader applyDefault2(final PluginContext context,
			final XSEventStream stream) {
		// Get the default parameters.
	    //YourParameters parameters = new YourParameters(input1, input2);
		// Apply the algorithm depending on whether a connection already exists.
		return super.apply2(context, stream, getFreshParameters(context));
	}
	*/
	
	protected XSEventStreamToAcceptingPetriNetReader constructReader(PluginContext context, CommunicationType comm,
			CompareStreamInductiveMinerStreamAlphaMinerParameters parameters) {
		return new CompareStreamInductiveMinerStreamAlphaMinerAPNXSEventReaderImpl(context, parameters);
	}

/*	protected XSEventStreamToAcceptingPetriNetReader constructReader2(PluginContext context, CommunicationType comm,
			StreamInductiveMinerStreamAlphaMinerParameters parameters) {
		return new UncoupledStreamAlphaAPNXSEventReaderImpl(context, parameters);
	}
*/	
	protected CompareStreamInductiveMinerStreamAlphaMinerParameters getFreshParameters(PluginContext context) {
		return new CompareStreamInductiveMinerStreamAlphaMinerParameters();
	}
}
	