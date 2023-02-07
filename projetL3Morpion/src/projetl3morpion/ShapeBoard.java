/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author mathi
 */
public class ShapeBoard extends GameBoard {
    
    
    public ShapeBoard(int height, int width){
        super(height ,width);
        
    }
    
    public void setShape(BufferedImage bi){
        for(int x = 0;x<this.getWidth();x++){
            for(int y = 0;y<this.getHeight();y++){
                int color = bi.getRGB(y, x);

                // extract each color component
                int red   = (color >>> 16) & 0xFF;
                int green = (color >>>  8) & 0xFF;
                int blue  = (color) & 0xFF;

                // calc luminance in range 0.0 to 1.0; using SRGB luminance constants
                float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;

                // choose brightness threshold as appropriate:
                if (luminance <= 0.5f) {
                    // dark color
                    this.getBoxBoard(y, x).setValue(50);
                } else {
                    // bright color
                }
            }
        }
    }
    
    @Override
    public int getNbQuintuplets(int x, int y){
        int nbQuintuplets = 0;
        if(x >= 0 && x < this.getHeight() && y >= 0 && y < this.getWidth()){
            
            for(int i = 0; i > -5; i--){
                //Les 5 quintuplets horizontaux
                //On vérifie que les quintuplets se trouve dans le plateau de jeu, sinon on ne le rajoute pas
                if(x + i >= 0 && x + i + 4 < this.getHeight()){
                    nbQuintuplets++;
                }
                
                 // Les 5 quintuplets verticaux
                if(y + i >= 0 && y + i + 4 < this.getWidth()){
                    nbQuintuplets++;
                }
                    
                for(int j = 0; j > -5; j--){
                    
                    //les diagonales haut-droit / bas-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y - j < this.getWidth() && y - j - 4 >= 0){
                        nbQuintuplets++;
                    }

                    //les diagonales bas-droit / haut-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y + j >= 0 && y + j + 4 < this.getWidth()){
                        nbQuintuplets++;
                            
                        
                    }
                }
            }
        }
        return nbQuintuplets;
    }
   
}
