package com.sound.processor.mp3;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.AudioInfo;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.sound.processor.base.BaseConverter;
import com.sound.processor.exception.AudioProcessException;

public class Mp3Converter extends BaseConverter{


	@Override
	public File convert(File source) throws AudioProcessException
	{
		if (null == source || !source.exists())
		{
			throw new RuntimeException("source file doesn't exist.");
		}

		Encoder encoder = null;
		try 
		{
			encoder = new Encoder();
			AudioInfo sourceInfo = encoder.getInfo(source).getAudio();

			AudioAttributes audio = new AudioAttributes();
			audio.setCodec("libmp3lame");
			audio.setBitRate(sourceInfo.getBitRate());
			audio.setChannels(sourceInfo.getChannels());
			audio.setSamplingRate(sourceInfo.getSamplingRate());
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp3");
			attrs.setAudioAttributes(audio);
			File target = new File("temp" + System.currentTimeMillis() + ".mp3");
			encoder.encode(source, target, attrs);

			return target;
		} catch (InputFormatException e) {
			e.printStackTrace();
			throw new AudioProcessException(e);
		} catch (EncoderException e) {
			e.printStackTrace();
			throw new AudioProcessException(e);
		}
	}

	public static void main(String [] args) throws UnsupportedAudioFileException, IOException, IllegalArgumentException, InputFormatException, EncoderException
	{
		Encoder encoder = new Encoder();
		File source = new File("C:\\Temp\\test.mp3");
		File target = new File("C:\\Temp\\testtt.wav");
		AudioInfo sourceInfo = encoder.getInfo(source).getAudio();
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("pcm_s16le");
		audio.setBitRate(sourceInfo.getBitRate());
		audio.setChannels(sourceInfo.getChannels());
		audio.setSamplingRate(sourceInfo.getSamplingRate());
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("wav");
		attrs.setAudioAttributes(audio);
		encoder.encode(source, target, attrs);
	}
}
