package projetl3morpion.tournament;

import board.Box;
import board.GameBoard;
import fr.IooGoZ.GomokolClient.interfaces.GameOwner;
import fr.IooGoZ.GomokolClient.interfaces.Validation;
import projetl3morpion.Data;
import projetl3morpion.Game;


//Ce GameOwner est donnée à titre d'exemple
//Il a été en partie générée par ChatGPT et Github Copilot
//Il n'est pas testé et est probablement instable
public class MasterOwnerExt3 implements GameOwner {

	private static final int EMPTY = -1;
	
	private int width, height;
	private int[][] board;
        
        Game jeu;
        Data data = Data.getInstance();
        
        private int scoreJ1 = 0;
        private int scoreJ2 = 0;
	
	public MasterOwnerExt3(int width, int height, Game jeu) {
            
                this.jeu = jeu;
                data.setExtension3(true);
		
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
                
                this.jeu.insertValue(player_id == 1, x, y);
                this.jeu.updateWeight(x, y);
                this.jeu.updateBoard();
                //System.out.println("ADDSTROKE");
                //this.print();
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
                System.out.print(this.board[i][j]);
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
            
                //System.out.println("CheckEnd");
		
		if (this.hasWin(x, y, player_id)){
			//Cas de victoire du joueur courant
                        //System.out.println("END");
                }
                if (!this.canPlay(player_id) ){
                    System.out.println("Score J1 : " + this.scoreJ1 + " Score J2 : " + this.scoreJ2);

                    //Cas de fin par partie nulle
                    if(this.scoreJ1 > this.scoreJ2){
                        System.out.println("Victoire joueur 1.");
                        return Validation.ENDGAME;
                    }
                    else if(this.scoreJ2 > this.scoreJ1){
                        System.out.println("Victoire joueur 2.");
                        return Validation.ENDGAME;
                    }
                    else{
                        System.out.println("DRAW");
                        return Validation.DRAW;
                    }
                        
                }
		
            //Cas classique, la partie continue normalement
            return Validation.CAVOK;
	}
        
        public boolean canPlay(int player_id){
        boolean verif = false;
        int cptJ = 0;
        int cptIA = 0;
        
            for(int i = 0; i < this.jeu.getGameboard().getBoardHeight(); i++){
                for(int j = 0 ; j < this.jeu.getGameboard().getBoardWidth() ; j++){
                    if(this.jeu.getGameboard().getVal(i, j) == 0 && this.jeu.getGameboard().getBoxWeight(i, j) > 0){
                        Box[][] quintuplets = this.jeu.getGameboard().getQuintuplets(i, j);
                        for(int k = 0; k < this.jeu.getGameboard().getNbQuintuplets(i, j) ; k++ ){
                            int note = GameBoard.noteQuintu(quintuplets[k]) + (player_id == 1 ? 1 : 6);
                            verif = verif || ((note >= 0 && note <= 4) || note%6 == 0);
                            if(note == 0){
                                cptJ++;
                                cptIA++;
                            }
                            else if ((note >= 1 && note <= 4)){
                                cptJ++;
                            }
                            else if(note%6 == 0){
                                cptIA++;
                            }
                        }
                    }
                }
            }
            //System.out.println("Joueur : " + cptJ + " IA : " + cptIA);
            return verif;
        }
	
