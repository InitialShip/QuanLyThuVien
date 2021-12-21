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
    * Get fxml relative to App class
    */
    public static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = getFXML(fxml);
        return fxmlLoader.load();
    }
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
    * Get FXMLloader
    */
    public static FXMLLoader getFXML(String fxml){
        return new FXMLLoader(App.class.getResource(fxml+".fxml"));
    }
}
