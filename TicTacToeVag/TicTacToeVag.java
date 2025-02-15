 /*
  Tic-Tac-Toe
   *
  This Java program implements a simple two-player Tic-Tac-Toe game.
  The game consists of the following steps:

  1. The program prompts each player to enter their name.
  2. The game alternates between Player 1 (symbol 'X') and Player 2 (symbol 'O').
  3. Players take turns selecting positions on a 3x3 board.
  4. After each move, the program checks if there's a winner or if the game ends in a draw.
  5. The game announces the winner or indicates if the match is a draw.

  Features:
  - Input validation ensures valid positions and prevents overwriting occupied spots.
  - The board is printed after each turn, showing the current state of play.
  - The game declares a winner if one player aligns three symbols in a row, column, or diagonal.
  - Draw conditions are handled after 9 turns if no winner is found.


  Author: [Vaggelis Theodorakis]
 */

package TicTacToeVag;
import java.util.InputMismatchException;
import java.util.Scanner;

    public class TicTacToeVag {

        static Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {
            char[][] ticTacToeBoard = {{' ', ' ', ' '},
                    {' ', ' ', ' '},
                    {' ', ' ', ' '}}; // Initialize empty game board
            int columnSelected = 0;
            int rowSelected = 0;
            boolean checkWin = false;
            int turnCount = 1;
            char currentPlayerSymbol = 'X'; // First player always starts with 'X'
            String winnerName = "";

            String namePlayer1 = " ";
            String namePlayer2 = " ";

            System.out.println("Let's play Tic-Tac-Toe");

            System.out.println("  T  |  I  |  C  ");
            System.out.println("- - -|- - -|- - -");
            System.out.println("  T  |  A  |  C  ");
            System.out.println("- - -|- - -|- - -");
            System.out.println("  T  |  O  |  E  ");
            System.out.println();

            namePlayer1 = getValidName("Player 1, enter your name");
            System.out.printf("OK %s, you get the X symbol \n", namePlayer1);

            System.out.println();
            namePlayer2 = getValidName("Player 2, enter your name");
            System.out.printf("OK %s, you get the O symbol \n", namePlayer2);

            System.out.println();
            System.out.println("Let's play!");
            System.out.printf("%s, you go first! \n", namePlayer1);

            while (true) {
                do {
                    printBoard(ticTacToeBoard);

                    System.out.println("In which row would you like to place your symbol?");
                    rowSelected = getValidInteger();
                    System.out.println("In which column would you like to place your symbol?");
                    columnSelected = getValidInteger();

                } while (!isPositionFree(ticTacToeBoard, rowSelected, columnSelected));

                ticTacToeBoard[rowSelected - 1][columnSelected - 1] = currentPlayerSymbol;

                // Check for a win condition only after turn 5 (minimum moves for a win)
                if (turnCount > 4) {
                    checkWin = matchIsOver(ticTacToeBoard);
                }

                if (checkWin) {
                    winnerName = currentPlayerSymbol == 'X' ? namePlayer1 : namePlayer2;
                    break; // Exit the loop when a winner is found
                }

                turnCount++;

                if (turnCount > 9) {
                    break;
                }

                currentPlayerSymbol = switchPlayerSymbol(currentPlayerSymbol, namePlayer1, namePlayer2);

            }

            // Announce the game result
            if (checkWin) {
                printBoard(ticTacToeBoard);
                System.out.printf("Congratulations %s, you win in %d turns! Better luck next time %s!\n",
                        winnerName, turnCount, winnerName.equals(namePlayer1) ? namePlayer2 : namePlayer1);
            } else {
                printBoard(ticTacToeBoard);
                System.out.println("It's a draw! Congratulations to both of you");
            }

        }
        // Validates and retrieves player names
        public static String getValidName(String prompt) {
            String playerName;
            while (true) {
                try {
                    System.out.println(prompt);
                    playerName = sc.nextLine().trim();

                    if (playerName.isEmpty()) {
                        System.out.println("Name cannot be blank. Please enter a valid name.");
                        continue;
                    }
                    break;

                } catch (Exception e) {
                    System.out.println("An unexpected error occurred. Please try again.");
                }
            }
            return playerName;
        }

        // Ensures the entered number is between 1 and 3
        public static int getValidInteger() {
            int userInteger = 0;
            while (true) {
                try {
                    userInteger = sc.nextInt();
                    if (userInteger > 3 || userInteger < 1) {
                        System.out.println("Please provide a number between 1 and 3");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number between 1 and 3");
                    sc.next();
                    continue;
                }
                return userInteger;
            }
        }

        // Checks if a position on the board is free
        public static boolean isPositionFree(char[][] board, int row, int col) {
            if (board[row - 1][col - 1] != ' ') {
                System.out.printf("Position { %d , %d } is already taken. Please retry. \n", row, col);
            }
            return board[row - 1][col - 1] == ' ';
        }

        // Prints the current state of the board
        public static void printBoard(char[][] ticTacToeBoard) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.printf(String.valueOf(ticTacToeBoard[i][j]));
                    if (j < 2) {
                        System.out.print("|");
                    }
                }
                System.out.println();
                if (i < 2 ) {
                    System.out.print("------");
                }
                System.out.println();
            }
        }

        // Determines if the match has ended with a winner
        public static boolean matchIsOver(char[][] ticTacToeBoard) {
            int countHor = 0;
            int countVer = 0;
            final int X_TO_ASCII = 88;
            final int O_TO_ASCII = 79;


            for (int i = 0; i < 3; i++) {
                countHor = 0;
                countVer = 0;
                for (int j = 0; j < 3; j++) {
                    countHor += ticTacToeBoard[i][j];
                    countVer += ticTacToeBoard[j][i];
                }
                if (countHor == (3 * X_TO_ASCII) || countHor == (3 * O_TO_ASCII) ||
                        countVer == (3 * X_TO_ASCII) || countVer == (3 * O_TO_ASCII)) {
                    return true;
                }
            }
            int mainDiag = ticTacToeBoard[0][0] + ticTacToeBoard[1][1] + ticTacToeBoard[2][2];
            int antiDiag = ticTacToeBoard[0][2] + ticTacToeBoard[1][1] + ticTacToeBoard[2][0];

            return mainDiag == (3 * X_TO_ASCII) || mainDiag == (3 * O_TO_ASCII) ||
                    antiDiag == (3 * X_TO_ASCII) || antiDiag == (3 * O_TO_ASCII);
        }

        // Switches the current player's symbol for the next turn
        public static char switchPlayerSymbol(char currentPlayerSymbol, String namePlayer1, String namePlayer2) {
            if (currentPlayerSymbol == 'X') {
                System.out.printf("%s, it's your turn \n", namePlayer2);
                return 'O';
            } else {
                System.out.printf("%s, it's your turn \n", namePlayer1);
                return 'X';
            }
        }


    }

