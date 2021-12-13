package main.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookController {
    @FXML private Label bookTitle;
    @FXML private Label author;

    @FXML 
    private void something(){
        System.out.println("owfowf");
    }
    public void setData(String title, String author){
        this.bookTitle.setText(title);;
        this.author.setText(author);
    }
}
