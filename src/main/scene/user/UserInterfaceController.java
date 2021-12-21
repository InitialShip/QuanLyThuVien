package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.utility.MyScene;

public class UserInterfaceController implements Initializable{
    @FXML private AnchorPane pane;
    @FXML private Pane bookView;
    @FXML private Pane idCard;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dosomething();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @FXML 
    private void dosomething() throws IOException{
        setUnselected();
        bookView.getStyleClass().add("selected");
        pane.getChildren().clear();
        pane.getChildren().add(MyScene.loadFXML("scene/user/BookView"));
    }
    @FXML
    public void dosomething2() throws IOException{
        setUnselected();
        idCard.getStyleClass().add("selected");
        pane.getChildren().clear();
        pane.getChildren().add(MyScene.loadFXML("scene/user/test2"));
    }
    private void setUnselected(){
        bookView.getStyleClass().clear();
        bookView.getStyleClass().add("verticalTab");
        
        idCard.getStyleClass().clear();
        idCard.getStyleClass().add("verticalTab");
    }
}
