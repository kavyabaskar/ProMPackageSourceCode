package org.processmining.kavyabaskar.algorithms;

import org.deckfour.xes.classification.XEventClass;
import org.processmining.acceptingpetrinet.models.AcceptingPetriNet;
import org.processmining.acceptingpetrinet.models.impl.AcceptingPetriNetFactory;
import org.processmining.alphaminer.abstractions.AlphaClassicAbstraction;
import org.processmining.alphaminer.abstractions.impl.AlphaClassicAbstractionImpl;
import org.processmining.alphaminer.algorithms.AlphaClassicMinerImpl;
import org.processmining.alphaminer.parameters.AlphaMinerParameters;
import org.processmining.alphaminer.parameters.AlphaVersion;
import org.processmining.basicutils.parameters.PluginParameters;
import org.processmining.eventstream.readers.abstr.AbstractXSEventReader;
import org.processmining.eventstream.readers.acceptingpetrinet.AcceptingPetriNetXSReaderResultVisualizer;
import org.processmining.eventstream.readers.acceptingpetrinet.XSEventStreamToAcceptingPetriNetReader;
import org.processmining.framework.packages.PackageManager.Canceller;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.util.Pair;
import org.processmining.kavyabaskar.parameters.CompareStreamInductiveMinerStreamAlphaMinerParameters;
import org.processmining.logabstractions.factories.StartEndActivityFactory;
import org.processmining.logabstractions.models.DirectlyFollowsAbstraction;
import org.processmining.logabstractions.models.EndActivityAbstraction;
import org.processmining.logabstractions.models.StartActivityAbstraction;
import org.processmining.plugins.InductiveMiner.dfgOnly.Dfg;
import org.processmining.plugins.InductiveMiner.dfgOnly.DfgImpl;
import org.processmining.plugins.InductiveMiner.dfgOnly.DfgMiner;
import org.processmining.plugins.InductiveMiner.dfgOnly.DfgMiningParameters;
import org.processmining.plugins.InductiveMiner.dfgOnly.DfgMiningParametersIMd;
import org.processmining.ptconversions.pn.ProcessTree2Petrinet;
import org.processmining.ptconversions.pn.ProcessTree2Petrinet.InvalidProcessTreeException;
import org.processmining.ptconversions.pn.ProcessTree2Petrinet.NotYetImplementedException;
import org.processmining.ptconversions.pn.ProcessTree2Petrinet.PetrinetWithMarkings;
import org.processmining.stream.reader.views.XSReaderSequenceOfResultsView;
import org.processmining.streamabstractrepresentation.algorithms.UncoupledXEventClassDFAReaderImpl;
import org.processmining.streamabstractrepresentation.parameters.UnCoupledAbstractionSchemeParametersImpl;

