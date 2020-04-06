import java.util.*;

/**
 * This is the AiPlayer class.  It simulates a minimax player for the max
 * connect four game.
 * The constructor essentially does nothing. 
 * 
 * @author james spargo
 *
 */

public class AiPlayer 
{
    /**
     * The constructor essentially does nothing except instantiate an
     * AiPlayer object.
     *
     */
    public AiPlayer() 
    {
	// nothing to do here
    }



    /**
     * This method plays a piece randomly on the board
     * @param currentGame The GameBoard object that is currently being used to
     * play the game.
     * @return an integer indicating which column the AiPlayer would like
     * to play in.
     */


    public int findBestPlay( GameBoard currentGame, int depth ) 
    {
    // Provided code for random play by Ai Player
	// start random play code
	// Random randy = new Random();
	// int playChoice = 99;
	
	// playChoice = randy.nextInt( 7 );
	
	// while( !currentGame.isValidPlay( playChoice ) )
	//     playChoice = randy.nextInt( 7 );
	
	// // end random play code
 
    // return playChoice;

    // Added
    int playChoice = 99;
    
    // Check if max or min player
    if (currentGame.getCurrentTurn() == 1) {

        // Define values for v,a and b
        int v = Integer.MAX_VALUE;
        int a = Integer.MIN_VALUE;
        int b = Integer.MAX_VALUE;
        int d = depth;

        for (int i = 0; i < 7; i ++) {
            if (currentGame.isValidPlay(i)) {

                // for(int i1 = 0;i<6;i++){
                //     for(int j1 = 0;j1<7;j1++) {
                //         System.out.println([i][j])
                //     }
                currentGame.getGameBoard();

                // define new node 
                GameBoard G = new GameBoard(currentGame.getGameBoard());
                // Play the first valid piece
                G.playPiece(i);

                // Returns a utility value
                int max_value = Max_value(G, d, a , b);

                if (v > max_value) {
                    playChoice = i;
                    v = max_value;
                }
            }
        }
    } else {
        // for min
        int v2 = Integer.MIN_VALUE;
        int a2 = Integer.MIN_VALUE;
        int b2 = Integer.MAX_VALUE;
        int d = depth;
        
        for (int i = 0; i < 7; i++) {
            if (currentGame.isValidPlay(i)) {

                currentGame.getGameBoard();

                // define a new node
                GameBoard G = new GameBoard(currentGame.getGameBoard());
                // play the first valid piece
                G.playPiece(i);

                int min_value = Min_value(G, d, a2, b2);

                if (v2 < min_value) {
                    playChoice = i;
                    v2 = min_value;
                }
            }
        }
    }

    // return a position
    return playChoice;
}

public int Max_value(GameBoard currentGame, int depth, int a, int b) {

    if (currentGame.getPieceCount() < 42 && depth > 0) {

        int v = Integer.MIN_VALUE;

        for (int i = 0; i < 7; i ++) {
            if (currentGame.isValidPlay(i)) {

                // Create a new node
                GameBoard G = new GameBoard(currentGame.getGameBoard());
                G.playPiece(i);

                int value = Min_value(G, depth - 1, a, b);

                if (v < value) {
                    v = value;
                }
                // if v>= b return b
                if (v >= b) {
                    return v;
                }
                // if a is greather in max of a,v return a 
                if (a < v) {
                    a = v;
                }
            }
        }
        return v;
    } else {
        // terminal utility
        return currentGame.getScore(2) - currentGame.getScore(1);
    }
}

public int Min_value(GameBoard currentGame, int depth, int a, int b) {

    if (currentGame.getPieceCount() < 42 && depth > 0) {

        int v = Integer.MAX_VALUE;

        for (int i = 0; i < 7; i ++) {
            if (currentGame.isValidPlay(i)) {

                GameBoard G = new GameBoard(currentGame.getGameBoard());
                G.playPiece(i);

                int value = Max_value(G, depth - 1, a, b);


                if (v > value) {
                    v = value;  			
                }
                // if v<=alpha return v
                if (v <= a) {
                    return v;
                }
                // return v if v is minimum in b,v
                if (b > v) {
                    b = v;
                }
            }
        }
        return v;
    } else {
        // terminal utility
        return currentGame.getScore(2) + - currentGame.getScore(1);
    }
}
    // End of Added 
    }