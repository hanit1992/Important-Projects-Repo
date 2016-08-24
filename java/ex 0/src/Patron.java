/**
 * This class represents a library patron that has a name and assigns values to different literary aspects of books. .
 */
public class Patron {

    /** object data. */

    /** The first name of the patron.. */
    final String firstName ;

    /** The last name of the patron. */
    final String lastName ;

    /** The weight the patron assigns to the comic aspects of books. */
    int comical ;

    /** The weight the patron assigns to the dramatic aspects of books. */
    int dramatic ;

    /** The weight the patron assigns to the educational aspects of books. */
    int educational ;

    /** The minimal literary value a book must have for this patron to enjoy it. */
    int enjoyment ;


    /** class constructor. */
    /**
     * Creates a new patron with the given characteristics.
     * @param patronFirstName the first name of the patron.
     * @param patronLastName the last name of the patron.
     * @param comicTendency the weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency the weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency the weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold the minimal literary value a book must have for this patron to enjoy it.
     */
    Patron(String patronFirstName,String patronLastName,int comicTendency,int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold){
        firstName = patronFirstName ;
        lastName = patronLastName ;
        comical = comicTendency ;
        dramatic = dramaticTendency ;
        educational = educationalTendency ;
        enjoyment = patronEnjoymentThreshold ;
    }

	/*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the patron
     */
    String stringRepresentation(){
        return firstName + " " + lastName ;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * @param book a Book object
     */
    int getBookScore(Book book){
        return book.comicValue*comical + book.dramaticValue*dramatic +
                book.educationalValue*educational ;
    }

    /**
     * Returns true if this patron will enjoy the given book, false otherwise.
     * @param book a Book object
     */
    boolean willEnjoyBook(Book book){
        if (getBookScore(book) >= enjoyment)
            return true ;
        return false ;

    }

}