public class CompareStreamInductiveMinerStreamAlphaMinerAPNXSEventReaderImpl extends AbstractXSEventReader<AcceptingPetriNet, AcceptingPetriNet, CompareStreamInductiveMinerStreamAlphaMinerParameters>
		implements XSEventStreamToAcceptingPetriNetReader {

	private final PluginContext context;
	private final UncoupledXEventClassDFAReaderImpl<Object, UnCoupledAbstractionSchemeParametersImpl> dfaDelegate;

	public CompareStreamInductiveMinerStreamAlphaMinerAPNXSEventReaderImpl(PluginContext context,
			final CompareStreamInductiveMinerStreamAlphaMinerParameters params) {
		super("Stream Inductive and Stream Alpha Miner (Accepting Petri Net, CxA: "
				+ params.getCaseAdministrationDataStructureType1().getName() + "-"
				+ params.getCaseAdministrationDataStructureParameters1().toString() + "; AxA: "
				+ params.getInternalRepresentationDataStructureType1().getName() + "-"
				+ params.getInternalRepresentationDataStructureType1().toString() + ")"
				
				+ params.getCaseAdministrationDataStructureType2().getName() + "-"
				+ params.getCaseAdministrationDataStructureParameters2().toString() + "; AxA: "
				+ params.getInternalRepresentationDataStructureType2().getName() + "-"
				+ params.getInternalRepresentationDataStructureType2().toString() + ")",
				new XSReaderSequenceOfResultsView<>("Visualizer of: Stream Inductive and Stream Alpha Miner (Accepting Petri Net)", null,
						params.getRefreshRate(), AcceptingPetriNetXSReaderResultVisualizer.instance(context)),
				params);
		this.context = context;
		dfaDelegate = new UncoupledXEventClassDFAReaderImpl<Object, UnCoupledAbstractionSchemeParametersImpl>(
				"DFA Reader (Stream Alpha Miner)", params);
		((XSReaderSequenceOfResultsView<AcceptingPetriNet>) getVisualization()).setReader(this);
	}

	protected AcceptingPetriNet discover(Dfg dfg, DfgMiningParameters dfgParams) {
		PetrinetWithMarkings pnwm;
		AcceptingPetriNet result = null;
		try {
			pnwm = ProcessTree2Petrinet.convert(DfgMiner.mine(dfg, dfgParams, new Canceller() {
				public boolean isCancelled() {
					return false;
				}
			}));
			result = AcceptingPetriNetFactory.createAcceptingPetriNet(pnwm.petrinet, pnwm.initialMarking,
					pnwm.finalMarking);
		} catch (NotYetImplementedException | InvalidProcessTreeException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	protected AcceptingPetriNet computeCurrentResult1() {
		DirectlyFollowsAbstraction<XEventClass> dfa = dfaDelegate.getCurrentResult1();
		Dfg dfg = new DfgImpl(dfa.getAllGEQThreshold().size());
		for (Pair<XEventClass, XEventClass> p : dfa.getAllGEQThreshold()) {
			dfg.addDirectlyFollowsEdge(p.getFirst(), p.getSecond(), (long) dfa.getValue(p.getFirst(), p.getSecond()));
		}
		DfgMiningParameters dfgParams = new DfgMiningParametersIMd();
		dfgParams.setDebug(getParameters().getMessageLevel() >= PluginParameters.DEBUG);
		return discover(dfg, dfgParams);		
	}
	
	protected AcceptingPetriNet computeCurrentResult2() {
		DirectlyFollowsAbstraction<XEventClass> dfa = dfaDelegate.getCurrentResult2();
		AlphaClassicAbstraction<XEventClass> abstraction = new AlphaClassicAbstractionImpl<>(dfa.getEventClasses(), dfa,
				constructStartActivityAbstraction(dfa), constructEndActivityAbstraction(dfa));
		AlphaMinerParameters params = new AlphaMinerParameters();
		params.setVersion(AlphaVersion.CLASSIC);
		AlphaClassicMinerImpl<XEventClass, AlphaClassicAbstraction<XEventClass>, AlphaMinerParameters> miner = new AlphaClassicMinerImpl<>(
				params, abstraction, context);
		return miner.runAccPN();
	}	
	
	private StartActivityAbstraction<XEventClass> constructStartActivityAbstraction(
			DirectlyFollowsAbstraction<XEventClass> dfa) {
		double[] startActivities = new double[dfa.getEventClasses().length];
		for (int i = 0; i < startActivities.length; i++) {
			if (dfa.getAllGeqForColumn(i).isEmpty()) {
				startActivities[i] = StartEndActivityFactory.DEFAULT_THRESHOLD_BOOLEAN;
			}
		}
		return StartEndActivityFactory.constructStartActivityAbstraction(dfa.getEventClasses(), startActivities,
				StartEndActivityFactory.DEFAULT_THRESHOLD_BOOLEAN);
	}

	private EndActivityAbstraction<XEventClass> constructEndActivityAbstraction(
			DirectlyFollowsAbstraction<XEventClass> dfa) {
		double[] endActivities = new double[dfa.getEventClasses().length];
		for (int i = 0; i < endActivities.length; i++) {
			if (dfa.getAllGeqForRow(i).isEmpty()) {
				endActivities[i] = StartEndActivityFactory.DEFAULT_THRESHOLD_BOOLEAN;
			}
		}
		return StartEndActivityFactory.constructEndActivityAbstraction(dfa.getEventClasses(), endActivities,
				StartEndActivityFactory.DEFAULT_THRESHOLD_BOOLEAN);
	}

	public void processNewXSEvent(String caseId, XEventClass activity) {
		dfaDelegate.processNewXSEvent(caseId, activity);
	}
}
