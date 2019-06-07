package pathbacktracking;

import java.util.Random;

import processing.core.PApplet;

public class Grid {
	private int width; 
	private int height; 
	private int windowWidth; 
	private int windowHeight; 
	private char[][] grid; 
	private PApplet p; 
	private int barriers;
	private int generatedBarries; 
	private boolean pathStart = false; 
	private boolean constructed = false;  
	private Path path; 
	
	public Grid(int width, int height, PApplet p) {
		this.width = width;
		this.height = height; 
		this.windowWidth = 25 * width + (width - 2); 
		this.windowHeight = 25 * height + (height - 2); 
		
		this.grid = new char[height][width];
		this.p = p; 
		this.barriers = (int)(width * height * 0.40) ; 
	}
	
	private void generateBarriers() {
		if (this.generatedBarries != this.barriers) {
			Random rand = new Random(); 
			boolean gen = false; 
			while (!gen) {
				int x = rand.nextInt(width);
				int y = rand.nextInt(height);	
				if (this.grid[y][x] != 'b') {
					this.grid[y][x] = 'b';
					gen = true; 
				}
			}
		this.generatedBarries++; 
		} else {
			this.pathStart = true; 
		}
	}
	
	private void drawLines() {
		//Draw the lines 
		p.stroke(255, 255, 255); 
		for (int i = 1; i < this.width; i++) {
			p.line(25 * i + (i - 1), 0, 25 * i + (i - 1), p.height); 
		}
		
		for (int i = 1; i < this.height; i++) {
			p.line(0, 25 * i + (i - 1), p.width, 25 * i + (i - 1));
		}
	}
	
	private void drawBarriers() {
		p.fill(0, 255, 100);
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[0].length; j++) {
				if (this.grid[i][j] == 'b') {
					p.rect(j * 26, i * 26, 25, 25);
				}
			}
		}
	}
	
	public void draw() { 
		//generate obstacles 		
		drawLines(); 
		
		for (int i = 0; i < 5; i++) {
			generateBarriers(); 
		}
		drawBarriers(); 
		
		if (this.pathStart) {
			if (!this.constructed) {
				this.path = new Path(this.p, this.grid); 
				this.constructed = true; 
				p.frameRate(60); 
			}
			this.path.handlePath();
		}
	}
	
	public int getWindowWidth() {
		return this.windowWidth;
	}
	
	public int getWindowHeight() {
		return this.windowHeight; 
	}
	
}
