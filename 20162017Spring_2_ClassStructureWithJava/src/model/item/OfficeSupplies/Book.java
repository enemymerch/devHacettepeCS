package model.item.OfficeSupplies;

public class Book extends OfficeSupply{
	private String Publisher;
	private String[] Authors;
	private int TotalPageNumber;
	/**
	 * @param iD
	 * @param price
	 * @param releaseDate
	 * @param coverTitle
	 * @param publisher
	 * @param authors
	 * @param totalPageNumber
	 */
	public Book(String iD, double price, String releaseDate, String coverTitle, String publisher, String[] authors,
			int totalPageNumber) {
		super(iD, price, releaseDate, coverTitle);
		Publisher = publisher;
		Authors = authors;
		TotalPageNumber = totalPageNumber;
	}
	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return Publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		Publisher = publisher;
	}
	/**
	 * @return the authors
	 */
	public String[] getAuthors() {
		return Authors;
	}
	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(String[] authors) {
		Authors = authors;
	}
	/**
	 * @return the totalPageNumber
	 */
	public int getTotalPageNumber() {
		return TotalPageNumber;
	}
	/**
	 * @param totalPageNumber the totalPageNumber to set
	 */
	public void setTotalPageNumber(int totalPageNumber) {
		TotalPageNumber = totalPageNumber;
	}
	
	
	/**
	 * toString method
	 */
	public String toString(){
		String bookStr = "Type: Book\n";
		bookStr = bookStr + "Item ID: "+ this.getID()+"\n";
		bookStr = bookStr + "Price: "+Double.toString(this.getPrice())+"$\n";
		bookStr = bookStr + "Release Date: "+this.getReleaseDate()+"\n";
		bookStr = bookStr + "Title: "+this.getCoverTitle()+"\n";
		bookStr = bookStr + "Publisher: "+this.getPublisher()+"\n";
		bookStr = bookStr + "Author: ";
		String[] authors = this.getAuthors();
		for(int i = 0 ; i<authors.length;i++){
			if(i != 0){
				bookStr = bookStr + " , ";
			}
			bookStr = bookStr + authors[i];
		}
		bookStr = bookStr + "\n";
		bookStr = bookStr + "Page: " + Integer.toString(this.getTotalPageNumber())+ " pages\n";
		
		return bookStr;
	}
}

