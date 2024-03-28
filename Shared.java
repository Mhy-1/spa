package com.tazerdev.spa;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Shared {
    private static double xOffset = 0;
    private static double yOffset = 0;

    /**
     * allow dragability of the login/register page
     * @param stage
     * @param ap
     */
    public static void Draggable(Stage stage, AnchorPane ap){
        ap.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        ap.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
    }
}
