/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author theodusehu
 */
public class Data {
    
    private final static Data INSTANCE = new Data();
    
    private double height;
    private double width;
    private boolean isHumanBegin;
    private Scene menu;
    private Stage stage;
    private boolean extension3;
    
    private Data(){
        height = 0;
        width = 0;
    }
    
    public static Data getInstance(){
        return INSTANCE;
    }
    
    public void setHeight(double height){
        this.height = height;
    }
    
    public void setWidth(double width){
        this.width = width;
    }
    
    public void setIsHumanBegin(boolean isHumanBegin){
        this.isHumanBegin = isHumanBegin;
    }
    
    public void setScene(Scene scene){
        this.menu = scene;
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public void setExtension3(boolean extension3){
        this.extension3 = extension3;
    }
    
    public double getHeight(){
        return height;
    }
    
    public double getWidth(){
        return width;
    }
    
    public boolean getIsHumanBegin(){
        return isHumanBegin;
    }
    
    public Scene getScene(){
        return menu;
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public boolean getExtension3(){
        return extension3;
    }
}
