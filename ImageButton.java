package com.tazerdev.spa;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ImageButton extends VBox {
    public String replacement = "";

    /**
     * create a button with an image icon
     * @param text
     * @param replacement
     * @param icon
     */
    public ImageButton(String text, String replacement, ImageView icon){
        icon.setFitHeight(234);
        icon.setFitWidth(191);
        icon.setPreserveRatio(true);
        Text txt = new Text(text);
        txt.setFont(Font.font("Segoe UI Semibold", 25));
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHalignment(this, HPos.CENTER);
        GridPane.setValignment(this, VPos.CENTER);
        this.replacement = replacement;
        getChildren().add(icon);
        getChildren().add(txt);
    }
}
