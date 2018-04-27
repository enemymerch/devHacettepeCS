/**
 * 
 */
package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.item.Electronics.*;
import model.item.Cosmetics.*;
import model.item.OfficeSupplies.*;

/**
 * @author mcany
 *
 */
public class ItemController {
	/**
	 * Cosmetics
	 */
	private ArrayList<HairCare> HairCares;
	private ArrayList<Perfume> Perfumes;
	private ArrayList<SkinCare> SkinCares;
	
	/**
	 * Electronics
	 */
	private ArrayList<Desktop> Desktops;
	private ArrayList<Laptop> Laptops;
	private ArrayList<Tablet> Tablets;
	private ArrayList<TV> TVs;
	private ArrayList<SmartPhone> SmartPhones;
	
	/**
	 * OfficeSupplies
	 */
	private ArrayList<Book> Books;
	private ArrayList<CD_DVD> CD_DVDs;
	
	
	
	
	
	/**
	 * Class constructer ,
	 * doesn't creates user objects ,
	 * only Initializes ArrayLists
	 */
	public ItemController() {
		super();
		this.HairCares = new ArrayList<HairCare>();
		Perfumes = new ArrayList<Perfume>();
		SkinCares = new ArrayList<SkinCare>();
		Desktops = new ArrayList<Desktop>();
		Laptops = new ArrayList<Laptop>();
		Tablets = new ArrayList<Tablet>();
		Books = new ArrayList<Book>();
		CD_DVDs = new ArrayList<CD_DVD>();
		TVs = new ArrayList<TV>();
		SmartPhones = new ArrayList<SmartPhone>();
	}
	
	/**
	 * Class constructer ,
	 * This cons. reads the users.txt file and creates all relative objects,
	 * and stores them in ArrayLists
	 * @param inputFile
	 */
	public ItemController(String inputFile) {
		super();
		this.HairCares = new ArrayList<HairCare>();
		Perfumes = new ArrayList<Perfume>();
		SkinCares = new ArrayList<SkinCare>();
		Desktops = new ArrayList<Desktop>();
		Laptops = new ArrayList<Laptop>();
		Tablets = new ArrayList<Tablet>();
		Books = new ArrayList<Book>();
		CD_DVDs = new ArrayList<CD_DVD>();
		TVs = new ArrayList<TV>();
		SmartPhones = new ArrayList<SmartPhone>();
		
		InitializeItems(inputFile);
	}
	
