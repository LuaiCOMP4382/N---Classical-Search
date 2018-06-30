import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	
	// Definition: State is the positions of the N queens
	
	public static Board BFSBoard(Board initialBoard) {
		
		HashMap<Integer, Board> visitedStates = new HashMap<Integer, Board>(100); // Holds the visited states
		Queue<Board> searchQueue = new LinkedList<Board>(); // Queue used for Breadth-First Searching
		
		visitedStates.put(initialBoard.numericalRep(), initialBoard); // Save the initial state
		searchQueue.add(initialBoard);
		
		Board currentBoard = null; // currentBoard is the current state
		Board branchBoards[]; // branchBoards is an array that contains next states (branches) generated by currentBoard
		
		while (!searchQueue.isEmpty()) {
			
			currentBoard = searchQueue.remove();
			
			if (currentBoard.getCollisions() == 0) // If we have no queens attacking, we found a solution
				return currentBoard;

			branchBoards = currentBoard.generateNextStates(0); // depth = 0 because we don't care about depth

			// Any unvisited states are added to visitedStates and to
			// searchQueue
			// visited states are ignored
			for (int i = 0; i < branchBoards.length; i++)
				if (!visitedStates.containsValue(branchBoards[i])) {

					visitedStates.put(branchBoards[i].numericalRep(), branchBoards[i]);
					searchQueue.add(branchBoards[i]);

				}
			
		}
		
		return currentBoard;
		
	}
	
}