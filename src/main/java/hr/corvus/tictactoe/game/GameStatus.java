package hr.corvus.tictactoe.game;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class GameStatus {
	
	private final long gameId;
	private String status;	
	@JsonInclude(Include.NON_NULL)
	private String winner = null;
	private List<Field> game = new ArrayList<Field>();
	
	public GameStatus(long gameId){
		this.gameId = gameId;
		for(int i=1; i<4; i++){
			for (int j=1; j<4; j++){
				game.add(new Field(i, j));
			}
		}
		this.status = "inProgress";
	}
	
	public long getGameId(){
		return gameId;
	}
	
	public List<Field> getGame(){
		return game;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setWinner(String winner){
		this.winner = winner;
	}
	
	public String getWinner(){
		return this.winner;
	}
	
	public void setStatusToFinished(){
		this.status = "finished";
	}
	
	public boolean playerMove(int row, int column, char value){
		for (Field field : game) {
			if(field.getRow() == row && field.getColumn() == column && field.getValue() == ' '){
				field.setValue(value);
				return true;
			}
		}
		return false;
	}
}
