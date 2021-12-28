package main.scene.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.entity.Book;
import main.manager.BookManager;
import main.manager.CategoryManager;
import main.myListener.MyOnUpdateListener;
import main.utility.MyScene;

public class AdminInterfaceController implements Initializable {
    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, String> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, String> categoryColumn;
    @FXML private TableColumn<Book, String> disabledColumn;

    ObservableList<Book> displayList;
    private MyOnUpdateListener myListener;

    @FXML private ComboBox<String> cbox_SearchOption;
    @FXML private TextField txt_Search;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbox_SearchOption.setItems(FXCollections.observableArrayList("ID","Title", "Author", "Year"));
        cbox_SearchOption.getSelectionModel().selectFirst();
        try {
            BookManager.getInstance();
            BookManager.loadData();
            CategoryManager.getInstance();
            CategoryManager.loadData();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        displayList = FXCollections.observableList(BookManager.getBooks());
        
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

        loadTable();

        //Initialize filter
        FilteredList<Book> filteredList = new FilteredList<>(displayList, b -> true);
        txt_Search.textProperty().addListener((Observable, oldVal, newVal) -> {
            filteredList.setPredicate(book ->{
                if( newVal == null || newVal.isBlank() || newVal.isEmpty())
                    return true;
                
                String searchKeyword = newVal.toLowerCase();
                
                switch (cbox_SearchOption.getSelectionModel().getSelectedItem()) {
                    case "ID":
                        if(book.getId().toLowerCase().contains(searchKeyword))
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
        SortedList<Book> sortedList = new SortedList<> (filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(filteredList);
        
        myListener = new MyOnUpdateListener() {
            @Override
            public void update() throws SQLException {
                reloadData();      
            }
        };

    }
    //
    private void loadTable(){
        tableView.setItems(displayList);
    }
    private void reloadData() throws SQLException{
        BookManager.reloadData();
        displayList = FXCollections.observableList(BookManager.getBooks());
        loadTable();
    }
    /*
    * Event
    */
    private static Stage modifier;
    private static BookModifierController updateController;
    @FXML
    public void clickItem(MouseEvent event) throws IOException
    {
        if (event.getClickCount() == 2) //Checking double click
        {
            Book book = tableView.getSelectionModel().getSelectedItem();   
            if (book == null)
                return;
            if(modifier == null)
            {
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
            if(bookAdder == null)
            {
                bookAdder = new Stage();
                bookAdder.setResizable(false);
                bookAdder.setTitle("Book Modifier");
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
}
