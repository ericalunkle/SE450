package se450finalproject;

public class Item {
	private String itemId;
	private int price;

	
	public Item(String itemId, int price) throws InvalidDataException{
		super();
		setItemId(itemId);
		setPrice(price);
	}
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) throws InvalidDataException{
		if(price < 0){
			throw new InvalidDataException("Item price is negative");
		}
		this.price = price;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", price=" + price + "]";
	}
	
}
