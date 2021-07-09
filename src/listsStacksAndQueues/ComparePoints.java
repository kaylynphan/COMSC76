/*
 * Create a Point class that implements the Comparable interface, and overrides the compareTo method to sort points by x value.
 * Create another CompareY class that implements Comparator<Point> to sort points by y value.
 * Write a test program that creates 100 random points and sorts them by x and y.
 * 
 * Kaylyn Phan
 * 10 March 2020
 */

package listsStacksAndQueues;

import java.util.Comparator;
import java.util.Arrays;

public class ComparePoints {
	
	public static void main(String[] args) {
		// create 100 random points
		Point[] points = new Point[100];
		for (int i = 0; i < 100; i++) {
			points[i] = new Point(Math.random() * 100, Math.random() * 100);
		}
		// sort by x
		System.out.println("Sorting by x:");
		Arrays.sort(points);
		for (int i = 0; i < 100; i++) {
			System.out.println(points[i].toString());
		}
		// sort by y
		System.out.println("Sorting by y:");
		Arrays.sort(points, new CompareY());
		for (int i = 0; i < 100; i++) {
			System.out.println(points[i].toString());
		}
	}
}

class Point implements Comparable<Point> {
	public double x;
	public double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int compareTo(Point point) {
		if (x > point.x) {
			return 1;
		} else if (x < point.x) {
			return -1;
		} else {
			if (y > point.x) {
				return 1;
			} else if (y < point.y) {
				return -1;
			} else {
				return 0;
			}
		}		
	}
	
	public String toString() {
		return ("(" + x + "," + y + ")");
	}
}

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

	


