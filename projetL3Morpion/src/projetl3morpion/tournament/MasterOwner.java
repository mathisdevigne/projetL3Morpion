package projetl3morpion.tournament;

import fr.IooGoZ.GomokolClient.interfaces.GameOwner;
import fr.IooGoZ.GomokolClient.interfaces.Validation;


//Ce GameOwner est donnée à titre d'exemple
//Il a été en partie générée par ChatGPT et Github Copilot
//Il n'est pas testé et est probablement instable
public class MasterOwner implements GameOwner {

	private static final int EMPTY = -1;
	
	private int width, height;
	private int[][] board;
	
	public MasterOwner(int width, int height) {
		
		//Initialisation du plateau
		this.board = new int[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				this.board[i][j] = EMPTY;
		
		//Définition des attributs
		this.width = width;
		this.height = height;
	}
	
	//Méthode appelée pour récupérer la validation de l'owner
	@Override
	public Validation getValidation(int player_id, int[] stroke) {
		
		//On récupère les coordonnées
		int x = stroke[0];
		int y = stroke[1];
		
		//On vérifie la validité du coup
		if (x >= 0 && y >= 0 && x < width && y < height) {
			if (this.board[x][y] == EMPTY) {
				return checkEnd(player_id, x, y);
			}
		}
		
		//Cas de Triche ou de jeu incorrect
		return Validation.CHEATING;
	}
	
	
	//Méthode appelée pour ajouter un coup au plateau
	@Override
	public void addStrokeToBoard(int player_id, int[] stroke) {
		int x = stroke[0];
		int y = stroke[1];
		
		this.board[x][y] = player_id;
	}

	
	//Fonction vérifiant la fin d'une partie (non-testée)
	private Validation checkEnd(int player_id, int x, int y) {
		if (checkBoardFilling(x, y))
			//Cas de fin par partie nulle
			return Validation.DRAW;
		else if (checkPlayerEnd(player_id, x, y))
			//Cas de victoire du joueur courant
			return Validation.ENDGAME;
		
			//Cas classique, la partie continue normalement
		return Validation.CAVOK;
	}
	
	
	//Fonctions générées et non-testées---------------------------------------------------------------------
	private boolean checkBoardFilling(int x, int y) {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) 
				if (!(i == x && j == y) && this.board[i][j] == EMPTY)
					return false;
		return true;
	}
	
	public boolean checkPlayerEnd(int player_id, int x, int y) {
	    int dx, dy, count;
	    
	    // Vérification des lignes horizontales
	    for (dx = -4; dx <= 0; dx++) {
	        if (x + dx < 0 || x + dx + 4 >= width) {
	            continue;
	        }
	        count = 0;
	        for (int i = 0; i < 5; i++) {
	            if (board[x + dx + i][y] == player_id) {
	                count++;
	            }
	        }
	        if (count == 5) {
	            return true;
	        }
	    }
	    // Vérification des lignes verticales
	    for (dy = -4; dy <= 0; dy++) {
	        if (y + dy < 0 || y + dy + 4 >= height) {
	            continue;
	        }
	        count = 0;
	        for (int i = 0; i < 5; i++) {
	            if (board[x][y + dy + i] == player_id) {
	                count++;
	            }
	        }
	        if (count == 5) {
	            return true;
	        }
	    }
	    // Vérification des diagonales
	    for (dx = -4; dx <= 0; dx++) {
	        for (dy = -4; dy <= 0; dy++) {
	            if (x + dx < 0 || x + dx + 4 >= width || y + dy < 0 || y + dy + 4 >= height) {
	                continue;
	            }
	            count = 0;
	            for (int i = 0; i < 5; i++) {
	                if (board[x + dx + i][y + dy + i] == player_id) {
	                    count++;
	                }
	            }
	            if (count == 5) {
	                return true;
	            }
	        }
	    }
	    // Aucune ligne de 5 n'a été trouvée
	    return false;
	}



}
