package fr.IooGoZ.GomokolClient.interfaces;

/**
 * @author IooGoZ - Tom BOIREAU
 * Représente un plateau de jeu.
 */
public interface Board {
	
	/**
	 * @param player_id Joueur courant réalisant le coup
	 * @param stroke Coup joué par le joueur
	 * Ajoute un coup au plateau de jeu
	 * 
	 */
	public void addStrokeToBoard(int player_id, int[] stroke);
	
}
