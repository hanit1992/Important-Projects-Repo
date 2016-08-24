import java.util.Random;
import java.util.Scanner;


/**
 * The Player class represents a player in the Nim game, producing Moves as a response to a Board state. Each player
 * is initialized with a type, either human or one of several computer strategies, which defines the move he
 * produces when given a board in some state. The heuristic strategy of the player is already implemented. You are
 * required to implement the rest of the player types according to the exercise description.
 * @author OOP course staff
 */
public class Player {

    //Constants that represent the different players.
    /** The constant integer representing the Random player type. */
    public static final int RANDOM = 1;
    /** The constant integer representing the Heuristic player type. */
    public static final int HEURISTIC = 2;
    /** The constant integer representing the Smart player type. */
    public static final int SMART = 3;
    /** The constant integer representing the Human player type. */
    public static final int HUMAN = 4;

    private static final int BINARY_LENGTH = 4;	//Used by produceHeuristicMove() for binary representation of board rows.

    /** The constant integer represent the user request to print the current board.*/
    private static final int PRINT_THE_CURRENT_BOARD = 1;
    /** The constant integer represent the user request to make a move. */
    private static final int MAKE_A_MOVE = 2;

    private final int playerType;
    private final int playerId;
    private Scanner scanner;

    /**
     * Initializes a new player of the given type and the given id, and an initialized scanner.
     * @param type The type of the player to create.
     * @param id The id of the player (either 1 or 2).
     * @param inputScanner The Scanner object through which to get user input
     * for the Human player type.
     */
    public Player(int type, int id, Scanner inputScanner){
        // Check for legal player type (we will see better ways to do this in the future).
        if (type != RANDOM && type != HEURISTIC
                && type != SMART && type != HUMAN){
            System.out.println("Received an unknown player type as a parameter"
                    + " in Player constructor. Terminating.");
            System.exit(-1);
        }
        playerType = type;
        playerId = id;
        scanner = inputScanner;
    }

    /**
     * @return an integer matching the player type.
     */
    public int getPlayerType(){
        return playerType;
    }

    /**
     * @return the players id number.
     */
    public int getPlayerId(){
        return playerId;
    }


    /**
     * @return a String matching the player type.
     */
    public String getTypeName(){
        switch(playerType){

            case RANDOM:
                return "Random";

            case SMART:
                return "Smart";

            case HEURISTIC:
                return "Heuristic";

            case HUMAN:
                return "Human";
        }
        //Because we checked for legal player types in the
        //constructor, this line shouldn't be reachable.
        return "UnknownPlayerType";
    }

    /**
     * This method encapsulates all the reasoning of the player about the game. The player is given the
     * board object, and is required to return his next move on the board. The choice of the move depends
     * on the type of the player: a human player chooses his move manually; the random player should
     * return some random move; the Smart player can represent any reasonable strategy; the Heuristic
     * player uses a strong heuristic to choose a move.
     * @param board - a Board object representing the current state of the game.
     * @return a Move object representing the move that the current player will play according to his strategy.
     */
    public Move produceMove(Board board){

        switch(playerType){

            case RANDOM:
                return produceRandomMove(board);

            case SMART:
                return produceSmartMove(board);

            case HEURISTIC:
                return produceHeuristicMove(board);

            case HUMAN:
                return produceHumanMove(board);

            //Because we checked for legal player types in the
            //constructor, this line shouldn't be reachable.
            default:
                return null;
        }
    }

    /**
     * Produces a random move.
     * @param board - a given Board object
     * @return move - a random move, Move object
     */
    private Move produceRandomMove(Board board) {
        Random random = new Random();
        int row = 0;
        int left = 0;
        int right = 0;
        while (true) {
            //the conditions in order to return a valid move
            if (board.isStickUnmarked(row, left) && board.isStickUnmarked(row, right) && row < board.getNumberOfRows()
                    && left < board.getRowLength(row) && right < board.getRowLength(row) && left <= right
                    && this.isSequenceisWhole(board,row,left,right)) {return new Move(row,left,right);}
            //keep searching it if not found
            row = random.nextInt(board.getNumberOfRows()) + 1;
            left = random.nextInt(board.getRowLength(row)) + 1;
            right = random.nextInt(board.getRowLength(row)) + 1;
            //if there is a lower probability, try a less random approach
            if (board.getNumberOfUnmarkedSticks() <= 7 && board.getNumberOfUnmarkedSticks() > 0) {
                //returning to un helper method
                int[] array = this.choosingByOrder(board);
                row = array[0];
                left = array[1];
                right = array[2];
                return new Move(row, left, right);
            }
        }
    }

    /**
     * an helper method to produceRandomMove -
      *checking the parts between the given limits
      * @param board
      * @returns array with the recommended spot for a move
     */
    private boolean isSequenceisWhole(Board board,int row,int left, int right){
        for(int i=left; i<=right;i++){
            if(!board.isStickUnmarked(row,i)){return false;}
        }
        return true;
    }

