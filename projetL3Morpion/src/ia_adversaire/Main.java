package ia_adversaire;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import fr.IooGoZ.GomokolClient.Game;
import fr.IooGoZ.GomokolClient.GamesManager;
import fr.IooGoZ.GomokolClient.interfaces.Board;
import fr.IooGoZ.GomokolClient.interfaces.Group;

public class Main {
	
	public static int BOARD_SIZE = 20;
	public static int ORDER = 2;
	public static int EMPTY = -1;
	public static int numPlayer = 1;
	public static char symbole = 'x';
	
	private static Symbol s = new Symbol(numPlayer, symbole);
	
	private static Plateau p = new Plateau(BOARD_SIZE, BOARD_SIZE);
		
	//On définit nos joueurs
	private static Computer player = new Computer(p,symbole);
	
	private static Board board = new Board() {
		
		//Méthode appelée lors de la réception d'un coup joué
		@Override
		public void addStrokeToBoard(int player_id, int[] stroke) {
			
			//On récupère les coordonnées du coup joué
			int x = stroke[0];
			int y = stroke[1];
			
			//On les place sur le plateau
			p.getCase(x, y).setStatut(player_id);
		}
		
	};
	
	//Instance de board
		private static Group group = new Group(ORDER) {
			
			//Méthode appelée lors de la création d'une partie dans le groupe inscrit
			@Override
			public void autoGameSubscriber(int game_id) {
				try {
					//On (ré)initialise le plateau de jeu
					for(Case c : p.getCasesPlateau()) {
						c.setStatut(0);
					}
					for(Quintuplet q : p.getQuintupletTT()) {
						q.reinit();
					}
					
					//On enregistre la partie
					Game game = GamesManager.MANAGER.registerNewGame(game_id, this.getOrder());
					
					//On enregistre les fonctions de réception des coups joués
					game.registerNewBoard(board);
					
					//On enregistre les joueurs
					game.registerNewPlayer(player);
					
				} catch (Exception e) {
					//Gestion des exceptions
					e.printStackTrace();
				}
			}
		};
		
		public static void ExamplePlayer() throws UnknownHostException, IOException, Exception {
			//On définit l'adresse du serveur
			String address = "127.0.0.1";
			
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
		
	public static void main(String[] args) {
		try {
			ExamplePlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
