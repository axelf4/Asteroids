package asteroids;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sun.audio.AudioStream;

public class SoundManager {
	private AudioStream[] sounds;

	public SoundManager(String[] urls) {
		int i = 0;
		sounds = new AudioStream[urls.length];
		for (String url: urls) {
			try {
				sounds[i] = new AudioStream(new FileInputStream(url));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				i++;
			}
            
		}
	}

	public void newSound() {

	}
}
