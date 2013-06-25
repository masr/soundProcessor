package com.sound.processor.model;

public class AudioInfo {

	/*kbps*/
	private int bitRate;
	private int channels;
	private int samplingRate;
	/*ms*/
	private long duration;

	public AudioInfo(int bitRate, int channels, int samplingRate, long duration) {
		super();
		this.bitRate = bitRate;
		this.channels = channels;
		this.samplingRate = samplingRate;
		this.duration = duration;
	}

	public int getBitRate() {
		return bitRate;
	}
	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}
	public int getChannels() {
		return channels;
	}
	public void setChannels(int channels) {
		this.channels = channels;
	}
	public int getSamplingRate() {
		return samplingRate;
	}
	public void setSamplingRate(int samplingRate) {
		this.samplingRate = samplingRate;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "AudioInfo [bitRate=" + bitRate + ", channels=" + channels
				+ ", samplingRate=" + samplingRate + ", duration=" + duration
				+ "]";
	}
}
