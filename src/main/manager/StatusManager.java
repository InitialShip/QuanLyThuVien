package main.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.entity.Status;
import main.service.StatusService;

public class StatusManager {
    private static List<Status> statusList;
    
    private static StatusManager instance = null;
    private StatusManager(){
        statusList = new ArrayList<>();
    }
    public static StatusManager getInstance(){
        if (instance == null){
            instance = new StatusManager();
        }
        return instance;
    }
    public static List<Status> getStatuses(){
        return statusList;
    }
    public static String getStatusName(int id){
        return statusList.stream().filter(c -> c.getId() == id).findFirst().orElse(null).getStatusName();
    }
    public static Status getStatus(int id){
        return statusList.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }
    public static void addStatus(Status newStatus){
        statusList.add(newStatus);
    }
    public static void loadData() throws SQLException{
        StatusService.getData();
    }
}
