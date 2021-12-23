package main.scene.user;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import main.data.AppUserData;

public class IDCardController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // try {
        //     AppUserData user = AppUserData.getInstance();
        //     user.loadData("CS1960001");

        //     System.out.println(user.getUser().getId());
        //     System.out.println(user.getUser().getFirstName());
        //     System.out.println(user.getUser().getLastName());
        //     System.out.println(user.getUser().getOccupation());
        //     System.out.println(user.getUser().getSex());
        //     System.out.println(user.getUser().getDateOfBirth());
        // } catch (SQLException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }  
    }
    
}
