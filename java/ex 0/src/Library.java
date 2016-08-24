/**
 * This class represents a library, which hold a collection of books.
 * Patrons can register at the library to be able to check out books, if a copy of the requested book is available.
 */
public class Library {

    /** object data. */

    /**
     * The maximal number of books this library can hold.
     */
    int maxBooks;

    /**
     * The maximal number of books this library allows a single patron to borrow at the same time.
     */
    int maxBorrowed;

    /**
     * The maximal number of registered patrons this library can handle.
     */
    int maxPatron;

    /**
     * an array with all the library books
     */
    Book currentLibraryBooks[] ;

    /**
     * an array with all the patrons registered to the library
     */
    Patron currentLibraryPatrons[];

    /** a constant of the class. */
    public static final int NEGATIVE_NUMBER = -1;


    /** class constructor. */
    /**
     * Creates a new library with the given parameters.
     *
     * @param maxBookCapacity   The maximal number of books this library can hold.
     * @param maxBorrowedBooks  The maximal number of books this library allows a single patron to borrow at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        maxBooks = maxBookCapacity;
        maxBorrowed = maxBorrowedBooks;
        maxPatron = maxPatronCapacity;
        currentLibraryBooks = new Book[maxBooks];
        currentLibraryPatrons = new Patron[maxPatron];
    }

	/*----=  Instance Methods  =-----*/

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     *
     * @param book a Book object
     */
    int addBookToLibrary(Book book) {
        for (int i=0; i<maxBooks; i++) {

            if (currentLibraryBooks[i]==book)
                //the book is already in the library
                return i;

            if (currentLibraryBooks[i] == null) {
                currentLibraryBooks[i] = book;
                book.returnBook();
                //the book was added
                return i;
            }
        }
        //the library is full and the given book is not there
        return NEGATIVE_NUMBER;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId int of a Book object in the library
     */
    boolean isBookIdValid(int bookId) {
        if (bookId<maxBooks && bookId>=0) {
            if (currentLibraryBooks[bookId] != null) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book a Book object
     */
    int getBookId(Book book) {
        for (int i=0; i<maxBooks; i++) {
            if (currentLibraryBooks[i] == book) {
                return i;
            }
        }
        return NEGATIVE_NUMBER;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId int of a Book object in the library
     */
    boolean isBookAvailable(int bookId) {
        if (isBookIdValid(bookId)&& currentLibraryBooks[bookId].currentBorrowerId == NEGATIVE_NUMBER) {
            return true;
        }
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron a Patron object
     */
    int registerPatronToLibrary(Patron patron) {
        for (int i=0; i<maxPatron; i++) {
            if (currentLibraryPatrons[i] == null) {
                currentLibraryPatrons[i] = patron;
                //patron was added
                return i;
            }
        }
        //patron was not added, library is full
        return NEGATIVE_NUMBER;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId int of a Patron object in the library
     */
    boolean isPatronIdValid(int patronId) {
        if(patronId<maxPatron && patronId>=0){
            if(currentLibraryPatrons[patronId] != null){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     *
     * @param patron a Patron object
     */
    int getPatronId(Patron patron) {
        for (int i=0; i<maxPatron; i++) {
            if (currentLibraryPatrons[i] == patron) {
                return i;
            }
        }
        return NEGATIVE_NUMBER;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id,
     * if this book is available, the given patron isn't already borrowing the maximal number of books allowed,
     * and if the patron will enjoy this book.
     *
     * @param bookId int of a Book object in the library
     * @param patronId int of a Patron object in the library
     */
    boolean borrowBook(int bookId, int patronId) {
        int numOfBorrowed = 0;
        for (int i = 0; i <= maxBooks; i++) {
            if (currentLibraryBooks[i]!=null){
                if (currentLibraryBooks[i].getCurrentBorrowerId() == patronId) {
                    numOfBorrowed = +1;
                }
            }
            if(isBookIdValid(bookId)&& isPatronIdValid(patronId)){
                if (isBookAvailable(bookId) && numOfBorrowed <= maxBorrowed &&
                        currentLibraryPatrons[patronId].willEnjoyBook(currentLibraryBooks[bookId])) {
                    currentLibraryBooks[bookId].setBorrowerId(patronId);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Return the given book.
     *
     * @param bookId int of a Book object in the library
     */
    void returnBook(int bookId) {
        if(isBookIdValid(bookId)){
            currentLibraryBooks[bookId].returnBook();
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most,
     * out of all available books he will enjoy, if any such exist.
     *
     * @param patronId int of a Patron object in the library
     */
    Book suggestBookToPatron(int patronId) {
        if(isPatronIdValid(patronId)==false){
            return null;
        }
        int currentMaxBookScore = 0;
        Book currentBestBook = null;
        for (int i=0; i<maxBooks; i++) {

            if(currentLibraryBooks[i]==null){
                break;
            }
            int bookScore = currentLibraryPatrons[patronId].getBookScore(currentLibraryBooks[i]);
            if (bookScore > currentMaxBookScore) {
                currentBestBook = currentLibraryBooks[i];
                currentMaxBookScore = bookScore;
            }
        }

        if (currentLibraryPatrons[patronId].willEnjoyBook(currentBestBook)) {
            return currentBestBook;
        }
        return null;
    }
}
