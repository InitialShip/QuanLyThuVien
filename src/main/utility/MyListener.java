package main.utility;

import java.io.IOException;

import main.entity.Book;

public interface MyListener {
    public void onClickListener(Book book)throws IOException;
}
