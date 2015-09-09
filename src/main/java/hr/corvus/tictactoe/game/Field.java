package hr.corvus.tictactoe.game;

public class Field {
	
	private int row;
	private int column;
	private char value;
	
	public Field(int row, int column){
		this.row = row;
		this.column = column;
		this.value = ' ';
	}
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return column;
	}
	
	public char getValue(){
		return value;
	}
	
	public void setValue(char value){
		this.value = value;
	}

}
