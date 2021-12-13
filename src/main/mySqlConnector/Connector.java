package main.mySqlConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Connection cnt;
	private static final String HOST = "localhost";
	private static final String PORT = "3306";
	private static final String DBNAME = "library_db";
    private static final String USER = "main";
    private static final String PASSWORD = "123456789";
    private final static String URL = String.format("jdbc:mysql://%s:%s/%s", Connector.HOST, Connector.PORT, Connector.DBNAME);
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static Connection getCnt() {
        return Connector.cnt;
    }
    public static void open() throws SQLException{
        Connector.cnt = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void close() throws SQLException{
        Connector.cnt.close();
    }
}
