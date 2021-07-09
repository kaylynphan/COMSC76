/*
 * Write a program that uses the algorithm in 22.8 to find the closest pair of 
 * points in a divide and conquer approach.
 * 
 * Kaylyn Phan
 * 27 March 2020
 */
package efficientAlgorithms;

import java.util.ArrayList;
import java.util.Comparator;

// POINT CLASS
class Point {
	public double x;
	public double y;
	
	public Point() {
		this(0,0);
	}
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return String.format("(%.2f, %.2f)", x, y);
	}
}

// COMPARE X
class CompareX implements Comparator<Point> {
	public int compare(Point point1, Point point2) {
		if (point1.x > point2.x) { 
			return 1;
		} else if (point1.x < point2.x) {
			return -1;
		} else {
			if (point1.y > point2.y) {
				return 1;
			} else if (point1.y < point2.y) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}

// COMPARE Y
class CompareY implements Comparator<Point> {
	public int compare(Point point1, Point point2) {
		if (point1.y > point2.y) {
			return 1;
		} else if (point1.y < point2.y) {
			return -1;
		} else {
			if (point1.x > point2.x) {
				return 1;
			} else if (point1.x < point2.x) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}

// PAIR CLASS
class Pair {
	public Point p1;
	public Point p2;
	
	public Pair() {
		this.p1 = new Point(0,0);
		this.p2 = new Point(0,0);
	}
	
	public Pair(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public double getDistance() {
		return Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
	}
	
	public String toString() {
		return p1.toString() + " and " + p2.toString();
	}
}

// TEST CLASS
public class ClosestPairOfPoints {
	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<Point>();
		System.out.println("Points created:");
		for(int i = 0; i < 20; i++) {
			Point addPoint = new Point(Math.random() * 100, Math.random() * 100);
			points.add(addPoint);
			System.out.println(addPoint.toString());
		}
		
		Pair closestPair = findClosestPoints(points);
		
		System.out.println("The closest pair of points are:");
		System.out.println(closestPair.toString());
} 

// RECURSIVE FUNCTION
	public static Pair findClosestPoints(ArrayList<Point> s) { 
		//sort the list of Points by x value
		s.sort(new CompareX());
		Pair closestPair = new Pair();
		// use brute force comparison if there are three or less points to compare
		if (s.size() <= 3) {
			closestPair = bruteForce(s);
		} else {
			//split S into new ArrayLists S1 and S2
			ArrayList<Point> s1 = new ArrayList<Point>();
			ArrayList<Point> s2 = new ArrayList<Point>();
			int midIndex = (s.size() - 1) / 2; 
			Point midpoint = s.get(midIndex);
			int index = 0; 
			while (index < s.size()) {
				if (index <= midIndex) {
					s1.add(s.get(index));
				} else {
					s2.add(s.get(index));
				}
				index++;
			}
		
			//recursive call to find the closest points in each section
			Pair s1pair = findClosestPoints(s1);
			Pair s2pair = findClosestPoints(s2);
			double d1 = s1pair.getDistance();
			double d2 = s2pair.getDistance();
			
			//find the minimum of d1 and d2
			double d;
			if (d1 < d2) {
				closestPair = s1pair;
				d = d1;
			} else {
				closestPair = s2pair;
				d = d2;
			}
			// to compare points between stripL and stripR, first order S by y value
			s.sort(new CompareY());
			ArrayList<Point> stripL = new ArrayList<Point>();
			ArrayList<Point> stripR = new ArrayList<Point>();
			/* strips include points to the left or right of the midpoint 
			 * by a length of d or less*/
			for(Point p: s) {
				if (Math.abs(midpoint.x - p.x) <= d) {
					//determine if the point to the left or right of the midpoint
					if (p.x <= midpoint.x) { 
						stripL.add(p);
					} else { 
						stripR.add(p);
					}
				}
			}
			// compare points between stripL and stripR
			int r = 0; 
			for (Point p: stripL) {
				while (r < stripR.size() && stripR.get(r).y <= p.y - d) {
					r++;
				}
				int r1 = r;
				while (r1 < stripR.size() && Math.abs(stripR.get(r1).y - p.y) <= d) {
					Pair compareThese = new Pair(p, stripR.get(r1));
					double d3 = compareThese.getDistance();
					if (d3 < d) {
						d = d3;
						closestPair = new Pair(p, stripR.get(r1));
					}
					r1++;
				}
			}
		}	
		return closestPair;
	}
	
	// Brute force method used only for comparing between three points
	public static Pair bruteForce(ArrayList<Point> s) {
		Pair closestPair = new Pair();
		if (s.size() == 2) {
			closestPair = new Pair(s.get(0), s.get(1));
		} else {
			Pair pair1 = new Pair(s.get(0), s.get(1));
			Pair pair2 = new Pair(s.get(1), s.get(2));
			Pair pair3 = new Pair(s.get(0), s.get(2));
		
			closestPair = pair1; //default	
			if (pair2.getDistance() < pair1.getDistance()) {
				if (pair2.getDistance() < pair3.getDistance()) {
					closestPair = pair2;
				} else {
					closestPair = pair3;
				}
			}	
		}
		return closestPair;
	}
}
	
		

	