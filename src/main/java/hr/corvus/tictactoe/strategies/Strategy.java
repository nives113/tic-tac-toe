package hr.corvus.tictactoe.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.corvus.tictactoe.ApplicationController;
import hr.corvus.tictactoe.game.Field;
import hr.corvus.tictactoe.game.GameStats;
import hr.corvus.tictactoe.game.GameStatus;

public abstract class Strategy {

	protected long gameId;
	protected char computerChar;
	
	public final Logger log = LoggerFactory.getLogger(this.getClass());

	public abstract void computersMove();

	public Strategy(long gameId, char computerChar) {
		this.gameId = gameId;
		ApplicationController.addGameStatus(gameId, new GameStatus(gameId));
		this.computerChar = computerChar;
	}

	public GameStatus getGame() {
		return ApplicationController.getGameStatusById(gameId);
	}

	protected boolean playerWon(char[][] cells, char playerChar) {		
		if (cells[0][0] == playerChar && cells[1][1] == playerChar && cells[2][2] == playerChar)
			return true;
		if (cells[0][2] == playerChar && cells[1][1] == playerChar && cells[2][0] == playerChar)
			return true;
		for (int i = 0; i < 3; ++i) {
			if (((cells[i][0] == cells[i][1] && cells[i][0] == cells[i][2] && cells[i][0] == playerChar) || 
					(cells[0][i] == cells[1][i]	&& cells[0][i] == cells[2][i] && cells[0][i] == playerChar))) {
				return true;
			}
		}
		return false;
	}

	
	protected boolean boardFull(char[][] cells) {
		for (char[] cs : cells) {
			for (char c : cs) {
				if(c == ' '){
					return false;
				}
			}
		}
		return true;
	}

	protected boolean checkIfGameOverAndUpdate() {
		char opponentChar;
		char[][] cells = new char[3][3];
		for (Field field : ApplicationController.getGameStatusById(gameId).getGame()) {
			cells[field.getRow() - 1][field.getColumn() - 1] = field.getValue();
		}
		if (computerChar == 'X')
		{
			opponentChar = 'O';
		}
		else{
			opponentChar = 'X';
		}
		if (boardFull(cells) || playerWon(cells, computerChar) || playerWon(cells ,opponentChar)) {
			ApplicationController.getGameStatusById(gameId).setStatusToFinished();
			if(playerWon(cells, computerChar)){				
				//computer won, update winner and add loss to player statistic
				ApplicationController.getGameStatusById(gameId).setWinner("computer");
				GameStats.getPlayerStatistic(ApplicationController.getGame(gameId).getPlayer()).addLoss();
			}
			else if(playerWon(cells, opponentChar)){				
				//player won, update player statistic and GameStatus winner field
				ApplicationController.getGameStatusById(gameId).setWinner(ApplicationController.getGame(gameId).getPlayer());
				GameStats.getPlayerStatistic(ApplicationController.getGame(gameId).getPlayer()).addWin();
			}
			else{
				//game ended without winner, add draw to player statistic
				GameStats.getPlayerStatistic(ApplicationController.getGame(gameId).getPlayer()).addDraw();
			}
			return true;
		}
		return false;
	}
}
