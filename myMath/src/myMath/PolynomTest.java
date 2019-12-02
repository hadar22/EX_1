package myMath;

public class PolynomTest {
	public static void main(String[] args) {
	//test1();
		//test2();
	test3();
	}
	public static void test1() {
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2", "0.5x^2"};
		for(int i=0;i<monoms.length;i++) {
		Monom m = new Monom(monoms[i]);
		p1.add(m);
		//double aa = p1.area(0, 1, 0.0001);
		//p1.substract(p1);
		}
		System.out.println(p1);
	}

	

	public static void test2() {
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1x^2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		System.out.println("p1: "+p1);
		System.out.println("p2: "+p2);
		p1.add(p2);
		System.out.println("p1+p2: "+p1);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: "+p1);
		String s1 = p1.toString();
		//Polynom_able pp1 = Polynom.parse(s1);
		//System.out.println("from string: "+pp1);
	}
	public static void test3() {
		Polynom []poly=new Polynom[4];
		Polynom pTest=new Polynom("-x^4+3x^2");
		String[] polynoms = {"1x+5a^7", "8x^3+4x+2", "-x^4+3x^2", "7x+8x^2+0.5x^4"};

		for (int i = 0; i < polynoms.length; i++) {
			poly[i]=new Polynom(polynoms[i]);
		}
		for(int i=0; i<poly.length; i++)
		{
			double areaPoly=0;
			double rootPoly=0;
			System.out.println("Polynom "+i+" is:\t"+poly[i].toString());
			 areaPoly = poly[i].area(0, 3, 0.0001);
		try{
			rootPoly= poly[i].root(-1,2,0.0001);
			}
		catch(RuntimeException error)
			{
				System.out.println("");
			}
			System.out.println("Polynom area is:\t"+areaPoly);
			System.out.println("Is equal pTest:\t"+poly[i].equals(pTest));
			System.out.println("Polynom root:\t"+rootPoly);
			Polynom_able pDet=poly[i].derivative();
			System.out.println("Polynom derivative is:\t"+pDet.toString());
			System.out.println("*******************************");
		}
	}

}
