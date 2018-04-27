package model.item.OfficeSupplies;

public class CD_DVD extends OfficeSupply{
	private String Composer;
	private String[] Songs;
	/**
	 * @param iD
	 * @param price
	 * @param releaseDate
	 * @param coverTitle
	 * @param composer
	 * @param songs
	 */
	public CD_DVD(String iD, double price, String releaseDate, String coverTitle, String composer, String[] songs) {
		super(iD, price, releaseDate, coverTitle);
		Composer = composer;
		Songs = songs;
	}
	/**
	 * @return the composer
	 */
	public String getComposer() {
		return Composer;
	}
	/**
	 * @param composer the composer to set
	 */
	public void setComposer(String composer) {
		Composer = composer;
	}
	/**
	 * @return the songs
	 */
	public String[] getSongs() {
		return Songs;
	}
	/**
	 * @param songs the songs to set
	 */
	public void setSongs(String[] songs) {
		Songs = songs;
	}
	
	/**
	 * toString method
	 */
	public String toString(){
		String cddvdSTr = "Type: CDDVD\n";
		cddvdSTr = cddvdSTr + "Item ID: "+ this.getID()+"\n";
		cddvdSTr = cddvdSTr + "Price: "+Double.toString(this.getPrice())+"$\n";
		cddvdSTr = cddvdSTr + "Release Date: "+this.getReleaseDate()+"\n";
		cddvdSTr = cddvdSTr + "Title: "+this.getCoverTitle()+"\n";
		cddvdSTr = cddvdSTr + "Songs: ";
		String[] songs = this.getSongs();
		for(int i = 0 ; i<songs.length;i++){
			if(i != 0){
				cddvdSTr = cddvdSTr + " , ";
			}
			cddvdSTr = cddvdSTr + songs[i];
		}
		cddvdSTr = cddvdSTr + "\n";
		cddvdSTr = cddvdSTr + "Composer: " + this.getComposer()+ " pages\n";
		
		return cddvdSTr;
	}
}
