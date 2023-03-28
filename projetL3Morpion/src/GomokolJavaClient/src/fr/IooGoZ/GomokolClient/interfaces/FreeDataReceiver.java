package fr.IooGoZ.GomokolClient.interfaces;

/**
 * @author IooGoZ - Tom BOIREAU
 * Interface permettant de receptionnée des données libres
 */
public interface FreeDataReceiver {

	/**
	 * Fonction appelée lors de la reception de données libres.
	 * @param data Données libres réceptionnées.
	 */
	public void receiveFreeData(int[] data);
}
