package Assignment2;

//Rene Gagnon, 260801777

import java.math.BigInteger;
import java.util.Iterator;


public class Polynomial 
{
	private SLinkedList<Term> polynomial;
	public int size()
	{	
		return polynomial.size();
	}
	private Polynomial(SLinkedList<Term> p)
	{
		polynomial = p;
	}
	
	public Polynomial()
	{
		polynomial = new SLinkedList<Term>();
	}
	
	// Returns a deep copy of the object.
	public Polynomial deepClone()
	{	
		return new Polynomial(polynomial.deepClone());
	}
	
	/* 
	 * TODO: Add new term to the polynomial. Also ensure the polynomial is
	 * in decreasing order of exponent.
	 */
	public void addTerm(Term t)
	{	
		SLinkedList<Term> deepCopy = new SLinkedList<Term>();
		boolean termFound = false;
		
		// check if we have a valid term
		if(!(t.getCoefficient().intValue() == 0) && (t.getExponent() >= 0)) {
			
			// if we have an empty polynomial
			if(polynomial.isEmpty()) {
				deepCopy.addFirst(t);
			}
			// otherwise iterate through it
			else {
				for(Term currentTerm: polynomial) {
					// current term exp is bigger
					if(currentTerm.getExponent() > t.getExponent()) {
						// add current term to the end
						deepCopy.addLast(currentTerm);
					}
					// current term exp is smaller
					else if(currentTerm.getExponent() < t.getExponent()) {
						if(!termFound) {
							// add term (if not already done)
							deepCopy.addLast(t);
							termFound = true;
						}
						// then add current term
						deepCopy.addLast(currentTerm);
					}
					// current term exp is equal
					else if(currentTerm.getExponent() == t.getExponent()) {
						// add coefficient to current term
						currentTerm.setCoefficient(currentTerm.getCoefficient().add(t.getCoefficient()));
						// add current term if possible
						if(!(currentTerm.getCoefficient().intValue() == 0)) {
							deepCopy.addLast(currentTerm);
						}
						termFound = true;
					}
				}

				// case where the term has the smallest exponent
				// we add it to the end
				if(!termFound) {
					deepCopy.addLast(t);
				}
			}
			polynomial = deepCopy;
		}
		
	}
	
	public Term getTerm(int index)
	{
		return polynomial.get(index);
	}
	
	//TODO: Add two polynomial without modifying either
	public static Polynomial add(Polynomial p1, Polynomial p2)
	{
		Polynomial result = new Polynomial();
		Term currentP2Term = null ;
		boolean flag = true; // false when we want the next p1 term
		boolean	flag2 = false; // false when we want the next p2 term
		boolean flag3 = false; // becomes true for the special case when p2 finishes with a smaller term
		int exp, coef;
		
		Iterator<Term> iterP2 = p2.polynomial.iterator();
		
		
		// iterate through p1
		for(Term currentP1Term: p1.polynomial) {
			
			flag = true;
			
			// iterate through p2
			while(iterP2.hasNext() && flag) {
				
				if(!flag2) {
					currentP2Term = iterP2.next();
				}
				
				if(currentP2Term.getExponent() > currentP1Term.getExponent()) {
					result.polynomial.addLast(currentP2Term);
					flag = true;// keep same p1 term
					flag2 = false;// get next p2 term
				}else if (currentP2Term.getExponent() == currentP1Term.getExponent()) {
					exp = currentP2Term.getExponent();
					coef = currentP2Term.getCoefficient().intValue() + currentP1Term.getCoefficient().intValue();
					if(coef != 0) {
						result.polynomial.addLast(new Term(exp, BigInteger.valueOf(coef)));
					}
					flag = false;// get next p1 term
					flag2 = false;// get next p2 term
				}else if (currentP2Term.getExponent() < currentP1Term.getExponent()) {
					result.polynomial.addLast(currentP1Term);
					flag = false;// get next p1 term
					flag2 = true;// keep same p2 term
					//if we just compared the last p2 term set flag3 to true
					if(!iterP2.hasNext()) {
						flag3 = true;
					}
				}
			}
			
			if(!iterP2.hasNext() && flag && !flag3) {
				result.polynomial.addLast(currentP1Term);
			}
			// we still have a p2 term that wasn't added, add it to the right place
			else if(!iterP2.hasNext() && flag && flag3) {
				if(currentP1Term.getExponent() > currentP2Term.getExponent()) {
					result.polynomial.addLast(currentP1Term);
				}else if(currentP1Term.getExponent() == currentP2Term.getExponent()) {
					exp = currentP2Term.getExponent();
					coef = currentP2Term.getCoefficient().intValue() + currentP1Term.getCoefficient().intValue();
					if(coef != 0) {
						result.polynomial.addLast(new Term(exp, BigInteger.valueOf(coef)));
					}
					flag3 = false;// set flag3 to false since the last p2 term has been added
				}else if(currentP1Term.getExponent() < currentP2Term.getExponent()) {
					result.polynomial.addLast(currentP2Term);
					result.polynomial.addLast(currentP1Term);
					flag3 = false;// set flag3 to false since the last p2 term has been added
				}
			}
			
		}
		
		if(iterP2.hasNext()) {
			while(iterP2.hasNext()) {
				result.polynomial.addLast(iterP2.next());
			}

		}else if(flag3) {
			result.polynomial.addLast(currentP2Term);
			flag3 = false;// set flag3 to false since the last p2 term has been added
		}
		
		
		return result;
	}
	
