/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.fabricator.easel_to_laser;

import java.io.File;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;

/**
 *
 * @author jura
 */
public class Main extends Application {
    private static Logger log = Logger.getLogger(Main.class.getName());
    
    Stage window;
    private File myFile;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  {
        final FileChooser fileChooser = new FileChooser();
        
        window = primaryStage;
        window.setTitle("G-code laser cutter translator");
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
                
        //First labell with site link input
        Label nameLabel = new Label("Please change default if it's needed");
        GridPane.setConstraints(nameLabel,0, 0,6,1);
                
        //Second Label with email input
        Label onLaser = new Label("Switch on laser: ");
        GridPane.setConstraints(onLaser, 0, 2,3,1);
        //onLaser.setMinWidth(150);
        TextField onField = new TextField();
        onField.setMaxWidth(100);
        //onField.setAlignment(Pos.BASELINE_LEFT);
        onField.setPromptText("M3 S12000");
        GridPane.setConstraints(onField, 5, 2);
        //Second Label with email input
        Label offLaser = new Label("Switch off laser: ");
        GridPane.setConstraints(offLaser, 0, 3,3,1);
        TextField offField = new TextField();
        offField.setPromptText("M5");
        offField.setMaxWidth(100);
        GridPane.setConstraints(offField, 5, 3);
        
        final Button openButton = new Button("Open");
        GridPane.setConstraints(openButton, 2, 5);
        GridPane.setHalignment(openButton, HPos.RIGHT);
        openButton.setOnAction(e-> {
            configureFileChooser(fileChooser);
            myFile = fileChooser.showOpenDialog(primaryStage);
            if (myFile != null) {
                Gcode.M3.setContent(onField.getText());
                Gcode.M5.setContent(offField.getText());
                Translator.readFile(myFile);
            }
            });

        //Third label with cancel button
        Button cancelButton = new Button("Exit");
        GridPane.setHalignment(cancelButton, HPos.RIGHT);
        GridPane.setConstraints(cancelButton, 5, 5);
                cancelButton.setOnAction(e -> window.close());
        
        grid.getChildren().addAll(onField,onLaser,offField,offLaser, nameLabel,openButton, cancelButton );
        Scene scene = new Scene(grid, 275, 160);
        window.setScene(scene);
        window.show();
    }

    private static void configureFileChooser(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("View G-coded files");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("G-code files", "*.gcode"," *.gc", "*.nc")
                );
    }    

}
