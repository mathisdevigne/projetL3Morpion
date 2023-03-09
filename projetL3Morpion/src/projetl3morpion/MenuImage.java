/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author theodusehu
 */
public class MenuImage extends BorderPane{
    
    private Data datas;
    private Button begin;
    private ToggleGroup tg;
    
    public MenuImage(){
        
        datas = Data.getInstance();
        
        //======
        //HEADER
        //======
        
        VBox header = new VBox();
        Label titre = new Label("Menu Image");
        titre.setStyle("-fx-font-size: 40px;");
        
        header.getChildren().addAll(titre);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20,20,20,20));
        
        this.setTop(header);
        
        //============
        //RADIO BUTTON
        //============
        
        VBox rb = new VBox();
        Label whoBegin = new Label("Mode de jeu : ");
        this.tg = new ToggleGroup();
        RadioButton img = new RadioButton("Image");
        RadioButton choice = new RadioButton("Choisir");
        img.setToggleGroup(tg);
        choice.setToggleGroup(tg);
        tg.selectToggle(img);
        rb.setSpacing(5);
        rb.setAlignment(Pos.CENTER);
        rb.getChildren().addAll(whoBegin, img, choice);
        
        this.setCenter(rb);
        
        //======
        //FOOTER
        //======
        
        HBox footer = new HBox();
        
        begin = new Button("Commencer");
        Button menu = new Button("Menu");
        menu.setOnAction(e->
        {
            datas.getStage().setTitle("Menu");
            datas.getStage().setScene(datas.getScene());
        });
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10, 0, 10, 0));
        footer.getChildren().addAll(begin, menu);
        footer.setSpacing(5);
                
        this.setBottom(footer);
        
        begin.addEventHandler(ActionEvent.ACTION, e-> 
        {
            if(tg.getSelectedToggle().equals(img)){
                datas.setPlayStyle(0);
            }else {
                datas.setPlayStyle(1);
            }
        });
    }
    
    public Button getButton(){
        return begin;
    }
    
}
