package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.entity.Book;
import main.manager.AppUserManager;
import main.myListener.MyOnClickListener;

public class BookCardController implements Initializable{
    @FXML private Label lb_Title;
    @FXML private Label lb_Author;
    @FXML private ImageView img_BookCover;
    @FXML private Button btn_Order;
    private Book book;
    private MyOnClickListener myListener;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_Order.setOpacity(0);
        btn_Order.setDisable(true);
    }
    public void setData(Book newBook, MyOnClickListener newMyListener){
        this.book = newBook;
        this.myListener = newMyListener;
        lb_Title.setText(book.getTitle());
        lb_Author.setText(book.getAuthor());
        img_BookCover.setImage(book.getImage());
    }

    @FXML
    private void onClick() throws IOException{
        myListener.onClickListener(book);
    }
    @FXML
    private void orderClick(){
        AppUserManager.order(book);
        //System.out.println(AppUserManager.getUser().getOrder().size());
    }
    @FXML
    private void onMouseEnter(){
        btn_Order.setOpacity(1);
        if(AppUserManager.getUser().getOrder().contains(book)){
            btn_Order.setText("Cancel");
        }else{
            btn_Order.setText("Order");
        }
        btn_Order.setDisable(false);
    }
    @FXML
    private void onMouseExited(){
        btn_Order.setOpacity(0);
        btn_Order.setDisable(true);
    }
}
