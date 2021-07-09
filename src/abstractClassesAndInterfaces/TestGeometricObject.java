/*
 * Use the abstract Geometric Object class, and Circle and Rectangle classes. Create a Triangle 
 * class that extends from Geometric Object and has three sides. Write a test program that prompts the user
 * to enter a triangle's side lengths, color, and whether or not it is filled. The program will print
 * the properties of the triangle and also circles and rectangles.
 * 
 * Kaylyn Phan
 * 4 Feb 2020
 * 
 */
package abstractClassesAndInterfaces;

import java.util.Scanner;

// test program
public class TestGeometricObject {
	
	// determines whether the triangle's side lengths are valid
	public static boolean validTriangle(double side1, double side2, double side3) {
		boolean valid = false;
		double halfPerimeter = (side1 + side2 + side3) / 2;
		if (halfPerimeter > side1 && halfPerimeter > side2 && halfPerimeter > side3) {
			valid = true;
		}
		return valid;
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		// repeatedly asks the user for triangle side lengths until they are valid
		System.out.print("Enter the side lengths of a triangle separated by spaces: ");
			double side1 = input.nextDouble();
			double side2 = input.nextDouble();
			double side3 = input.nextDouble();
		while (validTriangle(side1, side2, side3) == false) {
			System.out.print("These side lengths do not make a valid triangle. Please try again: ");
			side1 = input.nextDouble();
			side2 = input.nextDouble();
			side3 = input.nextDouble();
		}
		System.out.print("Enter the triangle's color: ");
		String color = input.next();
		System.out.print("Is your triangle filled? Respond \"yes\" if so. ");
		boolean filled = input.next().equalsIgnoreCase("yes");
		
		// construct a triangle, circle, and rectangle
		Triangle triangle = new Triangle(color, filled, side1, side2, side3);
		Circle circle = new Circle("blue", false, 10.0);
		Rectangle rectangle = new Rectangle("orange", true, 6.8, 9.5);
		
		//print the properties of each geometric object
		System.out.print("Triangle:\n" + triangle.toString() + "\nCircle:\n" + circle.toString() + "\nRectangle:\n" + rectangle.toString());
		input.close();
	}
}

abstract class GeometricObject {
	  private String color = "white";
	  private boolean filled;
	  private java.util.Date dateCreated;

	  /** Construct a default geometric object */
	  protected GeometricObject() {
	    dateCreated = new java.util.Date();
	  }

	  /** Construct a geometric object with color and filled value */
	  protected GeometricObject(String color, boolean filled) {
	    dateCreated = new java.util.Date();
	    this.color = color;
	    this.filled = filled;
	  }

	  /** Return color */
	  public String getColor() {
	    return color;
	  }

	  /** Set a new color */
	  public void setColor(String color) {
	    this.color = color;
	  }

	  /** Return filled. Since filled is boolean,
	   *  the get method is named isFilled */
	  public boolean isFilled() {
	    return filled;
	  }

	  /** Set a new filled */
	  public void setFilled(boolean filled) {
	    this.filled = filled;
	  }

	  /** Get dateCreated */
	  public java.util.Date getDateCreated() {
	    return dateCreated;
	  }

	  /** Return a string representation of this object */
	  public String toString() {
	    return "color: " + color +
	      "\nfilled: " + filled;
	  }

	  /** Abstract method getArea */
	  public abstract double getArea();

	  /** Abstract method getPerimeter */
	  public abstract double getPerimeter();
	}

class Triangle extends GeometricObject {
	private double side1;
	private double side2;
	private double side3;
	
	// construct a triangle with color, filled value, and 3 sides
	public Triangle (String color, boolean filled, double side1, double side2, double side3) {
		super(color, filled);
		this.side1 = side1;
		this.side2 = side2;
		this.side3 = side3;
	}

	// returns triangle's area using Heron's formula
	@Override
	public double getArea() {
		double p = 0.5 * getPerimeter(); // p = (side1 + side2 + side3) / 2
		return Math.sqrt(p * (p - side1) * (p - side2) * (p - side3));
	}

	// returns triangle's perimeter
	@Override
	public double getPerimeter() {
		return side1 + side2 + side3;
	}	
	
	// returns a string of triangle's properties
	@Override
	public String toString() {
	    return super.toString() + "\narea: " + getArea() + "\nperimeter: " + getPerimeter();  		
	}
}

class Circle extends GeometricObject {
	  private double radius;

	  public Circle() {
	  }

	  // construct a circle with color, filled value, and radius
	  public Circle(String color, boolean filled, double radius) {
	    super(color, filled);
		this.radius = radius;
	  }

	  /** Return radius */
	  public double getRadius() {
	    return radius;
	  }

	  /** Set a new radius */
	  public void setRadius(double radius) {
	    this.radius = radius;
	  }

	  @Override /** Return area */
	  public double getArea() {
	    return radius * radius * Math.PI;
	  }

	  /** Return diameter */
	  public double getDiameter() {
	    return 2 * radius;
	  }

	  @Override /** Return perimeter */
	  public double getPerimeter() {
	    return 2 * radius * Math.PI;
	  }

	  // return a string of the circle's properties
	  @Override
		public String toString() {
		    return super.toString() + "\narea: " + getArea() + "\nperimeter: " + getPerimeter();	
	  }
	}

class Rectangle extends GeometricObject {
	  private double width;
	  private double height;

	  public Rectangle() {
	  }

	  // construct a rectangle with color, filled value, width and height
	  public Rectangle(String color, boolean filled, double width, double height) {
		super(color, filled);
	    this.width = width;
	    this.height = height;
	  }

	  /** Return width */
	  public double getWidth() {
	    return width;
	  }

	  /** Set a new width */
	  public void setWidth(double width) {
	    this.width = width;
	  }

	  /** Return height */
	  public double getHeight() {
	    return height;
	  }

	  /** Set a new height */
	  public void setHeight(double height) {
	    this.height = height;
	  }

	  @Override /** Return area */
	  public double getArea() {
	    return width * height;
	  }

	  @Override /** Return perimeter */
	  public double getPerimeter() {
	    return 2 * (width + height);
	  }
	    
	  // returns a string of the rectangle's properties
	  @Override
	  public String toString() {
		return super.toString() + "\narea: " + getArea() + "\nperimeter: " + getPerimeter();
		    		
	  }
	}

