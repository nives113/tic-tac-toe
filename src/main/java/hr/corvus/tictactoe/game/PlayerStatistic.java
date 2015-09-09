package hr.corvus.tictactoe.game;

public class PlayerStatistic {
	private final String name;
	private int wins;
	private int losses;
	private int draws;
	
	
	public PlayerStatistic(String name){
		this.name = name;
		this.wins=0;
		this.losses=0;
		this.draws=0;		
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getWins(){
		return this.wins;
	}
	
	public void addWin(){
		this.wins++;
	}
	
	public int getLosses(){
		return this.losses;
	}
	
	public void addLoss(){
		this.losses++;
	}
	
	public int getDraws(){
		return this.draws;
	}
	
	public void addDraw(){
		this.draws++;
	}

}
