package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.utility.MyScene;

public class App extends Application{
    
    @Override
    public void start(Stage stage) throws IOException {
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("resource/book.png")));
        stage.setTitle("Library Assistant");
        Scene scene = new Scene(MyScene.loadFXML("scene/user/UserInterface"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) throws Exception {

        // String regex = "(?i)\\b"+"BA||cc"+".*?\\b";
        // String str = "BAbeeg Bagge cccc ba";
        // Pattern pattern = Pattern.compile(regex);
        // Matcher matcher = pattern.matcher(str);
        // while(matcher.find()) {
        //     System.out.print(matcher.group()+" ");
        //  }
        launch();
    }
}
