package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.data.BookData;
import main.data.CategoryData;
import main.entity.Book;
import main.entity.Category;
import main.utility.MyListener;
import main.utility.MyScene;

public class BookViewController implements Initializable{
    @FXML private VBox layout;
    @FXML private AnchorPane review;
    @FXML private Tab tabRequest;
    @FXML private ComboBox<String> searchComboBox;
    @FXML private ComboBox<Category> filterComboBox;


    private BookData bookData;
    
    private MyListener myListener;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myListener = new MyListener() {
            @Override
            public void onClickListener(Book book) throws IOException {
                loadReview(book);
            }
        };
        searchComboBox.setItems(FXCollections.observableArrayList("Title", "Authors","Year"));
        searchComboBox.getSelectionModel().selectFirst();
        //re check this thing add show all
        filterComboBox.setItems(FXCollections.observableList(CategoryData.getCategories()));
        filterComboBox.getSelectionModel().selectFirst();

        try {
            bookData = BookData.getInstance();
            bookData.sort();
            for (Book book : bookData.getBooks()) {
                FXMLLoader fxmlLoader = MyScene.getFXML("scene/user/BookCard");
                AnchorPane bookCard = fxmlLoader.load();
                BookCardController bookCardController = fxmlLoader.getController();
                bookCardController.setData(book, myListener);
                layout.getChildren().add(bookCard);
            }
            loadReview(bookData.getBooks().get(0));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void loadReview(Book book) throws IOException{
        FXMLLoader fxmlLoader = MyScene.getFXML("scene/user/BookReview");
        AnchorPane bookReview = fxmlLoader.load();
        BookReviewController bookReviewController = fxmlLoader.getController();
        bookReviewController.setData(book);
        review.getChildren().add(bookReview);
        tabRequest.setText("Yes");
    }

}
