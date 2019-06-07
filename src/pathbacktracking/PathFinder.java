package pathbacktracking;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class PathFinder {
	private char[][] grid; 
	private List<Point> path; 
	private Deque<StackFrame> stack; 
	private int boardHeight; 
	private int boardWidth; 
	private int boardBeginX; 
	private static final int RIGHT = 0; 
	private static final int DOWN = 1; 
	private static final int LEFT = 2; 
	private static final int UP = 3; 
	private static final int FAIL = 4; 
	
	public PathFinder(char[][] grid, List<Point> path) {
		this.grid = grid;
		this.path = path;
		this.stack = new ArrayDeque<>(); 
		this.boardHeight = this.grid.length; 
		this.boardWidth = this.grid[0].length; 
	}
	
	private class StackFrame {
		private int x; 
		private int y; 
		private int move; 
		private int position;
		
		public StackFrame(int x, int y, int move) {
			this.x = x; 
			this.y = y; 
			this.move = move; 
			grid[y][x] = 'p'; 
		}
		
		public int getMove() {
			return this.move; 
		}
		
		public void setMove(int move) {
			this.move = move; 
		}
		
		public int getX() {
			return this.x; 
		}
		
		public int getY() {
			return this.y; 
		}
		
		public int getPosition() {
			return this.position;
		}
		
		public void setPosition(int position) {
			this.position = position; 
		}
		
		public void tearDown() {
			grid[this.y][this.x] = ' '; 
		}
	}
	
	
	private int moveAllowed(int x, int y, int position) {
		//Check right 
		if (x + 1 < this.boardWidth && !isTaken(x+1, y) && position < RIGHT) {
			return RIGHT; 
		}
		
		//Check down 
		if (y + 1 < this.boardHeight && !isTaken(x, y+1) && position < DOWN) {
			return DOWN; 
		}
		
		//Check left 
		if (x - 1 >= 0 && !isTaken(x-1, y) && position < LEFT) {
			return LEFT; 
		}
		
		//Check up
		if (y - 1 >= 0 && !isTaken(x, y-1) && position < UP) {
			return UP; 
		}
		
		return FAIL; 
	}
	
	private boolean isTaken(int x, int y) {
		return this.grid[y][x] == 'b' || this.grid[y][x] == 'p'; 
	}
	
	public boolean nextStep() throws Exception {
		if (this.stack.isEmpty()) {
			System.out.println("Back at the start"); 
			for (; this.boardBeginX < this.boardHeight; this.boardBeginX++) {
				if (!isTaken(0, this.boardBeginX)) {
					this.stack.push(new StackFrame(0, this.boardBeginX, -1));
					this.path.add(new Point(
							this.stack.peekLast().getX(),
							this.stack.peekLast().getY()
							));
					this.boardBeginX++; 
					return false; 
				}
			}
			throw new Exception(); 
		} else {
			StackFrame previous = this.stack.peekLast();
			int move = moveAllowed(previous.getX(), previous.getY(), previous.getMove()); 
			int newX = previous.getX(); 
			int newY = previous.getY();
			
			switch (move) {
				case UP:
					newY--; 
					break; 
				case RIGHT:
					newX++; 
					break; 
				case DOWN:
					newY++;
					break; 
				case LEFT: 
					newX--; 
					break; 
				case FAIL:
					System.out.println("We have a failure"); 
					this.stack.removeLast();
					previous.tearDown();
					this.path.remove(this.path.size() - 1); 
					return false;  
			}
			
			previous.setMove(move);
			this.stack.addLast(new StackFrame(newX, newY, -1));
			this.path.add(new Point(
					newX,
					newY
					));
			if (newX == this.boardWidth - 1) {
				return true; 
			}
			
		}
		
		return false; 
	}
}