	/**
	 * Readsthe "item.txt" file, 
	 * and creates all releated items,
	 * @param inputFile
	 */
	private void InitializeItems(String inputFile) {
		FileReader fReader = null;
		BufferedReader bufReader = null;
		
		try{
			fReader = new FileReader(inputFile);
			bufReader = new BufferedReader(fReader);
			
			String line;
			
			while((line = bufReader.readLine()) != null){
				String[] tokens = line.split("\t");
				
				try{
					AddItem(tokens);
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println(e.getMessage());
				}
			}
			
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	
	/**
	 * 
	 * @param tokens
	 */
	public void AddItem(String[] tokens){
		
		switch(tokens[0]){
		case "BOOK":
			Book newBook = new Book(Integer.toString(this.Books.size()+1), Double.parseDouble(tokens[1]), 
						tokens[2], tokens[4] ,tokens[3], tokens[5].split(","), Integer.parseInt(tokens[6]));
			this.Books.add(newBook);
			break;
		case "CDDVD":
			CD_DVD newCD_DVD = new CD_DVD(Integer.toString(this.CD_DVDs.size()+1), Double.parseDouble(tokens[1]), 
						tokens[2], tokens[3], tokens[4], tokens[5].split(","));
			this.CD_DVDs.add(newCD_DVD);
			break;
		case "DESKTOP":
			Desktop newDesktop = new Desktop(Integer.toString(this.Desktops.size()+1) , Double.parseDouble(tokens[1]), tokens[2], tokens[3], 
						Integer.parseInt(tokens[4]),  Integer.parseInt(tokens[5]), tokens[6], 
						tokens[7], Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9]), tokens[10]);
			this.Desktops.add(newDesktop);
			break;
		case "LAPTOP":
			boolean htmlSup = false;
			if(tokens[10].equals("1")){
				htmlSup = true;
			}
			Laptop newLaptop = new Laptop(Integer.toString(this.Laptops.size()+1), Double.parseDouble(tokens[1]), tokens[2], tokens[3], 
					Integer.parseInt(tokens[4]),  Integer.parseInt(tokens[5]), tokens[6], 
					tokens[7], Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9]), htmlSup);
			this.Laptops.add(newLaptop);
			break;
		case "TABLET":
			Tablet newTablet = new Tablet(Integer.toString(this.Tablets.size()+1), Double.parseDouble(tokens[1]), tokens[2], tokens[3], 
					Integer.parseInt(tokens[4]),  Integer.parseInt(tokens[5]), tokens[6], 
					tokens[7], Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9]), Integer.parseInt(tokens[10]));
			this.Tablets.add(newTablet);
			break;
		case "TV":
			TV newTV = new TV(Integer.toString(this.Tablets.size()+1), Double.parseDouble(tokens[1]), tokens[2], tokens[3], 
					Integer.parseInt(tokens[4]),  Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
			this.TVs.add(newTV);
			break;
		case "SMARTPHONE":
			SmartPhone newPhone = new SmartPhone(Integer.toString(this.SmartPhones.size()+1), Double.parseDouble(tokens[1]), 
					tokens[2], tokens[3], Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), tokens[6]);
			this.SmartPhones.add(newPhone);
			break;
		case "HAIRCARE":
			boolean isOrganic = false;
			if(tokens[4].equals("1")){
				isOrganic = true;
			}
			boolean isMedicated = false;
			if(tokens[7].equals("1")){
				isMedicated = true;
			}
			HairCare newHairCare = new HairCare(Integer.toString(this.HairCares.size()+1), Double.parseDouble(tokens[1]), 
					tokens[2], tokens[3], tokens[5], Double.parseDouble(tokens[6]), isOrganic, isMedicated);
			this.HairCares.add(newHairCare);
			break;
		case "PERFUME":
			isOrganic = false;
			if(tokens[4].equals("1")){
				isOrganic = true;
			}
			Perfume newPerfume = new Perfume(Integer.toString(this.Perfumes.size()+1), Double.parseDouble(tokens[1]), 
					tokens[2], tokens[3], tokens[5], Double.parseDouble(tokens[6]), isOrganic, tokens[7]);
			this.Perfumes.add(newPerfume);
			break;
		case "SKINCARE":
			isOrganic = false;
			if(tokens[4].equals("1")){
				isOrganic = true;
			}
			boolean isBabySevsitive = false;
			if(tokens[7].equals("1")){
				isBabySevsitive = true;
			}
			SkinCare newSkinCare = new SkinCare(Integer.toString(this.SkinCares.size()+1), Double.parseDouble(tokens[1]), 
					tokens[2], tokens[3], tokens[5], Double.parseDouble(tokens[6]), isOrganic, isBabySevsitive);
			this.SkinCares.add(newSkinCare);
			break;
		default:
			break;
		}
	}
	
	/**
	 * displays officeSupplies
	 */
	public void displayOfficeSupplies(){
		displayBooks();
		displayCDDVDs();
	}
	
	/**
	 * display electronics 
	 */
	public void displayElectronics(){
		displayDesktops();
		displayLaptops();
		displayTablets();
		displayTVs();
		displaySmartPhones();
	}
	
	/**
	 *  displays Cosmetics
	 */
	public void displayCosmetics(){
		displayHairCares();
		displayPerfumes();
		displaySkinCares();
	}
	
	/**
	 * displays books
	 */
	public void displayBooks(){
		for(Book bk:this.Books){
			System.out.print(bk.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display CD_DVDs
	 */
	public void displayCDDVDs(){
		for(CD_DVD cddvd:this.CD_DVDs){
			System.out.print(cddvd.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display desktops
	 */
	public void displayDesktops(){
		for(Desktop ds:this.Desktops){
			System.out.print(ds.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display laptops
	 */
	public void displayLaptops(){
		for(Laptop lp:this.Laptops){
			System.out.print(lp.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display Tablets
	 */
	public void displayTablets(){
		for(Tablet tb:this.Tablets){
			System.out.print(tb.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display tvs
	 */
	public void displayTVs(){
		for(TV tv:this.TVs){
			System.out.print(tv.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display smartphones
	 */
	public void displaySmartPhones(){
		for(SmartPhone sm:this.SmartPhones){
			System.out.print(sm.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display haircares
	 */
	public void displayHairCares(){
		for(HairCare hc:this.HairCares){
			System.out.print(hc.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display perfumes
	 */
	public void displayPerfumes(){
		for(Perfume per:this.Perfumes){
			System.out.print(per.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * display skincare
	 */
	public void displaySkinCares(){
		for(SkinCare skn:this.SkinCares){
			System.out.print(skn.toString());
			System.out.println("-----------------------");
		}
	}
	/**
	 * @return the hairCares
	 */
	public ArrayList<HairCare> getHairCares() {
		return HairCares;
	}
	/**
	 * @param hairCares the hairCares to set
	 */
	public void setHairCares(ArrayList<HairCare> hairCares) {
		HairCares = hairCares;
	}
	/**
	 * @return the perfumes
	 */
	public ArrayList<Perfume> getPerfumes() {
		return Perfumes;
	}
	/**
	 * @param perfumes the perfumes to set
	 */
	public void setPerfumes(ArrayList<Perfume> perfumes) {
		Perfumes = perfumes;
	}
	/**
	 * @return the skinCares
	 */
	public ArrayList<SkinCare> getSkinCares() {
		return SkinCares;
	}
	/**
	 * @param skinCares the skinCares to set
	 */
	public void setSkinCares(ArrayList<SkinCare> skinCares) {
		SkinCares = skinCares;
	}
	/**
	 * @return the desktops
	 */
	public ArrayList<Desktop> getDesktops() {
		return Desktops;
	}
	/**
	 * @param desktops the desktops to set
	 */
	public void setDesktops(ArrayList<Desktop> desktops) {
		Desktops = desktops;
	}
	/**
	 * @return the laptops
	 */
	public ArrayList<Laptop> getLaptops() {
		return Laptops;
	}
	/**
	 * @param laptops the laptops to set
	 */
	public void setLaptops(ArrayList<Laptop> laptops) {
		Laptops = laptops;
	}
	/**
	 * @return the tablets
	 */
	public ArrayList<Tablet> getTablets() {
		return Tablets;
	}
	/**
	 * @param tablets the tablets to set
	 */
	public void setTablets(ArrayList<Tablet> tablets) {
		Tablets = tablets;
	}
	/**
	 * @return the books
	 */
	public ArrayList<Book> getBooks() {
		return Books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(ArrayList<Book> books) {
		Books = books;
	}
	/**
	 * @return the cD_DVDs
	 */
	public ArrayList<CD_DVD> getCD_DVDs() {
		return CD_DVDs;
	}
	/**
	 * @param cD_DVDs the cD_DVDs to set
	 */
	public void setCD_DVDs(ArrayList<CD_DVD> cD_DVDs) {
		CD_DVDs = cD_DVDs;
	}

	/**
	 * @return the tVs
	 */
	public ArrayList<TV> getTVs() {
		return TVs;
	}

	/**
	 * @param tVs the tVs to set
	 */
	public void setTVs(ArrayList<TV> tVs) {
		TVs = tVs;
	}

	/**
	 * @return the smartPhones
	 */
	public ArrayList<SmartPhone> getSmartPhones() {
		return SmartPhones;
	}

	/**
	 * @param smartPhones the smartPhones to set
	 */
	public void setSmartPhones(ArrayList<SmartPhone> smartPhones) {
		SmartPhones = smartPhones;
	}
	
}
