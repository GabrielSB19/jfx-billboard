package model;

import java.io.Serializable;

public class Billboard implements Serializable{
	private static final long serialVersionUID = 1;
	
	private double width;
	private double height;
	private boolean use;
	private String brand;
	
	public Billboard (double width, double height, boolean use, String brand) {
		this.width = width;
		this.height = height;
		this.use = use;
		this.brand = brand;
	}
	
	public double getWidth () {
		return width;
	}
	
	public double getHeight () {
		return height;
	}
	
	public boolean getUse () {
		return use;
	}
	
	public String getBrand () {
		return brand;
	}
	
	public double getArea () {
		double area;
		area = width*height;
		return area;
	}
}
