package projetl3morpion.tournament;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import fr.IooGoZ.GomokolClient.Game;
import fr.IooGoZ.GomokolClient.GamesManager;
import fr.IooGoZ.GomokolClient.interfaces.Board;
import fr.IooGoZ.GomokolClient.interfaces.Group;
import projetl3morpion.Data;

public class ExamplePlayer {
	
	public static int BOARD_SIZE = 20;
	public static int ORDER = 2;
	public static int EMPTY = -1;
	
	//Plateau de jeu
	public int board_array[][] = new int[BOARD_SIZE][BOARD_SIZE];
        
        Data datas = Data.getInstance();
        
        projetl3morpion.Game monJeu = datas.getGame();
	
	//On définit nos joueurs
	private VPlayer player1 = new VPlayer(this);
	
	//Instance de board
	private Board board = new Board() {
		
		//Méthode appelée lors de la réception d'un coup joué
		@Override
		public void addStrokeToBoard(int player_id, int[] stroke) {
			
			//On récupère les coordonnées du coup joué
			int x = stroke[0];
			int y = stroke[1];
			
                        monJeu.insertValue(false, x, y);
                        monJeu.print();
                        
			//On les place sur le plateau
			board_array[x][y] = player_id;
		}
		
	};
	
	//Instance de board
	private Group group = new Group(ORDER) {
		
		//Méthode appelée lors de la création d'une partie dans le groupe inscrit
		@Override
		public void autoGameSubscriber(int game_id) {
			try {
				//On (ré)initialise le plateau de jeu
				for (int i = 0; i < BOARD_SIZE; i++)
					for (int j = 0; j < BOARD_SIZE; j++)
						board_array[i][j] = EMPTY;
				
				//On enregistre la partie
				Game game = GamesManager.MANAGER.registerNewGame(game_id, this.getOrder());
				
				//On enregistre les fonctions de réception des coups joués
				game.registerNewBoard(board);
				
				//On enregistre les joueurs
				game.registerNewPlayer(player1);
				
			} catch (Exception e) {
				//Gestion des exceptions
				e.printStackTrace();
			}
		}
	};
	
	public ExamplePlayer() throws UnknownHostException, IOException, Exception {
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
			new ExamplePlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
