package hr.corvus.tictactoe.strategies.strong;

import java.util.ArrayList;
import java.util.List;


import hr.corvus.tictactoe.ApplicationController;
import hr.corvus.tictactoe.game.Field;
import hr.corvus.tictactoe.strategies.Strategy;

public class StrongStrategy extends Strategy {

	public StrongStrategy(long gameId, char computerChar) {
		super(gameId, computerChar);
	}

	@Override
	public void computersMove() {
		if (checkIfGameOverAndUpdate()) {
			return;
		} else {
			// computer move
			char[][] cells = new char[3][3];
			for (Field field : ApplicationController.getGameStatusById(gameId)
					.getGame()) {
				cells[field.getRow() - 1][field.getColumn() - 1] = field
						.getValue();
			}
			// minimax	
			List<Integer[]> availableMoves = getAvailableMoves(cells);
			List<Move> possibleMoves = new ArrayList<Move>();			
			for (Integer[] integers : availableMoves) {
				Move move = new Move(integers[0], integers[1], cells);
				//log.info("move: " + Integer.toString(integers[0]) + " " + Integer.toString(integers[0]));
				move.updateCell(integers[0], integers[1], computerChar);
				possibleMoves.add(move);				
				move.addScore(calculateScore(3, "opponent", move.getCells()));
				log.info("move " + Integer.toString(move.getRow()) + " " + Integer.toString(move.getColumn()) + " " + Integer.toString(move.getScore()));
			}			
			
			//find best score
			Move bestMove = possibleMoves.get(0);
			for (Move move : possibleMoves) {
				log.info("best move " + Integer.toString(move.getRow()) + " " + Integer.toString(move.getColumn()) + " " + Integer.toString(move.getScore()));
				if(move.getScore() > bestMove.getScore()){
					bestMove = move;
				}
			}
			log.info("best move: " + Integer.toString(bestMove.getRow()) + " " + Integer.toString(bestMove.getColumn()) + " " + Integer.toString(bestMove.getScore()));
			//make a move
			ApplicationController.getGameStatusById(gameId).playerMove(bestMove.getRow() + 1, bestMove.getColumn() + 1, computerChar);
			checkIfGameOverAndUpdate();
		}
	}	
	
	private int calculateScore(int depth, String player, char[][] cells){			
		if(depth == 0 || boardFull(cells)){
			return 0;
		}
		log.info("depth " + Integer.toString(depth));
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
		String test="";
		for (int i=0; i<cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				test += cells[i][j] + ", ";
			}
			test += "\n";
		}
		log.info(test);
		if(playerWon(cells, computerChar)){
			log.info("computer won");
			test="";
			for (int i=0; i<cells.length; i++) {
				for (int j = 0; j < cells[i].length; j++) {
					test += cells[i][j] + ", ";
				}
				test += "\n";
			}
			log.info(test);
			return 10;
		}
		else if(playerWon(cells, opponentChar)){
			log.info("player won");
			test="";
			for (int i=0; i<cells.length; i++) {
				for (int j = 0; j < cells[i].length; j++) {
					test += cells[i][j] + ", ";
				}
				test += "\n";
			}
			log.info(test);
			return -10;
		}
		else{
			List<Integer[]> moves = getAvailableMoves(cells);
			for (Integer[] integers : moves) {
				cells[integers[0]][integers[1]] = thisPlayerChar;				
				score += calculateScore(depth - 1, nextPlayer, cells);
				cells[integers[0]][integers[1]] = ' ';
				if(player.equals("computer") && score > bestResult){
					bestResult = score;
				}
				else if(!player.equals("computer") && score < bestResult){
					bestResult = score;
				}
			}
		}		
		//log.info(player + " " + Integer.toString(bestResult));
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
