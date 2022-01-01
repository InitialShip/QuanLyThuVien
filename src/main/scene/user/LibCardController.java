package main.scene.user;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.manager.AppUserManager;
import main.utility.Utils;

public class LibCardController implements Initializable{
    @FXML private Label lb_LibCardId;
    @FXML private Label lb_FirstName;
    @FXML private Label lb_LastName;
    @FXML private Label lb_Sex;
    @FXML private Label lb_Occupation;
    @FXML private Label lb_DateOfBirth;
    @FXML private Label lb_ValidUpto;
    @FXML private TextField txt_Email;
    @FXML private TextField txt_PhoneNumber;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lb_LibCardId.setText(AppUserManager.getUser().getId());
        lb_FirstName.setText(AppUserManager.getUser().getFirstName());
        lb_LastName.setText(AppUserManager.getUser().getLastName());
        lb_Sex.setText(AppUserManager.getUser().getSex());
        lb_Occupation.setText(AppUserManager.getUser().getOccupation());
        lb_DateOfBirth.setText(AppUserManager.getDateOfBirth());
        lb_ValidUpto.setText(AppUserManager.getVaildUpToDate());
        txt_Email.setText(AppUserManager.getUser().getEmail());
        txt_PhoneNumber.setText(AppUserManager.getUser().getPhoneNumber());
    }
    @FXML
    private void onUpdateClick() throws SQLException{
        Optional<ButtonType> option = Utils.getAlertBox("Save changes ?",AlertType.CONFIRMATION).showAndWait();
        if(option.get() == ButtonType.OK){
            if(AppUserManager.updateIdCard(txt_Email.getText(), txt_PhoneNumber.getText()))
                Utils.getAlertBox("Update successful!", AlertType.INFORMATION).showAndWait();
            else
                Utils.getAlertBox("Update Faile",AlertType.INFORMATION).showAndWait();
        }
    }
}
