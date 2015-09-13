package hr.corvus.tictactoe.strategies.weak;

import hr.corvus.tictactoe.ApplicationController;
import hr.corvus.tictactoe.game.Field;
import hr.corvus.tictactoe.strategies.Strategy;

public class WeakStrategy extends Strategy {

	public WeakStrategy(long gameId, char computerChar) {
		super(gameId, computerChar);
	}

	@Override
	public void computersMove() {
		if (checkIfGameOverAndUpdate()) {
			return;
		} else {
			int[] result = greedyAlgorithm();
			ApplicationController.getGameStatusById(gameId).playerMove(
					result[0] + 1, result[0] + 1, computerChar);
			checkIfGameOverAndUpdate();
		}
	}

	private int[] greedyAlgorithm() {
		char[][] cells = new char[3][3];
		for (Field field : ApplicationController.getGameStatusById(gameId)
				.getGame()) {
			cells[field.getRow() - 1][field.getColumn() - 1] = field.getValue();
		}
		int[] rowColumn = new int[2];
		char opponentChar;
		if (computerChar == 'X') {
			opponentChar = 'O';
		} else {
			opponentChar = 'X';
		}
		// check if opponent has 2 in a row or column
		for (int i = 0; i < 3; i++) {
			// rows
			if (cells[0][i] == opponentChar && cells[1][i] == opponentChar
					&& cells[2][i] == ' ') {
				return new int[] { 2, i };
			}
			if (cells[0][i] == opponentChar && cells[2][i] == opponentChar
					&& cells[1][i] == ' ') {
				return new int[] { 1, i };
			}
			if (cells[1][i] == opponentChar && cells[2][i] == opponentChar
					&& cells[0][i] == ' ') {
				return new int[] { 0, i };
			}
			// columns
			if (cells[i][0] == opponentChar && cells[i][1] == opponentChar
					&& cells[i][2] == ' ') {
				return new int[] { i, 2 };
			}
			if (cells[i][0] == opponentChar && cells[i][2] == opponentChar
					&& cells[i][1] == ' ') {
				return new int[] { i, 1 };
			}
			if (cells[i][1] == opponentChar && cells[i][2] == opponentChar
					&& cells[i][0] == ' ') {
				return new int[] { i, 0 };
			}
		}
		// check diagonal
		if (cells[0][0] == opponentChar && cells[1][1] == opponentChar
				&& cells[2][2] == ' ') {
			return new int[] { 2, 2 };
		}
		if (cells[0][0] == opponentChar && cells[1][1] == ' '
				&& cells[2][2] == opponentChar) {
			return new int[] { 1, 1 };
		}
		if (cells[0][0] == ' ' && cells[1][1] == opponentChar
				&& cells[2][2] == opponentChar) {
			return new int[] { 0, 0 };
		}
		if (cells[0][2] == opponentChar && cells[1][1] == opponentChar
				&& cells[2][0] == ' ') {
			return new int[] { 2, 0 };
		}
		if (cells[0][2] == opponentChar && cells[1][1] == ' '
				&& cells[2][0] == opponentChar) {
			return new int[] { 1, 1 };
		}
		if (cells[0][2] == ' ' && cells[1][1] == opponentChar
				&& cells[2][0] == opponentChar) {
			return new int[] { 0, 2 };
		}
		// check if computer has 2 in a row or column
		for (int i = 0; i < 3; i++) {
			// rows
			if (cells[0][i] == computerChar && cells[1][i] == computerChar
					&& cells[2][i] == ' ') {
				return new int[] { 2, i };
			}
			if (cells[0][i] == computerChar && cells[2][i] == computerChar
					&& cells[1][i] == ' ') {
				return new int[] { 1, i };
			}
			if (cells[1][i] == computerChar && cells[2][i] == computerChar
					&& cells[0][i] == ' ') {
				return new int[] { 0, i };
			}
			// columns
			if (cells[i][0] == computerChar && cells[i][1] == computerChar
					&& cells[i][2] == ' ') {
				return new int[] { i, 2 };
			}
			if (cells[i][0] == computerChar && cells[i][2] == computerChar
					&& cells[i][1] == ' ') {
				return new int[] { i, 1 };
			}
			if (cells[i][1] == computerChar && cells[i][2] == computerChar
					&& cells[i][0] == ' ') {
				return new int[] { i, 0 };
			}
		}
		// check diagonal for win
		if (cells[0][0] == computerChar && cells[1][1] == computerChar
				&& cells[2][2] == ' ') {
			return new int[] { 2, 2 };
		}
		if (cells[0][0] == computerChar && cells[1][1] == ' '
				&& cells[2][2] == computerChar) {
			return new int[] { 1, 1 };
		}
		if (cells[0][0] == ' ' && cells[1][1] == computerChar
				&& cells[2][2] == computerChar) {
			return new int[] { 0, 0 };
		}
		if (cells[0][2] == computerChar && cells[1][1] == computerChar
				&& cells[2][0] == ' ') {
			return new int[] { 2, 0 };
		}
		if (cells[0][2] == computerChar && cells[1][1] == ' '
				&& cells[2][0] == computerChar) {
			return new int[] { 1, 1 };
		}
		if (cells[0][2] == ' ' && cells[1][1] == computerChar
				&& cells[2][0] == computerChar) {
			return new int[] { 0, 2 };
		}
		//add in the middle if available
		if(cells[1][1] == ' '){
			return new int[]{1,1};
		}
		//add in corners if available
		if(cells[0][0] == ' '){
			return new int[]{0,0};
		}
		if(cells[0][2] == ' '){
			return new int[]{0,2};
		}
		if(cells[2][0] == ' '){
			return new int[]{2,0};
		}
		if(cells[2][2] == ' '){
			return new int[]{2,2};
		}
		//add in first available cell
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++){
				if(cells[i][i] == ' '){
					return new int[]{i,j};
				}
			}
			
		}
		return rowColumn;
	}

}
