package Assignment1;
/**
 * René Gagnon, 260801777
 * @author renet
 *
 */
public class SeasonalFruit extends Fruit {

	public SeasonalFruit(String name, double weight, int price) {
		super(name, weight, price);
	}
	
	@Override
	public int getCost() {
		return (int)(super.getCost() * 0.85);
	}
	
}
