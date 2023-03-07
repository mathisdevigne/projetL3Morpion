/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

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
            
            Jeu root = null;
            try {
                root = new Jeu();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene other = new Scene(root);
            primaryStage.setTitle("Morpion");
            primaryStage.setScene(other);
                        
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
