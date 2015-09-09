package hr.corvus.tictactoe.game;

import java.util.Collection;

public class GameStatsRepresentation {

	private Collection<PlayerStatistic> stats;
	
	public GameStatsRepresentation(Collection<PlayerStatistic> stat) {
		this.stats = stat;
	}
	
	public Collection<PlayerStatistic> getStat(){
		return stats;
	}
}