	//TODO: multiply this polynomial by a given term.
	private void multiplyTerm(Term t)
	{	
		if(t.getCoefficient().intValue() != 0) {
			for(Term currentTerm: this.polynomial) {
				currentTerm.setCoefficient(currentTerm.getCoefficient().multiply(t.getCoefficient()));
				currentTerm.setExponent(currentTerm.getExponent() + t.getExponent());
			}
		}else {
			this.polynomial = new SLinkedList<Term>();
		}
	}
	
	//TODO: multiply two polynomials
	public static Polynomial multiply(Polynomial p1, Polynomial p2)
	{
		Polynomial result = new Polynomial();
		Polynomial tmp;
		
		for(Term currentP1Term: p1.polynomial) {
			tmp = new Polynomial();
			for(Term currentP2Term: p2.polynomial) {
				if(currentP2Term.getCoefficient().intValue() != 0 &&
				   currentP1Term.getCoefficient().intValue() != 0) {
					tmp.polynomial.addLast(new Term(currentP1Term.getExponent() + currentP2Term.getExponent(),
					currentP2Term.getCoefficient().multiply(currentP1Term.getCoefficient())));
				}
			}
			result = Polynomial.add(result, tmp);
		}
		return result;
	}
	
	//TODO: evaluate this polynomial.
	// Hint:  The time complexity of eval() must be order O(m), 
	// where m is the largest degree of the polynomial. Notice 
	// that the function SLinkedList.get(index) method is O(m), 
	// so if your eval() method were to call the get(index) 
	// method m times then your eval method would be O(m^2).
	// Instead, use a Java enhanced for loop to iterate through 
	// the terms of an SLinkedList.

	public BigInteger eval(BigInteger x)
	{
		BigInteger result = new BigInteger("0");
	    int i = 0, exp = 0;
		
	    if(this.polynomial.size() > 1) {
			for(Term currentTerm: this.polynomial) {
				
				// if its the first iteration
				if(i == 0) {
					result = currentTerm.getCoefficient();
					exp = currentTerm.getExponent();
				}else {
					exp = exp - 1;
					if(exp == currentTerm.getExponent()) {
						result = result.multiply(x).add(currentTerm.getCoefficient());
					}else {
						// multiply by x until we reach the exp we are supposed to be at
						while(exp != currentTerm.getExponent()) {
							result = result.multiply(x);
							exp = exp - 1;
						}
						result = result.multiply(x).add(currentTerm.getCoefficient());
					}
				}
				i = i + 1;
			}
			if(exp != 0) {
				result = result.multiply(x.pow(exp));
			}
	    }
	    // if we only have one term in the polynomial
	    else if(this.polynomial.size() == 1) {
	    	result = this.polynomial.get(0).getCoefficient().multiply(x.pow(this.polynomial.get(0).getExponent()));
	    }
		return result;
	}
	
	// Checks if this polynomial is same as the polynomial in the argument.
	// Used for testing whether two polynomials have same content but occupy disjoint space in memory.
	// Do not change this code, doing so may result in incorrect grades.
	public boolean checkEqual(Polynomial p)
	{	
		// Test for null pointer exceptions!!
		// Clearly two polynomials are not same if they have different number of terms
		if (polynomial == null || p.polynomial == null || size() != p.size())
			return false;
		
		int index = 0;
		// Simultaneously traverse both this polynomial and argument. 
		for (Term term0 : polynomial)
		{
			// This is inefficient, ideally you'd use iterator for sequential access.
			Term term1 = p.getTerm(index);
			
			if (term0.getExponent() != term1.getExponent() || // Check if the exponents are not same
				term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || // Check if the coefficients are not same
				term1 == term0) // Check if the both term occupy same memory location.
					return false;
			
			index++;
		}
		return true;
	}
	
	// This method blindly adds a term to the end of LinkedList polynomial. 
	// Avoid using this method in your implementation as it is only used for testing.
	// Do not change this code, doing so may result in incorrect grades.
	public void addTermLast(Term t)
	{	
		polynomial.addLast(t);
	}
	
	// This is used for testing multiplyTerm.
	// Do not change this code, doing so may result in incorrect grades.
	public void multiplyTermTest(Term t)
	{
		multiplyTerm(t);
	}
	
	@Override
	public String toString()
	{	
		if (polynomial.size() == 0) return "0";
		return polynomial.toString();
	}
}
