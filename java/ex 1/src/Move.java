/**
 * The Move class represents a move in the game. each move will have a row, and a left and right limits,
 * whom witch the player requests to mark.
 */
public class Move
    extends java.lang.Object{
    private int row;
    private int leftBound;
    private int rightBound;

    /**
     * Constructs a Move object with the given parameters
     * @param inRow - The row on which the move is performed.
     * @param inLeft - The left bound of the sequence to mark.
     * @param inRight - The right bound of the sequence to mark.
     */
    public Move(int inRow,int inLeft,int inRight){
        row = inRow;
        leftBound = inLeft;
        rightBound = inRight;
    }

    /**
     * @return The left bound of the stick sequence to mark.
     */
    public int getLeftBound(){return leftBound;}

    /**
     * @return The right bound of the stick sequence to mark.
     */
    public int getRightBound(){return rightBound;}

    /**
     * @return The row on which the move is performed.
     */
    public int getRow(){return row;}

    /**
     * @return a string representation of the move.
     */
    public String toString(){
        return row+":"+leftBound+"-"+rightBound;
    }
}
