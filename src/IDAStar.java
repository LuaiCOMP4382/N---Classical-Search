import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class IDAStar {

	public static Board IDAStarBoard(Board initialBoard) {

		Comparator<Board> comp = new BoardHComparator();
		HashMap<Integer, Board> visitedStates = new HashMap<Integer, Board>(100); // Holds the visited states
		PriorityQueue<Board> searchHeap = new PriorityQueue<Board>(comp); // Heap used for A* Searching

		visitedStates.put(initialBoard.numericalRep(), initialBoard); // Save the initial state
		searchHeap.add(initialBoard);

		Board currentBoard = null; // currentBoard is the current state
		Board[] branchBoards; // branchBoards is an array that contains next states (branches) generated by currentBoard

		int threshold = initialBoard.getHeuristic(); // Determines to which heuristic limit to search
		int minHeur = Integer.MAX_VALUE;
		int depth; // Depth the graph is currently at

		while (!searchHeap.isEmpty()) {

			while (!searchHeap.isEmpty()) {

				currentBoard = searchHeap.remove();

				if (currentBoard.getCollisions() == 0)
					return currentBoard;

				depth = currentBoard.getDepth();

				branchBoards = currentBoard.generateNextStates(depth + 1);

				for (int i = 0; i < branchBoards.length; i++)
					if (!visitedStates.containsValue(branchBoards[i])) {

						if (branchBoards[i].getHeuristic() <= threshold) {

							minHeur = Math.min(minHeur, branchBoards[i].getHeuristic());
							visitedStates.put(branchBoards[i].numericalRep(), branchBoards[i]);
							searchHeap.add(branchBoards[i]);

						}

					}

			}
			
			threshold += minHeur; // Increase the heuristic threshold
			
			// Pretend like nothing ever happened
			visitedStates.clear();
			searchHeap.clear();

			visitedStates.put(initialBoard.numericalRep(), initialBoard); // Save the initial state
			searchHeap.add(initialBoard);

		}

		return currentBoard;

	}

}
