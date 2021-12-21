package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.data.BookData;
import main.entity.Book;
import main.utility.MyScene;

public class BookViewController implements Initializable{
    @FXML private VBox layout;

    private BookData bookData;
    private FXMLLoader fxmlLoader;
    private BookCardController controller;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            bookData = BookData.getInstance();
            bookData.loadData();
            bookData.sort();
            for (Book book : bookData.getBooks()) {
                fxmlLoader = MyScene.getFXML("scene/user/BookCard");
                AnchorPane bookCard = fxmlLoader.load();
                controller = fxmlLoader.getController();
                controller.setData(book.getName(), book.getAuthors());
                layout.getChildren().add(bookCard);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
