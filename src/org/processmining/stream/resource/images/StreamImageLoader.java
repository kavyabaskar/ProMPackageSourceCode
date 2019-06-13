package org.processmining.stream.resource.images;

import javax.swing.ImageIcon;

import org.processmining.stream.properties.StreamProperties;

public class StreamImageLoader {

	public static ImageIcon getPlayButton() {
		ImageIcon image = new ImageIcon(StreamImageLoader.class
				.getResource(StreamProperties.RESOURCE_IMAGES_LOCATION + StreamProperties.PLAY_BUTTON_IMAGE_FILE_NAME));
		return image;
	}

	public static ImageIcon getPauseButton() {
		ImageIcon image = new ImageIcon(StreamImageLoader.class.getResource(
				StreamProperties.RESOURCE_IMAGES_LOCATION + StreamProperties.PAUSE_BUTTON_IMAGE_FILE_NAME));
		return image;
	}

	public static ImageIcon getStopButton() {
		ImageIcon image = new ImageIcon(StreamImageLoader.class
				.getResource(StreamProperties.RESOURCE_IMAGES_LOCATION + StreamProperties.STOP_BUTTON_IMAGE_FILE_NAME));
		return image;
	}

}
