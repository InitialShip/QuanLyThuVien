package main.scene.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import main.manager.BookManager;
import main.manager.CategoryManager;
import main.myListener.MyOnUpdateListener;
import main.myListener.MyTextAreaChangeListener;
import main.myListener.MyTextFieldChangeListener;
import main.utility.Utils;

public class BookModifierController implements Initializable{
    @FXML private Label lb_bookId;
    @FXML private TextField txt_Title;
    @FXML private TextField txt_Author;
    @FXML private TextArea txt_Description;
    @FXML private TextField txt_Publisher;
    @FXML private TextField txt_Year;
    @FXML private ComboBox<Category> cbox_Category;
    @FXML private ImageView img_BookCover;
    @FXML private TextField txt_Place;
    @FXML private CheckBox ck_Disable;
    @FXML private DatePicker dp_DateAdded;

    @FXML private Button btn_Update;
    @FXML private Button btn_ResetInfo;
    @FXML private Button btn_ChoosePic;
    @FXML private Button btn_ResetPic;
    
    private MyOnUpdateListener myListener;
    private Book selectedBook;
    public void setData(Book book){
        this.selectedBook = book;
        loadData();
    }
    public void setListener(MyOnUpdateListener listener){
        this.myListener = listener;
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
        img_BookCover.setImage(selectedBook.getImage());
        ck_Disable.selectedProperty().set(selectedBook.isDisabled());
        dp_DateAdded.setValue(selectedBook.getDateAdded().toLocalDate());
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbox_Category.setItems(FXCollections.observableArrayList(CategoryManager.getCategories()));
        txt_Title.textProperty().addListener(new MyTextFieldChangeListener(txt_Title, 100, ""));
        txt_Author.textProperty().addListener(new MyTextFieldChangeListener(txt_Author, 100, ""));
        txt_Description.textProperty().addListener(new MyTextAreaChangeListener(txt_Description, 500, ""));
        txt_Publisher.textProperty().addListener(new MyTextFieldChangeListener(txt_Publisher, 50, ""));
        txt_Year.textProperty().addListener(new MyTextFieldChangeListener(txt_Year, 4, "([0-9]*\\.?)"));
        txt_Place.textProperty().addListener(new MyTextFieldChangeListener(txt_Place, 10, "([a-zA-Z0-9\s]*\\.?)"));
        
    }
    /*
    * Button Event
    */
    @FXML
    private void onUpdateClick() throws SQLException, FileNotFoundException{
        if(txt_Title.getText().isBlank()){
            Utils.getAlertBox("Please enter a title!", AlertType.WARNING).showAndWait();
            return;
        }
        btn_Update.setDisable(true);
        Optional<ButtonType> option = Utils.getAlertBox("Do you want to save changes ?", AlertType.CONFIRMATION).showAndWait();
        if (option.get() == ButtonType.OK){
            try {
                //update info
                Book newBook = new Book();
                newBook.setId(lb_bookId.getText());
                newBook.setTitle(txt_Title.getText());
                newBook.setAuthor(txt_Author.getText());
                newBook.setDescription(txt_Description.getText());
                newBook.setCategoryId(cbox_Category.getSelectionModel().getSelectedItem().getId());
                if (fileStream != null && !fileStream.isEmpty() )
                    newBook.setImageBinary(new FileInputStream(new File(fileStream)));
                newBook.setPublisher(txt_Publisher.getText());
                if(!txt_Year.getText().isBlank())
                    newBook.setYear(Integer.parseInt(txt_Year.getText()));
                newBook.setPlace(txt_Place.getText());
                newBook.setDisabled(ck_Disable.isSelected());
                newBook.setDateAdded(Date.valueOf(dp_DateAdded.getValue()));
                //insert to database
                if(BookManager.updateBook(newBook)){
                    //call parent stage to reload
                    myListener.update();
                    selectedBook = BookManager.getBook(lb_bookId.getText());
                    Utils.getAlertBox("Update successful!", AlertType.INFORMATION).showAndWait();
                }else{
                    Utils.getAlertBox("Update failed!", AlertType.ERROR).showAndWait();
                }
                
            }catch (FileNotFoundException fe){
                onResetPicClick();
                Utils.getAlertBox("Image can not be found!", AlertType.ERROR).showAndWait();
            }catch(SQLException se){
                onResetInfoClick();
                onResetPicClick();
                Utils.getAlertBox("Can not update to database", AlertType.ERROR).showAndWait();
            }catch (Exception e) {
                Utils.getAlertBox("Opps something went wrong!", AlertType.ERROR).showAndWait();
            }finally{
                btn_Update.setDisable(false);
            }
        }
        btn_Update.setDisable(false);
    }
    
    @FXML
    private void onResetInfoClick(){
        lb_bookId.setText(selectedBook.getId());
        txt_Title.setText(selectedBook.getTitle());
        txt_Author.setText(selectedBook.getAuthor());
        txt_Description.setText(selectedBook.getDescription());
        txt_Publisher.setText(selectedBook.getPublisher());
        txt_Year.setText(Integer.toString(selectedBook.getYear()));
        cbox_Category.getSelectionModel().select(CategoryManager.getCategory(selectedBook.getCategoryId()));
        txt_Place.setText(selectedBook.getPlace());
        dp_DateAdded.setValue(selectedBook.getDateAdded().toLocalDate());
        ck_Disable.selectedProperty().setValue(selectedBook.isDisabled());
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
        fileStream = null;
        img_BookCover.setImage(selectedBook.getImage());
    }
}
