package main.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.entity.Book;
import main.service.BookService;
import main.utility.Utils;

public class BookManager {
    private static List<Book> books = null;

    private static BookManager instance = null;
    private BookManager(){
        books = new ArrayList<>();
    }
    public static BookManager getInstance(){
        if (instance == null){
            instance = new BookManager();
        }
        return instance;
    }
    //getter
    public static List<Book> getBooks(){
        return books;
    }
    /*
    *   METHODS
    */
    public static void reloadData() throws SQLException{
        books.clear();
        loadData();
    }
    public static void addBook(Book book){
        books.add(book);
    }
    public static Book getBook(String id){
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }
    public static void loadData() throws SQLException{
        BookService.getData();
    }
    // find books
    /*
    * Will improve if I have time 
    */
    public static List<Book> findByTitle(String string, List<Book> list){
        return list.stream().filter(b -> b.getTitle().toLowerCase().contains(string.toLowerCase()) == true).toList();
    }
    public static List<Book> findByAuthor(String string, List<Book> list){
        return list.stream().filter(b ->  b.getAuthor().toLowerCase().contains(string.toLowerCase()) == true).toList();
    }
    public static List<Book> findByYear(String year, List<Book> list){
        return list.stream().filter(b ->  Integer.toString(b.getYear()).contains(year) == true).toList();
    }
    //filter by category
    public static List<Book> filter(int categoryId){
        // no filter applied
        if(categoryId == 0)
            return new ArrayList<>(books);

        return books.stream().filter(b -> b.getCategoryId() == categoryId).toList();
    }
    //sorting A->Z dsc
    public static List<Book> orderByTitle(List<Book> list){
        List<Book> sortedList = new ArrayList<>(list);
        sortedList.sort(new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getTitle().compareTo(b2.getTitle());
            }
        });
        return sortedList;
    }
    public static List<Book> orderByAuthor(List<Book> list){
        List<Book> sortedList = new ArrayList<>(list);
        sortedList.sort(new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getAuthor().compareTo(b2.getAuthor());
            }
        });
        return sortedList;
    }
    public static List<Book> orderByYear(List<Book> list){
        List<Book> sortedList = new ArrayList<>(list);
        sortedList.sort(new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return (b1.getYear()<b2.getYear())?-1:(b1.getYear()>b2.getYear())?1:0;
            }
        });
        return sortedList;
    }
    // asc
    public static List<Book> reverse(List<Book> list){
        List<Book> reversedList = new ArrayList<>(list);
        Utils.revlist(reversedList);
        return reversedList;
    }
   
}
