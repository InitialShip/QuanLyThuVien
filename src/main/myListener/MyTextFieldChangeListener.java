package main.myListener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class MyTextFieldChangeListener implements ChangeListener<String>{
    private final TextField textField;
    public MyTextFieldChangeListener(TextField newTextField, int maxLength, String regex){
        this.textField = newTextField;
        this.maxLength = maxLength;
        this.restrict = regex;
    }
    private boolean ignore;
    private int maxLength;
    private String restrict;
    @Override
    public void changed(ObservableValue<? extends String> arg0, String s, String s1) {
        if (ignore || s1 == null)
                    return;
        if (s1.length() > maxLength) {
            ignore = true;
            textField.setText(s1.substring(0, maxLength));
            ignore = false;
        }
        if (restrict != null && !restrict.equals("") && !s1.matches(restrict)) {
            ignore = true;
            textField.setText(s);
            ignore = false;
        }
    }
}
