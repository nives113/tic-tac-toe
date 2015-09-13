package hr.corvus.tictactoe.game;

import hr.corvus.tictactoe.strategies.Strategy;
import hr.corvus.tictactoe.strategies.strong.StrongStrategy;
import hr.corvus.tictactoe.strategies.weak.WeakStrategy;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class NewGame {

	final long id;
	@JsonIgnore
	final Strategy strategy;
	@JsonIgnore
	final String player;
	@JsonIgnore
	final char playerChar;	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public NewGame(long id, String player1, String player2){
		this.id = id;
		if(player1.isEmpty() && !player2.isEmpty()){
			this.player = player2;
			this.playerChar = 'O';
		}
		else if(!player1.isEmpty() && player2.isEmpty()){
			this.player = player1;
			this.playerChar = 'X';
		}
		else if(!player1.isEmpty() && player2.equals("computer")){
			this.player = player1;
			this.playerChar = 'X';
		}
		else if(player1.equals("computer") && !player2.isEmpty()){
			this.player = player2;
			this.playerChar = 'O';
		}
		else{
			System.out.println("test");
			throw new IllegalArgumentException();
		}
		if (GameStats.getPlayerStatistic(player) == null){
			GameStats.addPlayer(player);
		}
		this.strategy = chooseStrategy(player);		
		if(playerChar == 'O'){
			strategy.computersMove();
		}
	}

	public long getId() {
		return id;
	}
	
	public char getPlayerChar(){
		return playerChar;
	}
	
	public String getPlayer(){
		return player;
	}
	
	public Strategy getStrategy(){
		return strategy;
	}
	
	public Strategy chooseStrategy(String player){
		Strategy strategy;
		char computerChar;
		if(this.playerChar == 'X'){
			computerChar = 'O';
		}
		else{
			computerChar = 'X';
		}
		PlayerStatistic playerStatistic = GameStats.getPlayerStatistic(player);
		double winningPercent = ((double) playerStatistic.getWins()) / (playerStatistic.getLosses() + playerStatistic.getWins() + playerStatistic.getDraws());
		if(winningPercent <= 0.30){
			 log.info("Weak strategy");
			 strategy = new WeakStrategy(this.id, computerChar);
		}
		else if(winningPercent >= 0.90){
			log.info("Strong strategy");
			strategy = new StrongStrategy(this.id, computerChar);;
		}
		else{
			Random randomGenerator = new Random();
			if(randomGenerator.nextBoolean() == true){
				log.info("Weak strategy");
				strategy = new WeakStrategy(this.id, computerChar);;
			}
			else{
				log.info("Strong strategy");
				strategy = new StrongStrategy(this.id, computerChar);;
			}
		}
		return strategy;
	}
	
	
}
