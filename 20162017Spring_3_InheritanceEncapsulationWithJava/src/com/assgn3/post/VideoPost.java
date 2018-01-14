package com.assgn3.post;

public class VideoPost extends TextPost{
	private String videoFilename;
	private int maximumVideoLength;
	/**
	 * @return the videoFilename
	 */
	public String getVideoFilename() {
		return videoFilename;
	}
	/**
	 * @param videoFilename the videoFilename to set
	 */
	public void setVideoFilename(String videoFilename) {
		this.videoFilename = videoFilename;
	}
	/**
	 * @return the maximumVideoLength
	 */
	public int getMaximumVideoLength() {
		return maximumVideoLength;
	}
	/**
	 * @param maximumVideoLength the maximumVideoLength to set
	 */
	public void setMaximumVideoLength(int maximumVideoLength) {
		this.maximumVideoLength = maximumVideoLength;
	}
	

	@Override
	/**
	 * 
	 */
	public String toString(){
		return super.toString()+ "\nVideo: " + this.videoFilename + "\nVideo duration: "
						+Integer.toString(maximumVideoLength)+" minutes";
	}

}
