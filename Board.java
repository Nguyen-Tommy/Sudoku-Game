// Board class
// Create a Sudoku board object

package Sudoku;

import java.util.Arrays;
import java.util.Random;

public class Board {

	private int[][] board;
	
	// Board constructor
	public Board() {
		board = new int[9][9];
	}
	
	// Board copy constructor
	public Board(Board source) {
		board = new int[9][9];
		
		// Iterate through board array and copy each number
		for (int i = 0; i < 9; i++) 
			for (int j = 0; j < 9; j++) 
				board[i][j] = source.board[i][j];
	}
	
	// Return number at coordinate 
	public int get(int row, int col) {
		return board[row][col];
	}
	
	// Update coordinate with given number
	public void set(int row, int col, int num) {
		board[row][col] = num;
	}
	
	// Check if given coordinate and number is valid
	public boolean isValid(int row, int col, int num) {
		
		// Check if the row contains the number
		for (int i = 0; i < 9; i++) 
			if (board[row][i] == num && i != col)
				return false;
		
		// Check if the column contains the number
		for (int i = 0; i < 9; i++) 
			if (board[i][col] == num && i != row)
				return false;
		
		int x = row / 3;
		int y = col / 3;
		
		// Check if determined box contains the number
		for (int i = x * 3; i < x * 3 + 3; i++) 
			for (int j = y * 3; j < y * 3 + 3; j++) 
				if (board[i][j] == num && i != row && j != col)
					return false;
		
		return true;
	}

	// Generate random board with certain amount of numbers
	public void generateBoard(int count) {
		Random rand = new Random();
		int x;
		int y;
		int num;
		
		// Fill board with zeros
		for (int[] row : board)
			Arrays.fill(row, 0);
		
		// Set an amount of random numbers based on level
		while (count != 0) {
			x = rand.nextInt(9);
			y = rand.nextInt(9);
			num = rand.nextInt(9) + 1;
			
			// Check if slot is empty and valid
			if (board[x][y] == 0 && isValid(x,y,num)) {
				board[x][y] = num;
				count--;
			}
		}
	}
	
	// Print board in text
	public void print() {
		System.out.println("    1 2 3   4 5 6   7 8 9");
		
		// Iterate through and print board array
		for (int i = 0; i < board.length; i++) {
			if (i % 3 == 0)
				System.out.println("  -------------------------");
			
			System.out.print(i + 1 + " ");
			
			for (int j = 0; j < board.length; j++) {
				if (j % 3 == 0)
					System.out.print("| ");
				
				System.out.print(board[i][j] + " ");
			}
			System.out.print("|");
			System.out.println();	
		}
		System.out.println("  -------------------------");
	}
}