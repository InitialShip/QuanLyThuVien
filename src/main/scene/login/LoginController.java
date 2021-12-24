package main.scene.login;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
        ObservableList<String> roles = FXCollections.observableArrayList("User", "Librarian");
        rolesBox.setItems(roles);
        rolesBox.getSelectionModel().selectFirst();

        userIdError.setText("");
        passwordError.setText("");
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
            //txt_UserId.getStyleClass().add("error");
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
                Connector.open();
    
                PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT user_password FROM user_account WHERE user_id = ?");
                statement.setString(1, userIdInput);
                ResultSet rs = statement.executeQuery();
    
                if(!rs.isBeforeFirst()){
                    userIdError.setText("User does not exist. Please try again.");
                    userIdError.requestFocus();
                } 
                else{
                    //should be only one row
                    while (rs.next()) {
                        //#UNIT TEST 
                        String retrievedPass = rs.getString("user_password");
                        
                        if (retrievedPass.equals(Utils.getEncryted(userIdInput+userPassInput))){
                            
                            //System.out.println("You're in!");
                            MyScene.switchScene(event, "scene/primary");
                        }
                        else {
                            passwordError.setText("That was the wrong password. Please try again.");
                            txt_Password.requestFocus();
                        }          
                    }
                }
            }catch(SQLException sx){
                Utils.getAlertBox("Cannot connect to database!", AlertType.ERROR);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                Connector.close();
                loginButton.setDisable(false);
                rolesBox.setDisable(false);
            }
        }
        else
        {
            // Librarian
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
