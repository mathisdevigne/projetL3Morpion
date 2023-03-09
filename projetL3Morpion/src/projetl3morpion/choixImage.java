/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author theodusehu
 */
public class choixImage extends BorderPane{
    
    private Data datas;
    
    public choixImage(){
        datas = Data.getInstance();
        
        //======
        //HEADER
        //======
        
        VBox header = new VBox();
        Label titre = new Label("Choix Image");
        titre.setStyle("-fx-font-size: 40px;");
        
        header.getChildren().addAll(titre);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20,20,20,20));
        
        this.setTop(header);
        
        //====
        //View
        //====
        
        ScrollPane pane = new ScrollPane();
        
        FlowPane view = new FlowPane();
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(5, 5, 5, 5));
        view.setHgap(5);
        view.setVgap(5);
        
        Image stickMan = new Image(getClass().getResourceAsStream("Images/image.png"));
        ImageView iconMan = new ImageView(stickMan);
        Image beautifulYves = new Image(getClass().getResourceAsStream("Images/yb3.png"));
        ImageView iconYves = new ImageView(beautifulYves);
        
        Image beautifulYves2 = new Image(getClass().getResourceAsStream("Images/yb4.png"));
        ImageView iconYves2 = new ImageView(beautifulYves);

        iconMan.setFitHeight(50);
        iconMan.setFitWidth(50);
        iconYves.setFitHeight(50);
        iconYves.setFitWidth(50);
        iconYves2.setFitHeight(50);
        iconYves2.setFitWidth(50);

        Button stick = new Button("",iconMan);
        stick.setOnAction(e->
        {
            datas.setLink("Images/image.png");
            Jeu root = null;
            try {
                root = new Jeu();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene other = new Scene(root);
            datas.getStage().setTitle("Morpion");
            datas.getStage().setScene(other);
        });
        
        Button yves1 = new Button("Yves 3",iconYves);
        yves1.setOnAction(e->
        {
            datas.setLink("Images/yb3.png");
            Jeu root = null;
            try {
                root = new Jeu();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene other = new Scene(root);
            datas.getStage().setTitle("Morpion");
            datas.getStage().setScene(other);
        });
        
        Button yves2 = new Button("Yves 4",iconYves2);
        yves2.setOnAction(e->
        {
            datas.setLink("Images/yb4.png");
            Jeu root = null;
            try {
                root = new Jeu();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene other = new Scene(root);
            datas.getStage().setTitle("Morpion");
            datas.getStage().setScene(other);
        });

        view.getChildren().addAll(stick,yves1, yves2);
        
        
        pane.setContent(view);
        this.setCenter(pane);
    }
    
}
