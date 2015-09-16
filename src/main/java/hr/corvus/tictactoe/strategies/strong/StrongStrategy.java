package hr.corvus.tictactoe.strategies.strong;

import hr.corvus.tictactoe.game.Field;
import hr.corvus.tictactoe.game.data.GameData;
import hr.corvus.tictactoe.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

public class StrongStrategy extends Strategy {

	public StrongStrategy(long gameId, char computerChar) {
		super(gameId, computerChar);
	}

	@Override
	public void computersMove() {
		if (checkIfGameOverAndUpdate()) {
			return;
		} else {
			char[][] cells = new char[3][3];
			for (Field field : GameData.getGameStatusById(gameId)
					.getGame()) {
				cells[field.getRow() - 1][field.getColumn() - 1] = field
						.getValue();
			}
			List<Integer[]> availableMoves = getAvailableMoves(cells);
			List<Move> possibleMoves = new ArrayList<Move>();		
			log.info("new Minimax");
			for (Integer[] integers : availableMoves) {
				Move move = new Move(integers[0], integers[1], cells);
				move.updateCell(integers[0], integers[1], computerChar);
				possibleMoves.add(move);				
				move.addScore(calculateScore(5, "opponent", move.getCells()));
				log.info(Integer.toString(move.getRow()) + " " + Integer.toString(move.getColumn()) + ", " + Integer.toString(move.getScore()));
			}			
			Move bestMove = possibleMoves.get(0);
			for (Move move : possibleMoves) {
				if(move.getScore() > bestMove.getScore()){
					bestMove = move;
				}
			}
			GameData.getGameStatusById(gameId).playerMove(bestMove.getRow() + 1, bestMove.getColumn() + 1, computerChar);
			checkIfGameOverAndUpdate();
		}
	}	
	
	//find best score using minimax algorithm
	private int calculateScore(int depth, String player, char[][] cells){			
		if(depth == 0 || boardFull(cells)){
			return 0;
		}
		int bestResult;		
		if(player.equals("computer")){
			bestResult = -1000;
		}
		else{
			bestResult = 1000;
		}	
		
		char opponentChar = 'X';
		if(computerChar == 'X'){
			opponentChar = 'O';
		}
		
		String nextPlayer;
		char thisPlayerChar;
		if(player.equals("computer")){
			nextPlayer = "opponent";
			thisPlayerChar = computerChar;
		}
		else{
			nextPlayer = "computer";
			thisPlayerChar = opponentChar;
		}
		
		int score = 0;		
		if(playerWon(cells, computerChar)){
			return 10;
		}
		else if(playerWon(cells, opponentChar)){
			return -10;
		}
		else{
			List<Integer[]> moves = getAvailableMoves(cells);
			for (Integer[] integers : moves) {
				cells[integers[0]][integers[1]] = thisPlayerChar;				
				score += calculateScore(depth - 1, nextPlayer, cells)*0.1*(depth);
				cells[integers[0]][integers[1]] = ' ';
				if(player.equals("computer") && score > bestResult){
					bestResult = score;
				}
				else if(!player.equals("computer") && score < bestResult){
					bestResult = score;
				}
			}
		}		
		return bestResult;
	}
	
	private List<Integer[]> getAvailableMoves(char[][] cells){
		List<Integer[]> moves = new ArrayList<Integer[]>();
		for (int i=0; i<cells.length; i++){
			for(int j=0; j<cells[i].length; j++){
				if (cells[i][j] == ' '){					
					moves.add(new Integer[]{i,j});
				}
			}
		}
		return moves;
	}
	

}
