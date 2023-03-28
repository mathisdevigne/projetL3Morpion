package fr.IooGoZ.GomokolClient.interfaces;

/**
 * @author IooGoZ - Tom BOIREAU
 * Représente la validation faite par un propriétaire de partie.
 */
public enum Validation {
	CAVOK(0), ENDGAME(1), CHEATING(2), DRAW(3);

	private final int value;

	private Validation(int value) {
		this.value = value;
	}

	/**
	 * @return La valeur entière de la validation.
	 * Retourne la valeur entière de la validation.
	 */
	public int getValue() {
		return this.value;
	}
}
