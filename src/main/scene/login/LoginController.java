package main.scene.login;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.mySqlConnector.Connector;
import main.service.LoginService;
import main.utility.MyScene;
import main.utility.Utils;

public class LoginController implements Initializable{
    @FXML private ComboBox<String> rolesBox;
    @FXML private TextField txt_UserId;
    @FXML private PasswordField txt_Password;
    @FXML private Label userIdError;
    @FXML private Label passwordError;
    @FXML private Button loginButton;
    private String userIdInput;
    private String userPassInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> roles = FXCollections.observableArrayList("User", "Admin");
        rolesBox.setItems(roles);
        rolesBox.getSelectionModel().selectFirst();

        userIdError.setText("");
        passwordError.setText("");
        try {
            Connector.open();
            Connector.close();
        } catch (Exception e) {
            Utils.getAlertBox("Can not connect to database", AlertType.ERROR).showAndWait();
            Platform.exit();
        }
    }
    @FXML
    private void loginSubmit(ActionEvent event) throws SQLException{
        //set states
        loginButton.setDisable(true);
        rolesBox.setDisable(true);
        userIdError.setText("");
        passwordError.setText("");
        
        userIdInput = txt_UserId.getText();
        userPassInput = txt_Password.getText();
        
        //Validating before database
        if(!isUserIdValid(userIdInput)){
            userIdError.setText("Please Check the spelling and try again.");
            txt_UserId.requestFocus();
            loginButton.setDisable(false);
            rolesBox.setDisable(false);
            return;
        }
        if(!isUserPasswordValid(userPassInput)){
            passwordError.setText("That was the wrong password. Please try again.");
            txt_Password.setText("");
            txt_Password.requestFocus();
            loginButton.setDisable(false);
            rolesBox.setDisable(false);
            return;
        }
        //Validating with database
        if (rolesBox.getValue() == "User"){
            try {
                if(!LoginService.isAccountExisted(userIdInput)){
                    userIdError.setText("User does not exist. Please try again.");
                    userIdError.requestFocus();
                } else
                if(LoginService.isPasswordCorrect(userIdInput, userPassInput)){
                    MyScene.switchScene(event, "scene/user/UserInterface");
                }else{
                    passwordError.setText("That was the wrong password. Please try again.");
                    txt_Password.setText("");
                    txt_Password.requestFocus();
                }
            }catch(SQLException se){
                Utils.getAlertBox("Cannot connect to database!", AlertType.ERROR).showAndWait();
                System.out.println(se.getMessage());
            } 
            catch (Exception e) {
                Utils.getAlertBox("Opps Something went wrong!", AlertType.ERROR).showAndWait();
            }
            finally{
                loginButton.setDisable(false);
                rolesBox.setDisable(false);
            }
        }
        else
        {
            try {
                if(!LoginService.isAdminAccountExisted(userIdInput)){
                    userIdError.setText("User does not exist. Please try again.");
                    userIdError.requestFocus();
                } else
                if(LoginService.isAdminPasswordCorrect(userIdInput, userPassInput)){
                    MyScene.switchScene(event, "scene/admin/AdminInterface");
                }else{
                    passwordError.setText("That was the wrong password. Please try again.");
                    txt_Password.requestFocus();
                }
            }catch(SQLException se){
                System.out.println(se.getMessage());
                Utils.getAlertBox("Cannot connect to database!", AlertType.ERROR).showAndWait();
            } 
            catch (Exception e) {
                Utils.getAlertBox("Opps something went wrong!", AlertType.ERROR).showAndWait();
            }
            finally{
                loginButton.setDisable(false);
                rolesBox.setDisable(false);
            }
        }
        
    }
    
    @FXML 
    private void toRegisterForm(ActionEvent event) throws IOException{
        MyScene.switchScene(event, "scene/login/Register");
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
