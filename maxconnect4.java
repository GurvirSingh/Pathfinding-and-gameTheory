import java.io.*;
import java.util.Scanner;
/**
 * 
 * @author James Spargo
 * This class controls the game play for the Max Connect-Four game. 
 * To compile the program, use the following command from the maxConnectFour directory:
 * javac *.java
 *
 * the usage to run the program is as follows:
 * ( again, from the maxConnectFour directory )
 *
 *  -- for interactive mode:
 * java MaxConnectFour interactive [ input_file ] [ computer-next / human-next ] [ search depth]
 *
 * -- for one move mode
 * java maxConnectFour.MaxConnectFour one-move [ input_file ] [ output_file ] [ search depth]
 * 
 * description of arguments: 
 *  [ input_file ]
 *  -- the path and filename of the input file for the game
 *  
 *  [ computer-next / human-next ]
 *  -- the entity to make the next move. either computer or human. can be abbreviated to either C or H. This is only used in interactive mode
 *  
 *  [ output_file ]
 *  -- the path and filename of the output file for the game.  this is only used in one-move mode
 *  
 *  [ search depth ]
 *  -- the depth of the minimax search algorithm
 * 
 *   
 */

public class maxconnect4
{
  public static void main(String[] args) 
  {
    // check for the correct number of arguments
    if( args.length != 4 ) 
    {
      System.out.println("Four command-line arguments are needed:\n"
                         + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                         + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

      exit_function( 0 );
     }
		
    // parse the input arguments
    String game_mode = args[0].toString();				// the game mode
    String input = args[1].toString();					// the input game file
    int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search
    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    	
    // create and initialize the game board
    GameBoard currentGame = new GameBoard( input );
    
    // create the Ai Player
    AiPlayer calculon = new AiPlayer();
		
    //  variables to keep up with the game
    int playColumn = 99;				//  the players choice of column to play
    boolean playMade = false;			//  set to true once a play has been made

    if( game_mode.equalsIgnoreCase( "interactive" ) ) 
    {
        System.out.println("Game Initialized...");

        Boolean computer = false;
        if(args[2].equals("computer-next")) {
            computer = true;
        }
        else if(args[2].equals("human-next")) {
            computer = false;
        }
        else {
            System.out.println("Four command-line arguments are needed:[Cases should be matched]\n"
            + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
            + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

            exit_function( 0 );
        }
        try {
        while(currentGame.getPieceCount() != 42) { 
            if(computer) {
                System.out.println("Computer makes a move");
                
                //Calls PlayPiece in Gameboard and arguments are best play provied by AiPlayer
                // PlayPiece just places the best solution in the game board
                // findBestPlay searches the MinMax tree in AiPlayer and returns the column number

                System.out.println("Column played by Computer: " + calculon.findBestPlay(currentGame, depthLevel));

                if(!currentGame.isValidPlay(calculon.findBestPlay(currentGame, depthLevel))) {
                    for(int i = 0;i<6;i++){
                            if(currentGame.isValidPlay(i)){
                                currentGame.playPiece(i);
                                break;
                            }
                            else {
                                continue;
                            }
                    } 
                } else {
                    currentGame.playPiece(calculon.findBestPlay(currentGame, depthLevel));
                }

                // Displays the current Game Board
                System.out.println("The Current Game Board is: ");
                currentGame.printGameBoard();

                // Displays the Scores for both players
                int p1Score = currentGame.getScore(1);
                int p2Score = currentGame.getScore(2);
                System.out.println("Player 1 Score: " + p1Score);
                System.out.println("Player 2 Score: " + p2Score);

                computer = false;
            }
            else {
                // Checks who's turn is it and displays
                int turn = currentGame.getCurrentTurn();
                if(turn == 1) {
                    System.out.print("\nPlayer 1's Turn, ");
                }
                else if(turn == 2){
                    System.out.print("\nPlayer 2's Turn, ");
                }


                System.out.print("Your Turn to make a move: ");

                Integer userInput = myObj.nextInt();  // Read user input

                // Check if move is valid or not 
                while(!currentGame.isValidPlay(userInput - 1)) {
                    System.out.println("Invalid Move!, Please try again");
                    userInput = myObj.nextInt();
                }
                // The Human Player Makes a move
                currentGame.playPiece(userInput - 1);

                 //Displays the current game board 
                System.out.println("The Current Game Board is: ");
                currentGame.printGameBoard();

                // Displays the Scores for both players
                int p1Score = currentGame.getScore(1);
                int p2Score = currentGame.getScore(2);
                System.out.println("Player 1 Score: " + p1Score);
                System.out.println("Player 2 Score: " + p2Score);

                // Set computer to true to alternate the turns played
                computer = true;
            }
            
        }
    }
    catch (Exception e) {
        System.out.println("Some Error Occured! Please Try Again");
        e.printStackTrace();
    }

    System.out.println("Game Over!");
    // Displays the Scores for both players
    int p1Score = currentGame.getScore(1);
    int p2Score = currentGame.getScore(2);
    if(p1Score > p2Score) {
        System.out.println("Player 1 wins!");
    }
    else if(p2Score > p1Score) {
        System.out.println("Player 2 wins!");
    }
    else {
        System.out.println("Game Tied");
    }
    // used for testing purposes
	// System.out.println("interactive mode is currently not implemented\n");
	return;
    }
			
    else if( !game_mode.equalsIgnoreCase( "one-move" ) ) 
    {
      System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
      return;
    }

    /////////////   one-move mode ///////////
    // get the output file name
    String output = args[2].toString();				// the output game file
    
    System.out.print("\nMaxConnect-4 game\n");
    System.out.print("game state before move:\n");
    
    //print the current game board
    currentGame.printGameBoard();
    // print the current scores
    System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
			", Player2 = " + currentGame.getScore( 2 ) + "\n " );
    
    // ****************** this chunk of code makes the computer play
    if( currentGame.getPieceCount() < 42 ) 
    {
        int current_player = currentGame.getCurrentTurn();
	// AI play min-max play
	playColumn = calculon.findBestPlay( currentGame,depthLevel );
	
	// play the piece
	currentGame.playPiece( playColumn );
        	
        // display the current game board
        System.out.println("move " + currentGame.getPieceCount() 
                           + ": Player " + current_player
                           + ", column " + playColumn);
        System.out.print("game state after move:\n");
        currentGame.printGameBoard();
    
        // print the current scores
        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                            ", Player2 = " + currentGame.getScore( 2 ) + "\n " );
        
        currentGame.printGameBoardToFile( output );
    } 
    else 
    {
	System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
    }
	
    //************************** end computer play
			
    
    return;
    
} // end of main()
	
  /**
   * This method is used when to exit the program prematurly.
   * @param value an integer that is returned to the system when the program exits.
   */
  private static void exit_function( int value )
  {
      System.out.println("exiting from MaxConnectFour.java!\n\n");
      System.exit( value );
  }
} // end of class connectFour