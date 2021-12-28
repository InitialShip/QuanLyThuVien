package main.utility;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import main.App;

public class MyScene {
    /*
    * Switch to new scene relative to App class
    */
    public static void switchScene(ActionEvent event, String fxml) throws IOException{
        Scene scene = new Scene(loadFXML(fxml));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /*
    * Get fxml relative to App class
    */
    public static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = getFXML(fxml);
        return fxmlLoader.load();
    }
    /*
    * Get FXMLloader
    */
    public static FXMLLoader getFXML(String fxml){
        return new FXMLLoader(App.class.getResource(fxml+".fxml"));
    }

    /*
    * open child scene on child stage with controller
    */
    public static <T> Object openChildScene(Stage childStage,String fxml) throws IOException{
        FXMLLoader fxmlLoader = getFXML(fxml);
        childStage.setScene(new Scene(fxmlLoader.load()));
        return fxmlLoader.getController();
    }
}
