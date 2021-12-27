package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.entity.Book;
import main.entity.Category;
import main.manager.BookManager;
import main.manager.CategoryManager;
import main.myInterface.MyOnClickListener;
import main.utility.MyScene;

public class BookViewController implements Initializable{
    //TODO change filter list like admin
    @FXML private VBox layout;
    @FXML private AnchorPane review;
    @FXML private Tab tabRequest;
    @FXML private Pane searchButton; 
    @FXML private TextField searchText;
    @FXML private ComboBox<String> searchComboBox;
    @FXML private ComboBox<Category> filterComboBox;
    @FXML private ComboBox<String> orderByComboBox;
    @FXML private ComboBox<String> sortOrderComboBox;


    private MyOnClickListener myListener;
    private static List<Book> displayList;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myListener = new MyOnClickListener() {
            @Override
            public void onClickListener(Book book) throws IOException {
                loadReview(book);
            }
        };
        initializeComboBoxes();
        try {
            displayList = applyFilter(BookManager.getBooks());
            displayBook();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
    }
    @FXML
    private void onSearch() throws IOException{
        searchButton.setDisable(true);
        layout.getChildren().clear();

        displayList = searchBooks(BookManager.getBooks());

        displayBook();
        searchButton.setDisable(false);
    }
    @FXML
    private void comboboxSelect() throws IOException{
        layout.getChildren().clear();
        displayList = applyFilter(BookManager.getBooks());

        displayBook();
    }

    private void loadReview(Book book) throws IOException{
        FXMLLoader fxmlLoader = MyScene.getFXML("scene/user/BookReview");
        AnchorPane bookReview = fxmlLoader.load();
        BookReviewController bookReviewController = fxmlLoader.getController();
        bookReviewController.setData(book);
        review.getChildren().add(bookReview);
        tabRequest.setText("Yes");
    }
    private void displayBook() throws IOException{
        for (Book book : displayList) {
            FXMLLoader fxmlLoader = MyScene.getFXML("scene/user/BookCard");
            AnchorPane bookCard = fxmlLoader.load();
            BookCardController bookCardController = fxmlLoader.getController();
            bookCardController.setData(book, myListener);
            layout.getChildren().add(bookCard);
        }
        if(!displayList.isEmpty())
            loadReview(displayList.get(0));
    }
    private void initializeComboBoxes(){
        searchComboBox.setItems(FXCollections.observableArrayList("Title", "Authors","Year"));
        searchComboBox.getSelectionModel().selectFirst();
        
        filterComboBox.getItems().add(new Category(0, "Show All"));
        filterComboBox.getItems().addAll(FXCollections.observableArrayList(CategoryManager.getCategories()));
        filterComboBox.getSelectionModel().selectFirst();

        orderByComboBox.setItems(FXCollections.observableArrayList("Title", "Authors","Year"));
        orderByComboBox.getSelectionModel().selectFirst();

        sortOrderComboBox.setItems(FXCollections.observableArrayList("Ascending","Descending"));
        sortOrderComboBox.getSelectionModel().selectFirst();
    }

    private List<Book> searchBooks(List<Book> books){
        if(searchText.getText().isBlank())
            return applyFilter(books);
        
        switch (searchComboBox.getSelectionModel().getSelectedItem()) {
            case "Title":
                return BookManager.findByTitle(searchText.getText(), applyFilter(books));
            case "Authors":
                return BookManager.findByAuthor(searchText.getText(), applyFilter(books));
            case "Year":
                return BookManager.findByYear(searchText.getText(), applyFilter(books));
            default:    
                break;
        }
        return null;
    }
    private List<Book> applyFilter(List<Book> books){
        return sortOrder(orderBy(filterBy(books)));
    }
    private List<Book> filterBy(List<Book> list){
        return BookManager.filter(filterComboBox.getSelectionModel().getSelectedItem().getId());
    }
    private List<Book> orderBy(List<Book> filteredList){
        switch(orderByComboBox.getSelectionModel().getSelectedItem()){
            case "Title" : 
                return BookManager.orderByTitle(filteredList);
            case "Authors" :
                return BookManager.orderByAuthor(filteredList);
            case "Year" :
                return BookManager.orderByYear(filteredList);
        }
        return null;
    }
    private List<Book> sortOrder(List<Book> sortedList){
        switch (sortOrderComboBox.getSelectionModel().getSelectedItem()) {
            case "Ascending":
                return sortedList;
            case "Descending":
                return BookManager.reverse(sortedList);
        }
        return null;
    }
}
