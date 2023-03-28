package fr.IooGoZ.GomokolClient.interfaces;

/**
 * @author IooGoZ - Tom BOIREAU
 * Classe abstraite représentant un groupe de partie
 */
public abstract class Group {

	private final int order; 
	
	/**
	 * Constructeur de la classe abstraite
	 * @param order Ordre des parties jouées
	 */
	public Group(int order) {
		this.order = order;
	}
	
	/**
	 * Récupère l'ordre des parties jouées
	 * @return l'ordre des parties jouées
	 */
	public int getOrder() {
		return order;
	}
	
	/**
	 * Fonction à définir, elle permet l'inscription à une partie et l'enregistrement des joueurs.
	 * @param game_id Id de la partie à enregistrer.
	 */
	public abstract void autoGameSubscriber(int game_id);
	
}
