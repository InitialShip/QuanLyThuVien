package main.scene;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.mySqlConnector.Connector;
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
    private void close(){
        javafx.application.Platform.exit();
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

        if(!Utils.isUserIdValid(userIdInput)){
            userIdError.setText("Please Check the spelling and try again.");
            txt_UserId.requestFocus();
            registerButton.setDisable(false);
            return;
        }
        if(!Utils.isUserPasswordValid(userPassInput)){
            passwordError.setText("That was the wrong password. Please try again.");
            txt_Password.requestFocus();
            registerButton.setDisable(false);
            return;
        }
        if(userPassInput != userConfirmInput){
            confirmPassError.setText("Password does not match.");
            txt_Password.requestFocus();
            registerButton.setDisable(false);
            return;
        }
        //check if a user already have an account
        //insert into database
        try {
            Connector.open();
            Connector.getCnt().setAutoCommit(false);

            PreparedStatement statement = Connector.getCnt().prepareStatement("INSERT INTO test_account VALUES(?,?)");
            statement.setString(1, userIdInput);
            statement.setString(2, Utils.getEncryted(userIdInput+userPassInput));

            statement.executeUpdate(); // check returned values

            Connector.getCnt().commit();
        }catch (Exception e) {
            //TODO: handle exception
        }
        finally{
            Connector.close();
            registerButton.setDisable(false);
        }
    }
    @FXML 
    private void toLoginForm(ActionEvent event) throws IOException{
        Utils.switchScene(event, "scene/Login");
    }
}
