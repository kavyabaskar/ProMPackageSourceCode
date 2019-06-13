package org.processmining.stream.plugins;

import java.util.ArrayList;
import java.util.List;

import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.framework.util.Pair;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSStaticStream;

@Plugin(name = "Compare Two Static Streams", parameterLabels = { "Static Stream", "Static Stream" }, returnLabels = {
		"List of mismatch indices", "List of mismatches" }, returnTypes = { List.class, List.class })
public class CompareTwoStaticStreamsPlugin {

	@UITopiaVariant(author = "S.J. van Zelst", email = "s.j.v.zelst@tue.nl", affiliation = "Eindhoven University of Technology")
	@PluginVariant(requiredParameterLabels = { 0, 1 })
	public static <T extends XSDataPacket<?, ?>> Object[] apply(PluginContext context,
			XSStaticStream<T> xsStaticStream1, XSStaticStream<T> xsStaticStream2) {
		return apply(xsStaticStream1, xsStaticStream2);
	}

	public static <T extends XSDataPacket<?, ?>> Object[] apply(XSStaticStream<T> xsStaticStream1,
			XSStaticStream<T> xsStaticStream2) {
		List<Pair<Integer, Integer>> mismatchIndices = new ArrayList<Pair<Integer, Integer>>();
		List<Pair<String, String>> mismatches = new ArrayList<>();
		for (int i = 0; i < xsStaticStream1.size(); i++) {
			if (i > xsStaticStream2.size() - 1) {
				mismatchIndices.add(new Pair<Integer, Integer>(i, -1));
				mismatches.add(new Pair<String, String>(xsStaticStream1.get(i).toString(), "N/A"));

			} else if (!xsStaticStream1.get(i).equals(xsStaticStream2.get(i))) {
				mismatchIndices.add(new Pair<Integer, Integer>(i, i));
				mismatches.add(new Pair<String, String>(xsStaticStream1.get(i).toString(), xsStaticStream2.get(i).toString()));
			}
		}
		if (xsStaticStream2.size() > xsStaticStream1.size()) {
			for (int i = xsStaticStream1.size(); i < xsStaticStream2.size(); i++) {
				mismatchIndices.add(new Pair<Integer, Integer>(-1, i));
				mismatches.add(new Pair<String, String>("N/A", xsStaticStream2.get(i).toString()));
			}
		}
		System.out.println(mismatchIndices.toString());
		System.out.println(mismatches.toString());
		return new Object[] { mismatchIndices, mismatches };
	}

}
