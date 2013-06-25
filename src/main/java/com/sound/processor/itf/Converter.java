package com.sound.processor.itf;

import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;

public interface Converter {

	
	/**
	 * Convert input input sound to a target sound 
	 * 
	 * @return
	 * @throws InputFormatException 
	 * @throws EncoderException 
	 */
	public File convert(File source) throws Exception;

}
