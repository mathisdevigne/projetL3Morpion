/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package layouts;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projetl3morpion.Controller;
import projetl3morpion.Data;
import projetl3morpion.Jeu;

/**
 *
 * @author theodusehu
 */
public class Layout {
        
    public static void bindPopUp(Node node, String message){
        Stage helpPopUp = new Stage();
        BorderPane text = new BorderPane();
        text.setPadding(new Insets(10, 10, 10, 10));
        Label l = new Label(message);
        l.setMaxWidth(200);
        l.setWrapText(true);
        text.setCenter(l);
        
        helpPopUp.setScene(new Scene(text));
        helpPopUp.initStyle(StageStyle.UNDECORATED);
        node.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                helpPopUp.show();
            } else {
                helpPopUp.hide();
            }
        });
    }
    
    public static VBox createHeader(String name){
        
        VBox header = new VBox();
        Label titre = new Label(name);
        titre.setStyle("-fx-font-size: 40px;");
        
        header.getChildren().addAll(titre);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20,20,20,20));
        
        return header;
        
    }
    
    public static Button createIconButton(String link){
        
        Image img = new Image(Layout.class.getResourceAsStream(link));
        ImageView icon = new ImageView(img);
        
        icon.setFitHeight(50);
        icon.setFitWidth(50);
        
        Button button = new Button("",icon);
        button.setOnAction(e->
        {
            Data.getInstance().setLink(link);
            Jeu root = null;
            try {
                root = new Jeu();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Scene other = new Scene(root);
            Data.getInstance().getStage().setTitle("Morpion");
            //Data.getInstance().getStage().setScene(other);
        });
        
        return button;
        
    }
    
}
