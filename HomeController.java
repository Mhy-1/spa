package com.tazerdev.spa;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeController{
    private Calculator calc = new Calculator();
    private Websites web = new Websites();
    Stage stage = null;
    User user = null;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView power;
    @FXML
    private ImageView CalculatorShower;
    @FXML
    private ImageView WebsiteShower;
    @FXML
    private ImageView GoHome;
    @FXML
    private Pane mainpanel;
    @FXML
    private Pane Home;
    @FXML
    private Pane Flexable;
    @FXML
    private Text Welcome;

    @FXML
    Label DateTime;
    @FXML
    Label Day;

    /**
     * empty constructor to handle URISyntaxException
     * @throws URISyntaxException
     */
    public HomeController() throws URISyntaxException {
    }

    /**
     * exits the program
     */
    @FXML
    void Quit(){
        stage.close();
    }

    /**
     * minimizes the program
     */
    @FXML
    void Minmize(){
        stage.setIconified(true);
    }

    /**
     * excute the animation at the beginning of the program
     */
    void logoanim(){
        FadeTransition fade = new FadeTransition();
        TranslateTransition translate = new TranslateTransition();
        ScaleTransition scale = new ScaleTransition();
        fade.setNode(logo);
        fade.setDuration(Duration.millis(800));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        translate.setNode(logo);
        translate.setDuration(Duration.millis(1000));
        translate.setToX(-(logo.getLayoutX()+(logo.getFitWidth()/2)) + (stage.getWidth()/2));
        translate.setToY(-(stage.getHeight()/2.5));
        translate.play();
        scale.setNode(logo);
        scale.setDuration(Duration.millis(1000));
        scale.setToX(0.3);
        scale.setToY(0.3);
        scale.play();
        new Thread(() -> {
            try {
                Thread.sleep(800);
                FadeTransition fade1 = new FadeTransition();
                fade1.setNode(mainpanel);
                fade1.setDuration(Duration.millis(1500));
                fade1.setFromValue(0);
                fade1.setToValue(1);
                fade1.play();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    /**
     * initiates the clock
     */
    private void initClock() {
        Timeline date = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, YYYY | hh:mm:ss a");
            DateTime.setText(LocalDateTime.now().format(formatter));
            formatter = DateTimeFormatter.ofPattern("EEEE");
            Day.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        date.setCycleCount(Animation.INDEFINITE);
        date.play();
    }

    /**
     * show the main panel
     */
    private void ShowFlexable() {
        Home.setVisible(false);
        Flexable.setVisible(true);
        GoHome.setVisible(true);
    }

    /**
     * changes and sets the welcome message
     * @param user
     */
    public void setUser(User user){
        Welcome.setText("Welcome Mr." + user.getLName());
        Welcome.setLayoutX((stage.getWidth()/2)-(Welcome.getLayoutBounds().getWidth()/2));
        this.user = user;
    }

    /**
     * initiates the program functionality
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        logo.setFitWidth((stage.getWidth()/1920)*440);
        logo.setFitHeight((stage.getHeight()/1080)*268);
        logo.setLayoutX((stage.getWidth()/2)-(logo.getFitWidth()/2));
        logo.setLayoutY((stage.getHeight()/2)-(logo.getFitHeight()/2));
        mainpanel.setPrefWidth((stage.getWidth()/1920)*1908);
        mainpanel.setPrefHeight((stage.getHeight()/1080)*878);
        mainpanel.setOpacity(0);
        logoanim();
        initClock();
        calc.setVisible(false);
        web.setVisible(false);
        Flexable.getChildren().add(calc);
        Flexable.getChildren().add(web);
        power.setOnMouseClicked(mouseEvent -> {
            Quit();
        });
        CalculatorShower.setOnMouseClicked(mouseEvent -> {
            calc.setVisible(true);
            ShowFlexable();
        });
        WebsiteShower.setOnMouseClicked(mouseEvent -> {
            web.setVisible(true);
            ShowFlexable();
        });
        GoHome.setOnMouseClicked(mouseEvent -> {
            Home.setVisible(true);
            GoHome.setVisible(false);
            Flexable.setVisible(false);
            calc.setVisible(false);
            web.setVisible(false);

            web.reset();
            calc.reset();
        });
    }
}
