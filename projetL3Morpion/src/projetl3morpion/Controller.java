/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import menus.MenuChooseYourBoard;
import menus.MenuChoixImage;
import menus.MenuForme;
import menus.Menu;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author theodusehu
 */
public class Controller extends Application{
    
    private Data datas;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        datas = Data.getInstance();
    
        Menu menu = new Menu();
        Scene scene = new Scene(menu);
        datas.setScene(scene);
        datas.setStage(primaryStage);
        menu.getButton().addEventHandler(ActionEvent.ACTION, e->
        {
            if(!datas.getExtension4()){
                Jeu root = null;
                try {
                    root = new Jeu();
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Scene other = new Scene(root);
                primaryStage.setTitle("Morpion");
                //primaryStage.setScene(other);
            }
            else{
                MenuForme newMenu = new MenuForme();
                Scene otherBis = new Scene(newMenu);
                primaryStage.setTitle("Menu Forme");
                primaryStage.setScene(otherBis);
                newMenu.getButton().addEventHandler(ActionEvent.ACTION, f->
                {
                    switch(datas.getPlayStyle()){
                        case 0 -> {
                            MenuChoixImage menuImage = new MenuChoixImage();
                            Scene choixImage = new Scene(menuImage);
                            primaryStage.setTitle("Choix Image");
                            primaryStage.setScene(choixImage);
                        }
                        case 1 -> {
                            MenuChooseYourBoard menuBoard = new MenuChooseYourBoard();
                            Scene choixImage = new Scene(menuBoard);
                            primaryStage.setTitle("Choix Plateau");
                            primaryStage.setScene(choixImage);
                        }

                    }
                });
            }
            
                        
        });
        
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        // On ouvre la fenetre. 
                
        primaryStage.show();
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
}
