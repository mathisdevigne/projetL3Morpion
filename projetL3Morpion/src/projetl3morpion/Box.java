/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 *
 * @author fetiveau
 */
public class Box extends Label {
    private int id;
    private int value; //1 pour le joueur et 6 pour l'adversaire (IA)
    private int weight;
    private boolean last;
    private int[] used;
    
    private BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderWidths.DEFAULT); 
    
    public Box(int id){
        this.id = id;
        this.value = 0;
        this.weight = 0;
        this.last = false;
        this.used = new int[8];
        this.initId();
        this.setBorder(new Border(bs));
        this.setMinSize(40, 40);
        this.setMaxSize(40, 40);
        this.setText("");
        this.setAlignment(Pos.CENTER);
    }
    
    public void resetBox(){
        this.value = 0;
        this.weight = 0;
        this.last = false;
        this.used = new int[8];
        this.initId();
    }
    
    private void initId(){
        for(int i = 0; i < 8; i++){
           this.used[i] = -1; 
        }
    }
    
    //Accesseur de value
    public int getValue(){
        return this.value;
    }
    
    //Accesseur de weight
    public int getWeight(){
        return this.weight;
    }
    
    //Accesseur de used
    public boolean isUsed(int[] ids){
        for(int i = 0; i < this.used.length; i++){
            for(int j = 0; j < ids.length; j++){
                if(this.used[i] == ids[j] && this.used[i] != -1)
                    return true;
            }  
        }
        return false;
    }
    
    public boolean isUsed(){
        for(int i = 0; i < this.used.length; i++){
            if(this.used[i] != -1)
                return true;
        }
        return false;
    }
    
    public int nbUsed(){
        int compteur = 0;
        for(int i = 0; i < this.used.length; i++){
            if(this.used[i] != -1)
                compteur++;
        }
        return compteur;
    }
    
    public int[] getUsed(){
        return this.used;
    }
    
    //Accesseur de id
    public int getBoxId(){
        return this.id;
    }
    
    //Setter de last
    public void setLast(boolean newBool){
        this.last = newBool;
    }
    
    //Setter de value
    public void setValue(int newVal){
        this.value = newVal;
    }
    
    //Setter de weight
    public void setWeight(int newWeight){
        this.weight = newWeight;
    }
    
    //Setter de used
    public void setUsed(int v){
        for(int i = 0; i < this.used.length; i++){
            if(this.used[i] == -1){
                this.used[i] = v;
                break;
            }
        }
    }
    
    //Affiche la value de box
    public void print(){
        
        if(this.last){
            System.out.print(ConsoleColors.GREEN);
            this.setStyle("-fx-text-fill: lime; -fx-font-size: 20px;");
            this.last = false;
        }
        else{
            this.setStyle("-fx-text-fill: black; -fx-font-size: 20px;");
        }
           
        System.out.print(this.weight);
        
        switch (this.value) {
            // Case vide
            case 0 -> 
            {
                System.out.print(" ");
                this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            //Case Joueur
            case 1 -> 
            {
                //System.out.print("O");
                this.setText("O");
                this.quintuColor(true);
            }
            //Case IA
            case 6 -> 
            {
                //System.out.print("X" + ConsoleColors.RESET);
                this.setText("X");
                this.quintuColor(false);
            }
            case 50 -> {
                System.out.print(ConsoleColors.RED+"X"+ConsoleColors.RESET);
                this.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");
                this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                //this.setText("X");
            }
            default -> System.out.print("N");
        }
    }
    
    public void quintuColor(boolean isHuman){
        
        int red = 255;
        int blue = 255;
        
        if(isHuman){
            if(this.nbUsed() != 0){
               this.setBackground(new Background(new BackgroundFill(Color.rgb(red-((red/8)*this.nbUsed()), 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
                this.setStyle("-fx-text-fill: white; -fx-font-size: 20px;"); 
            }
            
        }
        else{
            if(this.nbUsed() != 0){
               this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, blue-((blue/8)*this.nbUsed())), CornerRadii.EMPTY, Insets.EMPTY)));
                this.setStyle("-fx-text-fill: white; -fx-font-size: 20px;"); 
            }
        }
        
        
    }
}
