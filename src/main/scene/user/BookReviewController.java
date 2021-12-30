package main.scene.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.entity.Book;
import main.manager.CategoryManager;

public class BookReviewController {
    @FXML private Label title;
    @FXML private Label authors;
    @FXML private Label description;
    @FXML private Label category;
    @FXML private Label publisher;
    @FXML private Label year;
    @FXML private Label dateAdded;
    @FXML private Label place;
    @FXML private ImageView imageView;

    private Book book;

    public void setData(Book book){
        this.book = book;
        loadData();
    }

    private void loadData(){
        title.setText(book.getTitle());
        authors.setText("Author: " + book.getAuthor());
        description.setText("Description: "+book.getDescription());
        category.setText("Category: " + CategoryManager.getCategoryName(book.getCategoryId()));
        publisher.setText("Publisher: " + book.getPublisher());
        year.setText("Year: " + book.getYear());
        dateAdded.setText("Date Added: " + book.getDateAdded());
        place.setText("Place: " + book.getPlace());
        imageView.setImage(book.getImage());
    }
}
