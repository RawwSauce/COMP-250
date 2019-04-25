package Assignment1;
/**
 * René Gagnon, 260801777
 * @author renet
 *
 */
public class Basket {

	private MarketProduct[] products;
	private int size, length;
	
	public Basket() {
		this.length = 5;
		this.products = new MarketProduct[length];
	}
	
	public MarketProduct[] getProducts() {
		return this.products.clone();
	}
	
	public void add(MarketProduct item) {
		// double array size if needed
		if(size >= length - 1) {
			length = length * 2;
			MarketProduct[] tmp = new MarketProduct[length];
			for(int i = 0 ; i < size ; i++) {
				tmp[i] = this.products[i];
			}
			this.products = tmp;
		}	
		// copy item at the end of the array
		this.products[size] = item;
		size = size + 1;	
	}
	
	public boolean remove(MarketProduct item) {
		// go through every products
		for(int i = 0 ; i < size ; i++) {
			if(this.products[i].equals(item)) {
				// shift every element, after item removed, down by one index 
				for(int n = i ; n < (size - 1) ; n++) {
					this.products[n] = this.products[n + 1];
				}
				this.products[--size] = null;
				return true;
			}
		}
		return false;
	}
	
	public void clear() {
		for(int i = 0; i < size; i++) {
			this.products[i] = null;
		}
		this.size = 0;
	}
	
	public int getNumOfProducts() {
		return this.size;
	}
	
	public int getSubTotal() {
		int subtotal = 0;
		for(int i = 0; i < size; i++) {
			subtotal += this.products[i].getCost();
		}
		return subtotal;
	}
	
	public int getTotalTax() {
		int tax = 0;
		for(int i = 0; i < size; i++) {
			if(this.products[i] instanceof Jam){
				tax += (int)(this.products[i].getCost() * 0.15);
			}
		}
		return tax;
	}
	
	public int getTotalCost() {
		return this.getSubTotal() + this.getTotalTax();
	}
	
	public String toString() {
		String receipt = "\n";
		for(int i = 0; i < size; i++) {
			receipt = receipt + this.products[i].getName() + "\t" + centsFormating(this.products[i].getCost()) + "\n";
		}
		receipt = receipt + "\nSubtotal\t" + centsFormating(this.getSubTotal()) + "\nTotal Tax\t" + centsFormating(this.getTotalTax()) + "\n\nTotal Cost\t" + centsFormating(this.getTotalCost());
		return receipt;
	}
	
	private String centsFormating(int cents) {
		if(cents == 0) { 
			return "-"; 
		}else {
			return String.format("%.2f", cents/100.0);
		}
	}
}

