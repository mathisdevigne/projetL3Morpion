package fr.IooGoZ.GomokolClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import fr.IooGoZ.GomokolClient.client.Client;
import fr.IooGoZ.GomokolClient.client.Orders;
import fr.IooGoZ.GomokolClient.interfaces.GameOwner;
import fr.IooGoZ.GomokolClient.interfaces.Group;
import fr.IooGoZ.GomokolClient.interfaces.Validation;

/**
 * @author IooGoZ - Tom BOIREAU
 *	Gestionnaire des parties du client.
 */
public class GamesManager {

	
	/**
	 * Unique instance du gestionnaire. 
	 */
	public static final GamesManager MANAGER = new GamesManager();
	
	/**
	 * Si activer, affiche les informations de debug.
	 */
	public static final boolean DEBUG = false;

	private Client client;

	private int game_id;

	private HashMap<Integer, Game> id2game;
	private HashMap<Integer, GameOwner> id2owner;
	private HashMap<Integer, Group> id2group;
	
	private int current_group_id;

	private GamesManager() {
		game_id = Client.DEFAULT_VALUE;
		current_group_id = Client.DEFAULT_VALUE;
		id2game = new HashMap<>();
		id2owner = new HashMap<>();
		id2group = new HashMap<>();
	}

	/**
	 * @param adress Addresse de connexion du serveur
	 * @param port Port de connexion du serveur
	 * @throws UnknownHostException Déclenché si l'adresse ou le port sont éronnés.
	 * @throws IOException Déclenché si le lien avec le serveur a échoué.
	 * @throws Exception Déclenché si un serveur est déjà connecté
	 * Permet de se connecter a un serveur
	 * 
	 * Attention : cette méthode ne doit-être appelée qu'une fois
	 * 
	 */
	public void connect(String adress, int port) throws UnknownHostException, IOException, Exception {
		if (this.client == null) {
			this.client = new Client(adress, port, this);
			Thread t = new Thread(client);
			t.start();
		} else
			throw new Exception("GameManager : Client is already connected.");
	}

	/**
	 * @param function
	 * @return
	 * @throws Exception
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public Client getClient(String function) throws Exception {
		if (this.client != null)
			return this.client;
		throw new Exception(function + " : Client is not connected.");
	}
	

	/**
	 * Initialise un groupe de jeu distant et local
	 * @param group Instance du groupe local
	 * @param nb_player_per_game Nombre de joueur attendu pour chaque partie
	 * @param nb_games Nombre de partie à jouer
	 * @return L'id du groupe distant
	 * @throws IOException Erreur d'envoi des informations
	 * @throws Exception Erreur de récupération du client
	 */
	public int initGroup(Group group, int nb_player_per_game, int nb_games) throws IOException, Exception {
		current_group_id = Client.DEFAULT_VALUE;
		long time = System.currentTimeMillis();
		
		this.getClient("initGroup").send(Orders.clientInitGroup(nb_player_per_game, nb_games));
		
		while (this.current_group_id == Client.DEFAULT_VALUE) {
		if (System.currentTimeMillis() - time > Client.TIMEOUT_DURATION)
			throw new Exception("initNewGame : Timeout server");
			Thread.yield();
		}
		
		id2group.put(current_group_id, group);
		int tmp_id = current_group_id;
		
		current_group_id = Client.DEFAULT_VALUE;
		
		return tmp_id;
	}
	
	/**
	 * Permet de se connecter à un groupe distant
	 * @param group_id Id du group distant
	 * @param group Instance du groupe local
	 * @throws IOException Erreur d'envoi des informations
	 * @throws Exception Erreur de récupération du client
	 */
	public void subscribeGroup(int group_id, Group group) throws IOException, Exception {
		this.getClient("subscribeGroup").send(Orders.clientSubscribeGroup(group_id));
		
		id2group.put(group_id, group);
	}
	
	/**
	 * Lie un GameOwner a une partie. Utile pour l'utilisation des groups
	 * @param owner Instance du GameOwner à liée
	 * @param game Instance de la partie à liée
	 */
	public void linkOwnerWithGame(GameOwner owner, Game game) {
		id2owner.put(game.getId(), owner);
	}

