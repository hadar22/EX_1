package myMath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 *
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	private ArrayList<Monom> monoms;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static final double EPSILON = 0.0000001;


	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		this.monoms=new ArrayList<Monom>();

	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x""};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		this.monoms=new ArrayList<Monom>();
		String  splitPoly[]=s.split("(?=[+-])");

		for(int i=0; i<splitPoly.length; i++)
		{
			Monom tmp=new Monom(splitPoly[i]);
			if(tmp.created==false)
			{
				this.monoms=null;
				return;
			}
			else
			{
				this.add(tmp);
			}
		}
		this.monoms.sort(_Comp);

	}
	@Override
	public String toString()
	{
		String ans="";
		if(this.monoms==null)
		{
			return ans;
		}
		for(int i=0; i<this.monoms.size(); i++)
		{
			if(this.monoms.get(i).get_coefficient()>=0)
			{
			ans=ans+"+"+this.monoms.get(i);
			}
			else
			{
				ans=ans+this.monoms.get(i);
			}
		}
		return ans;
	}
	@Override
	public double f(double x) {
		double ans=0;
		if(this.monoms==null) {
			return 0;
		}
		for(int i=0; i<this.monoms.size(); i++)
		{
			ans+=this.monoms.get(i).f(x);
		}
		return ans;
	}

	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> it=p1.iteretor();
		if(this.monoms==null||it==null) {
			return;
		}
		while(it.hasNext())
		{
			Monom m=new Monom(it.next());
			this.add(m);
		}

	}

	@Override
	public void add(Monom m1) {
		boolean add=false;
		if(m1==null){
			return;
		}
		if(this.monoms==null)
		{
			this.monoms.add(m1);
			this.monoms.sort(_Comp);
			return;
		}
		for(int i=0; i<this.monoms.size(); i++)
		{
			if(this.monoms.get(i).get_power()==m1.get_power())
			{
				this.monoms.get(i).add(m1);
				add=true;
				break;
			}
		}
		if(add==false)
		{
			this.monoms.add(m1);
			this.monoms.sort(_Comp);
		}
	}

	public void substract(Monom m1) {
		boolean substract=false;
		if(m1==null){
			return;
		}
		if(this.monoms==null)
		{
			m1.multiply(Monom.MINUS1);
			this.monoms.add(m1);
			this.monoms.sort(_Comp);
		}
		for(int i=0; i<this.monoms.size(); i++)
		{
			if(this.monoms.get(i).get_power()==m1.get_power())
			{
				this.monoms.get(i).substract(m1);
				substract=true;
				break;
			}
		}
		if(substract==false)
		{
			m1.multiply(Monom.MINUS1);
			this.monoms.add(m1);
			this.monoms.sort(_Comp);
		}
	}

	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> it=p1.iteretor();
		if(this.monoms==null||it==null) {
			return;
		}
		while(it.hasNext())
		{
			Monom m=new Monom(it.next());
			this.substract(m);
		}

	}

	@Override
	public void multiply(Polynom_able p1) {
		Iterator<Monom> it=p1.iteretor();
		if(this.monoms==null||it==null) {
			return;
		}
		Polynom pTmp=new Polynom();
		while(it.hasNext())
		{
			Monom m=new Monom(it.next());
			for(int i=0; i<this.monoms.size(); i++)
			{
				Monom m2=new Monom(this.monoms.get(i));
				m2.multiply(m);
				pTmp.add(m2);
			}
		}

		this.monoms=pTmp.monoms;
	}

	@Override
	public boolean equals(Polynom_able p1) {
		Polynom_able pTmp=this.copy();
		if(pTmp==null||p1==null)
        {
            return false;
        }
        pTmp.substract(p1);
        Iterator<Monom> it=pTmp.iteretor();
        while(it.hasNext())
        {
            Monom t=new Monom(it.next());
            if(!(Math.abs(t.get_coefficient())<=EPSILON))
            {
                return false;
            }
        }
        return true;
	}


	@Override
	public boolean isZero() {
		if(this.monoms==null)
		{
			return false;
		}
		for(int i=0; i<this.monoms.size(); i++)
		{
			if(this.monoms.get(i).isZero()==false)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
	    if(this.monoms==null)
        {
            return 0;
        }
		 if (f(x0) * f(x1) > 0)
	        {
			 {throw new RuntimeException("No avilable root within given range");}
	        }

	        double c = x0;
	        while ((x1-x0) >= eps)
	        {
	            c = (x0+x1)/2;
	            if (f(c) == 0)
	            {
	                break;
	            }
	            else if (f(c)*f(x0) < 0)
	                x1 = c;
	            else
	                x0= c;
	        }
	            return c;
	}

	@Override
	public Polynom_able copy() {
		Polynom_able p1=new Polynom();
		if(this.monoms==null)
		{
			return p1;
		}
		Polynom pTmp=new Polynom();
		for(int i=0; i<this.monoms.size(); i++)
		{
			Monom tmp=new Monom(this.monoms.get(i));
			pTmp.monoms.add(tmp);
		}
		p1=pTmp;
		return p1;
	}

	@Override
	public Polynom_able derivative() {
		Polynom_able p1=new Polynom();
		Polynom pDer=new Polynom();
		if(this.monoms==null)
		{
			return pDer;
		}
		for(int i=0; i<this.monoms.size(); i++)
		{
			Monom tmp=new Monom(this.monoms.get(i));
			Monom tmp2=tmp.derivative();
			pDer.monoms.add(tmp2);
		}
		p1=pDer;
		return p1;
	}

	public double area(double x0, double x1, double eps) {
		double area=0;
		double midPointSum=0;
		double tmpArea=0;
		if(x0>=x1)
		{
			return 0;
		}
		int steps=(int)((x1-x0)/eps);
		for(int i=1; i<=steps; i++) {
			midPointSum=(this.f(x0+eps*i)+this.f(x0+eps*(i-1)))/2;
			tmpArea=midPointSum*eps;
			if(tmpArea>=0)
			{
				area+=tmpArea;
			}
			tmpArea=0;
		}
		return area;
	}
	@Override
	public Iterator<Monom> iteretor() {
		Iterator<Monom> it=null;
		try{it=this.monoms.iterator();}
		catch (NullPointerException error)
		{
			System.out.println("NullPointerException");
		}
		return it;
	}
	@Override
	public void multiply(Monom m1) {
		if((this.monoms==null)||(m1==null))
		{
			return;
		}
		for(int i=0; i<this.monoms.size(); i++)
		{
			this.monoms.get(i).multiply(m1);
		}

	}

}

