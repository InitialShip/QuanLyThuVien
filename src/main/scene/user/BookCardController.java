package main.scene.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookCardController {
    @FXML private Label title;
    @FXML private Label authors;

    public void setData(String newTitle, String newAuthors){
        title.setText(newTitle);
        authors.setText(newAuthors);
    }
}
