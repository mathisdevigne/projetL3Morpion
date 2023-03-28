package fr.IooGoZ.GomokolClient.interfaces;

/**
 * @author IooGoZ - Tom BOIREAU
 * Représente le propriétaire d'une partie.
 * Le propriétaire est en charge de la vérification du bon déroulement des partie. 
 * 
 */
public interface GameOwner {

	
	/**
	 * @param player_id Identifiant du joueur courant
	 * @param stroke Coup joué par ledit joueur
	 * @return La validation du propriétaire
	 * 
	 * Le propriétaire vérifie que :
	 * 	- la partie se déroule bien
	 * 	- la partie se termine
	 * 	- un joueur triche
	 */
	public Validation getValidation(int player_id, int[] stroke);
	
	/**
	 * @param player_id Indentifant du joueur courant réalisant le coup
	 * @param stroke Coup joué par le joueur
	 * Ajoute un coup au plateau de jeu du propriétaire.
	 * 
	 */
	public void addStrokeToBoard(int player_id, int[] stroke);

}
