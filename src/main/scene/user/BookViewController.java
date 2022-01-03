package main.scene.user;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.entity.Book;
import main.entity.Category;
import main.entity.UserOrder;
import main.manager.AppUserManager;
import main.manager.BookManager;
import main.manager.CategoryManager;
import main.manager.StatusManager;
import main.myListener.MyOnClickListener;
import main.myListener.MyOnOrderListener;
import main.utility.MyScene;
import main.utility.Utils;

public class BookViewController implements Initializable{
    @FXML private VBox layout;
    @FXML private Tab tabRequest;
    @FXML private TextField searchText;
    @FXML private ComboBox<String> searchComboBox;
    @FXML private ComboBox<Category> filterComboBox;
    @FXML private ComboBox<String> orderByComboBox;
    @FXML private ComboBox<String> sortOrderComboBox;
    @FXML private TableView<Book> tv_Borrowing;
    @FXML private TableColumn<Book,String> col_BookId;
    @FXML private TableColumn<Book,String> col_BookName;
    @FXML private TableColumn<Book,String> col_Author;
    @FXML private TableColumn<Book,String> col_Category;
    @FXML private Button btn_Order;
    @FXML private Button btn_ClearOrder;
    @FXML private TableView<UserOrder> tv_OrderHis;
    @FXML private TableColumn<UserOrder, Date> col_OrderDate;
    @FXML private TableColumn<UserOrder, Date> col_ExpireDate;
    @FXML private TableColumn<UserOrder, Date> col_ReturnedDate;
    @FXML private TableColumn<UserOrder, String> col_Status;
    @FXML private TableColumn<UserOrder, Integer> col_Fine;
    @FXML private TableColumn<UserOrder, String> col_BName;

