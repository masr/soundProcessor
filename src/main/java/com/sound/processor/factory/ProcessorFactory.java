package com.sound.processor.factory;

import com.sound.processor.itf.Converter;
import com.sound.processor.itf.Extractor;
import com.sound.processor.mp3.Mp3Converter;
import com.sound.processor.mp3.WavConverter;
import com.sound.processor.mp3.WavExtractor;

public class ProcessorFactory {

	public Converter getConverter(String type)
	{
		if ("mp3".equalsIgnoreCase(type))
		{
			return new Mp3Converter();
		}

		if ("wav".equalsIgnoreCase(type))
		{
			return new WavConverter();
		}

		throw new RuntimeException("converter for " + type + "not supported.");
	}

	public Extractor getExtractor(String type)
	{
		if ("wav".equalsIgnoreCase(type))
		{
			return new WavExtractor();
		}

		throw new RuntimeException("extractor for " + type + "not supported.");
	}
}
