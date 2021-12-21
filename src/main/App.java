package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.utility.MyScene;

public class App extends Application{
    
    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("resource/book.png")));
        Scene scene = new Scene(MyScene.loadFXML("scene/user/UserInterface"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) throws Exception {
        launch();
    }
}
