package com.tazerdev.spa;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class CustomButton extends Button {
    public String replacement = "";

    /**
     * create a custom button without replacement text
     * @param text
     */
    public CustomButton(String text){
        setFont(Font.font("Segoe UI Semibold", 25));
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHalignment(this, HPos.CENTER);
        GridPane.setValignment(this, VPos.CENTER);
        this.replacement = text;
        setText(text);
    }
    /**
     * create a custom button with replacement text
     * @param text
     */
    public CustomButton(String text, String replacement){
        setFont(Font.font("Segoe UI Semibold", 25));
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHalignment(this, HPos.CENTER);
        GridPane.setValignment(this, VPos.CENTER);
        this.replacement = replacement;
        setText(text);
    }
}
