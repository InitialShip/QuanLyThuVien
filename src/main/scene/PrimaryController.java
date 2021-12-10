package main.scene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.entity.Book;
import main.mySqlConnector.Connector;
import main.service.BookService;

public class PrimaryController {
    List<Book> books = new ArrayList<>();
    BookService bookService = new BookService();
    @FXML
    private void sayHello() throws SQLException{
        books = bookService.getBooks();
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

    private String s;
    @FXML private ImageView imageView;

    @FXML
    private void doBrowse(){
        JFileChooser fileChooser = new JFileChooser();
         fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
         FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
         fileChooser.addChoosableFileFilter(filter);
         int result = fileChooser.showSaveDialog(null);
         if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            imageView.setImage(new Image(path));
            s = path;
            System.out.println("got file");
             }
        else if(result == JFileChooser.CANCEL_OPTION){
            System.out.println("No Data");
        }
    }

    @FXML 
    private void doUpload() throws SQLException, FileNotFoundException{
        Connector.open();

        try {
            PreparedStatement ps = Connector.getCnt().prepareStatement("INSERT INTO test_table(image) VALUES (?);");
            InputStream is = new FileInputStream(new File(s));
            ps.setBlob(1, is);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("cannot ");
        }
        System.out.println("no error");
        Connector.close();
    }
    
    
}
