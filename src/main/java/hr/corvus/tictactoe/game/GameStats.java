package hr.corvus.tictactoe.game;


import java.util.Collection;
import java.util.HashMap;


public class GameStats {
	
	private static HashMap<String, PlayerStatistic> stats = new HashMap<String, PlayerStatistic>();
	
	public static void addPlayer(String name){
		stats.put(name, new PlayerStatistic(name));
	}
	
	public static Collection<PlayerStatistic> getStats(){
		return stats.values();
	}
	
	public static PlayerStatistic getPlayerStatistic(String name){
		return stats.get(name);
	}
	
}
