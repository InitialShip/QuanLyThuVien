package main.scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LibraryController implements Initializable{
    @FXML private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 1; i < 10; i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Book.fxml"));
            try {
                HBox book = fxmlLoader.load();
                gridPane.add(book,1,i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
}
