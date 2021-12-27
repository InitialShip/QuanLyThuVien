package main.utility;

import java.io.InputStream;

import javafx.scene.image.Image;
import main.App;

public class MyImage {
    public static Image placeHolder;
    static {
        placeHolder = new Image(App.class.getResourceAsStream("resource/placeholder.png"));
    }
    /*
    * get image from binary stream
    */
    public static Image toImage(InputStream inputStream){
        return new Image(inputStream);
    }
}
