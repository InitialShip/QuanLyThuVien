package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.manager.AppUserManager;
import main.manager.BookManager;
import main.manager.CategoryManager;
import main.utility.MyScene;

public class UserInterfaceController implements Initializable{
    @FXML private AnchorPane pane;
    @FXML private Pane bookView;
    @FXML private Pane idCard;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AppUserManager.getInstance();
            //initialize library data
            CategoryManager.getInstance();
            CategoryManager.loadData();
            BookManager.getInstance();
            BookManager.loadData();
        
            toBookView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    @FXML 
    private void toBookView() throws IOException{
        setUnselected();
        bookView.getStyleClass().add("selected");
        pane.getChildren().clear();
        pane.getChildren().add(MyScene.loadFXML("scene/user/BookView"));
    }
    @FXML
    public void toIdCard() throws IOException{
        setUnselected();
        idCard.getStyleClass().add("selected");
        pane.getChildren().clear();
        pane.getChildren().add(MyScene.loadFXML("scene/user/IDCard"));
    }
    private void setUnselected(){
        bookView.getStyleClass().clear();
        bookView.getStyleClass().add("verticalTab");
        
        idCard.getStyleClass().clear();
        idCard.getStyleClass().add("verticalTab");
    }
}
