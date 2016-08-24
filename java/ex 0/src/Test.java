
public class Test {
    public static void main (String[] args){
        Book n  = new Book("nam", "pat", 1990, 1,1,1);
        Book booki = new Book("the catcher in the rye" , "j.d salinger", 1960 , 8,9,8);
        System.out.println(booki.stringRepresentation());
        System.out.println(booki.getCurrentBorrowerId());
        System.out.println(booki.getLiteraryValue());
        Patron jiji = new Patron("harry","potter",1,2,4,50);
        Patron huhu = new Patron("hanit","hakim",1,3,4,60);
        System.out.println(jiji.stringRepresentation());
        System.out.println(jiji.getBookScore(booki));
        System.out.println(jiji.willEnjoyBook(booki));
        Library huji = new Library(20,3,6);
        System.out.println(huji.addBookToLibrary(booki));
        System.out.println(huji.addBookToLibrary(n));
        System.out.println(huji.registerPatronToLibrary(jiji));
        System.out.println(huji.borrowBook(6,0));
        System.out.println(huji.borrowBook(0,0));
        System.out.println(huji.borrowBook(8,9));
        System.out.println(huji.isBookAvailable(6));
        System.out.println(huji.isBookAvailable(0));
        huji.returnBook(0);
        System.out.println(booki.getCurrentBorrowerId());
        System.out.println(huji.suggestBookToPatron(0));
        System.out.println(huji.isBookAvailable(0));
        System.out.println(huji.isBookIdValid(80));
        System.out.println(huji.getPatronId(huhu));
        System.out.println(huji.registerPatronToLibrary(huhu));
        System.out.println(huji.getPatronId(huhu));
        huji.returnBook(1);
        Library gita = new Library(20,5,4);
        System.out.println(gita.registerPatronToLibrary(jiji));
        System.out.println(gita.addBookToLibrary(booki));
        System.out.println(gita.isBookAvailable(0));
        huji.borrowBook(0,0);
        System.out.println(gita.isBookAvailable(0));

    }
}
