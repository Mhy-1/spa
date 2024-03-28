package com.tazerdev.spa;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class Websites extends Pane {
    private final GridPane gridPane = new GridPane();
    private final WebView webView = new WebView();
    private final WebEngine webViewEn = webView.getEngine();
    private ImageButton google;
    private ImageButton youtube;
    private ImageButton odus;
    private ImageButton blackboard;
    private File googlef = new File(getClass().getResource("SPA/Icons/google.png").toURI());
    private File youtubef = new File(getClass().getResource("SPA/Icons/youtube.png").toURI());
    private File odusf = new File(getClass().getResource("SPA/Icons/odus.png").toURI());
    private File blackboardf = new File(getClass().getResource("SPA/Icons/blackboard.png").toURI());

    /**
     * construct the page and button
     * @throws URISyntaxException
     */
    public Websites() throws URISyntaxException {
        Image image = new Image(googlef.toURI().toString());
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        google = new ImageButton("","https://www.google.com/",imageView);
        image = new Image(youtubef.toURI().toString());
        imageView = new ImageView();
        imageView.setImage(image);
        youtube = new ImageButton("", "https://www.youtube.com/",imageView);
        image = new Image(odusf.toURI().toString());
        imageView = new ImageView();
        imageView.setImage(image);
        odus = new ImageButton("","https://ssb.uj.edu.sa/banprod/twbkwbis.P_WWWLogin",imageView);
        image = new Image(blackboardf.toURI().toString());
        imageView = new ImageView();
        imageView.setImage(image);
        blackboard = new ImageButton("","https://lms.uj.edu.sa/",imageView);
        setHeight(753);
        setWidth(1605);
        CustomText text = new CustomText("Websites", 50, "#2145d3");
        text.setLayoutX((getWidth() - text.getLayoutBounds().getWidth())/2);
        getChildren().add(text);
        webViewEn.load("https://www.google.com/");
        gridPane.setGridLinesVisible(false);
        final int numCols = 4 ;
        final int numRows = 1 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            gridPane.getRowConstraints().add(rowConst);
        }
        gridPane.setMinHeight(getHeight() * 0.75);
        gridPane.setMinWidth(getWidth() *0.7);
        webView.setMinHeight(getHeight() * 0.75);
        webView.setMinWidth(getWidth() * 0.7);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(google,0,0);
        gridPane.add(youtube,1,0);
        gridPane.add(odus,2,0);
        gridPane.add(blackboard,3,0);
        ButtonInit();
        webView.setTranslateX(-(getWidth() * 0.7)/2);
        webView.setTranslateY(-(getHeight() * 0.75)/2);
        webView.setLayoutX(getWidth()/2);
        webView.setLayoutY(getHeight()/2);
        gridPane.setTranslateX(-(getWidth() *0.7)/2);
        gridPane.setTranslateY(-(getHeight() * 0.75)/2);
        gridPane.setLayoutX(getWidth()/2);
        gridPane.setLayoutY(getHeight()/2);
        getChildren().add(gridPane);
        getChildren().add(webView);
        webView.setVisible(false);
    }

    /**
     * initiates the buttons functionality
     */
    private void ButtonInit(){
        ImageButton[] websites = {google,youtube,odus,blackboard};
        for (ImageButton website: websites) {
            website.setOnMouseClicked(mouseEvent -> {
                gridPane.setVisible(false);
                webView.setVisible(true);
                webViewEn.load(website.replacement);
            });
        }
    }

    /**
     * reset the page to default values
     */
    public void reset(){
        webView.setVisible(false);
        gridPane.setVisible(true);
        webViewEn.load("");
    }
}
