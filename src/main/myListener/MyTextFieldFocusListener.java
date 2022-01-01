package main.myListener;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * perform an action after leaving text field
 */
public class MyTextFieldFocusListener implements ChangeListener<Boolean>{
    private final MyActionListener action;
    public MyTextFieldFocusListener(MyActionListener newAction) {
        this.action = newAction;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2){
        if (!arg2){
            action.performAction();
        }
    }
    
}
