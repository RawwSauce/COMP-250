package Assignment2;
import java.math.BigInteger;

/**
 * Polynomial Tester with clever check methods
 * @author Xavier Pellemans 260775554
 *
 */
public class PolynomialTesterXavier {
	
	private static void testClone()
	{	
		Polynomial poly = new Polynomial();
		
		poly.addTermLast(new Term(0, new BigInteger("1")));
		poly.addTermLast(new Term(1, new BigInteger("2")));
		poly.addTermLast(new Term(2, new BigInteger("3")));
		poly.addTermLast(new Term(3, new BigInteger("4")));
		poly.addTermLast(new Term(4, new BigInteger("5")));
		
		Polynomial clone = poly.deepClone();
		
		if (clone.checkEqual(poly))
		{
			System.out.println("Passed: deepClone()");
			return;
		}
		System.out.println("Failed: deepClone()");
		System.out.println("Expected:"+poly+" size:"+poly.size()+"\tresult:"+clone+" size:"+clone.size());
	}
	
	//Home made method for the clone method
	private static void testClone2()
	{	
		/*
		 * empty.deepClone();
		 * Must also give empty
		 */
		Polynomial poly = new Polynomial();
		
		Polynomial clone = poly.deepClone();
		
		if (clone.checkEqual(poly))
		{
			System.out.println("Passed: deepClone2()");
			return;
		}
		System.out.println("Failed: deepClone2()");
		System.out.println("Expected:"+poly+" size:"+poly.size()+"\tresult:"+clone+" size:"+clone.size());
	}
	
	// Tests if the terms are arranged in correct order.
	private static void testAddTerm_1()
	{
		/*
		 * add:
		 * x
		 * 2x^2
		 * 3x^3
		 * 4x^4
		 * =4x^4+3x^3+2x^2+x
		 */
		Polynomial p1 = new Polynomial(); // reference polynomial
		Polynomial p2 = new Polynomial();
		
		p1.addTermLast(new Term(4, new BigInteger("4")));
		p1.addTermLast(new Term(3, new BigInteger("3")));
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(1, new BigInteger("1")));
		
		p2.addTerm(new Term(1, new BigInteger("1")));
		p2.addTerm(new Term(2, new BigInteger("2")));
		p2.addTerm(new Term(3, new BigInteger("3")));
		p2.addTerm(new Term(4, new BigInteger("4")));
				
