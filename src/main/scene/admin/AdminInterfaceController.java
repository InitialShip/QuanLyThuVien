package main.scene.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.entity.Book;
import main.entity.Order;
import main.manager.BookManager;
import main.manager.CategoryManager;
import main.manager.OrderManager;
import main.manager.StatusManager;
import main.myListener.MyOnUpdateListener;
import main.utility.MyScene;
import main.utility.Utils;

public class AdminInterfaceController implements Initializable {
    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, String> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, String> categoryColumn;
    @FXML private TableColumn<Book, String> disabledColumn;

    @FXML private TableView<Order> orderView;
    @FXML private TableColumn<Order,Integer> orderIdColumn;
    @FXML private TableColumn<Order, String> userIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, Date> expireDateColumn;
    @FXML private TableColumn<Order, String> orderStatusColumn;

    ObservableList<Book> displayList;
    private MyOnUpdateListener myListener;
    private MyOnUpdateListener myOrderListener;

    @FXML private ComboBox<String> cbox_SearchOption;
    @FXML private TextField txt_Search;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
            BookManager.getInstance();
            BookManager.loadData();
            CategoryManager.getInstance();
            CategoryManager.loadData();
            StatusManager.getInstance();
            StatusManager.loadData();
            OrderManager.getInstance();
            OrderManager.loadData();
        } catch (SQLException e) {
            Utils.getAlertBox("Can not load data!", AlertType.ERROR).showAndWait();
            Platform.exit();
        }
        cbox_SearchOption.setItems(FXCollections.observableArrayList("ID","Title", "Author", "Year"));
        cbox_SearchOption.getSelectionModel().selectFirst();

        displayList = FXCollections.observableArrayList(BookManager.getAllBooks());
        
        idColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Book,Integer>("year"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("publisher"));
        categoryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> b) {
                if(b.getValue() != null)
                    return new SimpleStringProperty(CategoryManager.getCategoryName(b.getValue().getCategoryId()));
                return null;
            }
        });
        disabledColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> b) {
                if(b.getValue() != null)
                    return new SimpleStringProperty(b.getValue().isDisabled()?"Disabled":"");
                return null;
            }
        });
        //Initialize filter for table view
        initializeFilter(displayList);
        
        myListener = new MyOnUpdateListener() {
            @Override
            public void update() throws SQLException {
                reloadData();      
            }
        };
        //Order view 
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
        orderView.setItems(FXCollections.observableArrayList(OrderManager.getRecentOrder()));
        myOrderListener = new MyOnUpdateListener() {
            @Override
            public void update() throws SQLException {
                reloadOrderView();
            }
        };
    }
    private void reloadOrderView() throws SQLException{
        OrderManager.reloadData();
        orderView.setItems(FXCollections.observableArrayList(OrderManager.getRecentOrder()));
    }
    private void reloadData() throws SQLException{
        BookManager.reloadData();
        displayList = FXCollections.observableArrayList(BookManager.getAllBooks());
        initializeFilter(displayList);
    }
    private void initializeFilter(ObservableList<Book> list){
        FilteredList<Book> filteredList = new FilteredList<>(list, b -> true);
        txt_Search.textProperty().addListener((Observable, oldVal, newVal) -> {
            filteredList.setPredicate(book ->{
                if(newVal == null || newVal.isBlank() || newVal.isEmpty())
                    return true;
                String searchKeyword = newVal.toLowerCase().trim();
                switch (cbox_SearchOption.getSelectionModel().getSelectedItem()) {
                    case "ID":
                        if (book.getId().toLowerCase().contains(searchKeyword))
                            return true;
                        break;
                    case "Title":
                        if (book.getTitle().toLowerCase().contains(searchKeyword))
                            return true;
                        break;
                    case "Author":
                        if (book.getAuthor().toLowerCase().contains(searchKeyword))
                            return true;
                        break;
                    case "Year":
                        if (Integer.toString(book.getYear()).contains(searchKeyword))
                            return true;
                        break;
                    default:
                        break;
                }
                return false;
            });
        });
        SortedList<Book> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }
    /*
    * Event
    */
    private static Stage modifier;
    private static BookModifierController updateController;
    @FXML
    public void clickItem(MouseEvent event) throws IOException{
        if (event.getClickCount() == 2){
            Book book = tableView.getSelectionModel().getSelectedItem();   
            if (book == null)
                return;
            if(modifier == null){
                modifier = new Stage();
                modifier.setResizable(false);
                modifier.setTitle("Book Modifier");
                updateController = (BookModifierController)MyScene.openChildScene(modifier,"scene/admin/BookModifier");
                updateController.setListener(myListener);
                modifier.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            }
            updateController.setData(book);
            modifier.show();
            modifier.toFront();
        }
    }
    private static Stage bookAdder;
    private static AddBookController adderController;
    @FXML
    private void onBtnAddBookSelect(ActionEvent event) throws IOException{
        try {
            if(bookAdder == null){
                bookAdder = new Stage();
                bookAdder.setResizable(false);
                bookAdder.setTitle("Book Adder");
                adderController = (AddBookController)MyScene.openChildScene(bookAdder,"scene/admin/AddBook");
                adderController.setListener(myListener);
                bookAdder.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            }
            bookAdder.show();
            bookAdder.toFront();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            orderDetailViewController.setListener(myOrderListener);
            orderStage.show();
            orderStage.toFront();
        }
    }
    private static Stage orderHisStage;
    @FXML
    private void toHistory(ActionEvent event) throws IOException{  
        if(orderHisStage == null){
            orderHisStage = new Stage();
            orderHisStage.setResizable(false);
            orderHisStage.setTitle("Order History");
            orderHisStage.setScene(new Scene(MyScene.loadFXML("scene/admin/OrderHistory")));
            orderHisStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        }
        orderHisStage.show();
        orderHisStage.toFront();
    }
    private static Stage statisticStage;
    @FXML
    private void toStatistic(ActionEvent event) throws IOException{  
        if(statisticStage == null){
            statisticStage = new Stage();
            statisticStage.setResizable(false);
            statisticStage.setTitle("Library Statistic");
            statisticStage.setScene(new Scene(MyScene.loadFXML("scene/admin/Statistic")));
            statisticStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        }
        statisticStage.show();
        statisticStage.toFront();
    }
}
