package main.scene.admin;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import main.entity.Category;
import main.manager.CategoryManager;
import main.utility.MyImage;

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CategoryManager.getInstance();
        try {
            CategoryManager.loadData();
        } catch (SQLException e) {
            // TODO remove after
            e.printStackTrace();
        }
        cbox_Category.setItems(FXCollections.observableArrayList(CategoryManager.getCategories()));
        cbox_Category.getSelectionModel().selectFirst();
        dp_DateAdded.setValue(LocalDate.now());
        
        txt_Id.textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;
            private int maxLength = 10;
            String restrict = "([a-zA-Z0-9\s]*\\.?)";
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if (ignore || s1 == null)
                    return;
                if (s1.length() > maxLength) {
                    ignore = true;
                    txt_Id.setText(s1.substring(0, maxLength));
                    ignore = false;
                }
                if (!s1.matches(restrict)) {
                    ignore = true;
                    txt_Id.setText(s);
                    ignore = false;
                }
            }
        });
        txt_Title.textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;
            private int maxLength = 100;
            String restrict = "([a-zA-Z0-9\s]*\\.?)";
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if (ignore || s1 == null)
                    return;
                if (s1.length() > maxLength) {
                    ignore = true;
                    txt_Title.setText(s1.substring(0, maxLength));
                    ignore = false;
                }
                if (!s1.matches(restrict)) {
                    ignore = true;
                    txt_Title.setText(s);
                    ignore = false;
                }
            }
        });
        txt_Author.textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;
            private int maxLength = 500;
            String restrict = "([a-zA-Z0-9\s]*\\.?)";
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if (ignore || s1 == null)
                    return;
                if (s1.length() > maxLength) {
                    ignore = true;
                    txt_Author.setText(s1.substring(0, maxLength));
                    ignore = false;
                }
                if (!s1.matches(restrict)) {
                    ignore = true;
                    txt_Author.setText(s);
                    ignore = false;
                }
            }
        });
        txt_Description.textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;
            private int maxLength = 500;
            String restrict = "([a-zA-Z0-9\s]*\\.?)";
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if (ignore || s1 == null)
                    return;
                if (s1.length() > maxLength) {
                    ignore = true;
                    txt_Description.setText(s1.substring(0, maxLength));
                    ignore = false;
                }
                if (!s1.matches(restrict)) {
                    ignore = true;
                    txt_Description.setText(s);
                    ignore = false;
                }
            }
        });
        txt_Publisher.textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;
            private int maxLength = 45;
            String restrict = "([a-zA-Z0-9\s]*\\.?)";
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if (ignore || s1 == null)
                    return;
                if (s1.length() > maxLength) {
                    ignore = true;
                    txt_Publisher.setText(s1.substring(0, maxLength));
                    ignore = false;
                }
                if (!s1.matches(restrict)) {
                    ignore = true;
                    txt_Publisher.setText(s);
                    ignore = false;
                }
            }
        });
        txt_Year.textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;
            private int maxLength = 4;
            private String restrict = "([0-9]*\\.?)";
            @Override 
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if (ignore || s1 == null)
                    return;
                if (s1.length() > maxLength) {
                    ignore = true;
                    txt_Year.setText(s1.substring(0, maxLength));
                    ignore = false;
                }
                if (!s1.matches(restrict)) {
                    ignore = true;
                    txt_Year.setText(s);
                    ignore = false;
                }
            }
        });
        txt_Place.textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;
            private int maxLength = 4; //TODO change
            private String restrict = "([a-zA-Z0-9\s]*\\.?)";
            @Override 
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                if (ignore || s1 == null)
                    return;
                if (s1.length() > maxLength) {
                    ignore = true;
                    txt_Place.setText(s1.substring(0, maxLength));
                    ignore = false;
                }
                if (!s1.matches(restrict)) {
                    ignore = true;
                    txt_Place.setText(s);
                    ignore = false;
                }
            }
        });
    }

    /*
    * Event
    */
    private String fileStream;
    @FXML
    private void onConfirmClick(){

    }
    @FXML
    private void onResetInfoClick(){
        txt_Id.setText("");
        txt_Title.setText("");
        txt_Author.setText("");
        txt_Description.setText("");
        txt_Publisher.setText("");
        txt_Year.setText(""); //set this year
        cbox_Category.getSelectionModel().selectFirst();
        txt_Place.setText("");
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
