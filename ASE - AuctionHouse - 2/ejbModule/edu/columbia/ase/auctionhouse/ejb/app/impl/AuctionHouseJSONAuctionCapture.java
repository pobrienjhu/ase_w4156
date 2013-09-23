package edu.columbia.ase.auctionhouse.ejb.app.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseDataSource;
import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseAuctionCapture;
import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseTimer;
import edu.columbia.ase.auctionhouse.ejb.data.Auction;
import edu.columbia.ase.auctionhouse.ejb.data.AuctionItem;

/*
 * 
 * Parses the json auction and item definitions from the file to the java objects
 * the reason 
 * 
 */
@Startup
@Singleton  (mappedName="edu.columbia.ase.auctionhouse.ejb.AuctionHoueJSONAuctionCapture")
public class AuctionHouseJSONAuctionCapture implements AuctionHouseAuctionCapture {

	private Logger log = LoggerFactory.getLogger(AuctionHouseJSONAuctionCapture.class);
	
	// @Resource if this was a real data source
	@EJB 
	private AuctionHouseDataSource auctionHouseDataSource;

	@EJB 
	private AuctionHouseTimer auctionHouseTimer;

	
	private String itemsFileName = "/resources/Items.json";
	
	private String auctionsFileName = "/resources/Auctions.json";
		
	/*
	 * Load the auctions from a JSON file. This simulates a system sending items 
	 * and auctions to a live running auction house.
	 * 
	 * The public methods addItem and addAuction defined in the interface would be called
	 * by another system, perhaps rmi, or for a queue via a mdb, or via a webservice. 
	 * There would possible be validation methods in the auction capture piece, much more
	 * in detail then we have here, possibly validating the auction user, and item etc... 
	 * 
	 * (non-Javadoc)
	 * @see edu.columbia.ase.auctionhouse.core.app.AuctionHouseDataLoader#loadAuctions()
	 */
	@Override
	@PostConstruct
	public void loadAuctions() {

		try {
			log.info("loading auctions");
			
			/*
			 * Load the Items
			 */
			
			log.debug("Loading items file |"+getItemsFileName()+"|");
			
			String jsonItemContents = readFile(getItemsFileName());
			
			log.info("Successfully loaded item file |"+getItemsFileName()+"|");
			log.debug("Loaded json contents |"+jsonItemContents+"|");
			
			
			/*
			 * Load the Auctions
			 */
			
			log.debug("Loading auction file |"+getAuctionsFileName()+"|");
			
			String jsonAuctionContents = readFile(getAuctionsFileName());
			
			log.info("Successfully loaded auction file |"+getAuctionsFileName()+"|");
			log.debug("Loaded json contents |"+auctionsFileName+"|");

			log.info("Loading Auction Items...");

			
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			
			/*
			 * Parse the items from JSON to Java Object
			 */
			
			xstream.alias("AuctionItem", AuctionItem.class);
			xstream.alias("AuctionItems", List.class);

			@SuppressWarnings("unchecked")
			List<AuctionItem> auctionItems = (List<AuctionItem>)xstream.fromXML(jsonItemContents);

			
			for( AuctionItem item: auctionItems){
				try {
					addItem(item);
				} catch (Exception e) {
					log.warn("Invalid item root cause ("+e.getMessage()+")");
				}
			}
			
			log.info("Auction Items Loaded...");
			
			log.info("Loading Auctions...");

			/*
			 * Parse the auctions from JSON to Java Object
			 */	
			xstream.alias("Auction", Auction.class);
			xstream.alias("Auctions", List.class);
			
			@SuppressWarnings("unchecked")
			List<Auction> auctions = (List<Auction>)xstream.fromXML(jsonAuctionContents);
			
			Date currentTime = new Date();
					
			for( Auction auction: auctions){
				try {
					auction.setItem(auctionHouseDataSource.getAuctionItem(new Integer(auction.getAuctionItemId())) );
					
					/*
					 * Set the auction start and end time from the start offset and the auction length
					 * while this may not be as useful in a real production ready system,
					 * this makes it easier for testing and grading
					 */
					Date startTime = DateUtils.addMinutes(currentTime, auction.getStartOffsetMinutes() );
					Date endTime =  DateUtils.addMinutes(startTime, auction.getAuctionLengthInMinutes() );
					
					auction.setStartTime(startTime);
					auction.setEndTime(endTime);
					
					addAuction(auction);
					
					
				} catch (Exception e) {
					log.warn("Invalid auction root cause ("+e.getMessage()+")");
				}
			}
			
			log.info("Auctions Loaded...");
		} catch (Exception e) {
			log.error("Unable to load auctions. Root Cause ("+e.getMessage()+")");
		}
		
	}

	@Override
	public void addAuction(Auction auction) throws Exception {
		auctionHouseDataSource.addAuction(auction);		
		auctionHouseTimer.addAuction(auction);
	}

	@Override
	public void addItem(AuctionItem item) throws Exception {
		auctionHouseDataSource.addAuctionItem(item);		
	}	
	
	private String readFile(String fileName) throws IOException{
		
		if( StringUtils.isEmpty( StringUtils.trimToEmpty(fileName) ) ){
			throw new IOException("FileName is empty.");
		}
		
		InputStream stream = AuctionHouseJSONAuctionCapture.class.getResourceAsStream(fileName);

		log.debug("InputStream is |"+stream+"|");
		
		if( stream == null ){
			throw new IOException("Cannot load file |"+fileName+"|");
		}
		
		List<String> fileLines = IOUtils.readLines(stream, "UTF-8");
		
		StringBuffer fileString = new StringBuffer("");
		
		for(String line: fileLines){
			fileString.append(line);
		}
		
		return fileString.toString();
		
	}

	
	public String getItemsFileName() {
		return itemsFileName;
	}

	public void setItemsFileName(String itemsFileName) {
		this.itemsFileName = itemsFileName;
	}

	public String getAuctionsFileName() {
		return auctionsFileName;
	}

	public void setAuctionsFileName(String auctionsFileName) {
		this.auctionsFileName = auctionsFileName;
	}

	@Override
	public void setAuctionHouseDataSource(AuctionHouseDataSource auctionHouseDataSource) {
		this.auctionHouseDataSource = auctionHouseDataSource;		
	}

	@Override
	public void setAuctionHouseTimer(AuctionHouseTimer auctionHouseTimer) {
		this.auctionHouseTimer = auctionHouseTimer;		
	}
	


	

}
