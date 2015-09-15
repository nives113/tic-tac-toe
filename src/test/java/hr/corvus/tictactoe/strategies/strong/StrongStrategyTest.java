package hr.corvus.tictactoe.strategies.strong;

import static org.junit.Assert.assertEquals;
import hr.corvus.tictactoe.game.NewGame;
import hr.corvus.tictactoe.game.data.GameData;
import hr.corvus.tictactoe.strategies.Strategy;



import org.junit.Test;

public class StrongStrategyTest {
	
	@Test
	public void ComputerPreventPlayerWin(){
		NewGame game = new NewGame(100, "Nives", "");
		Strategy strategy = new StrongStrategy(100, 'O');
		game.setStrategy(strategy);
		GameData.getGameStatusById(100).playerMove(1, 1, 'X');
		strategy.computersMove();
		GameData.getGameStatusById(100).playerMove(3, 1, 'X');
		strategy.computersMove();
		assertEquals(GameData.getGameStatusById(100).getGame().get(3).getValue(),'O');
	}

	
	
	@Test
	public void ComputerPreventPlayerWinDiagonal(){
		NewGame game = new NewGame(100, "Nives", "");
		Strategy strategy = new StrongStrategy(100, 'O');
		game.setStrategy(strategy);
		GameData.getGameStatusById(100).playerMove(2, 2, 'X');
		strategy.computersMove();
		GameData.getGameStatusById(100).playerMove(1, 3, 'X');
		strategy.computersMove();
		assertEquals(GameData.getGameStatusById(100).getGame().get(6).getValue(),'O');
	}

	
}
