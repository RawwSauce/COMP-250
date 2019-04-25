package Assignment1;
/**
 * René Gagnon, 260801777
 * @author renet
 *
 */
public class Jam extends MarketProduct {

	private int nbrjars, price;
	
	public Jam(String name, int jars, int price) {
		super(name);
		this.nbrjars = jars;
		this.price = price;
	}
	
	@Override
	public int getCost() {
		return nbrjars * price;
	}
	
	private int getNbrJars() {
		return this.nbrjars;
	}

	/**
	 * @Override
	 * Jam class custom equal
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Jam 
		   && this.getName().equals(((Jam) obj).getName())
	       && this.getCost() == ((Jam) obj).getCost()
		   && this.nbrjars == ((Jam) obj).getNbrJars())
		{
			return true;
		}
		else {
			return false;
		}
	}

}
