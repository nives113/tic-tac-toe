package hr.corvus.tictactoe.strategies.weak;

import static org.junit.Assert.*;
import hr.corvus.tictactoe.ApplicationController;
import hr.corvus.tictactoe.game.NewGame;
import hr.corvus.tictactoe.strategies.Strategy;

import org.junit.Test;

public class WeakStrategyTest {
	
	@Test
	public void ComputersMoveAddInTheMiddle(){
		NewGame game = new NewGame(100, "Nives", "");
		Strategy strategy = new WeakStrategy(100, 'O');
		game.setStrategy(strategy);
		ApplicationController.getGameStatusById(100).playerMove(3, 3, 'X');
		strategy.computersMove();
		assertEquals(ApplicationController.getGameStatusById(100).getGame().get(4).getValue(),'O');
	}
	
	@Test
	public void ComputersMoveAddToFirstAvailableCorner(){
		NewGame game = new NewGame(100, "Nives", "");
		Strategy strategy = new WeakStrategy(100, 'O');
		game.setStrategy(strategy);
		ApplicationController.getGameStatusById(100).playerMove(1, 1, 'X');
		strategy.computersMove();
		ApplicationController.getGameStatusById(100).playerMove(3, 3, 'X');
		strategy.computersMove();
		assertEquals(ApplicationController.getGameStatusById(100).getGame().get(2).getValue(),'O');
	}
	
	@Test
	public void ComputerPreventPlayerWin(){
		NewGame game = new NewGame(100, "Nives", "");
		Strategy strategy = new WeakStrategy(100, 'O');
		game.setStrategy(strategy);
		ApplicationController.getGameStatusById(100).playerMove(1, 1, 'X');
		strategy.computersMove();
		ApplicationController.getGameStatusById(100).playerMove(1, 3, 'X');
		strategy.computersMove();
		assertEquals(ApplicationController.getGameStatusById(100).getGame().get(1).getValue(),'O');
	}
	
	@Test
	public void ComputerPreventPlayerWinDiagonal(){
		NewGame game = new NewGame(100, "Nives", "");
		Strategy strategy = new WeakStrategy(100, 'O');
		game.setStrategy(strategy);
		ApplicationController.getGameStatusById(100).playerMove(2, 2, 'X');
		strategy.computersMove();
		ApplicationController.getGameStatusById(100).playerMove(1, 3, 'X');
		strategy.computersMove();
		assertEquals(ApplicationController.getGameStatusById(100).getGame().get(6).getValue(),'O');
	}
	
}
