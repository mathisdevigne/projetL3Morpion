package fr.IooGoZ.GomokolClient.client;

import fr.IooGoZ.GomokolClient.DontUseOutsideAPI;
import fr.IooGoZ.GomokolClient.GamesManager;

/**
 * @author IooGoZ - Tom BOIREAU
 * Ne pas utiliser.
 */
@DontUseOutsideAPI
public enum Orders {
	// ClientOrders (C2S)
	C_INIT_GAME(0),
	C_START_GAME(5),
	C_EMIT_STROKE(6),
	C_ANSWER_VALIDATION(7),
	C_REGISTER_PLAYER(8),
	C_SUBSCRIBE_GROUP(12),
	C_INIT_GROUP(14),
	C_FREE_DATA(16),

	// ServerOrder (S2C)
	S_REQUEST_STROKE(1),
	S_SEND_STROKE(2),
	S_REQUEST_VALIDATION(3),
	S_GAME_CREATED(4),
	S_PLAYER_REGISTERED(9),
	S_ERROR_REQUEST(10),
	S_END_GAME(11),
	S_NEW_GROUPGAME(12),
	S_GROUP_REGISTERED(15),
	S_FREE_DATA(17);

	private final int id;

	private Orders(int id) {
		this.id = id;
	}

	/**
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public int getId() {
		return this.id;
	}

	/**
	 * @param order
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public static int[] clientInitGame(int group, int order) {
		if (GamesManager.DEBUG) 
			System.out.println("[Orders] - Init Game (0) : group=" + group + " order=" + order);
		return new int[] { C_INIT_GAME.getId(), group, order };
	}

	/**
	 * @param gameId
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public static int[] clientStartGame(int gameId) {
		if (GamesManager.DEBUG) 
			System.out.println("[Orders] - Start Game (5) : game_id=" + gameId);
		return new int[] { C_START_GAME.getId(), gameId };
	}

	/**
	 * @param gameId
	 * @param playerId
	 * @param stroke
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public static int[] clientEmitStroke(int gameId, int playerId, int[] stroke) {
		if (GamesManager.DEBUG) {
			System.out.print("[Orders] - Emit Stroke (6) : game_id=" + gameId + ", player_id=" + playerId + ", stroke=");
			for (int i : stroke) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
		
		int[] msg = new int[4 + stroke.length];
		msg[0] = C_EMIT_STROKE.getId();
		msg[1] = gameId;
		msg[2] = playerId;
		msg[3] = stroke.length;
		for (int i = 0; i < stroke.length; i++)
			msg[4 + i] = stroke[i];
		return msg;
	}

	/**
	 * @param gameId
	 * @param validation
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public static int[] clientAnswerValidation(int gameId, int validation) {
		if (GamesManager.DEBUG) 
			System.out.println("[Orders] - Answer Validation (7) : game_id=" + gameId + ", validation=" + validation);
		return new int[] { C_ANSWER_VALIDATION.getId(), gameId, validation };
	}

	/**
	 * @param gameId
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public static int[] clientRegisterPlayer(int gameId) {
		if (GamesManager.DEBUG) 
			System.out.println("[Orders] - Register Player (8) : game_id=" + gameId);
		return new int[] { C_REGISTER_PLAYER.getId(), gameId };
	}
	
	@DontUseOutsideAPI
	public static int[] clientSubscribeGroup(int gameId) {
		if (GamesManager.DEBUG) 
			System.out.println("[Orders] - Subscribe Group (12) : game_id=" + gameId);
		return new int[] { C_SUBSCRIBE_GROUP.getId(), gameId };
	}
	
	@DontUseOutsideAPI
	public static int[] clientInitGroup(int nb_player_per_game, int nb_game) {
		if (GamesManager.DEBUG) 
			System.out.println("[Orders] - Init Group () : nb_player_per_game=" + nb_player_per_game + ", nb_game=" + nb_game);
		return new int[] { C_INIT_GROUP.getId(), nb_player_per_game, nb_game};
	}
	
	
	@DontUseOutsideAPI
	public static int[] clientFreeData(int game_id, int[] data) {
		if (GamesManager.DEBUG) {
			System.out.print("[Orders] - Free Data () : game_id=" + game_id + ", data=");
			for (int i : data) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
		
		int[] msg = new int[3 + data.length];
		msg[0] = C_FREE_DATA.getId();
		msg[1] = game_id;
		msg[2] = data.length;
		for (int i = 0; i < data.length; i++)
			msg[2 + i] = data[i];
		return msg;
		
	}

}
