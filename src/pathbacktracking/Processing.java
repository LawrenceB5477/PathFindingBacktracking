package pathbacktracking;

import processing.core.PApplet;

public class Processing extends PApplet {
	Grid grid; 
	private boolean called = false; 
	public static void main(String[] args) {
		PApplet.main("pathbacktracking.Processing");
	}
	
	@Override 
	public void settings() {
		this.grid = new Grid(50, 25, this); 
		size(this.grid.getWindowWidth(),  this.grid.getWindowHeight());
	}
	
	@Override
	public void setup() {
	}
	
	@Override 
	public void draw() {
		fill(0, 0, 0);
		rect(0, 0, width -1, height - 1); 
		this.grid.draw(); 
	}
}
