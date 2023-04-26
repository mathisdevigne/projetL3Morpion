/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package images;

import board.ShapeBoard;
import board.GameBoard;
import board.Box;
import java.util.Arrays; 
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import projetl3morpion.Game;

/**
 *
 * @author theodusehu
 */
public class Main {
    
    public static void main(String[] args) {
        

        /*BufferedImage bi = null;
        try {
            bi = ImageIO.read(Main.class.getResourceAsStream("yb3.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        Game jeu = new Game("../images/yb3.png");
        
        //jeu.saveImageAsTxt(bi);
        
    }
    
}
