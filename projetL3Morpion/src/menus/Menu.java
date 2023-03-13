/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menus;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import layouts.Layout;
import projetl3morpion.Data;
import layouts.SliderBetter;

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
        
        VBox header = Layout.createHeader("Menu");
        
        //=======
        //SLIDERS
        //=======
        
        VBox infos = new VBox();
        heightSlider = new SliderBetter("Height", 10, 50, 10);
        heightSlider.setAlignment(Pos.CENTER);
        Layout.bindPopUp(heightSlider.getLabel(), "Permet de choisir la hauteur du plateau.");
        widthSlider = new SliderBetter("Width", 10, 50, 10);
        widthSlider.setAlignment(Pos.CENTER);
        Layout.bindPopUp(widthSlider.getLabel(), "Permet de choisir la largeur du plateau.");
        
        //===========
        //RADIOBUTTON
        //===========
        
        HBox rb = new HBox();
        Label whoBegin = new Label("Qui commence : ");
        Layout.bindPopUp(whoBegin, "Permet de choisir quel joueur commence la partie.");
        this.tg = new ToggleGroup();
        RadioButton joueur = new RadioButton("Joueur");
        RadioButton ia = new RadioButton("IA");
        joueur.setToggleGroup(tg);
        ia.setToggleGroup(tg);
        tg.selectToggle(joueur);
        rb.setSpacing(5);
        rb.setAlignment(Pos.CENTER);
        rb.getChildren().addAll(whoBegin,joueur, ia);
        
        //========
        //CHECKBOX
        //========
        
        VBox extensions = new VBox();
        
        CheckBox canContinue = new CheckBox("Extension 3");
        Layout.bindPopUp(canContinue, "Extension 3 : Permet de continuer à jouer après que le premier quintuplet soit fait.");
        CheckBox selectForm = new CheckBox("Extension 4");
        Layout.bindPopUp(selectForm, "Extension 4 : Permet de changer la forme du plateau en bloquant des cases.");
        
        extensions.setPadding(new Insets(10, 0, 0, 0));
        extensions.setSpacing(5);
        extensions.setAlignment(Pos.CENTER);
        extensions.getChildren().addAll(new Label("Choix d'Extensions"), canContinue, selectForm);
        
        infos.getChildren().addAll(heightSlider, widthSlider, rb, extensions);
        
        canContinue.setOnAction(e->datas.setExtension3(canContinue.isSelected()));
        selectForm.setOnAction(e->datas.setExtension4(selectForm.isSelected()));
        
        //======
        //FOOTER
        //======
        
        HBox footer = new HBox();
        
        begin = new Button("Commencer");
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10, 0, 10, 0));
        footer.getChildren().addAll(begin);
        
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