    /**
     * an helper method -
      *in case of some low probability of finding sequence(random), or in case a specific choice
      * isn't relevant(smart).
      * @param board a Board object.
      * @returns array with the recommended spot for a move
     */
    private int[] choosingByOrder(Board board){
        for(int i=1;i<=board.getNumberOfRows();i++){
            for(int j=1;j<=board.getRowLength(i);j++){
                if(board.isStickUnmarked(i,j)){
                    int row = i;
                    int left = j;
                    int right = j;
                    return new int[]{row,left,right};
                }
            }
        }
            //no reason to get here, since it will enter the condition aventualy in any case
            return null;
    }

    /**
     * Produce some intelligent strategy to produce a move
     * @param board - a Board given object;
     * @return move - a calculated smart move, who will have better winning chances.
     */
    private Move produceSmartMove(Board board){
        if(howManySequences(board)>1){
            //isolating a sequence by marking the rest;
            return this.handelingSequence(board,true);
        }

        if(howManySequences(board)==1){
            if(howManyIsolatedUnMarked(board)%2==0){
                //isolating a sequence by marking the rest;
                return this.handelingSequence(board,true);
            }
            else{
                //marking all the sequence;
                return this.handelingSequence(board,false);
            }
        }
        if(howManyIsolatedUnMarked(board)==board.getNumberOfUnmarkedSticks()){
            //in this case it doesn't matter;
            int[] array = this.choosingByOrder(board);
            int row = array[0];
            int left = array[1];
            int right = array[2];
            return new Move(row, left, right);
        }
        //no reason to get here;
        return null;
    }

    /**
     * Calculates how many isolated unmarked sticks there are and returning the int number;
     * @param board - a Board given object;
     * @return int - number of isolated sticks on the current board;
     */
    private int howManyIsolatedUnMarked(Board board){
        int numOfIsolated=0;
        for(int row=1;row<=board.getNumberOfRows();row++){
            for(int stick=1;stick<=board.getRowLength(row);stick++){
                //if you reached an isolated one;
                if(board.isStickUnmarked(row,stick)&& !board.isStickUnmarked(row,stick-1)
                        && !board.isStickUnmarked(row,stick+1)){
                    numOfIsolated+=1;
                }
            }
        }
        return numOfIsolated;
    }

    /**
     * Calculating how many sequences there are on the board
     * @param board - a Board given object;
     * @return int - the number of sequences on the current board;
     */
    private int howManySequences(Board board){
        int numOfSequences=0;
        for(int row=1;row<=board.getNumberOfRows();row++){
            int stick=1;
                //checking if we reached a sequence in a loop;
                while(board.isStickUnmarked(row,stick)|| stick < board.getRowLength(row)){
                    if(!board.isStickUnmarked(row,stick)){
                        //if we reached a marked one, check the next one;
                        stick+=1;
                        //just to clarify the intention;
                        continue;
                    }
                    //if we reached a sequence, go to the end, mark, and check the following stick;
                    else if(board.isStickUnmarked(row,stick)&&board.isStickUnmarked(row,stick+1)){
                        numOfSequences+=1;
                        for(int i=stick;i<=board.getRowLength(row);i++){
                            if(board.isStickUnmarked(row,i)&& i!=board.getRowLength(row)){
                                //just to clarify the intention;
                                continue;}
                            else{
                                if(i!=board.getRowLength(row)){stick=i-1;}
                                else {
                                    stick=board.getRowLength(row);
                                    break;}
                    }
                        }
                    }
                    //we didn't reach an unmarked one yet;
                    else{stick+=1;}
                }
        }
        return numOfSequences;
    }

    /**
     * Produce a smart move helper - Produce a move, based on the sequences strategy;
     * @param board - a Board given object;
     * @param isolate - the requested mode - to isolate the stick or to mark all;
     * @return move - a calculated smart move, who will be used by the smart player function;
     */
    private Move handelingSequence(Board board,boolean isolate){
        int finalRow;
        int finalLeft;
        int finalRight=0;
        for(int row=1;row<=board.getNumberOfRows();row++){
            for(int stick=1;stick<=board.getRowLength(row);stick++){
                //checking if we reached a sequence;
                if(board.isStickUnmarked(row,stick)&& board.isStickUnmarked(row,stick+1)){
                    finalRow=row;
                    finalLeft=stick;
                    for(int i=stick;i<=board.getRowLength(row);i++){
                        if(board.isStickUnmarked(row,i) && i!=board.getRowLength(row)){continue;}
                        //marks all the sticks or all the sticks besides one,
                        // according to some conditions, regarding the limit of the row;
                        else {
                            if((isolate && i==board.getRowLength(row)&&!board.isStickUnmarked(row,i))
                                    ||(isolate&&i!=board.getRowLength(row))){finalRight = i-2;}
                            else if((isolate && i==board.getRowLength(row)&&board.isStickUnmarked(row,i))
                                    ||(!isolate&&i==board.getRowLength(row)&&!board.isStickUnmarked(row,i))
                                    ||(i!=board.getRowLength(row))&&!isolate) {finalRight = i-1;}
                            else if(!isolate&&i==board.getRowLength(row)&&board.isStickUnmarked(row,i)){finalRight =i;}
                            }
                        return new Move(finalRow,finalLeft,finalRight);
                    }
                }
            }
        }
        //there is no reason to get here,since the return value will execute anyway;
        return null;
    }

