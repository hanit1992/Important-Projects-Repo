import java.util.Scanner;

/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of rounds.
 * It also keeps track of the number of victories of each player.
 */

public class Competition {

    //constants of the class.
    public static final int negativeNum = -1;

    //class data members.
	Player player1;
    int player1Score=0;
    Player player2;
    int player2Score=0;
    boolean displayMessage;
    private Board competitonBoard;

    /**
     * Receives two Player objects, representing the two competing opponents,
     * and a flag determining whether messages should be displayed.
     * @param player1 - The Player objects representing the first player.
     * @param player2 - The Player objects representing the second player.
     * @param displayMessage - a flag indicating whether game play messages should be printed to the console.
     */
    public Competition(Player player1,Player player2,boolean displayMessage){
        this.player1 = player1;
        this.player2 = player2;
        this.displayMessage = displayMessage;

    }

    /**
     * If playerPosition = 1, the results of the first player is returned.
     * If playerPosition = 2, the result of the second player is returned.
     * If playerPosition equals neither, -1 is returned.
     * @param playerPosition - an id of a player in the game.
     * @return the number of victories of a player.
     */
    public int getPlayerScore(int playerPosition){
        if(playerPosition==player1.getPlayerId()){
            return player1Score;
        }
        else if(playerPosition==player2.getPlayerId()){
            return player2Score;
        }
        else {
            return negativeNum;
        }
        }

    /**
     * updating the scores of each player in each competition
     * @param playerPosition - an id of a player in the game.
     */
    private void setPlayerScore(int playerPosition){
        if(playerPosition==player1.getPlayerId()){
            player1Score+=1;
        }
        else if(playerPosition==player2.getPlayerId()){
            player2Score+=1;
        }
    }

    /**
     * Run the game for the given number of rounds.
     * @param numberOfRounds - number of rounds to play.
     */
    public void playMultipleRounds(int numberOfRounds){
        //is printed in each competition.
        System.out.println("Starting a Nim competition of "+ numberOfRounds +" rounds" +
                " between a "+player1.getTypeName()+" player and a "+player2.getTypeName()+" player.");
        //will be executed as the given number of rounds.
        for(int i=1 ; i<=numberOfRounds; i++){
            //creating a new board.
            competitonBoard=new Board();
            if(displayMessage){System.out.println("Welcome to the sticks game!");}
            Player currentPlayer = player1;
            boolean sign = true;
            //will be executed as long as the board isn't all marked.
            while (competitonBoard.getNumberOfUnmarkedSticks()>0){
                if(displayMessage&&sign){
                    System.out.println("Player "+currentPlayer.getPlayerId()+", it is now your turn!");
                }
                sign = true;
                Move move = currentPlayer.produceMove(competitonBoard);
                int moveStatue = competitonBoard.markStickSequence(move);
                if(moveStatue ==0){
                    if(displayMessage){System.out.println("Player "+currentPlayer.getPlayerId()+" made the move: "+move);}
                }
                else {
                    if(displayMessage){System.out.println("Invalid move. Enter another:");}
                    sign=false;
                    continue;
                }
                //switching the players and run again.
                if(currentPlayer==player1){currentPlayer=player2;}
                else {currentPlayer=player1;}
            }
            if(displayMessage){System.out.println("Player "+ currentPlayer.getPlayerId() +" won! ");}
            setPlayerScore(currentPlayer.getPlayerId());
        }
        //always printed.
        System.out.println("The results are "+getPlayerScore(1)+":"+getPlayerScore(2));

    }

    /*
     * Returns the integer representing the type of player 1; returns -1 on bad
     * input.
     */
    private static int parsePlayer1Type(String[] args){
        try{
            return Integer.parseInt(args[0]);
        } catch (Exception E){
            return -1;
        }
    }

    /*
     * Returns the integer representing the type of player 2; returns -1 on bad
     * input.
     */
    private static int parsePlayer2Type(String[] args){
        try{
            return Integer.parseInt(args[1]);
        } catch (Exception E){
            return -1;
        }
    }

    /*
     * Returns the integer representing the type of player 2; returns -1 on bad
     * input.
     */
    private static int parseNumberOfGames(String[] args){
        try{
            return Integer.parseInt(args[2]);
        } catch (Exception E){
            return -1;
        }
    }

    /**
     * The method runs a Nim competition between two players according to the three user-specified arguments.
     * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
     *     player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
     * (2) The type of the second player, which is a positive integer between 1 and 4.
     * (3) The number of rounds to be played in the competition.
     * @param args an array of string representations of the three input arguments, as detailed above.
     */
    public static void main(String[] args) {
        //creating all objects to start the game.
        int p1Type = parsePlayer1Type(args);
        int p2Type = parsePlayer2Type(args);
        int numGames = parseNumberOfGames(args);
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player(p1Type,1,scanner);
        Player player2 = new Player(p2Type,2,scanner);
        //present message condition.
        boolean presentMesseges=false;
        if(p1Type==4 || p2Type==4){
            presentMesseges =true;
        }
        //creating the game.
        Competition competition = new Competition(player1,player2,presentMesseges);
        //running the game by a class function.
        competition.playMultipleRounds(numGames);
        scanner.close();


    }

}
