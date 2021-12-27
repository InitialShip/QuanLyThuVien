package main.scene.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import main.entity.Book;
import main.entity.Category;
import main.manager.CategoryManager;
import main.myInterface.MyOnUpdateListener;
import main.service.BookService;
import main.utility.MyImage;
import main.utility.Utils;

public class BookModifierController implements Initializable{
    //TODO limit text input call reload after update
    @FXML private Label lb_bookId;
    @FXML private TextField txt_Title;
    @FXML private TextField txt_Author;
    @FXML private TextArea txt_Description;
    @FXML private TextField txt_Publisher;
    @FXML private TextField txt_Year;
    @FXML private ComboBox<Category> cbox_Category;
    @FXML private ImageView img_BookCover;
    @FXML private TextField txt_Place;

    @FXML private Button btn_Update;
    @FXML private Button btn_ResetInfo;
    @FXML private Button btn_ChoosePic;
    @FXML private Button btn_ResetPic;
    
    private MyOnUpdateListener myListenter;
    private Book selectedBook;
    public void setData(Book book, MyOnUpdateListener listener){
        this.selectedBook = book;
        this.myListenter = listener;
        loadData();
    }
    private void loadData(){

        lb_bookId.setText(selectedBook.getId());
        txt_Title.setText(selectedBook.getTitle());
        txt_Author.setText(selectedBook.getAuthor());
        txt_Description.setText(selectedBook.getDescription());
        txt_Publisher.setText(selectedBook.getPublisher());
        txt_Year.setText(Integer.toString(selectedBook.getYear()));
        cbox_Category.getSelectionModel().select(CategoryManager.getCategory(selectedBook.getCategoryId()));
        txt_Place.setText(selectedBook.getPlace());
        if(selectedBook.getImageBinary() != null)
            img_BookCover.setImage(MyImage.toImage(selectedBook.getImageBinary()));
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbox_Category.setItems(FXCollections.observableArrayList(CategoryManager.getCategories()));
    }
    /*
    * Event
    */
    @FXML
    private void onUpdateClick() throws SQLException, FileNotFoundException{
        btn_Update.setDisable(true);
        Optional<ButtonType> option = Utils.getAlertBox("Do you want to save changes ?", AlertType.CONFIRMATION).showAndWait();
        if (option.get() == ButtonType.OK){
            try {
                //TODO: check if null images
                Book newBook = new Book();
                newBook.setId(lb_bookId.getText());
                newBook.setTitle(txt_Title.getText());
                newBook.setAuthor(txt_Author.getText());
                newBook.setDescription(txt_Description.getText());
                newBook.setCategoryId(cbox_Category.getSelectionModel().getSelectedItem().getId());

                if (fileStream != null && !fileStream.isEmpty() )
                    newBook.setImageBinary(new FileInputStream(new File(fileStream)));

                newBook.setPublisher(txt_Publisher.getText());
                newBook.setYear(Integer.parseInt(txt_Year.getText()));
                //insert to database
                BookService.updateData(newBook);
                
                //call parent stage to reload
                myListenter.onUpdateListener();
                
                //TODO: handle it properly
            }catch (FileNotFoundException fe){
                System.out.println("File error");
                System.out.println(fe.getMessage());
            }catch(SQLException se){
                System.out.println("SQl error");
                System.out.println(se.getMessage());
            }catch (Exception e) {
                System.out.println("Do not know");
                System.out.println(e.getMessage());
            }finally{
                btn_Update.setDisable(false);
            }
        }
        btn_Update.setDisable(false);
    }
    @FXML
    private void onResetInfoClick(){
        
    }
    private String fileStream;
    @FXML
    private void onChoosePicClick(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        ExtensionFilter filter = new ExtensionFilter("IMAGE", "*jpg","*png");
        fileChooser.getExtensionFilters().addAll(filter);
         
        File file = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        if( file != null){
            String path = file.getAbsolutePath();
            img_BookCover.setImage(new Image(path));
            fileStream = path;
        }
    }
    @FXML
    private void onResetPicClick(){
        
    }
}
