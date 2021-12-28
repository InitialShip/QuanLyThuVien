package main.myListener;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextArea;
import javafx.beans.value.ObservableValue;

/**
 * Text Area restrict input
 * @category Change Directly
 */
public class MyTextAreaChangeListener implements ChangeListener<String>{
    private final TextArea textArea;
    public MyTextAreaChangeListener(TextArea newTextField, int maxLength, String regex){
        this.textArea = newTextField;
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
            textArea.setText(s1.substring(0, maxLength));
            ignore = false;
        }
        if (restrict != null && !restrict.equals("") && !s1.matches(restrict)) {
            ignore = true;
            textArea.setText(s);
            ignore = false;
        }
    }
}
