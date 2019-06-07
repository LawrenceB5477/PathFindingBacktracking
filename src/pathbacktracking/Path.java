package pathbacktracking;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Path {
	private List<Point> path; 
	private PApplet p; 
	private char[][] grid; 
	private PathFinder pathfinder; 
	private boolean finished = false; 
	
	public Path(PApplet p, char[][] grid) {
		this.path = new ArrayList<>();
		this.p = p; 
		this.grid = grid; 
		this.pathfinder = new PathFinder(this.grid, this.path); 
	}
	
	public List<Point> getPath() {
		return this.path; 
	}
	
	public void drawPath() {
		p.stroke(255, 0, 0);
		for (int i = 0; i < path.size(); i++) {
			Point current = path.get(i); 
			Point next; 
			if (i == path.size() - 1) {
				next = path.get(i);
			} else {
				next = path.get(i+1); 
			}
			
			p.line(12 + (int)current.getX() * 26, 
					12 + (int) current.getY() * 26,
					12 +  (int) next.getX() * 26, 
					12 + (int) next.getY() * 26);
		}
	}
	
	public void handlePath() {
		if (this.finished) {
			drawPath(); 
			return; 
		}
		
		try {
			boolean pathFound = this.pathfinder.nextStep();
			if (pathFound) {
				System.out.println("The path is complete");
				this.finished = true;
			}
		} catch (Exception e) {
			System.out.println("The path is impossible.");
			this.finished = true;
		}
		
		drawPath(); 
	}
	

}
