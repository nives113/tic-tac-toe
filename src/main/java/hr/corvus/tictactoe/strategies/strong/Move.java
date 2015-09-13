package hr.corvus.tictactoe.strategies.strong;

public class Move {

	private int score;
	private final int row;
	private final int column;
	private char[][] cells = new char[3][3];
	
	
	public Move(int row, int column, char[][] cells){
		this.row = row;
		this.column = column;
		this.score = 0;
		for (int i=0; i<cells.length; i++) {
			for (int j=0; j<cells.length; j++) {
				this.cells[i][j] = cells[i][j];
			}
		}
	}
	
	public int getScore(){
		return this.score;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getColumn(){
		return this.column;
	}
	
	public void addScore(int score){
		this.score += score;
	}
	
	public boolean updateCell(int row, int column, char value){
		if(cells[row][column] == ' '){
			cells[row][column] = value;
			return true;
		}
		return false;
	}
	
	public char[][] getCells(){
		return this.cells;
	}
	
}
