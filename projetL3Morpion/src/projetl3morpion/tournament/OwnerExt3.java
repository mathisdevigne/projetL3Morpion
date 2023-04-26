package projetl3morpion.tournament;

import java.util.Scanner;



import fr.IooGoZ.GomokolClient.Game;
import fr.IooGoZ.GomokolClient.GamesManager;
import fr.IooGoZ.GomokolClient.interfaces.Group;

public class OwnerExt3 {
	
	private static int WIDTH = 20, HEIGHT = 20, NB_PLAYER = 2, NB_GAMES=100, ORDER=2;
	
	//Déclaration du groupe
	private static Group group = new Group(ORDER) {
		
		//Méthode appelée lors de la création d'une partie liée au groupe 
		@Override
		public void autoGameSubscriber(int game_id) {
			try {
				//On enregistre notre partie localement
				Game game = GamesManager.MANAGER.registerNewGame(game_id, ORDER);
                                projetl3morpion.Game jeu = new projetl3morpion.Game(20, 20);
				
				//On instancie l'owner
				MasterOwnerExt3 owner = new MasterOwnerExt3(WIDTH, HEIGHT, jeu);
				
				//On lie l'owner et la partie
				GamesManager.MANAGER.linkOwnerWithGame(owner, game);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public static void main(String[] args) {
		try {
			//Connexion au serveur
			GamesManager.MANAGER.connect("127.0.0.1", 8080);
			
			//Création d'un groupe de jeu
			int group_id = GamesManager.MANAGER.initGroup(group, NB_PLAYER, NB_GAMES);
			
			//On affiche l'id du group pour le transmettre aux joueurs
			System.out.println("Le groupe est créé avec l'id : " + group_id);
			
			//On bloque notre programme pour laisser les joueurs se connecter
			System.out.println("Appuyez sur Entrée pour lancer les parties.");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			sc.close();
			
			//On créer notre première partie
                        projetl3morpion.Game jeu = new projetl3morpion.Game(20, 20);
			MasterOwnerExt3 owner = new MasterOwnerExt3(WIDTH, HEIGHT, jeu);
			GamesManager.MANAGER.initNewGame(owner, group_id, ORDER);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
