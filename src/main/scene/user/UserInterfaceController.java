package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.manager.AppUserManager;
import main.manager.BookManager;
import main.manager.CategoryManager;
import main.manager.StatusManager;
import main.utility.MyScene;

public class UserInterfaceController implements Initializable{
    @FXML private AnchorPane pane;
    @FXML private Pane bookView;
    @FXML private Pane idCard;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            StatusManager.getInstance();
            StatusManager.loadData();
            CategoryManager.getInstance();
            CategoryManager.loadData();
            BookManager.getInstance();
            BookManager.loadData();
            //user Data
            AppUserManager.getInstance();
            AppUserManager.loadData(AppUserManager.getUser().getId());
            AppUserManager.getRecentOrder();
            AppUserManager.getOrderHistory();
            toBookView();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " 1");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML 
    private void toBookView() throws IOException{
        setUnselected();
        bookView.getStyleClass().add("selected");
        pane.getChildren().clear();
        pane.getChildren().add(MyScene.loadFXML("scene/user/BookView"));
    }
    private static Stage libCardStage;
    @FXML
    public void toIdCard(MouseEvent event) throws IOException{
        if(libCardStage == null){
            libCardStage = new Stage();
            libCardStage.setResizable(false);
            libCardStage.setTitle("Library Card");
            libCardStage.setScene(new Scene(MyScene.loadFXML("scene/user/LibCard")));
            libCardStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        }
        libCardStage.show();
        libCardStage.toFront(); 
    }
    private void setUnselected(){
        bookView.getStyleClass().clear();
        bookView.getStyleClass().add("verticalTab");
        
        idCard.getStyleClass().clear();
        idCard.getStyleClass().add("verticalTab");
    }
}
