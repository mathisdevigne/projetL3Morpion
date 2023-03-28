package projetl3morpion.tournament;

import java.util.Random;

import fr.IooGoZ.GomokolClient.interfaces.Player;
import projetl3morpion.Data;
import projetl3morpion.Game;

public class VPlayer implements Player {

	private final ExamplePlayer main;
        
        Data datas = Data.getInstance();
        Game monJeu = datas.getGame();
	
	public VPlayer(ExamplePlayer main) {
		this.main = main;
	}
	
	//Dans notre cas, on récupère le plateau via un Board dans ExamplePlayer
	@Override
	public void receiveNewStroke(int player_id, int[] stroke) {
		//On ne fait donc rien ici
		return;
	}
	
	
	//Méthode appelée lors de la demande d'un coup
	@Override
	public int[] getStroke() {
		//On déclare notre tableau
		int position[] = new int[2];
		
		/*//On initialise le générateur de nombres aléatoires
		Random rand = new Random(System.currentTimeMillis());
		
		//On génère un coup aléatoire (pour l'exemple)
		do {
			position[0] = rand.nextInt(0, ExamplePlayer.BOARD_SIZE);
			position[1] = rand.nextInt(0, ExamplePlayer.BOARD_SIZE);
		} while (main.board_array[position[0]][position[1]] != ExamplePlayer.EMPTY);*/
                                
                position = monJeu.bestBox();
                monJeu.iaTurn();
                monJeu.print();
                
		//On retourne le coup
		return position;
	}
	 
	//Gestion de l'id (cette partie ne devrait pas changé)
	private int id = -1;
	
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int getId() {
		return this.id;
	}

}