    /**
     * Interact with the user to produce his move.
     * @param board - the board of the current competition;
     * @return a move, constructed by the program user;
     */
    private Move produceHumanMove(Board board){
        while (true){
            System.out.println("Press 1 to display the board. Press 2 to make a move:");
            int input1 = this.scanner.nextInt();
            if(input1==PRINT_THE_CURRENT_BOARD){
                System.out.println(board);
            }
            else if(input1==MAKE_A_MOVE){
                System.out.println("Enter the row number:");
                int row = this.scanner.nextInt();
                System.out.println("Enter the index of the leftmost stick:");
                int left = this.scanner.nextInt();
                System.out.println("Enter the index of the rightmost stick:");
                int right = this.scanner.nextInt();
                return new Move(row,left,right);
            }
            else{
                System.out.println("Unsupported command");
            }
        }
    }

    /*
     * Uses a winning heuristic for the Nim game to produce a move.
     */
    private Move produceHeuristicMove(Board board){

        int numRows = board.getNumberOfRows();
        int[][] bins = new int[numRows][BINARY_LENGTH];
        int[] binarySum = new int[BINARY_LENGTH];
        int bitIndex,higherThenOne=0,totalOnes=0,lastRow=0,lastLeft=0,lastSize=0,lastOneRow=0,lastOneLeft=0;

        for(bitIndex = 0;bitIndex<BINARY_LENGTH;bitIndex++){
            binarySum[bitIndex] = 0;
        }

        for(int k=0;k<numRows;k++){

            int curRowLength = board.getRowLength(k+1);
            int i = 0;
            int numOnes = 0;

            for(bitIndex = 0;bitIndex<BINARY_LENGTH;bitIndex++){
                bins[k][bitIndex] = 0;
            }

            do {
                if(i<curRowLength && board.isStickUnmarked(k+1,i+1) ){
                    numOnes++;
                } else {

                    if(numOnes>0){

                        String curNum = Integer.toBinaryString(numOnes);
                        while(curNum.length()<BINARY_LENGTH){
                            curNum = "0" + curNum;
                        }
                        for(bitIndex = 0;bitIndex<BINARY_LENGTH;bitIndex++){
                            bins[k][bitIndex] += curNum.charAt(bitIndex)-'0'; //Convert from char to int
                        }

                        if(numOnes>1){
                            higherThenOne++;
                            lastRow = k +1;
                            lastLeft = i - numOnes + 1;
                            lastSize = numOnes;
                        } else {
                            totalOnes++;
                        }
                        lastOneRow = k+1;
                        lastOneLeft = i;

                        numOnes = 0;
                    }
                }
                i++;
            }while(i<=curRowLength);

            for(bitIndex = 0;bitIndex<BINARY_LENGTH;bitIndex++){
                binarySum[bitIndex] = (binarySum[bitIndex]+bins[k][bitIndex])%2;
            }
        }


        //We only have single sticks
        if(higherThenOne==0){
            return new Move(lastOneRow,lastOneLeft,lastOneLeft);
        }

        //We are at a finishing state
        if(higherThenOne<=1){

            if(totalOnes == 0){
                return new Move(lastRow,lastLeft,lastLeft+(lastSize-1) - 1);
            } else {
                return new Move(lastRow,lastLeft,lastLeft+(lastSize-1)-(1-totalOnes%2));
            }

        }

        for(bitIndex = 0;bitIndex<BINARY_LENGTH-1;bitIndex++){

            if(binarySum[bitIndex]>0){

                int finalSum = 0,eraseRow = 0,eraseSize = 0,numRemove = 0;
                for(int k=0;k<numRows;k++){

                    if(bins[k][bitIndex]>0){
                        eraseRow = k+1;
                        eraseSize = (int)Math.pow(2,BINARY_LENGTH-bitIndex-1);

                        for(int b2 = bitIndex+1;b2<BINARY_LENGTH;b2++){

                            if(binarySum[b2]>0){

                                if(bins[k][b2]==0){
                                    finalSum = finalSum + (int)Math.pow(2,BINARY_LENGTH-b2-1);
                                } else {
                                    finalSum = finalSum - (int)Math.pow(2,BINARY_LENGTH-b2-1);
                                }

                            }

                        }
                        break;
                    }
                }

                numRemove = eraseSize - finalSum;

                //Now we find that part and remove from it the required piece
                int numOnes=0,i=0;
                while(numOnes<eraseSize){

                    if(board.isStickUnmarked(eraseRow,i+1)){
                        numOnes++;
                    } else {
                        numOnes=0;
                    }
                    i++;

                }
                return new Move(eraseRow,i-numOnes+1,i-numOnes+numRemove);
            }
        }

        //If we reached here, and the board is not symmetric, then we only need to erase a single stick
        if(binarySum[BINARY_LENGTH-1]>0){
            return new Move(lastOneRow,lastOneLeft,lastOneLeft);
        }

        //If we reached here, it means that the board is already symmetric, and then we simply mark one stick from the last sequence we saw:
        return new Move(lastRow,lastLeft,lastLeft);
    }


}
