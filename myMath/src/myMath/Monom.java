
package myMath;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a simple "Monom" of shape ax^b, where a is a real number and a is an integer (summed a none negative),
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public static boolean containsX=false;
	public boolean created=true;
	
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}

	/**
	 * This method return the result of f(x_
	 * @param x
	 * @return
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	}

	/**
	 * This method return if the is a Zero monom , coefficient=0
	 * @return
	 */
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************

	/**
	 * This method created  a new monom with a given string input
	 * @param s
	 */
	public Monom(String s) {
		
		boolean isValidString;
		if(s.contains("x"))
		{
			containsX=true;
		}
		else
		{
			containsX=false;
		}
		s=updateStringForValidation(s,containsX);
		isValidString=checkValid(s,containsX);
	
			if(isValidString==true)
			{
			StringTokenizer st= new StringTokenizer(s,"x^");
			if(st.countTokens()==2)
			{

				try {
					this.set_coefficient(Double.parseDouble(st.nextToken()));
				}catch(RuntimeException error)
				{
					throw new RuntimeException("ERROR: "+error);
				}
				try {
					this.set_power(Integer.parseInt(st.nextToken()));
				}catch(RuntimeException error)
				{
					throw new RuntimeException("ERROR: "+error);
				}

			}
			else
			{
	
				try {
					this.set_coefficient(Double.parseDouble(st.nextToken()));
				}catch(RuntimeException error)
				{
					throw new RuntimeException("ERROR: "+error);
				}
				if(containsX==true)
				{
					this.set_power(1);
				}
			}
		}
		else
		{
			System.out.println("Invalid input");
			this.created=false;
			return;
		}
			
		}

	/**
	 * Help function that taking care of edge corner cases with a givin string input
	 * @param s
	 * @param containsX
	 * @return
	 */
	private String updateStringForValidation(String s, boolean containsX)
	{
		int xPosition=0;
		String prefix="";
		if(containsX==false)
		{
			if(s.equals("-")|s.equals("+"))
			{
				s=s+"1";
			}
		}
		else if(containsX==true)
		{
			xPosition=s.indexOf('x');
			prefix=s.substring(0,xPosition);
			if(prefix.equals("-")|(prefix.equals("+")))
			{
				s=prefix+"1"+s.substring(xPosition);
			
			}
			else if(prefix.equals(""))
			{
				return "1"+s;
			}
			else
			{
				return s;
			}

		}
		return s;
	}

	/**
	 * Check if a givin input string is valid using regex expression
	 * @param s
	 * @param containsX
	 * @return
	 */
	private boolean checkValid(String s, boolean containsX)
	{
		Pattern patternWithOutX= Pattern.compile("(^[+-]?([0-9]*\\.([0-9]+)?|[0-9]+))$");
		Pattern patternWithX = Pattern.compile("(^[+-]?([0-9]*\\.([0-9]+)?|[0-9]+)[x]([\\^][0-9]+)?)$");
		Matcher matcher;
		if(containsX==false)
		{
		matcher = patternWithOutX.matcher(s);
		}
		else
		{
			matcher=patternWithX.matcher(s);
		}
		
		if (matcher.find()){
			return true;
		}
		return false;
	}
	/**
	 * This method compute addition between two monoms
	 */
	public void add(Monom m)  {
		if(this.get_power()== m.get_power())
		{
			this.set_coefficient(this.get_coefficient()+m.get_coefficient());
		}
		else
		{
			
			System.out.println("Different power levels");
		}
		}

	/**
	 * 	  This method compute subtraction between two monoms
	 * @param m
	 */
	public void substract(Monom m) {
		if(this.get_power()== m.get_power())
		{
			this.set_coefficient(this.get_coefficient()-m.get_coefficient());
		}
		else
		{

			System.out.println("Different power levels");
		}
	}
	/**
	 * 	  This method compute multiply between two monoms
	 * @param d
	 */
	public void multiply(Monom d) {
		if(this==null || d==null)
		{
			return;
		}
		this.set_coefficient(this.get_coefficient()*d.get_coefficient());
		this.set_power(this.get_power()+d.get_power());
		;}
	/**
	 * 	  This method return the monmo as a string
	 * @param
	 */
	public String toString() {
		String coefficient="";
		String power="";
		String ans ="";
		if(this.get_power()!=0)
		{
			coefficient=Double.toString(this._coefficient);
			power=Integer.toString(this._power);
			ans=coefficient+"x"+"^"+power;
		}
		else
		{
			coefficient=Double.toString(this._coefficient);
			ans=coefficient;
		}
		return ans;
	}

	/**
	 * This method check if two monoms are equals logically
	 * @param
	 * @return
	 */
	public boolean equals(Monom m)
	{
		if(this.created==false || m.created==false)
		{
			return false;
		}
		if(this.isZero()&&m.isZero())
		{
			return true;
		}
		if(this._power!=m._power) {
			return false;
		}
		double diff=this._coefficient-m._coefficient;
		if(Math.abs(diff)<=EPSILON)
		{
			return true;
		}
		return false;
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************
	

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
}