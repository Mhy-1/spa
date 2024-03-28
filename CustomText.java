package com.tazerdev.spa;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CustomText extends Text {
    /**
     * creates the text with the app's format
     * @param text
     * @param size
     * @param color
     */
    public CustomText(String text,int size, String color){
        this.setText(text);
        this.setFont(Font.font("Segoe UI Semibold", size));
        this.setFill(Color.rgb(Integer.valueOf(color.substring(1,3),16),Integer.valueOf(color.substring(3,5),16),Integer.valueOf(color.substring(5,7),16)));
    }
}