		if (p1.size() != 0 && p2.size() != 0 && p1.checkEqual(p2))
		{
			System.out.println("Passed: addTerm() - Test1");
			return;
		}
		System.out.println("Failed: addTerm() - Test1");
		System.out.println("Expected: "+p1 +"\tresult: "+ p2 );
	}
	
	// Check if adding a new term updates/removes an existing term in the polynomial.   
	private static void testAddTerm_2()
	{
		/*
		 * Try to add
		 * 4x^4
		 * 3x^3
		 * 2x^2
		 * x
		 * 2x^-1 (should not be added) 
		 * =4x^4+3x^3+2x^2+x
		 */
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial(); // reference polynomial
		
		p1.addTerm(new Term(4, new BigInteger("4")));
		p1.addTerm(new Term(3, new BigInteger("3")));
		p1.addTerm(new Term(2, new BigInteger("2")));
		p1.addTerm(new Term(1, new BigInteger("1")));
		p1.addTerm(new Term(2, new BigInteger("-1")));

		p2.addTermLast(new Term(4, new BigInteger("4")));
		p2.addTermLast(new Term(3, new BigInteger("3")));
		p2.addTermLast(new Term(2, new BigInteger("1")));
		p2.addTermLast(new Term(1, new BigInteger("1")));
						
		if (p1.size() != 0 && p2.size() != 0 && p1.checkEqual(p2))
		{
			System.out.println("Passed: addTerm() - Test2");
			return;
		}
		System.out.println("Failed: addTerm() - Test2");
		System.out.println("Expected: "+p2 +"\tresult: "+ p1 );
	}
	
	//Home made test
	private static void testAddTerm_3()
	{
		/*
		 * Try to add
		 * 4x^4
		 * -4x^4
		 * 0x^0
		 * =0 (empty polynomial)
		 */
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial(); // reference polynomial
		
		p1.addTerm(new Term(4, new BigInteger("4")));
		p1.addTerm(new Term(4, new BigInteger("-4")));
		p1.addTerm(new Term(0, new BigInteger("0")));
						
		if (p1.size() == 0 && p2.size() == 0 && p1.checkEqual(p2))
		{
			System.out.println("Passed: addTerm() - Test3");
			return;
		}
		System.out.println("Failed: addTerm() - Test3");
		System.out.println("Expected: "+p2 +"\tresult: "+ p1 );
	}
	
	// Test case when there are no terms with same exponent in two polynomial
	private static void testAdd_1()
	{
		/*
		 * 2x^2+1
		 * + 2x^4+x^3   
		 * ==2x^4+x^3+2x^2+1  ?
		 */
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
		
		p1.addTerm(new Term(2, new BigInteger("2")));
		p1.addTerm(new Term(0, new BigInteger("1")));
		
		p2.addTerm(new Term(3, new BigInteger("1")));
		p2.addTerm(new Term(4, new BigInteger("2")));
		
		Polynomial sum = Polynomial.add(p1, p2);
		Polynomial expectedSum = new Polynomial(); // reference
		expectedSum.addTermLast(new Term(4, new BigInteger("2")));
		expectedSum.addTermLast(new Term(3, new BigInteger("1")));
		expectedSum.addTermLast(new Term(2, new BigInteger("2")));
		expectedSum.addTermLast(new Term(0, new BigInteger("1")));
		
		if (sum != null && expectedSum.checkEqual(sum))
		{
			System.out.println("Passed: add() - Test 1");
			return;
		}
		System.out.println("Failed: add() - Test 1");
		System.out.println("Expected: "+expectedSum +"\tresult: "+ sum );
	}
	
	// Test case when there are terms with same exponent in two polynomial
	private static void testAdd_2()
	{
		/*
		 * 2x^4+x^3+2x^2+1
		 * + 2x^3 + 5x^2
		 * ==2x^4+3x^3+7x^2+1  ?
		 */
		
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
			
		p1.addTerm(new Term(2, new BigInteger("2")));
		p1.addTerm(new Term(0, new BigInteger("1")));
		p1.addTerm(new Term(3, new BigInteger("1")));
		p1.addTerm(new Term(4, new BigInteger("2")));
		
		p2.addTerm(new Term(3, new BigInteger("2")));
		p2.addTerm(new Term(2, new BigInteger("5")));
			
		Polynomial sum = Polynomial.add(p1, p2);
		Polynomial expectedSum = new Polynomial(); //reference
		expectedSum.addTermLast(new Term(4, new BigInteger("2")));
		expectedSum.addTermLast(new Term(3, new BigInteger("3")));
		expectedSum.addTermLast(new Term(2, new BigInteger("7")));
		expectedSum.addTermLast(new Term(0, new BigInteger("1")));
			
		if (sum != null && expectedSum.checkEqual(sum))
		{
			System.out.println("Passed: add() - Test 2");
			return;
		}
		System.out.println("Failed: add() - Test 2");
		System.out.println("Expected: "+expectedSum +"\tresult: "+ sum );
		}
	//home made test
	private static void testAdd_3(){
		/*
		 * 2x^4+x^3+2x^2+1
		 * + 0    (empty polynomial) 
		 * ==2x^4+x^3+2x^2+1  ?
		 */
		
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
			
		p1.addTerm(new Term(2, new BigInteger("2")));
		p1.addTerm(new Term(0, new BigInteger("1")));
		p1.addTerm(new Term(3, new BigInteger("1")));
		p1.addTerm(new Term(4, new BigInteger("2")));
		
			
		Polynomial sum = Polynomial.add(p1, p2);
		Polynomial expectedSum = new Polynomial(); //reference
		expectedSum.addTermLast(new Term(4, new BigInteger("2")));
		expectedSum.addTermLast(new Term(3, new BigInteger("1")));
		expectedSum.addTermLast(new Term(2, new BigInteger("2")));
		expectedSum.addTermLast(new Term(0, new BigInteger("1")));
			
		if (sum != null && expectedSum.checkEqual(sum))
		{
			System.out.println("Passed: add() - Test 3");
			return;
		}
		System.out.println("Failed: add() - Test 3");
		System.out.println("Expected: "+expectedSum +"\tresult: "+ sum );
	}
	
	
	private static void testAdd_4()
	{
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();

		p1.addTerm(new Term(8, new BigInteger("2")));
		p1.addTerm(new Term(0, new BigInteger("1")));

		p2.addTerm(new Term(3, new BigInteger("1")));
		p2.addTerm(new Term(4, new BigInteger("2")));
		

		Polynomial sum = Polynomial.add(p1, p2);
		Polynomial expectedSum = new Polynomial(); // reference
		expectedSum.addTermLast(new Term(8, new BigInteger("2")));
		expectedSum.addTermLast(new Term(4, new BigInteger("2")));
		expectedSum.addTermLast(new Term(3, new BigInteger("1")));
		expectedSum.addTermLast(new Term(0, new BigInteger("1")));

		if (sum != null && expectedSum.checkEqual(sum))
		{
			System.out.println("Passed: add() - Test 4");
			return;
		}
		System.out.println("Failed: add() - Test 4");
		System.out.println("Expected: "+expectedSum + "\tresult: "+ sum );
	}
	public static void testAdd_5(){
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
		p1.addTerm(new Term(10, new BigInteger("185")));
		p1.addTerm(new Term(2, new BigInteger("-1")));
		p1.addTerm(new Term(3, new BigInteger("-1")));
		p1.addTerm(new Term(4, new BigInteger("-1")));
		p1.addTerm(new Term(5, new BigInteger("-1")));
		p1.addTerm(new Term(1, new BigInteger("-1")));
		p1.addTerm(new Term(0, new BigInteger("-1")));

		p2.addTerm(new Term(6, new BigInteger("1")));
		p2.addTerm(new Term(7, new BigInteger("1")));
		p2.addTerm(new Term(5, new BigInteger("1")));

		Polynomial sum = Polynomial.add(p2, p1);
		
		Polynomial expectedSum=new Polynomial();
		expectedSum.addTermLast(new Term(10, new BigInteger("185")));
		expectedSum.addTermLast(new Term(7, new BigInteger("1")));
		expectedSum.addTermLast(new Term(6, new BigInteger("1")));
		expectedSum.addTermLast(new Term(4, new BigInteger("-1")));
		expectedSum.addTermLast(new Term(3, new BigInteger("-1")));
		expectedSum.addTermLast(new Term(2, new BigInteger("-1")));
		expectedSum.addTermLast(new Term(1, new BigInteger("-1")));
		expectedSum.addTermLast(new Term(0, new BigInteger("-1")));
		
		if (sum != null && expectedSum.checkEqual(sum))
		{
			System.out.println("Passed: add() - Test 5");
			return;
		}
		System.out.println("Failed: add() - Test 5");
		System.out.println("Expected: "+expectedSum + "\tresult: "+ sum );
	}
	private static void testAdd_6()
	{
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();

		p2.addTerm(new Term(8, new BigInteger("2")));
		p2.addTerm(new Term(0, new BigInteger("1")));

		p1.addTerm(new Term(3, new BigInteger("1")));
		p1.addTerm(new Term(4, new BigInteger("2")));
		

		Polynomial sum = Polynomial.add(p1, p2);
		Polynomial expectedSum = new Polynomial(); // reference
		expectedSum.addTermLast(new Term(8, new BigInteger("2")));
		expectedSum.addTermLast(new Term(4, new BigInteger("2")));
		expectedSum.addTermLast(new Term(3, new BigInteger("1")));
		expectedSum.addTermLast(new Term(0, new BigInteger("1")));

		if (sum != null && expectedSum.checkEqual(sum))
		{
			System.out.println("Passed: add() - Test 6");
			return;
		}
		System.out.println("Failed: add() - Test 6");
		System.out.println("Expected: "+expectedSum + "\tresult: "+ sum );
	}
	
	// Small polynomial test
	private static void testEval_1()
	{
		//2x^4+x^3+2x^2+1
		//(((2x+1)x+2)x)x+1
		// 2 5 12 24 48 49
		Polynomial p1 = new Polynomial();
		
		p1.addTermLast(new Term(4, new BigInteger("2")));
		p1.addTermLast(new Term(3, new BigInteger("1")));
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
		BigInteger result=p1.eval(new BigInteger("2"));
		if (result.compareTo(new BigInteger("49")) == 0)
		{
			System.out.println("Passed: eval() - Test 1");
			return;
		}
		System.out.println("Failed: eval()- Test 1");
		System.out.println("Expected: 49\tresult: "+ result );
	}
	
	// Long polynomial test.    Creates a polynomial with a large number of terms and each term has 
	//  coefficient 1 and we are evaluating at x=1,  so c_i x^i = 1 for each of these terms.
	//  Thus the polynomial would have value equal to the number of terms, i.e.  1 + 1 + .... +  1 = number of terms
	private static void testEval_2()
	{
		Polynomial p1 = new Polynomial();
		System.out.println("Preparation for test eval 2");
		for (int i = 0; i < 1000000; i++)
			p1.addTermLast(new Term(1000000 - i - 1, new BigInteger("1")));
		System.out.println("Test eval 2");
		BigInteger result=p1.eval(new BigInteger("1"));
		if (result.compareTo(new BigInteger("1000000")) == 0)
		{
			System.out.println("Passed: eval() - Test 2");
			return;
		}
		System.out.println("Failed: eval()- Test 2");
		System.out.println("Expected: 1000000\tresult: "+ result );
	}
	
	//Home made test for polynomial evaluation
	private static void testEval_3(){
		//2x^4+x^3+2x^2+x+2 @x=180
		//(((2x+1)x+2)x+1)x+2
		// ->2 361 64982 11696761 2105416982
		Polynomial p1 = new Polynomial();
		
		p1.addTermLast(new Term(4, new BigInteger("2")));
		p1.addTermLast(new Term(3, new BigInteger("1")));
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(1, new BigInteger("1")));
		p1.addTermLast(new Term(0, new BigInteger("2")));
		BigInteger result=p1.eval(new BigInteger("180"));
		if (result.compareTo(new BigInteger("2105416982")) == 0)
		{
			System.out.println("Passed: eval() - Test 3");
			return;
		}
		System.out.println("Failed: eval()- Test 3");
		System.out.println("Expected: 2105416982\tresult: "+ result );
	}
	
	//Home made test for polynomial evaluation
	private static void testEval_4(){
		//654223x^8+987x^3+20000 @x=0
		//(((654223x+987)x)x)x+20000
		// ->654223 987 0 0 0 20000
		Polynomial p1 = new Polynomial();
		
		p1.addTermLast(new Term(8, new BigInteger("654223")));
		p1.addTermLast(new Term(3, new BigInteger("987")));
		p1.addTermLast(new Term(0, new BigInteger("20000")));
		BigInteger result=p1.eval(new BigInteger("0"));
		if (result.compareTo(new BigInteger("20000")) == 0)
		{
			System.out.println("Passed: eval() - Test 4");
			return;
		}
		System.out.println("Failed: eval()- Test 4");
		System.out.println("Expected: 20000\tresult: "+ result );
	}
	
	private static void testMultiplyTerm()
	{	
		Polynomial p1 = new Polynomial();
				
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
		p1.multiplyTermTest(new Term(1, new BigInteger("3")));
		
		Polynomial result = new Polynomial();
		result.addTermLast(new Term(3, new BigInteger("6")));
		result.addTermLast(new Term(1, new BigInteger("3")));
		
		if (p1.size() != 0 && p1.checkEqual(result))
		{
			System.out.println("Passed: multiplyTerm()");
			return;
		}
		System.out.println("Failed: multiplyTerm()");
		System.out.println("Expected: "+result + "\tresult: "+ p1 );
	}
	
	//Home made test for term multiplication
	private static void testMultiplyTerm2()
	{	
		Polynomial p1 = new Polynomial();
				
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
		p1.multiplyTermTest(new Term(0, new BigInteger("0")));
		
		Polynomial result = new Polynomial();
		
		if (p1.size() == 0 && p1.checkEqual(result))
		{
			System.out.println("Passed: multiplyTerm2()");
			return;
		}
		System.out.println("Failed: multiplyTerm2()");
		System.out.println("Expected: "+result + "\tresult: "+ p1 );
	}
	
	//Home made test for term multiplication
	private static void testMultiplyTerm3()
	{	
		Polynomial p1 = new Polynomial();
				
		p1.addTermLast(new Term(1, new BigInteger("1")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
		p1.multiplyTermTest(new Term(1, new BigInteger("1")));
		
		Polynomial result = new Polynomial();
		result.addTermLast(new Term(2, new BigInteger("1")));
		result.addTermLast(new Term(1, new BigInteger("1")));
		
		if (p1.size() != 0 && p1.checkEqual(result))
		{
			System.out.println("Passed: multiplyTerm3()");
			return;
		}
		System.out.println("Failed: multiplyTerm3()");
		System.out.println("Expected: "+result + "\tresult: "+ p1 );
	}
	
	private static void testMultiply()
	{
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
		
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
		
		p2.addTermLast(new Term(2, new BigInteger("2")));
		p2.addTermLast(new Term(0, new BigInteger("1")));
		
		Polynomial product = Polynomial.multiply(p1, p2);
		Polynomial expectedProduct = new Polynomial();
		
		expectedProduct.addTermLast(new Term(4, new BigInteger("4")));
		expectedProduct.addTermLast(new Term(2, new BigInteger("4")));
		expectedProduct.addTermLast(new Term(0, new BigInteger("1")));
		
		if (product != null && product.checkEqual(expectedProduct))
		{
			System.out.println("Passed: multiply()");
			return;
		}
		System.out.println("Failed: multiply()");
		System.out.println("Expected: "+expectedProduct + "\tresult: "+ product );
		
	}
	
	//Home made basic multiplication test (to try when having problems)
	private static void testMultiply2()
	{
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
		
		p1.addTermLast(new Term(1, new BigInteger("1")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
		
		p2.addTermLast(new Term(1, new BigInteger("1")));
		p2.addTermLast(new Term(0, new BigInteger("1")));
		
		Polynomial product = Polynomial.multiply(p1, p2);
		Polynomial expectedProduct = new Polynomial();
		
		expectedProduct.addTermLast(new Term(2, new BigInteger("1")));
		expectedProduct.addTermLast(new Term(1, new BigInteger("2")));
		expectedProduct.addTermLast(new Term(0, new BigInteger("1")));
		
		if (product != null && product.checkEqual(expectedProduct))
		{
			System.out.println("Passed: multiply2()");
			return;
		}
		System.out.println("Failed: multiply2()");
		System.out.println("Expected: "+expectedProduct + "\tresult: "+ product );
	}
	
	public static void main(String[] args) 
	{
		testClone();
		testClone2();
		testAddTerm_1();
		testAddTerm_2();
		testAddTerm_3();
		testAdd_1();
		testAdd_2();
		testAdd_3();
		testAdd_4();
		testAdd_5();
		testAdd_6();
		testMultiplyTerm();
		testMultiplyTerm2();
		testMultiplyTerm3();
		testMultiply();
		testMultiply2();
		testEval_1();
		testEval_2();
		testEval_3();
		testEval_4();
	}
}
