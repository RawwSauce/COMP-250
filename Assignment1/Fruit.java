package Assignment1;
/**
 * René Gagnon, 260801777
 * @author renet
 *
 */
public class Fruit extends MarketProduct {

	private double weight;
	private int price;
	
	public Fruit(String name, double weight, int price) {
		super(name);
		this.weight = weight;
		this.price = price;
	}
	
	
	/**
	 * getCost method of fruit
	 * @Override
	 */
	public int getCost() {
		return (int)(weight * price);
	}
	
	private double getWeight() {
		return this.weight;
	}
	
	/**
	 * @Override
	 * Fruit class custom equal
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Fruit 
		   && this.getName().equals(((Fruit) obj).getName())
	       && this.getCost() == ((Fruit) obj).getCost()
		   && this.weight == ((Fruit) obj).getWeight())
		{
			return true;
		}
		else {
			return false;
		}
	}

}
