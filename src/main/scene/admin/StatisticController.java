package main.scene.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.manager.OrderManager;

public class StatisticController implements Initializable{
    @FXML Label q_Total;
    @FXML Label q_Returned;
    @FXML Label q_NotReturned;
    @FXML Label q_TotalFine;
    @FXML Label y_Total;
    @FXML Label y_Returned;
    @FXML Label y_NotReturned;
    @FXML Label y_TotalFine;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        q_Total.setText(Integer.toString(OrderManager.getQTotalOrder()));
        q_Returned.setText(Integer.toString(OrderManager.getQReturnedOrder()));
        q_NotReturned.setText(Integer.toString(OrderManager.getQNotReturnedOrder()));
        q_TotalFine.setText(Integer.toString(OrderManager.getQTotalFine()));
        y_Total.setText(Integer.toString(OrderManager.getYTotalOrder()));
        y_Returned.setText(Integer.toString(OrderManager.getYReturnedOrder()));
        y_NotReturned.setText(Integer.toString(OrderManager.getYNotReturnedOrder()));
        y_TotalFine.setText(Integer.toString(OrderManager.getYTotalFine()));
    }
}
