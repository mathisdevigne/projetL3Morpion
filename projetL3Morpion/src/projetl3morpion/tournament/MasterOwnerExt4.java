package projetl3morpion.tournament;

import fr.IooGoZ.GomokolClient.interfaces.GameOwner;
import fr.IooGoZ.GomokolClient.interfaces.Validation;
import projetl3morpion.Game;


//Ce GameOwner est donnée à titre d'exemple
//Il a été en partie générée par ChatGPT et Github Copilot
//Il n'est pas testé et est probablement instable
public class MasterOwnerExt4 implements GameOwner {

	private static final int EMPTY = -1;
        private static final int BLOCKED = -2;
	
	private int width, height;
	private int[][] board;
        
        Game jeu;
	
	public MasterOwnerExt4(int width, int height, Game jeu) {
            
                this.jeu = jeu;
                this.jeu.updateAllWeight();
		
		//Initialisation du plateau
		this.board = new int[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++){
                            
                            if(this.jeu.getGameboard().getVal(j, i) == 50){
                                this.board[i][j] = BLOCKED;
                            }
                            else{
                                this.board[i][j] = EMPTY;
                            }
                            
                        }
		
		//Définition des attributs
		this.width = width;
		this.height = height;
                
                /*System.out.println("Width : " + this.width + " Height : " + this.height);
                System.out.println("1 47 : " + this.jeu.getGameboard().getBoxBoard(1, 47).getValue());
                System.out.println("47 1 : " + this.board[47][1]);*/
                
                //====================================
                //On a une inversion, 1 47 dans Jeu est ouvert alors que 1 47 dans Board est fermé
                //====================================

                //this.print();
                //this.print();
                this.jeu.print();
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
                this.jeu.insertValue(player_id == 1, x, y);
                this.jeu.updateWeight(x, y);
                this.jeu.updateBoard();
                this.print();
	}
        
       // Affichage du jeu (plateau) besoin d'ajustement
    public void print(){
        System.out.print("  ");
        for(int k = 0; k < this.width; k++){
            if(k <= 9){
                System.out.print(" ");
            }         
            System.out.print( " " + k + " ");
        }
        System.out.println();
        System.out.print("  +");
        for(int k = 0; k < this.width; k++){
            System.out.print( "---+");     
        }
        
        System.out.println();
        for(int i = 0; i < this.height; i++){
            System.out.print(i);
            if(i <= 9){
                System.out.print(" ");
            }
            System.out.print("| ");
            for(int j = 0; j < this.width; j++){
                System.out.print(this.board[j][i]);
                System.out.print(" | ");
            }
            System.out.println();
            System.out.print("  +");
            for(int k = 0; k < this.width; k++){
                System.out.print( "---+");     
            }
            System.out.println();
        }
    }

	
	//Fonction vérifiant la fin d'une partie (non-testée)
	private Validation checkEnd(int player_id, int x, int y) {
		if (checkBoardFilling(x, y)){
			//Cas de fin par partie nulle
                        //System.out.println("DRAW");
			return Validation.DRAW;}
		else if (checkPlayerEnd(player_id, x, y)){
			//Cas de victoire du joueur courant
                        //System.out.println("END"); 
			return Validation.ENDGAME;}
		
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
