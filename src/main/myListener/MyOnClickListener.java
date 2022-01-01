package main.myListener;

import java.io.IOException;

import javafx.scene.input.MouseEvent;
import main.entity.Book;

public interface MyOnClickListener {
    public void onClickListener(Book book, MouseEvent event)throws IOException;
}
