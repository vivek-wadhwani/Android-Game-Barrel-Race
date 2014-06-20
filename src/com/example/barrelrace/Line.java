package com.example.barrelrace;
import android.graphics.Point;
//import android.util.Log;

public class Line {
	private   int A;
	private   int B;
	private   int C;
	private   Point p , q ;
	//Getters and setters.................................
	
	//Constructors...................
	public Line(){
		
	}
	public Line(int x1 , int y1 , int x2 , int y2){
		//y1 = - y1;
		//y2 = - y2;
		A = y1-y2;
	
		B = x2-x1;
	
		C = (x1*(y2-y1))+( y1 * (x1-x2));

		p = new Point(x1,y1);
		q = new Point(x2,y2);
	
	}
	
	//Extra methods ...........................
	//to Get line perpendicular to the given line..........................

	public Line perpendicularLine(){
		Line perpendicularLine = new Line();
		int pa,pb,pc;
		pa = getQ().x - getP().x;
		pb = getQ().y - getP().y;
		pc = (getP().x * (getP().x - getQ().x)) +(getP().y * (getP().y - getQ().y));
		perpendicularLine.setA(pa);
		perpendicularLine.setB(pb);
		perpendicularLine.setC(pc);
		perpendicularLine.setP(new Point(0,0));
		perpendicularLine.setQ(new Point(0,0));
		return perpendicularLine;
	}
	public  double pointDistance(int x1 , int y1){
		double distance=0;
		//y1 = -y1;
		distance = Math.abs((A * x1)+(B * y1)+C)/(Math.sqrt((A*A) +(B*B)));
		return distance;
	}
	public int getA() {
		return A;
	}
	public void setA(int a) {
		A = a;
	}
	public int getB() {
		return B;
	}
	public void setB(int b) {
		B = b;
	}
	public int getC() {
		return C;
	}
	public void setC(int c) {
		C = c;
	}
	public Point getP() {
		return p;
	}
	public void setP(Point P) {
		p = P;
	}
	public Point getQ() {
		return q;
	}
	public void setQ(Point Q) {
		q = Q;
	}

}