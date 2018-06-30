import java.util.HashMap;
import java.util.Stack;

public class DFS {
	
	// Definition: State is the positions of the N queens
	
	public static Board DFSBoard(Board initialBoard) {
		
		HashMap<Integer, Board> visitedStates = new HashMap<Integer, Board>(100); // Holds the visited states
		Stack<Board> searchStack = new Stack<Board>(); // Stack used for Depth-First Searching
		
		visitedStates.put(initialBoard.numericalRep(), initialBoard); // Save the initial state
		searchStack.add(initialBoard);
		
		Board currentBoard = null; // currentBoard is the current state
		Board[] branchBoards; // branchBoards is an array that contains next states (branches) generated by currentBoard
		
		while (!searchStack.isEmpty()) {
			
			currentBoard = searchStack.pop();	
			
			if (currentBoard.getCollisions() == 0) // If we have no queens attacking, we found a solution
				return currentBoard;

			branchBoards = currentBoard.generateNextStates(0); // depth = 0 because we don't care about depth

			// Any unvisited states are added to visitedStates and to
			// searchStack
			// visited states are ignored
			for (int i = 0; i < branchBoards.length; i++)
				if (!visitedStates.containsValue(branchBoards[i])) {

					visitedStates.put(branchBoards[i].numericalRep(), branchBoards[i]);
					searchStack.add(branchBoards[i]);

				}

		}
		
		return currentBoard;
		
	}
	
}
