package hr.corvus.tictactoe.game;

import java.util.Collection;


public class GameStatsRepresentation {

	//private Collection<PlayerStatistic> stats;
	private Object[] stats;
	
	public GameStatsRepresentation(Collection<PlayerStatistic> stats) {
		this.stats = stats.toArray();
	}
	
	public Object[] getStat(){
		return stats;
	}
}
