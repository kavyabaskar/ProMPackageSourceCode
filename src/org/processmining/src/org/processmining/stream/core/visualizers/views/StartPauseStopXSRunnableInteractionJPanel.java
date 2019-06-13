package org.processmining.stream.core.visualizers.views;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.processmining.stream.core.interfaces.XSRunnable;
import org.processmining.stream.resource.images.StreamImageLoader;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class StartPauseStopXSRunnableInteractionJPanel extends JPanel {

	private static final long serialVersionUID = 808756019435302271L;
	private final XSRunnable runnable;

	private final JLabel playButton;
	private final MouseListener playButtonListener = new PlayButtonClickedMouseListener();

	private final JLabel stopButton;
	private final MouseListener stopButtonListener = new StopButtonClickedMouseListener();

	private final JLabel pauseButton;
	private final MouseListener pauseButtonListener = new PauseButtonClickedMouseListener();

	public StartPauseStopXSRunnableInteractionJPanel(XSRunnable runnable) {
		this.runnable = runnable;
		playButton = new JLabel(StreamImageLoader.getPlayButton());
		playButton.addMouseListener(playButtonListener);
		stopButton = new JLabel(StreamImageLoader.getStopButton());
		stopButton.addMouseListener(stopButtonListener);
		pauseButton = new JLabel(StreamImageLoader.getPauseButton());
		pauseButton.addMouseListener(pauseButtonListener);
		if (!runnable.isRunning()) {
			add(playButton, BorderLayout.LINE_START);
		} else {
			add(pauseButton, BorderLayout.LINE_START);
		}
		add(stopButton, BorderLayout.CENTER);
	}

	private class PlayButtonClickedMouseListener extends StartPauseStopMouseListener {

		public void mouseClicked(MouseEvent e) {
			runnable.start();
			addPauseStop();
		}
	}

	private class PauseButtonClickedMouseListener extends StartPauseStopMouseListener {

		public void mouseClicked(MouseEvent e) {
			runnable.pause();
			addStartStop();
		}
	}

	private class StopButtonClickedMouseListener extends StartPauseStopMouseListener {

		public void mouseClicked(MouseEvent e) {
			runnable.interrupt();
			playButton.removeMouseListener(playButtonListener);
			pauseButton.removeMouseListener(pauseButtonListener);
			removeAll();
			add(SlickerFactory.instance().createLabel(runnable.getName() + ": Stopped execution (triggered by user)"));
			revalidate();
			repaint();
		}

	}

	private abstract class StartPauseStopMouseListener implements MouseListener {

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		protected void addStartStop() {
			removeAll();
			add(playButton, BorderLayout.LINE_START);
			add(stopButton, BorderLayout.CENTER);
			revalidate();
			repaint();
		}

		protected void addPauseStop() {
			removeAll();
			add(pauseButton, BorderLayout.LINE_START);
			add(stopButton, BorderLayout.CENTER);
			revalidate();
			repaint();
		}

	}

}
