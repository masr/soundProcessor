package com.sound.processor.exception;

public class AudioProcessException extends Exception {

	private static final long serialVersionUID = 1L;

	public AudioProcessException() {
		super();
	}

	public AudioProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public AudioProcessException(String message) {
		super(message);
	}

	public AudioProcessException(Throwable cause) {
		super(cause);
	}

}
