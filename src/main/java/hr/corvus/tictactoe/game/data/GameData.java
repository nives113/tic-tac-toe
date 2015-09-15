package hr.corvus.tictactoe.game.data;

import hr.corvus.tictactoe.game.GameStatus;
import hr.corvus.tictactoe.game.NewGame;

import java.util.HashMap;

public class GameData {

	private static HashMap<Long, NewGame> games = new HashMap<Long, NewGame>();
	private static HashMap<Long, GameStatus> gamesStatus = new HashMap<Long, GameStatus>();

	public static GameStatus getGameStatusById(long id) {
		return gamesStatus.get(id);
	}

	public static void addGameStatus(long id, GameStatus game) {
		gamesStatus.put(id, game);
	}

	public static NewGame getGame(long id) {
		return games.get(id);
	}

	public static void addGame(long id, NewGame game) {
		games.put(id, game);
	}

}
