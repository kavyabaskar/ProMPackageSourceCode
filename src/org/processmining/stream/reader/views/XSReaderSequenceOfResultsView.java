package org.processmining.stream.reader.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.processmining.framework.util.Pair;
import org.processmining.stream.core.abstracts.AbstractXSRunnable;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSReader;
import org.processmining.stream.core.interfaces.XSReaderResultVisualizer;
import org.processmining.stream.core.interfaces.XSVisualization;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class XSReaderSequenceOfResultsView<T> extends AbstractXSRunnable implements XSVisualization<T> {

	//private final JComponent wholevisualization = new JPanel();
	private final JComponent visualization1 = new JPanel();
	private final JComponent visualization2 = new JPanel();

	private final XSReaderResultVisualizer<T> readerResultVisualizer;
	private final List<JComponent> results = new ArrayList<>();
	private final List<JComponent> results2 = new ArrayList<>();
	private XSReader<? extends XSDataPacket<?, ?>, T> reader;
	private final long SLEEP_TIME = 1000l;

	public void setReader(XSReader<? extends XSDataPacket<?, ?>, T> reader) {
		this.reader = reader;
	}

	private final int refreshRate;
	private long lastSample = 0;
	private long lastPacketAtReader = 0;

	private final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
	private final JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
	private final JCheckBox autoUpdate = SlickerFactory.instance().createCheckBox("Refresh view", false);
	private static final String PACKETS_RECEIVEC_LABEL_PREFIX = "Packets Received: ";
	private static final String PACKETS_RECEIVEC_LABEL_PREFIX2 = "Packets Received: ";
	private final JLabel packetsReceived = new JLabel(PACKETS_RECEIVEC_LABEL_PREFIX + "0");
	private final JLabel packetsReceived2 = new JLabel(PACKETS_RECEIVEC_LABEL_PREFIX2 + "0");
	private final JComponent modelInteractionContainer;
	private final JComponent modelInteractionContainer2;
	private final JButton triggerNewResultButton = SlickerFactory.instance().createButton("Update Result");
	private final JButton triggerNewResultButton2 = SlickerFactory.instance().createButton("Update Result");
	private static final String PACKETS_USED_LABEL_PREFIX = "#Models: "; 
	private static final String PACKETS_USED_LABEL_PREFIX2 = "#Models: ";
	private final JLabel numberOfPackets = new JLabel(PACKETS_USED_LABEL_PREFIX + "0");
	private final JLabel numberOfPackets2 = new JLabel(PACKETS_USED_LABEL_PREFIX2 + "0");

	private JComponent currentResult1 = SlickerFactory.instance().createLabel("No result to show yet.");
	private JComponent currentResult2 = SlickerFactory.instance().createLabel("No result to show yet!!!!!!!!!.");
	//	private final JSplitPane resultAndSliderPane;

	public XSReaderSequenceOfResultsView(String name, XSReader<? extends XSDataPacket<?, ?>, T> reader, int refrehsRate,
			XSReaderResultVisualizer<T> readerResultVisualizer) {
		super(name);
		this.reader = reader;
		this.refreshRate = refrehsRate;
		this.readerResultVisualizer = readerResultVisualizer;
		modelInteractionContainer = constructModelInteractionContainer(refrehsRate);
		visualization1.setLayout(new BorderLayout());
		visualization1.add(currentResult1, BorderLayout.CENTER);
		//visualization1.add(currentResult2, BorderLayout.WEST);
		visualization1.add(modelInteractionContainer, BorderLayout.SOUTH);
		
		
		modelInteractionContainer2 = constructModelInteractionContainer2(refrehsRate);
		visualization2.setLayout(new BorderLayout());
		visualization2.add(currentResult2, BorderLayout.CENTER);
		//visualization1.add(currentResult2, BorderLayout.WEST);
		visualization2.add(modelInteractionContainer2, BorderLayout.SOUTH);
		//wholevisualization.add(visualization1, BorderLayout.EAST);
		
	}

	public JComponent asComponent() {
		return visualization1;
	}
	
	public JComponent asComponent2() {
		return visualization2;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComponent constructModelInteractionContainer(int refreshRate) {
		JComponent container = new JPanel();
		container.setLayout(new BorderLayout());
		// draw the major tick marks (one for every tick label)
		slider.setMajorTickSpacing(1);
		// draw the tick marks
		slider.setPaintTicks(true);
		Dictionary labelTable = new Hashtable();
		labelTable.put(new Integer(0), new JLabel("no model yet"));
		slider.setLabelTable(labelTable);
		// draw the tick mark labels
		slider.setPaintLabels(true);

		slider.addChangeListener(new ChangeListener() {
			//Listen to slider.
			public void stateChanged(ChangeEvent e) {
				numberOfPackets.setText(PACKETS_USED_LABEL_PREFIX + (((JSlider)e.getSource()).getValue()+1));
				if (!slider.getValueIsAdjusting()) {
					int solutionToShow = slider.getValue();
					selectResult(solutionToShow);
					visualization1.revalidate();
					visualization1.repaint();
					
					
					//visualization2.revalidate();
					//visualization2.repaint();
					//wholevisualization.revalidate();
					//wholevisualization.repaint();
				}
			}
		});
		
		//packetsReceived.setText(PACKETS_RECEIVEC_LABEL_PREFIX + slider.getValue());

		container.add(slider, BorderLayout.CENTER);
		JComponent autoRefreshNewModelContainer = new JPanel();
		if (refreshRate != -1) {
			autoRefreshNewModelContainer.add(autoUpdate);
		}
		autoRefreshNewModelContainer.add(packetsReceived);		

		triggerNewResultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshResultsView(true);
				
			}
		});

		autoRefreshNewModelContainer.add(triggerNewResultButton);
		autoRefreshNewModelContainer.add(numberOfPackets);
		container.add(autoRefreshNewModelContainer, BorderLayout.EAST);
		return container;
	}

	//for visualization2
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComponent constructModelInteractionContainer2(int refreshRate) {
		JComponent container = new JPanel();
		container.setLayout(new BorderLayout());
		// draw the major tick marks (one for every tick label)
		slider2.setMajorTickSpacing(1);
		// draw the tick marks
		slider2.setPaintTicks(true);
		Dictionary labelTable = new Hashtable();
		labelTable.put(new Integer(0), new JLabel("no model yet"));
		slider2.setLabelTable(labelTable);
		// draw the tick mark labels
		slider2.setPaintLabels(true);

		slider2.addChangeListener(new ChangeListener() {
			//Listen to slider.
			public void stateChanged(ChangeEvent e) {
				numberOfPackets2.setText(PACKETS_USED_LABEL_PREFIX2 + (((JSlider)e.getSource()).getValue()+1));
				if (!slider2.getValueIsAdjusting()) {
					int solutionToShow = slider2.getValue();
					selectResult2(solutionToShow);
					visualization2.revalidate();
					visualization2.repaint();
					//wholevisualization.revalidate();
					//wholevisualization.repaint();
				}
			}
		});
		
		container.add(slider2, BorderLayout.CENTER);
		JComponent autoRefreshNewModelContainer = new JPanel();
		if (refreshRate != -1) {
			autoRefreshNewModelContainer.add(autoUpdate);
		}
		autoRefreshNewModelContainer.add(packetsReceived2);		

		triggerNewResultButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshResultsView2(true);
				
			}
		});

		autoRefreshNewModelContainer.add(triggerNewResultButton2);
		autoRefreshNewModelContainer.add(numberOfPackets2);
		container.add(autoRefreshNewModelContainer, BorderLayout.EAST);
		return container;
	}
	
	//FIXME: Change by using a Queue of results via update function
	protected void workPackage() {
		// prevent reader from receiving new messages when visualizing.
		boolean sleep = false;
		synchronized (reader.getDeliveryLock()) {
			if (lastPacketAtReader == reader.getNumberOfPacketsReceived()) {
			//if (lastPacketAtReader == lastPacketAtReader) {
				sleep = true;
				Thread.yield();
			} else {
				lastPacketAtReader = reader.getNumberOfPacketsReceived();
				//lastPacketAtReader = lastPacketAtReader + 50;
				//lastPacketAtReader = 50;

				packetsReceived.setText(PACKETS_RECEIVEC_LABEL_PREFIX + lastPacketAtReader);
				packetsReceived2.setText(PACKETS_RECEIVEC_LABEL_PREFIX2 + lastPacketAtReader);
				//if ((lastPacketAtReader - lastSample) / refreshRate > 0) {
				
				
				if (reader.getNumberOfPacketsReceived()%50 < 10) {
					refreshResultsView(true);
					refreshResultsView2(true);
					//refreshResultsView(autoUpdate.isSelected());
					lastSample = lastPacketAtReader;
					
					visualization1.revalidate();
					visualization1.repaint();
					
					
					visualization2.revalidate();
					visualization2.repaint();
					
					
					//wholevisualization.revalidate();
					//wholevisualization.repaint();
				}
				visualization1.revalidate();
				visualization1.repaint();
				
				visualization2.revalidate();
				visualization2.repaint();
				
			}
		}
	
		if (sleep) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void refreshResultsView(boolean updateToLatest) {
		T curRes = null;
		synchronized (reader.getDeliveryLock()) {
			lastPacketAtReader = reader.getNumberOfPacketsReceived();
			//lastPacketAtReader = 50;

			curRes = reader.getCurrentResult1();
		}
		if (curRes != null) {
			results.add(readerResultVisualizer.visualize(curRes));
			slider.setMaximum(results.size() - 1);
			@SuppressWarnings("rawtypes")
			Dictionary labelTable = slider.getLabelTable();
			labelTable.put(new Integer(results.size() - 1), new JLabel(String.valueOf(lastPacketAtReader)));
			slider.setLabelTable(labelTable);
			if (updateToLatest || results.size() == 1) {
				selectResult(results.size() - 1);
				slider.setValue(results.size() - 1);
			}
			visualization1.revalidate();
			visualization1.repaint();
			
			
			//visualization2.revalidate();
			//visualization2.repaint();
			
			//wholevisualization.revalidate();
			//wholevisualization.repaint();
		}
	}

	//for visualization2
	@SuppressWarnings("unchecked")
	private void refreshResultsView2(boolean updateToLatest) {
		T curRes = null;
		synchronized (reader.getDeliveryLock()) {
			lastPacketAtReader = reader.getNumberOfPacketsReceived();
			//lastPacketAtReader = 50;

			curRes = reader.getCurrentResult2();
		}
		if (curRes != null) {
			results2.add(readerResultVisualizer.visualize(curRes));
			slider2.setMaximum(results2.size() - 1);
			@SuppressWarnings("rawtypes")
			Dictionary labelTable = slider2.getLabelTable();
			labelTable.put(new Integer(results2.size() - 1), new JLabel(String.valueOf(lastPacketAtReader)));
			slider2.setLabelTable(labelTable);
			if (updateToLatest || results2.size() == 1) {
				selectResult2(results2.size() - 1);
				slider2.setValue(results2.size() - 1);
			}
			visualization2.revalidate();
			visualization2.repaint();
			
			
			//visualization2.revalidate();
			//visualization2.repaint();
			
			//wholevisualization.revalidate();
			//wholevisualization.repaint();
		}
	}
	
	private void selectResult(int i) {
		assert (i < results.size());
		assert (i >= 0);
		
		visualization1.remove(currentResult1);
		//visualization2.remove(currentResult2);
		currentResult1 = results.get(i);
		//currentResult2 = results2.get(i);
		visualization1.add(currentResult1, BorderLayout.CENTER);
		//visualization2.add(currentResult2, BorderLayout.CENTER);
		
		

		//Does not work if set to NORTH, SOUTH, EAST or WEST
	
		//wholevisualization.add(visualization1, BorderLayout.CENTER);
	
	}

	//for visualization2
	private void selectResult2(int j) {
		assert (j < results.size());
		assert (j >= 0);
		
		//visualization1.remove(currentResult1);
		visualization2.remove(currentResult2);
		//currentResult1 = results.get(i);
		currentResult2 = results2.get(j);
		//visualization1.add(currentResult1, BorderLayout.CENTER);
		visualization2.add(currentResult2, BorderLayout.CENTER);
	}
	/**
	public void triggerNewResultButton()
	{
	 int level = slider.getValue();
	 
	 JOptionPane.showMessageDialog(slider,
	  "Remember, this is for posterity.n"
	   + "Tell me...how do you feel?",
	  "Level " + level,
	  JOptionPane.INFORMATION_MESSAGE);
	 
	 numberOfPackets.setText(PACKETS_USED_LABEL_PREFIX + slider.getValue());
	}
	**/
	public void update(Pair<Date, String> message) {
	}

	public void update(String object) {
	}

	public void updateVisualization(Pair<Date, T> newArtifact) {	
	}

	public void updateVisualization(T newArtifact) {
	}

}