	// GameOwner--------------------------------------------------------
	/**
	 * @param owner Définit le propriétaire de la partie
	 * @param order Définit l'ordre des coordonnées souhaité
	 * @return La partie initialiser
	 * @throws Exception Déclenché en cas d'echec de la création
	 * 
	 * Demande la création d'une nouvelle partie au serveur.
	 */
	public synchronized Game initNewGame(GameOwner owner, int group, int order) throws Exception {

		this.game_id = Client.DEFAULT_VALUE;

		long time = System.currentTimeMillis();
		
		this.getClient("initNewGame").send(Orders.clientInitGame(group, order));
		
		while (this.game_id == Client.DEFAULT_VALUE) {
		if (System.currentTimeMillis() - time > Client.TIMEOUT_DURATION)
			throw new Exception("initNewGame : Timeout server");
			Thread.yield();
		}

		Game game = new Game(this, this.game_id, order);

		id2game.put(this.game_id, game);
		id2owner.put(this.game_id, owner);

		this.game_id = Client.DEFAULT_VALUE;

		return game;
	}
	
	/**
	 * @param game_id Identifiant de la partie
	 * @param order Ordre des coordonnées de la parties
	 * @return La partie non vérifiée coté serveur
	 * @throws Exception Déclenché si la partie existe déjà.
	 * 
	 * Permet d'enregistrer manuellement une partie.
	 */
	public synchronized Game registerNewGame(int game_id, int order) throws Exception {
		if (id2game.getOrDefault(game_id, null) == null) {
			Game game = new Game(this, game_id, order);
			id2game.put(game_id, game);
			return game;
		} else throw new Exception("registerNewGame : game already exist.");
	}

	
	/**
	 * @param owner Propriétaire de la partie
	 * @param game_id Identifiant de la partie
	 * @throws Exception Si le propriétaire n'est pas lié à la partie
	 * 
	 * Lance une partie.
	 */
	public synchronized void startGame(GameOwner owner, int game_id) throws Exception {
		if (id2owner.getOrDefault(game_id, null) == owner) {
			this.getClient("startGame").send(Orders.clientStartGame(game_id));
			return;
		}
		throw new Exception("startGame : owner and id must match.");
	}

	// DontUseOutsideAPI---------------------------------------------------

	/**
	 * @param game_id
	 * @param player_id
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public boolean serverPlayerRegistered(int game_id, int player_id) {
		Game game = id2game.getOrDefault(game_id, null);
		if (game != null) {
			game.serverSetPlayerId(player_id);
			return true;
		}
		System.err.println("serverPlayerRegistered : server request bad game.");
		return false;
	}

	/**
	 * @param game_id
	 * @param player_id
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public boolean serverRequestStroke(int game_id, int player_id) {
		Game game = id2game.getOrDefault(game_id, null);
		if (game != null) {
			try {
				game.serverRequestPlayerStroke(player_id);
				return true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return false;
	}

	/**
	 * @param game_id
	 * @param player_id
	 * @param stroke
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public boolean serverRequestValidation(int game_id, int player_id, int[] stroke) {
		GameOwner owner = id2owner.getOrDefault(game_id, null);
		if (owner != null)
			try {
				Validation valid = owner.getValidation(player_id, stroke);
				getClient("serverRequestValidation").send(Orders.clientAnswerValidation(game_id, valid.getValue()));
				return true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		return false;
	}

	/**
	 * @param game_id
	 * @param player_id
	 * @param stroke
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public boolean serverSendStroke(int game_id, int player_id, int[] stroke) {
		Game game = id2game.getOrDefault(game_id, null);
		if (game != null)
			try {
				game.serverSendStroke(player_id, stroke);
				GameOwner owner = id2owner.getOrDefault(game_id, null);
				if (owner != null)
					owner.addStrokeToBoard(player_id, stroke);
				return true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		return false;
	}

	/**
	 * @param game_id
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public void serverSetGameId(int game_id) {
		this.game_id = game_id;
	}
	
	/**
	 * @param group_id
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public void serverSetGroupId(int group_id) {
		this.current_group_id = group_id;
	}
	
	/**
	 * @param game_id
	 * @param player_id
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public boolean serverEndGame(int game_id, int player_id) {
		Game game = id2game.getOrDefault(game_id, null);
		if (game != null)
			try {
				game.serverEndGame(player_id);
				return true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		return false;
	}
	
	/**
	 * @param group_id
	 * @param game_id
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public boolean serverNewGroupGame(int group_id, int game_id) {
		Group group = id2group.getOrDefault(group_id, null);
		if (group != null) {
			
			Thread t = new Thread(() -> {
				group.autoGameSubscriber(game_id);
			});
			t.start();
			
			return true;
		}
		return false;
	}
	
	/**
	 * @param game_id
	 * @param data
	 * @return
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public boolean serverFreeData(int game_id, int[] data) {
		Game game = id2game.getOrDefault(game_id, null);
		if (game != null)
			return game.serverFreeData(data);
		return false;
	}
}
