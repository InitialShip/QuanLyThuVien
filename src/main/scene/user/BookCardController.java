package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.entity.Book;
import main.manager.AppUserManager;
import main.myListener.MyOnClickListener;
import main.myListener.MyOnOrderListener;

public class BookCardController implements Initializable{
    @FXML private Label lb_Title;
    @FXML private Label lb_Author;
    @FXML private ImageView img_BookCover;
    @FXML private Button btn_Order;
    private Book book;
    private MyOnClickListener myListener;
    private MyOnOrderListener myOnOrderListener;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!AppUserManager.getUser().getCanOrder())
            btn_Order.setDisable(true);
    }
    public void setData(Book newBook, MyOnClickListener newMyListener, MyOnOrderListener newMyOnOrderListener){
        this.book = newBook;
        this.myListener = newMyListener;
        this.myOnOrderListener = newMyOnOrderListener;
        lb_Title.setText(book.getTitle());
        lb_Author.setText(book.getAuthor());
        img_BookCover.setImage(book.getImage());
    }

    @FXML
    private void onClick(MouseEvent event) throws IOException{
        if(event.getClickCount() == 2){
            myListener.onClickListener(book, event);
        }
    }
    @FXML
    private void orderClick(){
        if(AppUserManager.getUser().getCanOrder()){
            AppUserManager.order(book);
            myOnOrderListener.orderClick();
        }
    }
    

}
