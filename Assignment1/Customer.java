package Assignment1;
/**
 * René Gagnon, 260801777
 * @author renet
 *
 */
public class Customer {

	private String name;
	private int balance;
	private Basket CBasket;
	
	public Customer(String name, int balance) {
		this.name = name;
		this.balance = balance;
		CBasket = new Basket();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public Basket getBasket() {
		return this.CBasket;
	}
	
	public int addFunds(int funds) {
		if(funds < 0) {
			throw new IllegalArgumentException("You can't add negtive funds to a customer's balance!");
		}
		else {
			this.balance += funds;
		}
		return this.balance;
	}
	
	public void addToBasket(MarketProduct item) {
		this.CBasket.add(item);
	}
	
	public boolean removeFromBasket(MarketProduct item) {
		return this.CBasket.remove(item);
	}
	
	public String checkOut() {
		String receipt;
		if(this.balance < this.CBasket.getTotalCost()) {
			throw new IllegalStateException("Not enough funds in the customer's balance!");
		}
		else {
			this.balance = balance - this.CBasket.getTotalCost();
			receipt = this.CBasket.toString();
			this.CBasket.clear();
			return receipt;
		}
	}
}
