package uk.ac.yorksj.sem2.assignment;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio extends Thread {
	URL resource = null;
	MediaPlayer player = null;

	public Audio(String name) {

		try {
			resource = getClass().getResource(name);
			Media media = new Media(resource.toString());
			player = new MediaPlayer(media);
			player.play();
		} catch (Exception e) {
			System.out.println("[Sound] error: " + e.getMessage());
		}

	}

	public void run() {
		try {
			player.play();
		} catch (Exception e) {
			System.err.println("[Sound] error: " + e.getMessage());
		}
	}

}
