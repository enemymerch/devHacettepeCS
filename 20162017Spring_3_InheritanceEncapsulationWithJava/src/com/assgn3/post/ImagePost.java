package com.assgn3.post;

public class ImagePost extends TextPost{
	private String imageFileName;
	private int imageWidth;
	private int imageHeight;
	/**
	 * @return the imageFileName
	 */
	public String getImageFileName() {
		return imageFileName;
	}
	/**
	 * @param imageFileName the imageFileName to set
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}
	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	/**
	 * @return the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}
	/**
	 * @param imageHeight the imageHeight to set
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	

	@Override
	/**
	 * 
	 */
	public String toString(){
		return super.toString()+ "\nImage: " + this.imageFileName + "\nImage resolution: "
						+Integer.toString(this.imageWidth) + "x"
						+ Integer.toString(this.imageHeight);
	}

	
}
