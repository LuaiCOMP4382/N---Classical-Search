import java.util.HashMap;
import java.util.Stack;

public class IDS {
	
public static Board IDSBoard(Board initialBoard) {
		
		HashMap<Integer, Board> visitedStates = new HashMap<Integer, Board>(100); // Holds the visited states
		Stack<Board> searchStack = new Stack<Board>(); // Stack used for Depth-First Searching
		
		visitedStates.put(initialBoard.numericalRep(), initialBoard); // Save the initial state
		searchStack.add(initialBoard);
		
		Board currentBoard = null; // currentBoard is the current state
		Board[] branchBoards; // branchBoards is an array that contains next states (branches) generated by currentBoard
		
		int levelLimit = 1; // Determines to which level or depth to search
		int depth; // Depth the graph is currently at
		
		while(!searchStack.isEmpty()) {
		
			while (!searchStack.isEmpty()) {
			
				currentBoard = searchStack.pop();	
			
				if (currentBoard.getCollisions() == 0) // If we have no queens attacking, we found a solution
					return currentBoard;
				
				depth = currentBoard.getDepth();

				if (depth <= levelLimit) { // Checks if this depth is allowed in
											// the current level

					branchBoards = currentBoard.generateNextStates(depth + 1);

					// Any unvisited states are added to visitedStates and to
					// searchStack
					// visited states are ignored
					for (int i = 0; i < branchBoards.length; i++)
						if (!visitedStates.containsValue(branchBoards[i])) {

							visitedStates.put(branchBoards[i].numericalRep(), branchBoards[i]);
							searchStack.add(branchBoards[i]);

						}
					
				}

			}
			
			// Pretend like nothing ever happened
			visitedStates.clear();
			searchStack.clear();
			
			visitedStates.put(initialBoard.numericalRep(), initialBoard); // Save the initial state
			searchStack.add(initialBoard);
			
			levelLimit++;
			
		}
		
		return currentBoard;
		
	}

}
