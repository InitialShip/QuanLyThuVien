package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.entity.Book;
import main.myInterface.MyOnClickListener;

public class BookCardController implements Initializable{
    @FXML private Label title;
    @FXML private Label authors;
    @FXML private VBox interactVBox;
    private Book book;
    private MyOnClickListener myListener;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        interactVBox.setOpacity(0);
        interactVBox.setDisable(true);
    }
    public void setData(Book book, MyOnClickListener myListener){
        this.book = book;
        this.myListener = myListener;
        title.setText(book.getTitle());
        authors.setText(book.getAuthor());
    }

    @FXML
    private void onClick() throws IOException{
        myListener.onClickListener(book);
    }

    @FXML
    private void onMouseEnter(){
        interactVBox.setOpacity(1);
        interactVBox.setDisable(false);
    }
    @FXML
    private void onMouseExited(){
        interactVBox.setOpacity(0);
        interactVBox.setDisable(true);
    }
}
