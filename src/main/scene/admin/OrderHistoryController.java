package main.scene.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.entity.Order;
import main.manager.OrderManager;
import main.manager.StatusManager;
import main.utility.MyScene;

public class OrderHistoryController implements Initializable{
    @FXML private TableView<Order> orderView;
    @FXML private TableColumn<Order,Integer> orderIdColumn;
    @FXML private TableColumn<Order, String> userIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, Date> expireDateColumn;
    @FXML private TableColumn<Order, String> orderStatusColumn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("userId"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<Order,Date>("orderDate"));
        expireDateColumn.setCellValueFactory(new PropertyValueFactory<Order,Date>("expireDate"));
        orderStatusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Order, String> o) {
                return new SimpleStringProperty(StatusManager.getStatusName(o.getValue().getOrderStatusId()));
            }
        });
        orderView.setItems(FXCollections.observableArrayList(OrderManager.getOrderHistory()));
    }
    private static Stage orderStage;
    private static OrderDetailViewController orderDetailViewController;
    @FXML
    private void clickOrder(MouseEvent event) throws IOException{
        if(event.getClickCount() == 2){
            Order order = orderView.getSelectionModel().getSelectedItem();
            if(order == null)
                return;
            if(orderStage == null){
                orderStage = new Stage();
                orderStage.setResizable(false);
                orderStage.setTitle("Order Detail | " + "ID :"+order.getOrderId());
                orderDetailViewController = (OrderDetailViewController)MyScene.openChildScene(orderStage, "scene/admin/OrderDetailView");
                orderStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            }
            orderDetailViewController.setData(order);
            orderDetailViewController.setDisable();
            orderStage.show();
            orderStage.toFront();
        }
    }
}
