package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.service.OrderService;
import main.utility.MyScene;

public class App extends Application{
    
    @Override
    public void start(Stage stage) throws IOException {
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("resource/book.png")));
        stage.setTitle("Library Assistant");
        Scene scene = new Scene(MyScene.loadFXML("scene/admin/AdminInterface"));
        //Scene scene = new Scene(MyScene.loadFXML("scene/user/UserInterface"));
        //Scene scene = new Scene(MyScene.loadFXML("scene/login/Login"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) throws Exception {
        //OrderService.createOrder("CS1960008");
        //System.out.println(OrderService.getLatestOrderId("CS1960008"));
        launch();
    }
   
}
