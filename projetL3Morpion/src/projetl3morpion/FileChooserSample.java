package projetl3morpion;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import menus.MenuChoixImage;
 
public final class FileChooserSample extends Button {
 
    private Desktop desktop = Desktop.getDesktop();
    
    public FileChooserSample(final Stage stage) {
        stage.setTitle("File Chooser Sample");
 
        final FileChooser fileChooser = new FileChooser();
        
        this.setText("Open a Picture...");
 
        this.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        copyFile(file);
                        MenuChoixImage menuImage = new MenuChoixImage();
                        Scene choixImage = new Scene(menuImage);
                        stage.setTitle("Choix Image");
                        stage.setScene(choixImage);
                    }
                }
            });

    }
  
    private void copyFile(File file) {
        try {
            Files.copy(file.toPath(), Paths.get("./src/Images/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file.toPath(), Paths.get("./build/classes/Images/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(
                FileChooserSample.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
}