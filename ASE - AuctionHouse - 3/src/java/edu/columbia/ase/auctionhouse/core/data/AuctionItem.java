package edu.columbia.ase.auctionhouse.core.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="auction")
public class AuctionItem {

	private int itemId;
	
	private String itemName;

	
	
	public AuctionItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuctionItem(int itemId, String itemName) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
	}

	@XmlAttribute(name="itemId")
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	@XmlAttribute(name="itemName")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "AuctionItem [itemId=" + itemId + ", itemName=" + itemName + "]";
	}
	
	

}
