package main.scene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    private void browse(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        ExtensionFilter filter = new ExtensionFilter("IMAGE", "*jpg","*png");
        fileChooser.getExtensionFilters().addAll(filter);

        File file = fileChooser.showOpenDialog(null);
        if( file != null){
            String path = file.getAbsolutePath();
            imageView.setImage(new Image(path));
            s = path;
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
    // public ImageIcon ResizeImage(String imgPath){
    //     ImageIcon MyImage = new ImageIcon(imgPath);
    //     Image img = MyImage.getImage();
    //     Image newImage = img.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
    //     ImageIcon image = new ImageIcon(newImage);
    //     return image;
    // }
    @FXML private ImageView imageView2;
    @FXML 
    private void getImage() throws SQLException, IOException{
        Connector.open();

        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM test_table WHERE id = 4");
        //byte[] image = null;
        InputStream in = null;
        while(rs.next()){
            in = rs.getBinaryStream("image");
        }
        System.out.println(in);
        Image image = new Image(in);
        if (in != null)
            imageView2.setImage(image);
        Connector.close();
    }
}
