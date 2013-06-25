package com.sound.processor.model;

import javax.sound.sampled.AudioInputStream;

public class Sound {

	AudioInputStream soundStream;
	
	public AudioInputStream getStream() {
		return soundStream;
	}

	public void setStream(AudioInputStream audioInputStream) {
		soundStream = audioInputStream;
	}

}
