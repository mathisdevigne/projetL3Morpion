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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author theodusehu
 */
public class chooseYourBoard extends ScrollPane{
    
    private Data datas;
    
    public chooseYourBoard(){
        
        datas = Data.getInstance();
        
        ShapeBoard board = new ShapeBoard((int)datas.getHeight(), (int)datas.getWidth());
        
        //======
        //HEADER
        //======
        
        VBox header = new VBox();
        Label titre = new Label("Choix Plateau");
        titre.setStyle("-fx-font-size: 40px;");
        
        header.getChildren().addAll(titre);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20,20,20,20));
        
        //RESTE
        
        VBox jeu = new VBox();
        Button envoie = new Button("Terminer");
        jeu.getChildren().add(board);
        
        board.setOnMouseClicked(g->
        {
            
            if(board.getVal((int)(g.getY()/board.getSize()),(int)(g.getX()/board.getSize())) == 50){
                board.putVal(0, (int)(g.getY()/board.getSize()),(int)(g.getX()/board.getSize()));
                board.print();
            }
            else{
                board.putVal(50, (int)(g.getY()/board.getSize()),(int)(g.getX()/board.getSize()));
                board.print(); 
            }

        });
        
        HBox footer = new HBox();
        
        envoie.setOnAction(e->
        {
            datas.setShapeBoard(board);
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
        
        footer.getChildren().add(envoie);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10, 0, 0, 10));
        
        BorderPane root = new BorderPane();
        
        root.setTop(header);
        root.setCenter(jeu);
        root.setBottom(footer);
        
        this.setPadding(new Insets(0, 10, 10, 10));
        this.setContent(root);
    }
    
}
