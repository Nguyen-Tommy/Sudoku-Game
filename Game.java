// Game class
// Create a Sudoku game object

package Sudoku; 

import java.util.Scanner;

import Sudoku.Board;

public class Game {
	
	private Board board;
	private Board newBoard;
	private boolean generated;
	private boolean play;
	private boolean solved;
	
	// Game constructor
	public Game() {
		board = new Board();
		generated = false;
		play = true;
	}
	
	// Start Sudoku game
	public void startGame() {
		System.out.println("Sudoku Game");
		
		// Play game while user continues and didn't win
		while (!winCondition() && play) 
			mainMenu();
		
		// Win game comment
		if (winCondition() && !solved) {
			board.print();
			System.out.println("You have completed the Sudoku board. You Win!");
		}
	}
	
	// Prompts the user options 
	public void mainMenu() {
		Scanner s = new Scanner(System.in);
		int row, col, num;
		
		// Check if board is generated
		if (generated) 
			board.print();
	
		// Main menu
		System.out.println("1: New Game\n"
					   + "2: Write\n"
					   + "3: Erase\n"
					   + "4: Solve\n"
					   + "5: Exit\n"
					   + "Enter numbered choice: ");
		
		// Retrieve user input
		int input = s.nextInt();
		
		// Retrieve user input and create new board
		if (input == 1) {
			System.out.println("1: Easy\n"
							 + "2: Medium\n"
							 + "3: Hard\n"
							 + "Enter level of difficulty: ");
			int level = s.nextInt(); 
			
			// Check if input is valid
			if (level > 0 && level < 4) {
				boolean solved = false;
				
				// Find a valid board
				while(!solved) {
					
					// Generate board based on level difficulty
					if (level == 1)
						board.generateBoard(40);
					if (level == 2)
						board.generateBoard(30);
					if (level == 3)
						board.generateBoard(20);
					
					// Save board and check if board has solution
					newBoard = new Board(board);
					solved = solve();
					board = new Board(newBoard);
				}
				generated = true;
			} else
				System.out.println("Invalid input.");
		
		// Retrieve user inputs and update board
		} else if (input == 2) {
			if (generated) {
				System.out.println("Enter row: ");
				row = s.nextInt();
				System.out.println("Enter column: ");
				col = s.nextInt();
				System.out.println("Enter number: ");
				num = s.nextInt();		
				write(row - 1, col - 1, num);
			} else
				System.out.println("Create a new game first.");
		
		// Retrieve user inputs and update board
		} else if (input == 3) {
			if (generated) {
				System.out.println("Enter row: ");
				row = s.nextInt();
				System.out.println("Enter column: ");
				col = s.nextInt();
				erase(row - 1, col - 1);
			} else
				System.out.println("Create a new game first.");
		
		// Solve board
		} else if (input == 4) {
			if (generated) {
				solve();
				solved = true;
				board.print();
				System.out.println("Board solution.");
			}
			else
				System.out.println("Create a new game first.");
		
		// Exit, end loop
		} else if (input == 5)
			play = false;
		else 
			System.out.println("Invalid input.");
	}
	
	// Update board with user's number
	public void write(int row, int col, int num) {
		
		// Check if input is valid
		if (row < 0 || row > 8 || col < 0 || col > 8 || num < 1 || num > 9 || newBoard.get(row, col) != 0)
			System.out.println("Invalid input.");
		else {
			
			// Check if number is valid
			if (board.isValid(row, col, num))
				board.set(row, col, num);
			else
				System.out.println("Invalid number.");
		}
	}
	
	// Update board with 0
	public void erase(int row, int col) {
		
		// Check if input is valid
		if (row < 0 || row > 8 || col < 0 || col > 8 || newBoard.get(row, col) != 0)
			System.out.println("Invalid input.");
		else {
			
			// Check if slot is 0
			if (board.get(row, col) == 0)
				System.out.println("Already empty.");
			else
				board.set(row, col, 0);
		}
	}
	
	// Solve board with backtracking algorithm 
	public boolean solve() {
		boolean backtrack = false;
		int count = 0;
		
		// Iterate through whole board array
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				
				// Check if slot can be played
				if (newBoard.get(i, j) == 0) {
					count = board.get(i, j);
					count++;
					
					// Count every possible number
					while (count <= 9) {
						
						// Check if number is valid and set
						if (board.isValid(i, j, count)) {
							board.set(i, j, count);
							break;
						}
						count++;
					}
					
					// Check if there's no possible numbers, start backtracking
					if (count == 10) {
						board.set(i, j, 0);
						backtrack = true;
					} else
						backtrack = false;
				}
				
				// Backtrack by 2, will increment back to 1
				if (backtrack) {
					if (j == 0) {
						i--;
						
						// Check for unsolvable boards
						if (i == -1)
							return false;
						j = 7;
					} else 
						j -= 2;
				}
			}
		}
		return true;
	}
	
	public boolean winCondition() {
		
		// Iterate through board 
		for (int i = 0; i < 9; i++) 
			for (int j = 0; j < 9; j++) 
				
				// Check if any slots are 0
				if (board.get(i, j) == 0)
					return false;
		return true;
	}
}
