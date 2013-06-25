package com.sound.processor.base;

import java.io.File;

import com.sound.processor.exception.AudioProcessException;
import com.sound.processor.itf.Converter;

abstract public class BaseConverter implements Converter{

	public abstract File convert(File source) throws AudioProcessException;

}
