package main.myListener;

import java.sql.SQLException;

public interface MyOnUpdateListener {
    public void update() throws SQLException;
}
