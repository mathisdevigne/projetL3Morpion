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
public class Menu extends BorderPane{
    
    private Button begin;
    private SliderBetter heightSlider;
    private SliderBetter widthSlider;
    private Data datas;
    private ToggleGroup tg;
    
    
    public Menu(){
        
        datas = Data.getInstance();
        
        //======
        //HEADER
        //======
        
        VBox header = new VBox();
        Label titre = new Label("Menu");
        titre.setStyle("-fx-font-size: 40px;");
        
        header.getChildren().addAll(titre);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20,20,20,20));
        
        //=====================
        //SLIDERS + RADIOBUTTON
        //=====================
        
        VBox infos = new VBox();
        heightSlider = new SliderBetter("Height", 10, 20, 10);
        widthSlider = new SliderBetter("Width", 10, 20, 10);
        
        HBox rb = new HBox();
        Label whoBegin = new Label("Qui commence : ");
        this.tg = new ToggleGroup();
        RadioButton joueur = new RadioButton("Joueur");
        RadioButton ia = new RadioButton("IA");
        joueur.setToggleGroup(tg);
        ia.setToggleGroup(tg);
        tg.selectToggle(joueur);
        rb.setSpacing(5);
        rb.setAlignment(Pos.CENTER);
        rb.getChildren().addAll(whoBegin,joueur, ia);
        
        infos.getChildren().addAll(heightSlider, widthSlider, rb);
        
        //======
        //FOOTER
        //======
        
        HBox footer = new HBox();
        
        begin = new Button("Commencer");
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10, 0, 10, 0));
        footer.getChildren().add(begin);
        
        begin.addEventHandler(ActionEvent.ACTION, e-> 
        {
            datas.setHeight(heightSlider.getValSlider());
            datas.setWidth(widthSlider.getValSlider());
            
            if(tg.getSelectedToggle().equals(joueur)){
                datas.setIsHumanBegin(true);
            }
            else{
                datas.setIsHumanBegin(false);
            }
            
        });
        
        //=====
        //OTHER
        //=====
        
        this.setTop(header);
        this.setCenter(infos);
        this.setBottom(footer);
        
        
        
    }
    
    public Button getButton(){
        return begin;
    }
    
    public double getHeightValue(){
        return heightSlider.getValSlider();
    }
    
    public double getWidthValue(){
        return widthSlider.getValSlider();
    }
    
    
    
}
