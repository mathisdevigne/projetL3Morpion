/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menus;

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
import layouts.Layout;
import projetl3morpion.Data;

/**
 *
 * @author theodusehu
 */
public class MenuForme extends BorderPane{
    
    private Data datas;
    private Button begin;
    private ToggleGroup tg;
    
    public MenuForme(){
        
        datas = Data.getInstance();
        
        //======
        //HEADER
        //======
        
        VBox header = Layout.createHeader("Menu Forme");
        
        this.setTop(header);
        
        //============
        //RADIO BUTTON
        //============
        
        VBox rb = new VBox();
        Label whoBegin = new Label("Mode de jeu : ");
        this.tg = new ToggleGroup();
        RadioButton img = new RadioButton("Image");
        Layout.bindPopUp(img, "Permet de générer un plateau à partir d'un image.");
        RadioButton choice = new RadioButton("Choisir");
        Layout.bindPopUp(choice, "Permet de définir sois même la forme du plateau.");
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
            //datas.getStage().setTitle("Menu");
            //datas.getStage().setScene(datas.getScene());
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
