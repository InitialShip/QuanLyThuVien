package main.scene.login;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.mySqlConnector.Connector;
import main.service.RegisterService;
import main.utility.MyScene;
import main.utility.Utils;

public class RegisterController implements Initializable{
    @FXML private TextField txt_UserId;
    @FXML private PasswordField txt_Password;
    @FXML private PasswordField txt_ConfirmPassword;
    @FXML private Label userIdError;
    @FXML private Label passwordError;
    @FXML private Label confirmPassError;
    @FXML private Button registerButton;

    private String userIdInput;
    private String userPassInput;
    private String userConfirmInput;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userIdError.setText("");
        passwordError.setText("");
        confirmPassError.setText("");
    }

    @FXML
    private void registerSubmit() throws SQLException{
        //set states
        registerButton.setDisable(true);
        userIdError.setText("");
        passwordError.setText("");
        confirmPassError.setText("");

        userIdInput = txt_UserId.getText();
        userPassInput = txt_Password.getText();
        userConfirmInput = txt_ConfirmPassword.getText();
        //client check
        if(!isUserIdValid(userIdInput)){
            userIdError.setText("Please Check the spelling and try again.");
            txt_UserId.requestFocus();
            registerButton.setDisable(false);
            return;
        }
        if(!isUserPasswordValid(userPassInput)){
            passwordError.setText("Contain atleast one Uppercase, Lowercase, Number\nAleast 8 characters, At most 20.");
            txt_Password.requestFocus();
            registerButton.setDisable(false);
            return;
        }
        if(!userPassInput.equals(userConfirmInput)){
            confirmPassError.setText("Password does not match.");
            txt_Password.requestFocus();
            registerButton.setDisable(false);
            return;
        }
        //database check
        try {
            if(!RegisterService.isFromSchool(userIdInput)){
                userIdError.setText("ID does not exist. Please try again.");
                userIdError.requestFocus();
            }else
            if(!RegisterService.isAccountExisted(userIdInput)){
                if(RegisterService.register(userIdInput, userPassInput)){
                    Utils.getAlertBox("Register Successful!", AlertType.INFORMATION).showAndWait();
                }else{
                    Utils.getAlertBox("Register failed!", AlertType.ERROR).showAndWait();
                }
            }else{
                userIdError.setText("You are already have an account.");
                userIdError.requestFocus();
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally{
            Connector.close();
            registerButton.setDisable(false);
        }
    }
    @FXML 
    private void toLoginForm(ActionEvent event) throws IOException{
        MyScene.switchScene(event, "scene/login/Login");
    }
    /*
    * Validating user Id and user password from inputs
    */
    private boolean isUserIdValid(String input){
        return Utils.isMatched(input, "^(?=[A-Za-z0-9])(?=\\S+$).{8,20}$");
    }
    private boolean isUserPasswordValid(String input){
        return Utils.isMatched(input, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$");
    }
}
