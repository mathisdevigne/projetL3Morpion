/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projetl3morpion;

import java.io.IOException;
import static projetl3morpion.Game.getIntInput;

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
        Game game;
        int choix = getIntInput("Voulez vous jouez normalement(0), avec un dessin (bonhomme : 1, logo univ : 2), ou choisir les cases a enlever (3) :");
        switch (choix) {
            case 0:
                game = new Game();
                break;
            case 1:
                game = new Game("image.png");
                break;
            case 2:
                game = new Game("logo.png");
                break;
            case 3:
                game = new Game(true);
                break;
            default:
                throw new AssertionError();
        }
        game.play();
    }
}
