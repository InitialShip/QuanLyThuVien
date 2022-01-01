package main.scene.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
import main.utility.MyImage;
import main.utility.Utils;

public class AddBookController implements Initializable{
    @FXML private TextField txt_Id;
    @FXML private TextField txt_Title;
    @FXML private TextField txt_Author;
    @FXML private TextArea txt_Description;
    @FXML private TextField txt_Publisher;
    @FXML private TextField txt_Year;
    @FXML private ComboBox<Category> cbox_Category;
    @FXML private ImageView img_BookCover;
    @FXML private TextField txt_Place;
    @FXML private DatePicker dp_DateAdded;

    @FXML private CheckBox ck_Disable;
    @FXML private Button btn_Confirm;
    @FXML private Button btn_ResetInfo;
    @FXML private Button btn_ChoosePic;
    @FXML private Button btn_ResetPic;

    private MyOnUpdateListener myListener;
    public void setListener(MyOnUpdateListener listener){
        this.myListener = listener;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        cbox_Category.setItems(FXCollections.observableArrayList(CategoryManager.getCategories()));
        cbox_Category.getSelectionModel().selectFirst();
        dp_DateAdded.setValue(LocalDate.now());
        txt_Year.setText(Integer.toString(LocalDate.now().getYear()));
         /*
        * Event
        */
        //text field listener      
        txt_Id.textProperty().addListener(new MyTextFieldChangeListener(txt_Id, 10, "([a-zA-Z0-9]*\\.?)"));
        txt_Title.textProperty().addListener(new MyTextFieldChangeListener(txt_Title, 100, ""));
        txt_Author.textProperty().addListener(new MyTextFieldChangeListener(txt_Author, 100, ""));
        txt_Description.textProperty().addListener(new MyTextAreaChangeListener(txt_Description, 500, ""));
        txt_Publisher.textProperty().addListener(new MyTextFieldChangeListener(txt_Publisher, 50, ""));
        txt_Year.textProperty().addListener(new MyTextFieldChangeListener(txt_Year, 4, "([0-9]*\\.?)"));
        txt_Place.textProperty().addListener(new MyTextFieldChangeListener(txt_Place, 10, "([a-zA-Z0-9\s]*\\.?)"));
    }
    private String fileStream;
    @FXML
    private void onConfirmClick() throws SQLException{
        try {
            if(txt_Id.getText().isBlank() || BookManager.isIdExistd(txt_Id.getText())){
                Utils.getAlertBox("Blank or Duplicated ID!",AlertType.WARNING).showAndWait();
                return;
            }
        } catch (SQLException se) {
            Utils.getAlertBox("Can not connect to database!",AlertType.ERROR).showAndWait();
            return;
        }catch (Exception e){
            Utils.getAlertBox("Opps something went wrong!", AlertType.ERROR).showAndWait();
            return;
        }
        if(txt_Title.getText().isBlank()){
            Utils.getAlertBox("Please enter a title", AlertType.WARNING).showAndWait();
            return;
        }
        btn_Confirm.setDisable(true);
        Optional<ButtonType> option = Utils.getAlertBox("Do you want to insert data ?", AlertType.CONFIRMATION).showAndWait();
        if (option.get() == ButtonType.OK){
            try {
                //get info
                Book newBook = new Book();
                newBook.setId(txt_Id.getText());
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
                if(BookManager.insertBook(newBook)){
                    //call parent stage to reload
                    myListener.update();

                    Utils.getAlertBox("Insert successful!", AlertType.INFORMATION).showAndWait();
                }else{
                    Utils.getAlertBox("Insert failed!", AlertType.ERROR).showAndWait();
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
                btn_Confirm.setDisable(false);
            }
        }
        btn_Confirm.setDisable(false);
    }
    @FXML
    private void onResetInfoClick(){
        txt_Id.setText("");
        txt_Title.setText("");
        txt_Author.setText("");
        txt_Description.setText("");
        txt_Publisher.setText("");
        txt_Year.setText(Integer.toString(LocalDate.now().getYear()));
        cbox_Category.getSelectionModel().selectFirst();
        txt_Place.setText("");
        dp_DateAdded.setValue(LocalDate.now());
        ck_Disable.selectedProperty().setValue(false);
    }
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
        img_BookCover.setImage(MyImage.placeHolder);
    }
}
