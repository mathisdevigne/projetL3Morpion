/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tournoi_ext3;

import tournoi_ext3.JoueurExt3;
import fr.IooGoZ.GomokolClient.GamesManager;
import fr.IooGoZ.GomokolClient.interfaces.Group;
import java.util.Scanner;
import projetl3morpion.Game;


/**
 *
 * @author theodusehu
 */
public class MainExt3 {
    
    private static final int ORDER = 2;

    
    //Instance de board
	private static Group group = new Group(ORDER) {
		
            //Méthode appelée lors de la création d'une partie dans le groupe inscrit
            @Override
            public void autoGameSubscriber(int game_id) {
                    try {
                            
                            Game jeu = new Game(20, 20);
                        
                            //On enregistre la partie
                            fr.IooGoZ.GomokolClient.Game game = GamesManager.MANAGER.registerNewGame(game_id, this.getOrder());

                            //On enregistre les joueurs
                            game.registerNewPlayer(new JoueurExt3(jeu));

                    } catch (Exception e) {
                            //Gestion des exceptions
                            e.printStackTrace();
                    }
            }
    };
    
    public static void main(String[] args) throws Exception {
                
        //On définit l'adresse du serveur
        String address = "127.0.0.1"; //127.0.0.1
		
        //On se connecte au serveur
        GamesManager.MANAGER.connect(address, 8080);
        
        //Récupération de l'id du groupe
        System.out.print("Entrez l'id du groupe : ");
        int group_id;
        Scanner sc = new Scanner(System.in);
        group_id = sc.nextInt();
        sc.close();

        //On s'inscrit au groupe
        GamesManager.MANAGER.subscribeGroup(group_id, group);
        
    }
    
}
