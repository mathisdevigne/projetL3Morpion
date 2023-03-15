/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menus;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import layouts.Layout;
import projetl3morpion.Data;
import projetl3morpion.FileChooserSample;

/**
 *
 * @author theodusehu
 */
public class MenuChoixImage extends BorderPane{
    
    private Data datas;
    private final String PATH = "../Images/";
    
    public MenuChoixImage(){
        datas = Data.getInstance();
        
        //======
        //HEADER
        //======
        
        VBox header = new VBox();
        VBox title = Layout.createHeader("Choix Image");
        Layout.bindPopUp(title, "Pour rajouter une image, traiter la et mettez la dans le dossier Images");
        Button fileChooser = new FileChooserSample(datas.getStage());
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(title, fileChooser);
        header.setPadding(new Insets(0, 0, 10, 0));
        this.setTop(header);
        
        //====
        //View
        //====
        
        ScrollPane pane = new ScrollPane();
        
        FlowPane view = new FlowPane();
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(5, 5, 5, 5));
        view.setHgap(5);
        view.setVgap(5);
        
        //Creating a File object for directory
        File directoryPath = null;
        try {
            directoryPath = new File(getClass().getResource(PATH).toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(MenuChoixImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Creating filter for jpg files
        FilenameFilter jpgFilefilter = (File dir, String name) -> {
            String lowercaseName = name.toLowerCase();
            return lowercaseName.endsWith(".png");
        };        
          String imageFilesList[] = directoryPath.list(jpgFilefilter);
          System.out.println("List of the jpeg files in the specified directory:");  
          for(String fileName : imageFilesList) {
             System.out.println(fileName);
             view.getChildren().add(Layout.createIconButton(PATH+fileName));
        }
          
        pane.setContent(view);
        this.setCenter(pane);
    }
    
}
