package Assignment1;

/**
 * René Gagnon, 260801777
 * @author renet
 *
 */

public class Egg extends MarketProduct {

	private int nbrEggs, price12;
	
	public Egg(String name, int nbr, int price) {
		super(name);
		this.nbrEggs = nbr;
		this.price12 = price;
	}
	
	@Override
	public int getCost() {
		return (int)(nbrEggs * (price12 / 12.0));
	}
	
	private int getNbrEggs() {
		return nbrEggs;
	}

	/**
	 * @Override
	 * Egg class custom equal
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Egg 
		  && this.getName().equals(((Egg) obj).getName())
		  && this.getCost() == ((Egg) obj).getCost()
		  && this.nbrEggs == ((Egg) obj).getNbrEggs())
		{
			return true;
		}
		else {
			return false;
		}
	}

}