    private MyOnClickListener myListener;
    private MyOnOrderListener myOnOrderListener;
    private static ObservableList<Book> displayList;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!AppUserManager.getUser().getOrder().isEmpty()){
            btn_Order.setDisable(true);
            btn_ClearOrder.setDisable(true);
        }
        myListener = new MyOnClickListener() {
            @Override
            public void onClickListener(Book book, MouseEvent event) throws IOException {
                loadReview(book, event);
            }
        };
        myOnOrderListener = new MyOnOrderListener() {
            @Override
            public void orderClick() {
                tv_Borrowing.setItems(FXCollections.observableArrayList(AppUserManager.getUser().getOrder()));
            }
        };
        col_BookId.setCellValueFactory(new PropertyValueFactory<Book,String>("id"));
        col_BookName.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        col_Author.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        col_Category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> b) {
                if(b.getValue() != null)
                    return new SimpleStringProperty(CategoryManager.getCategoryName(b.getValue().getCategoryId()));
                return null;
            }
        });
        initializeComboBoxes();
        displayList = FXCollections.observableArrayList(applyFilter(BookManager.getBooks()));
        try {
            displayBook();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Utils.getAlertBox("Can not display book", AlertType.ERROR).showAndWait();
            Platform.exit();
        }
        searchText.textProperty().addListener((observal,oldVal,newVal)->{
            displayList = FXCollections.observableArrayList(searchBooks(BookManager.getBooks(),searchText.getText()));
            try {
                displayBook();
            } catch (IOException e1) {
                Utils.getAlertBox("Can not display book", AlertType.ERROR).showAndWait();
                Platform.exit();
            }
        });
        tv_Borrowing.setItems(FXCollections.observableArrayList(AppUserManager.getUser().getOrder()));

        col_OrderDate.setCellValueFactory(new PropertyValueFactory<UserOrder,Date>("orderDate"));
        col_ExpireDate.setCellValueFactory(new PropertyValueFactory<UserOrder,Date>("expireDate"));
        col_ReturnedDate.setCellValueFactory(new PropertyValueFactory<UserOrder,Date>("returnedDate"));
        col_Fine.setCellValueFactory(new PropertyValueFactory<UserOrder,Integer>("fine"));
        col_BName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserOrder,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<UserOrder, String> st) {
                return new SimpleStringProperty(BookManager.getBook(st.getValue().getBookId()).getTitle());
            }
        });
        col_Status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserOrder,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<UserOrder, String> st) {
                return new SimpleStringProperty(StatusManager.getStatusName(st.getValue().getStatusId()));
            }
        });
        tv_OrderHis.setItems(FXCollections.observableArrayList(AppUserManager.getUser().getOrderHistory()));
    }
    @FXML
    private void comboboxSelect() throws IOException{
        layout.getChildren().clear();
        displayList = FXCollections.observableArrayList(searchBooks(BookManager.getBooks(),searchText.getText()));

        displayBook();
    }
    private static Stage reviewStage;
    private static BookReviewController reviewController;
    private void loadReview(Book book, MouseEvent event) throws IOException{
        if(reviewStage == null){
            reviewStage = new Stage();
            reviewStage.setResizable(false);
            reviewStage.setTitle("Book Review");
            reviewController = (BookReviewController)MyScene.openChildScene(reviewStage, "scene/user/BookReview");
            reviewStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        }
        reviewController.setData(book);
        reviewStage.show();
        reviewStage.toFront();
    }
    private void displayBook() throws IOException{
        for (Book book : displayList) {
            FXMLLoader fxmlLoader = MyScene.getFXML("scene/user/BookCard");
            AnchorPane bookCard = fxmlLoader.load();
            BookCardController bookCardController = fxmlLoader.getController();            
            bookCardController.setData(book, myListener,myOnOrderListener);
            layout.getChildren().add(bookCard);
        }
    }
    private void initializeComboBoxes(){
        searchComboBox.setItems(FXCollections.observableArrayList("Title", "Authors","Year"));
        searchComboBox.getSelectionModel().selectFirst();
        
        filterComboBox.getItems().add(new Category(0, "Show All"));
        filterComboBox.getItems().addAll(FXCollections.observableArrayList(CategoryManager.getCategories()));
        filterComboBox.getSelectionModel().selectFirst();

        orderByComboBox.setItems(FXCollections.observableArrayList("Title", "Authors","Year"));
        orderByComboBox.getSelectionModel().selectFirst();

        sortOrderComboBox.setItems(FXCollections.observableArrayList("Ascending","Descending"));
        sortOrderComboBox.getSelectionModel().selectFirst();
    }

    private List<Book> searchBooks(List<Book> books, String keyWord){
        layout.getChildren().clear();
        if(keyWord.isBlank())
            return applyFilter(books);
        
        switch (searchComboBox.getSelectionModel().getSelectedItem()) {
            case "Title":
                return BookManager.findByTitle(keyWord.trim(), applyFilter(books));
            case "Authors":
                return BookManager.findByAuthor(keyWord.trim(), applyFilter(books));
            case "Year":
                return BookManager.findByYear(keyWord.trim(), applyFilter(books));
            default:    
                break;
        }
        return null;
    }
    private List<Book> applyFilter(List<Book> books){
        return sortOrder(orderBy(filterBy(books)));
    }
    private List<Book> filterBy(List<Book> list){
        return BookManager.filter(filterComboBox.getSelectionModel().getSelectedItem().getId());
    }
    private List<Book> orderBy(List<Book> filteredList){
        switch(orderByComboBox.getSelectionModel().getSelectedItem()){
            case "Title" : 
                return BookManager.orderByTitle(filteredList);
            case "Authors" :
                return BookManager.orderByAuthor(filteredList);
            case "Year" :
                return BookManager.orderByYear(filteredList);
        }
        return null;
    }
    private List<Book> sortOrder(List<Book> sortedList){
        switch (sortOrderComboBox.getSelectionModel().getSelectedItem()) {
            case "Ascending":
                return sortedList;
            case "Descending":
                return BookManager.reverse(sortedList);
        }
        return null;
    }
    @FXML
    private void clearOrderClick(){
        AppUserManager.clearOrder();
        tv_Borrowing.setItems(FXCollections.observableArrayList(AppUserManager.getUser().getOrder()));
    }
    @FXML
    private void submitOrderClick() throws SQLException{
        if(AppUserManager.getUser().getOrder().isEmpty())
            return;
        Optional<ButtonType> option = Utils.getAlertBox("Do you want to order?", AlertType.CONFIRMATION).showAndWait();
        if(option.get() == ButtonType.OK){
            try {
                btn_ClearOrder.setDisable(true);
                btn_Order.setDisable(true);
                AppUserManager.submitOrder();
                AppUserManager.getUser().setCanOrder(false);
            } catch (Exception e) {
                btn_ClearOrder.setDisable(false);
                btn_Order.setDisable(false);
                Utils.getAlertBox("Some thing went wrong!", AlertType.ERROR).showAndWait();
                System.out.println(e.getMessage());
            }
        }
    }
}
