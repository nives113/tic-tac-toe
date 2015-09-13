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
					result[0] + 1, result[1] + 1, computerChar);
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
		//check if player player can win
		rowColumn = playerHasTwoInRow(cells, computerChar);
		if(rowColumn != null){
			return rowColumn;
		}
		rowColumn = playerHasTwoDiagonal(cells, computerChar);
		if(rowColumn != null){
			return rowColumn;
		}
		// check if can win
		rowColumn = playerHasTwoInRow(cells, opponentChar);
		if(rowColumn != null){
			return rowColumn;
		}
		rowColumn = playerHasTwoDiagonal(cells, opponentChar);
		if(rowColumn != null){
			return rowColumn;
		}
		// add in the middle if available
		if (cells[1][1] == ' ') {
			return new int[] { 1, 1 };
		}
		// add in corners if available
		if (cells[0][0] == ' ') {
			return new int[] { 0, 0 };
		}
		if (cells[0][2] == ' ') {
			return new int[] { 0, 2 };
		}
		if (cells[2][0] == ' ') {
			return new int[] { 2, 0 };
		}
		if (cells[2][2] == ' ') {
			return new int[] { 2, 2 };
		}
		// add in first available cell
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (cells[i][i] == ' ') {
					return new int[] { i, j };
				}
			}
		}
		return rowColumn;
	}

	private int[] playerHasTwoInRow(char[][] cells, char playerChar) {
		for (int i = 0; i < 3; i++) {
			// rows
			if (cells[0][i] == playerChar && cells[1][i] == playerChar
					&& cells[2][i] == ' ') {
				return new int[] { 2, i };
			}
			if (cells[0][i] == playerChar && cells[2][i] == playerChar
					&& cells[1][i] == ' ') {
				return new int[] { 1, i };
			}
			if (cells[1][i] == playerChar && cells[2][i] == playerChar
					&& cells[0][i] == ' ') {
				return new int[] { 0, i };
			}
			// columns
			if (cells[i][0] == playerChar && cells[i][1] == playerChar
					&& cells[i][2] == ' ') {
				return new int[] { i, 2 };
			}
			if (cells[i][0] == playerChar && cells[i][2] == playerChar
					&& cells[i][1] == ' ') {
				return new int[] { i, 1 };
			}
			if (cells[i][1] == playerChar && cells[i][2] == playerChar
					&& cells[i][0] == ' ') {
				return new int[] { i, 0 };
			}
		}
		return null;
	}

	private int[] playerHasTwoDiagonal(char[][] cells, char playerChar) {
		if (cells[0][0] == playerChar && cells[1][1] == playerChar
				&& cells[2][2] == ' ') {
			return new int[] { 2, 2 };
		}
		if (cells[0][0] == playerChar && cells[1][1] == ' '
				&& cells[2][2] == playerChar) {
			return new int[] { 1, 1 };
		}
		if (cells[0][0] == ' ' && cells[1][1] == playerChar
				&& cells[2][2] == playerChar) {
			return new int[] { 0, 0 };
		}
		if (cells[0][2] == playerChar && cells[1][1] == playerChar
				&& cells[2][0] == ' ') {
			return new int[] { 2, 0 };
		}
		if (cells[0][2] == playerChar && cells[1][1] == ' '
				&& cells[2][0] == playerChar) {
			return new int[] { 1, 1 };
		}
		if (cells[0][2] == ' ' && cells[1][1] == playerChar
				&& cells[2][0] == playerChar) {
			return new int[] { 0, 2 };
		}
		return null;
	}

}
