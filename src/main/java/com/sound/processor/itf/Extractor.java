package com.sound.processor.itf;

import java.io.File;

import com.sound.processor.exception.AudioProcessException;
import com.sound.processor.model.AudioInfo;
import com.sound.processor.model.Wave;

public interface Extractor {

	
	/**
	 * Extract wave data from sound
	 * 
	 * @param sound
	 * @return
	 */
	public Wave extractWaveByUnit(File sound, Integer wavePointsPerS);

	public Wave extractWaveByTotal(File sound, Integer extractWaveByTotal) throws AudioProcessException;

	public AudioInfo extractInfo(File sound) throws AudioProcessException;
}
