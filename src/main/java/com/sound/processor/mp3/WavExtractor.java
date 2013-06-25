package com.sound.processor.mp3;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sound.processor.exception.AudioProcessException;
import com.sound.processor.itf.Extractor;
import com.sound.processor.model.AudioInfo;
import com.sound.processor.model.Wave;

public class WavExtractor implements Extractor{

	public static final float Signed16BitRange = 32768;
	public static final float Signed8BitBRange = 128;
	public static final float Unsigned8BitBRange = 256;
	
	public Wave extractWaveByUnit(File sound, Integer wavePointsPerS) 
	{
		if (null == sound || !sound.exists())
		{
			throw new RuntimeException("File not found.");
		}

		wavePointsPerS = (null == wavePointsPerS)? 15 : wavePointsPerS;
		AudioInputStream sourceStream = null;
		try
		{
			sourceStream = AudioSystem.getAudioInputStream(sound);
			int numChannels = sourceStream.getFormat().getChannels();
			int sampleInterval = (int) (sourceStream.getFormat().getSampleRate() / (wavePointsPerS * numChannels));
			int frameLength = (int) sourceStream.getFrameLength();
			int frameSize = (int) sourceStream.getFormat().getFrameSize();
			int sampleLength = (int) ((sourceStream.getFormat().getSampleRate() / sourceStream.getFormat().getFrameRate()) * frameLength);

			byte[] eightBitByteArray = new byte[frameLength * frameSize];
			sourceStream.read(eightBitByteArray);

			float[][] waveData = extractWaveData(sampleLength / sampleInterval, sourceStream,
					numChannels, sampleLength, sampleInterval,
					eightBitByteArray);
			
			return new Wave(waveData);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Wave extractWaveByTotal(File sound, Integer totalWavePoints) throws AudioProcessException
	{
		if (null == sound || !sound.exists())
		{
			throw new RuntimeException("File not found.");
		}

		totalWavePoints = (null == totalWavePoints)? 1800 : totalWavePoints;
		AudioInputStream sourceStream = null;
		try
		{
			sourceStream = AudioSystem.getAudioInputStream(sound);
			int numChannels = sourceStream.getFormat().getChannels();
			int frameLength = (int) sourceStream.getFrameLength();
			int frameSize = (int) sourceStream.getFormat().getFrameSize();
			int sampleLength = (int) ((sourceStream.getFormat().getSampleRate() / sourceStream.getFormat().getFrameRate()) * frameLength);
			int sampleInterval = sampleLength / (totalWavePoints);

			byte[] eightBitByteArray = new byte[frameLength * frameSize];
			sourceStream.read(eightBitByteArray);

			float[][] waveData = extractWaveData(totalWavePoints, sourceStream,
					numChannels, sampleLength, sampleInterval,
					eightBitByteArray);

			return new Wave(waveData);
		} 
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new AudioProcessException(e);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new AudioProcessException(e);
		}
	}

	private float[][] extractWaveData(Integer totalWavePoints,
			AudioInputStream sourceStream, int numChannels, int sampleLength,
			int sampleInterval, byte[] eightBitByteArray) 
	{
		float[][] waveData = new float[numChannels][totalWavePoints];
		int wavePointIndex = 0;

		for (int sampleIndex = 0; sampleIndex < sampleLength;) 
		{
			if (wavePointIndex < totalWavePoints && sampleIndex % sampleInterval == 0)
			{
				for (int channel = 0; channel < numChannels; channel++) 
				{
					switch(sourceStream.getFormat().getSampleSizeInBits())
					{
						case 8:
							if (sourceStream.getFormat().getEncoding().toString().toLowerCase().startsWith("pcm_sign"))
							{
								waveData[channel][wavePointIndex] = eightBitByteArray[sampleIndex] / Signed8BitBRange;
							}
							else
							{
								waveData[channel][wavePointIndex] = (eightBitByteArray[sampleIndex] & 0xFF) / Unsigned8BitBRange;
							}
							break;
						case 16:
							int low = 0, high = 0;
							if (sourceStream.getFormat().isBigEndian())
							{
								high = (int) eightBitByteArray[2 * sampleIndex];
								low = (int) eightBitByteArray[2 * sampleIndex + 1];
							}
							else
							{
								low = (int) eightBitByteArray[2 * sampleIndex];
								high = (int) eightBitByteArray[2 * sampleIndex + 1];
							}
							waveData[channel][wavePointIndex] = (get16BitSample(high, low) / Signed16BitRange);
							break;
						default:
							break;
					}
					sampleIndex++;
				}
				wavePointIndex++;
			}
			else
			{
				sampleIndex ++;
			}
		}
		return waveData;
	}

	/**
	 * 
	 * @param high
	 * @param low
	 * @return
	 */
	private float get16BitSample(int high, int low) {
		return (high << 8) | (low & 0x00ff);
	}

	public AudioInfo extractInfo(File sound) throws AudioProcessException 
	{
		if (null == sound || !sound.exists())
		{
			throw new RuntimeException("File not found.");
		}

		Encoder encoder = null;
		try 
		{
			encoder = new Encoder();
			MultimediaInfo medieInfo = encoder.getInfo(sound);
			it.sauronsoftware.jave.AudioInfo sourceInfo = medieInfo.getAudio();

			return new AudioInfo(sourceInfo.getBitRate(), sourceInfo.getChannels(), sourceInfo.getSamplingRate(), medieInfo.getDuration());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new AudioProcessException(e);
		}
	}

	public static void main(String [] args) throws Exception
	{
		WavExtractor me = new WavExtractor();
		System.out.print(me.extractWaveByTotal(new File("C:\\Temp\\test8bit.wav"), null));
	}

}
