package Assignment1;

/**
 * MarketProduct class, super class for all the items in the market
 * @author renet
 * René Gagnon, 260801777
 */

abstract public class MarketProduct {
	
	private String name;
	
	public MarketProduct (String name) {
		this.name = name;
	}
	
	final public String getName() {
		return this.name;
	}
	
	abstract public int getCost();
	
	abstract public boolean equals(Object obj);
}
