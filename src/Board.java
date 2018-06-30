import java.util.ArrayList;
import java.util.Random;

public class Board {

	public static int N = 8;
	
	private int[] positions;
	private int collisions; // Number of queen pairs attacking each other
	private int heuristic; // f(n) = g(n) + h(n) value (for A*)
	private int depth; // The depth this board (node) is at
	
	public Board(int depth) { // Initialize with random positions for N queens with depth given (for A*)
		
		this.positions = new int[N];
		
		Random random = new Random();
		
		byte[] randomArray = new byte[N]; // Used for generating queens on different columns
		
		for (int i = 0; i < N; i++)
			randomArray[i] = 0;
		
		int randomInt;
		
		for (int i = 0; i < N; i++) {
			
			
			while (true) {
				
				randomInt = random.nextInt(N);
				
				if (randomArray[randomInt] == 1) // If randomInt is already in randomArray, get another random
					continue;
				else {
					
					randomArray[randomInt] = 1; // Mark randomInt as 1 (now is taken
					break;
				
				} 
				
			}
			
			this.positions[i] = randomInt;
		
		}
		
		this.collisions = calculateCollisions();
		this.depth = depth;
		this.heuristic = depth + this.collisions;
		
	}

	public Board(Board b, int depth) { // Initialize with positions copied from Board b with depth given (for A*)
		
		this.positions = b.getPositions();
		this.collisions = calculateCollisions();
		this.depth = depth;
		this.heuristic = depth + this.collisions;
		
	}
	
	public int calculateCollisions() {
		
		// Get number of attacking pairs. Start with the first queen, and check with the queen after this queen. Do the same for other queens
		
		int result = 0;
		
		for (int i = 0; i < N; i++)
			for (int j = i + 1; j < N; j++)
				if (collide(i, positions[i], j, positions[j]))
					result++;
		
		return result;
		
	}
	
	public boolean collide(int x1, int y1, int x2, int y2) {
		return x1 == x2 || y1 == y2 || Math.abs(x1 - x2) == Math.abs(y1 - y2); // Same row, same column or same diagonal
	}
	
	public int getCollisions() {
		return collisions;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public int getHeuristic() {
		return heuristic;
	}
	
	public int[] getPositions() {
		
		// Get a copy of positions array
		
		int[] result = new int[N];
		
		for (int i = 0; i < N; i++)
			result[i] = positions[i];
		
		return result;
		
	}
	
	@Override
	public boolean equals(Object other) {
		
		// Two Boards are equal when their heuristics are equal
		
		if (other == null)
			return false;
		
		if (other == this)
			return true;
		
		if (!(other instanceof Board))
			return false;
		
		return this.numericalRep() == ((Board) other).numericalRep();
		
	}
	
	@Override
	public int hashCode() {
		return numericalRep();
	}
	
	
	public int numericalRep() {
		
		// Numerical representation of the board is a number with the most signifcant digit is position[0],
		// then position[1] and so on. If position[0] is 0, make it N, to avoid deleting zeros from the left.
		// Useful for hashCode and ensuring unique representation
		
		long result = (long) Math.pow(10, N - 1) * (positions[0] == 0 ? N : positions[0]);
		long tens = 1;
		
		for (int i = N - 1; i > 0; i--) {
			
			result += tens * positions[i];
			tens *= 10;
		
		}			
					
		return (int) result;
		
	}
	
	public Board[] generateNextStates(int depth) { // depth is used for heuristic functions (for A*)
		
		ArrayList<Board> result = new ArrayList<Board>(((N - 1) * (N - 2)) / 2);
		
		int temp;
		for (int i = 0; i < N; i++) { 
			
			Board boardToAdd = new Board(this, depth); // Create a copy of this board
			
			for (int j = i + 1; j < N; j++) {
				
				// Next states are gathered simply by replacing queen i's column with queen j's column
				temp = boardToAdd.positions[i];
				boardToAdd.positions[i] = boardToAdd.positions[j];
				boardToAdd.positions[j] = temp;
				
				result.add(new Board(boardToAdd, depth));
				
			}
			
		}
		
		// We need to return Board[], not ArrayList<Board>
		// Size of arrayToReturn may be lower than N * 2, because redundant states aren't added
		// For example: moving a queen on the leftmost square to the left won't work, and thus does not make a new state
		
		Board[] arrayToReturn = new Board[result.size()]; 
		arrayToReturn = result.toArray(arrayToReturn);
		
		return arrayToReturn;
		
	}
	
	public Board clone() {
		return new Board(this, 0);
	}
	
	public String toString() {
		
		// In the result string, the ith character starting from 0 from left is the column the queen in, with row i
		// Example: 04321, queen #1 is in row 0 column 0, queen #2 is in row 1 column 4, queen #3 is in row 2 column 3, queen #4 is in row 3 column 2, queen #5 is in row 4 column 1
		
		String result = "";
		
		for (byte i = 0; i < N; i++)
			result += positions[i];
		
		return result;
		
	}
	
}
