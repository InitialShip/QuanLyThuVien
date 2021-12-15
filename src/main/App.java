package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application{
    
    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("resource/book.png")));
        Scene scene = new Scene(loadFXML("scene/Login"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml+".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) throws Exception {
        launch();
    }
}
