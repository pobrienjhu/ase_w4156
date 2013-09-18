package edu.columbia.ase.auctionhouse.core.data;

public class AuctionItem {

	private int itemId;
	
	private String itemName;

	public AuctionItem(int itemId, String itemName) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

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
