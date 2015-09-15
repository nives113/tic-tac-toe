package hr.corvus.tictactoe;

import hr.corvus.tictactoe.game.GameStats;
import hr.corvus.tictactoe.game.GameStatsRepresentation;
import hr.corvus.tictactoe.game.GameStatus;
import hr.corvus.tictactoe.game.NewGame;
import hr.corvus.tictactoe.game.data.GameData;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

	
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.GET,value = "/game/new")
    public ResponseEntity<NewGame> startNewGame(@RequestParam(value="first", defaultValue="") String player1,
    		@RequestParam(value="second", defaultValue="") String player2) {
    	try{
    		long id = counter.incrementAndGet();
    	    GameData.addGame(id, new NewGame(id, player1, player2));
    	    return new ResponseEntity<NewGame>(GameData.getGame(id), HttpStatus.OK);
    	}
    	catch (Exception e){
    		return new ResponseEntity<NewGame>(HttpStatus.BAD_REQUEST);    		
    	}    
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/game/status")
    public ResponseEntity<GameStatus> getStatus(@RequestParam(value="gameId")  long id) {
       if(GameData.getGameStatusById(id) != null){
    	   return new ResponseEntity<GameStatus>(GameData.getGameStatusById(id), HttpStatus.OK);  
       }
       else{
    	   return new ResponseEntity<GameStatus>(HttpStatus.BAD_REQUEST);  
       }
    }
    
    
    @RequestMapping(method = RequestMethod.GET,value = "/game/stats")
    public GameStatsRepresentation getStats() {
        return new GameStatsRepresentation(GameStats.getStats());
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/game/play")
    public ResponseEntity<GameStatus> playerMove(@RequestParam(value="gameId")  long id,
    		@RequestParam(value="row")  int row,
    		@RequestParam(value="column")  int column) {
       if(GameData.getGameStatusById(id).playerMove(row, column, GameData.getGame(id).getPlayerChar())){
    	   //add computer move 
    	   GameData.getGame(id).getStrategy().computersMove();
    	   return new ResponseEntity<GameStatus>(GameData.getGameStatusById(id), HttpStatus.OK);  
       }
       else{
    	   return new ResponseEntity<GameStatus>(HttpStatus.PRECONDITION_FAILED);  
       }
    }
    
   
}
