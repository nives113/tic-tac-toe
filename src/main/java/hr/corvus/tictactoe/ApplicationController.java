package hr.corvus.tictactoe;

import hr.corvus.tictactoe.game.NewGame;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/game/new")
	public NewGame greeting(
			@RequestParam(value = "first", defaultValue = "") String player1,
			@RequestParam(value = "second", defaultValue = "") String player2) {
		return new NewGame(counter.incrementAndGet());

	}

}
