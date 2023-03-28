package fr.IooGoZ.GomokolClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.IooGoZ.GomokolClient.client.Client;
import fr.IooGoZ.GomokolClient.client.Orders;
import fr.IooGoZ.GomokolClient.interfaces.Board;
import fr.IooGoZ.GomokolClient.interfaces.FreeDataReceiver;
import fr.IooGoZ.GomokolClient.interfaces.Player;



/**
 * @author IooGoZ - Tom BOIREAU
 * Représente une partie de Gomoku
 */
public class Game {

	private final HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private final int order;
	private final int id;
	private final GamesManager manager;

	private int player_id = Client.DEFAULT_VALUE;
	private final HashMap<int[], Integer> board = new HashMap<>();
	private final List<Board> boards = new ArrayList<>();
	private final List<FreeDataReceiver> freeDatas = new ArrayList<>();
	
	/**
	 * @param manager
	 * @param id
	 * @param order
	 * 
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public Game(GamesManager manager, int id, int order) {
		this.id = id;
		this.order = order;
		this.manager = manager;
	}

	
	/**
	 * Retourne l'ordre des coordonées utilisées par la parties.
	 * @return L'ordre des coordonées utilisées par la parties.
	 */
	public int getOrder() {
		return this.order;
	}

	/**
	 * Retourne l'identifiant de la parties.
	 * @return L'identifiant de la parties.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Permet d'enregistrer un plateau de jeu
	 * @param board Plateau de jeu à enregistrer
	 */
	public void registerNewBoard(Board board) {
		boards.add(board);
	}
	
	/**
	 * Permet d'enregistrer une instance de données libres.
	 * @param fdr Instance d'une interface de FreeDataReceiver
	 */
	public void registerFreeDataReceiver(FreeDataReceiver fdr) {
		freeDatas.add(fdr);
	}
	
	/**
	 * @param pos Position souhaitée
	 * @return null si la position n'existe pas.
	 * Permet de récuperer une position du plateau.
	 */
	public int getPlayerAtPosition(int[] pos) {
		return board.getOrDefault(pos, null);
	}
	
	/**
	 * @param player Nouveau joueur a enregistrer
	 * @throws Exception Les exceptions sont déclenchées lors de problème de communication avec le serveur. 
	 * Demande l'enregistrement d'un nouveau joueur auprès du serveur. 
	 * 
	 */
	public synchronized void registerNewPlayer(Player player) throws Exception {
		player_id = Client.DEFAULT_VALUE;
		long time = System.currentTimeMillis();
		
		this.manager.getClient("registerNewPlayer").send(Orders.clientRegisterPlayer(id));
		
		while (this.player_id == Client.DEFAULT_VALUE) {
			if (System.currentTimeMillis() - time > Client.TIMEOUT_DURATION)
				throw new Exception("initNewGame : Timeout server");
			Thread.yield();
		}

		player.setId(this.player_id);
		players.put(this.player_id, player);

		player_id = Client.DEFAULT_VALUE;
	}

	
	/**
	 * @param player_id
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public void serverSetPlayerId(int player_id) {
		this.player_id = player_id;
	}

	/**
	 * @param player_id
	 * @throws IOException
	 * @throws Exception
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public void serverRequestPlayerStroke(int player_id) throws IOException, Exception {
		Thread.yield();
		Player player = players.getOrDefault(player_id, null);
		if (player != null) {
			int[] stroke = player.getStroke();
			this.manager.getClient("serverRequestPlayerStroke").send(Orders.clientEmitStroke(id, player_id, stroke));
			return;
		}
		Thread.sleep(100);
		Thread.yield();
		player = players.getOrDefault(player_id, null);
		if (player != null) {
			int[] stroke = player.getStroke();
			this.manager.getClient("serverRequestPlayerStroke").send(Orders.clientEmitStroke(id, player_id, stroke));
			return;
		}
		
		throw new Exception("serverRequestPlayerStroke : server request bad player.");
	}

	/**
	 * @param player_id
	 * @param stroke
	 * @throws Exception
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public void serverSendStroke(int player_id, int[] stroke) throws Exception {
		board.put(stroke, player_id);
		for (int plId : players.keySet()) {
			players.get(plId).receiveNewStroke(player_id, stroke);
		}
		for (Board b : boards) {
			b.addStrokeToBoard(player_id, stroke);
		}
		return;
	}
	
	/**
	 * @param player_id
	 * Ne pas utiliser.
	 */
	@DontUseOutsideAPI
	public void serverEndGame(int player_id) {
		if (player_id == -2) {
			System.out.println("La partie " + id + " est nulle.");
		} else if (player_id == -1) {
			System.out.println("La partie " + id + " s'est terminée sans gagnant.");
		} else {
			System.out.println("La partie " + id + "est terminée : le joueur " + player_id + " a gagné.");
		}
	}


	/**
	 * @param data
	 * @return
	 * Ne pas utiliser.
	 */
	public boolean serverFreeData(int[] data) {
		for (FreeDataReceiver fdr : freeDatas)
			fdr.receiveFreeData(data);
		return true;
	}

}
