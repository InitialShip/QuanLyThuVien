package main.scene.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.entity.Book;
import main.manager.BookManager;
import main.manager.CategoryManager;
import main.utility.MyScene;

public class AdminInterfaceController implements Initializable {
    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, String> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> yeetColumn;

    ObservableList<Book> displayList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.getScene();
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
        yeetColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("publisher"));

        tableView.setItems(displayList);
    }
    private static Stage secondStage;
    private static BookModifierController controller;
    @FXML
    public void clickItem(MouseEvent event) throws IOException
    {
        if (event.getClickCount() == 2) //Checking double click
        {
            Book book = tableView.getSelectionModel().getSelectedItem();   
            if(secondStage == null)
            {
                secondStage = new Stage();
                secondStage.setResizable(false);
                secondStage.setTitle("Book Modifier");
                controller = (BookModifierController)MyScene.openChildScene(secondStage, "scene/admin/BookModifier");
                
                secondStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            }
            controller.setData(book);

            secondStage.show();
            secondStage.toFront();
        }
    }
}
