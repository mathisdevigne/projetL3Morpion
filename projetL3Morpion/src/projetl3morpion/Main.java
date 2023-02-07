/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projetl3morpion;

import java.io.IOException;

/*
 *
 * @author fetiveau
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        //Game monJeu = new Game();
        //monJeu.play();
        Game game = new Game("image.png");
        game.play();
    }
}
