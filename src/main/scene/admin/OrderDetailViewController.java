package main.scene.admin;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.entity.Order;
import main.entity.OrderDetail;
import main.manager.OrderManager;
import main.manager.StatusManager;
import main.myListener.MyOnUpdateListener;
import main.utility.Utils;

public class OrderDetailViewController implements Initializable{
    @FXML private TableView<OrderDetail> orderDetailView;
    @FXML private TableColumn<OrderDetail, String> bookIdColumn;
    @FXML private TableColumn<OrderDetail, Date> returnedDateColumn;
    @FXML private TableColumn<OrderDetail, Integer> fineColumn;
    @FXML private TableColumn<OrderDetail, String> statusColumn;
    @FXML private TableColumn<OrderDetail, String> todayFineColumn;
    @FXML private Label lb_OrderId;
    @FXML private Label lb_UserId;
    @FXML private Label lb_OrderDate;
    @FXML private Label lb_OrderStatus;
    @FXML private Label lb_ExpireDate;
    @FXML private Button btn_CancelOrder;
    @FXML private Button btn_Action;
    @FXML private TableView<OrderDetail> checkOutView;
    @FXML private TableColumn<OrderDetail, String> checkOutBIdColumn;
    private List<OrderDetail> checkOutList;
    private Order selectedOrder;
    private MyOnUpdateListener myOnUpdateListener;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("bookId"));
        returnedDateColumn.setCellValueFactory(new PropertyValueFactory<OrderDetail,Date>("returnedDate"));
        fineColumn.setCellValueFactory(new PropertyValueFactory<OrderDetail,Integer>("fine"));
        statusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderDetail,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderDetail, String> od) {
                return new SimpleStringProperty(StatusManager.getStatusName(od.getValue().getStatusId()));
            }
        });
        todayFineColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderDetail,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderDetail, String> od) {
                if(od.getValue().getStatusId() == 1 || od.getValue().getStatusId() == 3 || od.getValue().getStatusId() == 4)
                    return null;
                int dayDiff = 0;
                if(LocalDate.now().compareTo(selectedOrder.getExpireDate().toLocalDate()) > 0){
                    dayDiff = (int) Utils.getDaysDiff(selectedOrder.getExpireDate().toLocalDate(), LocalDate.now());
                }
                return new SimpleStringProperty(Integer.toString((dayDiff*5000)));
            }  
        });
        checkOutBIdColumn.setCellValueFactory(new PropertyValueFactory<OrderDetail,String>("bookId"));
    }
    public void setData(Order order){
        this.selectedOrder = order;
        loadData();
    }
    public void setListener(MyOnUpdateListener myListener){
        this.myOnUpdateListener = myListener;
    }
    public void setDisable(){
        btn_Action.setDisable(true);
        btn_CancelOrder.setDisable(true);
        btn_Action.setVisible(false);
        btn_CancelOrder.setVisible(false);
    }
    private void loadData(){
        orderDetailView.setItems(FXCollections.observableArrayList(selectedOrder.getListOrder()));
        lb_OrderId.setText(Integer.toString(selectedOrder.getOrderId()));
        lb_UserId.setText(selectedOrder.getUserId());
        lb_OrderDate.setText(selectedOrder.getOrderDate().toString());
        lb_OrderStatus.setText(StatusManager.getStatusName(selectedOrder.getOrderStatusId()));
        lb_ExpireDate.setText(selectedOrder.getExpireDate().toString());
        if(selectedOrder.getOrderStatusId() != 1)
            btn_CancelOrder.setDisable(true);
        if(selectedOrder.getOrderStatusId() == 1)
            btn_Action.setText("Check In");
        if(selectedOrder.getOrderStatusId() == 2)
            btn_Action.setText("Check Out");
        if(selectedOrder.getOrderStatusId() == 3 || selectedOrder.getOrderStatusId() == 4){
            btn_Action.setDisable(true);
            btn_Action.setVisible(false);
        }
        if(checkOutList == null)
            checkOutList = new ArrayList<>();
        else
            checkOutList.clear();
        checkOutView.setItems(FXCollections.observableArrayList(checkOutList));
    }
    @FXML
    private void action(ActionEvent event) throws SQLException{
        if(selectedOrder.getOrderStatusId() == 3 || selectedOrder.getOrderStatusId() == 4)
            return;
        if(selectedOrder.getOrderStatusId() == 1)
            checkIn();
        if(selectedOrder.getOrderStatusId() == 2)
            checkOut();
    }
    @FXML
    private void cancelOrder() throws SQLException{
        if(selectedOrder.getOrderStatusId() != 1)
            return;
        Optional<ButtonType> option = Utils.getAlertBox("Cancel this order ?", AlertType.CONFIRMATION).showAndWait();
        if(option.get() == ButtonType.OK){
            try{
                btn_CancelOrder.setDisable(true);
                btn_Action.setDisable(true);
                OrderManager.cancelAnOrder(selectedOrder.getOrderId());
                Utils.getAlertBox("Canceled !", AlertType.INFORMATION).showAndWait();
            }catch (SQLException se){
                Utils.getAlertBox("Some thing went wrong !", AlertType.ERROR).showAndWait();
                btn_CancelOrder.setDisable(false);
                btn_Action.setDisable(false);
            }finally{
                btn_CancelOrder.setDisable(false);
                btn_Action.setDisable(false);
                myOnUpdateListener.update();
                ((Stage)btn_CancelOrder.getScene().getWindow()).close();
            }
        }
    }
    private void checkIn() throws SQLException{
        if(selectedOrder.getOrderStatusId() != 1)
            return;
        Optional<ButtonType> option = Utils.getAlertBox("Check in ?", AlertType.CONFIRMATION).showAndWait();
        if(option.get() == ButtonType.OK){
            try{
                btn_CancelOrder.setDisable(true);
                btn_Action.setDisable(true);
                OrderManager.checkInAnOrder(selectedOrder.getOrderId());
                Utils.getAlertBox("Check in success !", AlertType.INFORMATION).showAndWait();
            }catch(SQLException se){
                Utils.getAlertBox("Some thing went wrong !", AlertType.ERROR).showAndWait();
                btn_CancelOrder.setDisable(false);
                btn_Action.setDisable(false);
            }finally{
                btn_CancelOrder.setDisable(false);
                btn_Action.setDisable(false);
                myOnUpdateListener.update();
                ((Stage)btn_Action.getScene().getWindow()).close();
            }
        }
    }
    private void checkOut() throws SQLException{
        if(selectedOrder.getOrderStatusId() != 2)
            return;
        if(checkOutList.isEmpty())
            return;
        Optional<ButtonType> option = Utils.getAlertBox("Check out ?", AlertType.CONFIRMATION).showAndWait();
        if(option.get() == ButtonType.OK){
            try {
                btn_CancelOrder.setDisable(true);
                btn_Action.setDisable(true);
                if(OrderManager.checkOutOrder(selectedOrder.getOrderId(), checkOutList,selectedOrder.getExpireDate()))
                    Utils.getAlertBox("Check out success !", AlertType.INFORMATION).showAndWait();
                else
                    Utils.getAlertBox("Check out failed !", AlertType.ERROR).showAndWait();
            } catch (SQLException se) {
                System.out.println(se.getMessage());
                Utils.getAlertBox("Some thing went wrong !", AlertType.ERROR).showAndWait();
                btn_CancelOrder.setDisable(false);
                btn_Action.setDisable(false);
            }finally{
                myOnUpdateListener.update();
                btn_CancelOrder.setDisable(false);
                btn_Action.setDisable(false);
                ((Stage)btn_Action.getScene().getWindow()).close();
            }
        }
    }
    @FXML
    private void clickItem(MouseEvent event){
        if(event.getClickCount() == 2){
            OrderDetail orderDetail = orderDetailView.getSelectionModel().getSelectedItem();
            if(orderDetail == null)
                return;
            if(orderDetail.getStatusId() == 2){
                addCheckOut(orderDetail);
                checkOutView.setItems(FXCollections.observableArrayList(checkOutList));
            }
        }
    }
    private void addCheckOut(OrderDetail newOrderDetail){
        if(checkOutList.contains(newOrderDetail))
            checkOutList.remove(newOrderDetail);
        else
            checkOutList.add(newOrderDetail);
    }
}
