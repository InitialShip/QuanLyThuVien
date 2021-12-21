package main.utility;

import java.io.InputStream;

import javafx.scene.image.Image;

public class MyImage {
    /*
    * get image from binary stream
    */
    public static Image toImage(InputStream inputStream){
        return new Image(inputStream);
    }
}
