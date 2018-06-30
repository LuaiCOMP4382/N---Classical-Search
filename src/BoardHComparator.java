import java.util.Comparator;

public class BoardHComparator implements Comparator<Board> {

	@Override
	public int compare(Board x, Board y) {
		
		if (x.getHeuristic() > y.getHeuristic())
			return 1;
		else if (x.getHeuristic() < y.getHeuristic())
			return -1;
		else
			return 0;
		
	}
	
}
