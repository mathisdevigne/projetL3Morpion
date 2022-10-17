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
     */
    public static void main(String[] args) throws IOException{
        Game monJeu = new Game();
        
        monJeu.getGameboard().print();
        
        monJeu.getGameboard().putVal("1", 0, 0);
        monJeu.getGameboard().putVal("2", 1, 0);
        monJeu.getGameboard().putVal("3", 2, 0);
        monJeu.getGameboard().putVal("4", 1, 1);
        monJeu.getGameboard().putVal("5", 2, 2);
        monJeu.getGameboard().putVal("6", 0, 1);
        
        monJeu.getGameboard().print();
        Box[][] myQuintuplets = monJeu.getGameboard().getQuintuplets(0,0);
        
        for(int i = 0; i < monJeu.getGameboard().getNbQuintuplets(0,0) ; i++){
            for(int j = 0; j < 5; j++){
                System.out.println(i + " " +j + " "+ myQuintuplets[i][j].getValue());
                
            }
        }
    }
    
}