        //Regarde si au moins un quintuplet gagnant se situe dans la case x y, si oui renvoie true sinon false
        public boolean hasWin(int x, int y, int player_id){
            boolean hasWin = false;

            int noteQuintuplet;
            Box[][] quintuplets = this.jeu.getGameboard().getQuintuplets(x, y);
            for(int i = 0; i < this.jeu.getGameboard().getNbQuintuplets(x, y) ; i++ ){
                noteQuintuplet = GameBoard.noteQuintu(quintuplets[i]) + (player_id == 1 ? 1 : 6);

                //System.out.println(noteQuintuplet);

                if(noteQuintuplet == 5){
                    //System.out.println("Vous avez gagné un point");
                    for(int j = 0; j < 5; j++){
                        quintuplets[i][j].setUsed(Game.usedValue);
                    }
                    //this.jeu.updateBoard();
                    Game.usedValue++;
                    hasWin = true;
                    this.scoreJ1++;
                }
                else if(noteQuintuplet == 30){
                    //System.out.println("Votre adversaire à gagné un point");
                    for(int j = 0; j < 5; j++){
                        quintuplets[i][j].setUsed(Game.usedValue);
                    }
                    //this.jeu.updateBoard();
                    Game.usedValue++;
                    hasWin = true;
                    this.scoreJ2++;
                }
            }

            return hasWin;
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
            int lastX = 0, lastY = 0;
            int[] tabX = new int[5];
            int[] tabY = new int[5];
	    
	    // Vérification des lignes horizontales
	    for (dx = -4; dx <= 0; dx++) {
	        if (x + dx < 0 || x + dx + 4 >= width) {
	            continue;
	        }
	        count = 0;
	        for (int i = 0; i < 5; i++) {
	            if (board[x + dx + i][y] == player_id) {
                        tabX[count] = x + dx + i;
                        tabY[count] = y;
	                count++;
                        lastX = x + dx + i;
                        lastY = y;
	            }
	        }
	        if (count == 5) {
	            if(player_id == 1){
                        this.scoreJ1+=1;
                        System.out.println("X : " + lastX + " Y : " + lastY + "H");
                        for(int i = 0; i < 5; i++){
                            System.out.print("X" + i + " : " + tabX[i]);
                        }
                        System.out.println();
                        for(int i = 0; i < 5; i++){
                            System.out.print("Y" + i + " : " + tabY[i]);
                        }
                        System.out.println();
                    }
                    else{
                        this.scoreJ2+=1;
                        System.out.println("X : " + lastX + " Y : " + lastY + "H");  
                        for(int i = 0; i < 5; i++){
                            System.out.print("X" + i + " : " + tabX[i]);
                        }
                        System.out.println();
                        for(int i = 0; i < 5; i++){
                            System.out.print("Y" + i + " : " + tabY[i]);
                        }
                        System.out.println();
                    }
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
                        tabX[count] = x;
                        tabY[count] = y + dy + i;
	                count++;
                        lastX = x;
                        lastY = y + dy + i;
	            }
	        }
	        if (count == 5) {
	            if(player_id == 1){
                        this.scoreJ1+=1;
                        System.out.println("X : " + lastX + " Y : " + lastY + "V");
                        for(int i = 0; i < 5; i++){
                            System.out.print("X" + i + " : " + tabX[i]);
                        }
                        System.out.println();
                        for(int i = 0; i < 5; i++){
                            System.out.print("Y" + i + " : " + tabY[i]);
                        }
                        System.out.println();
                    }
                    else{
                        this.scoreJ2+=1;
                        System.out.println("X : " + lastX + " Y : " + lastY + "V");
                        for(int i = 0; i < 5; i++){
                            System.out.print("X" + i + " : " + tabX[i]);
                        }
                        System.out.println();
                        for(int i = 0; i < 5; i++){
                            System.out.print("Y" + i + " : " + tabY[i]);
                        }
                        System.out.println();
                    }
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
                            tabX[count] = x + dx + i;
                            tabY[count] = y + dy + i;
	                    count++;
                            lastX = x + dx + i;
                            lastY = y + dy + i;
	                }
	            }
	            if (count == 5) {
	                if(player_id == 1){
                            this.scoreJ1+=1;
                            System.out.println("X : " + lastX + " Y : " + lastY + "D");
                            for(int i = 0; i < 5; i++){
                            System.out.print("X" + i + " : " + tabX[i] + " ");
                        }
                        System.out.println();
                        for(int i = 0; i < 5; i++){
                            System.out.print("Y" + i + " : " + tabY[i] + " ");
                        }
                        System.out.println();
                        }
                        else{
                            this.scoreJ2+=1;
                            System.out.println("X : " + lastX + " Y : " + lastY + "D");
                            for(int i = 0; i < 5; i++){
                            System.out.print("X" + i + " : " + tabX[i] + " ");
                        }
                        System.out.println();
                        for(int i = 0; i < 5; i++){
                            System.out.print("Y" + i + " : " + tabY[i] + " ");
                        }
                        System.out.println();
                        }
	            }
	        }
	    }
	    // Aucune ligne de 5 n'a été trouvée
	    return false;
	}



}
