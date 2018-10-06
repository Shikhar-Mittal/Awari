public class studentAI extends Player {
	private int maxDepth;

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public void move(BoardState state) {

		// introducing pit to store the best move
	    move = alphabetaSearch(state, maxDepth);

		// TEST CASES
		// System.out.println(maxValue(state, maxDepth, 2, -1000, 1000));
		// System.out.println(alphabetaSearch(state, 10));

	}

	public int alphabetaSearch(BoardState state, int maxDepth) {

		// initializing array for 6 pits on the board
		int[] pits = new int[6];
		
		// state to check moves
		BoardState move = state;
	
		// initializing the alpha and the beta value
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		int bestPit = 0;

		// for loop to get the SBE of the pits
		for (int i = 0; i <= 5; i++) {

			// checking if the move is legal
			if (state.isLegalMove(1, i)) {

				// variable set to max since node at depth 1 is a max value
				int v = minValue(move.applyMove(1, i), maxDepth, maxDepth - 1, alpha, beta);

				// updating the alpha value based on our search
				if (v > alpha) {
					alpha = v;
					bestPit = i;
				}

				// pruning
				if (beta <= alpha)
					break;
			}
		}

		return bestPit;
	}

	public int maxValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta) {

		// if the search has reached the max cut-off depth; return the max
		// value, i.e. alpha
		if (currentDepth == 0)
			return sbe(state);

		// initializing the smaller possible number to x
		int v = Integer.MIN_VALUE;

		for (int i = 0; i <= 5; i++) {

			// checking if a move is legal
			if (state.isLegalMove(1, i)) {

				// updating our max value to v
				v = Math.max(v, minValue(state.applyMove(1, i), maxDepth, currentDepth - 1, alpha, beta));
				alpha = Math.max(alpha, v);
				
				// Pruning
				if (beta <= alpha) 	
					break;
				
			}
		}

		if (v == Integer.MIN_VALUE) {
			return sbe(state);
		}

		return v;
	}

	public int minValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta) {

		// if the search has reached the max cut-off depth; return the min
		// value, i.e. beta
		if (currentDepth == 0)
			return sbe(state);

		// initializing variable to the maximum possible value
		int v = Integer.MAX_VALUE;

		for (int i = 0; i <= 5; i++) {
			
			// checks for the legal move
			if (state.isLegalMove(2, i)) {
				
				// updating our max value to v
				v = Math.min(v, maxValue(state.applyMove(2, i), maxDepth, currentDepth - 1, alpha, beta));

				// updating beta is a new min is found
				beta = Math.min(beta, v);

				//pruning
				if (beta <= alpha)
					break;

			}
		}
		if (v == Integer.MAX_VALUE) {
			return sbe(state);
		}
		return v;
	}

	public int sbe(BoardState state) {
		// difference of the two stones' positions
		return state.score[0] - state.score[1];
	}

}