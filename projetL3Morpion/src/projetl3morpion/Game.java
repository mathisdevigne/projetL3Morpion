/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays; 
/**
 *
 * @author fetiveau
 */
public class Game {
    public GameBoard gameboard;
    public int score1 = 0;
    public int score2 = 0;
    
    private static int usedValue = 1;
    
    private final static int EMPTY_WEIGHT = 1;
    private final static int ONE_PLAYER = EMPTY_WEIGHT*2;
    private final static int ONE_IA = ONE_PLAYER*2;
    private final static int TWO_PLAYER = ONE_IA*2;
    private final static int TWO_IA = TWO_PLAYER*2;
    private final static int THREE_PLAYER = TWO_IA*5;
    private final static int THREE_IA = THREE_PLAYER*5;
    private final static int FOUR_PLAYER = THREE_IA*10;
    private final static int FOUR_IA = FOUR_PLAYER*10;
    
    
    public Game() throws IOException{
        int height = -1, width = -1;
        
        while(height < 10){
            height = Game.getIntInput("Veuillez rentrez la hauteur du plateau de jeu (minimun 10): ");
        }
        while(width < 10){
            width = Game.getIntInput("Veuillez rentrez la largeur du plateau de jeu (minimun 10): ");
        }
        this.gameboard = new GameBoard(height, width);
    }
    
    public GameBoard getGameboard(){
        return this.gameboard;
    }
    
    // Renvoie un entier entrer par l'utilisateur
    public static int getIntInput(String text) throws IOException{
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         
        int value = -1;
        
        boolean format = false;
        while(!format){
            format = true;
            try {
                System.out.print(text);
                value = Integer.parseInt(br.readLine());
            } catch(NumberFormatException nfe) {
                format = false;
                System.err.println("Invalid Format!");
            }
        }
        return value;
    }
    
    
        // Simule le jeu complet: initialisation du plateau, affichage, coup joué et victoire.
    public void play() throws IOException{
        boolean humanTurn;
        
        int joueur = getIntInput("Voulez-vous jouer en 1er (1) ou en 2e (2): ");
        humanTurn = joueur == 1;
        
        this.initWeight();
        while(this.canPlay()){
            playOneRound(humanTurn);
            humanTurn = !humanTurn;
        }
        
        this.print();
        
        if(this.score1 > this.score2){
            System.out.println("Vous avez gagné");
        }
        else if(this.score1 < this.score2){
            System.out.println("Vous avez perdu");
        }
        else{
            System.out.println("Vous avez fait une égalité");
        }
    }
    
    //Simule un tour de jeu
    public void playOneRound(boolean isHuman) throws IOException{
        if(isHuman){
            if(this.humanTurn()){
                //this.score1++;
            }
        }
        else{
            if(this.iaTurn()){
               //this.score2++;
            }
        }
    }
    
    //Simule le tour de jeu de l'IA (recherche de la première case avec le plus grand poids(weight)
    public boolean iaTurn(){
        //0 -> coord x ; 1 -> coord y
        int[] bestBox = this.bestBox();
        this.insertValue(false, bestBox[0], bestBox[1]);
        this.updateWeight(bestBox[0], bestBox[1]);
        return this.hasWin(bestBox[0], bestBox[1]);
    }
    
    public int[] bestBox(){
        int[] bestBox = {0,0};
        float bestWeight = -1f;
        
        for(int i = 0; i < this.getGameboard().getHeight(); i++){
            for(int j = 0 ; j < this.getGameboard().getWidth() ; j++){
                if(this.getGameboard().getBoxWeight(i, j) > bestWeight){
                    bestBox[0] = i;
                    bestBox[1] = j;
                    bestWeight = this.getGameboard().getBoxWeight(i, j);
                }
            }
        }
        return bestBox;
    }
    
    public boolean canPlay(){
        int[] test = {-1,-1};
        int[] bestBox = {-1,-1};
        float bestWeight = -1f;
        
        for(int i = 0; i < this.getGameboard().getHeight(); i++){
            for(int j = 0 ; j < this.getGameboard().getWidth() ; j++){
                if(this.getGameboard().getBoxWeight(i, j) > bestWeight){
                    bestBox[0] = i;
                    bestBox[1] = j;
                    bestWeight = this.getGameboard().getBoxWeight(i, j);
                }
            }
        }
        if(bestWeight == 0){
            return false;
        }
        return !Arrays.equals(bestBox, test);
    }
    
    //Simule le tour du joueur humain (demande des coordonnées à l'utilisateur et joue 
    public boolean humanTurn() throws IOException{
        int playerInput[] = new int[2];
        boolean isInside, isEmpty;
        //this.gameboard.printW();
        this.print();
        // Si la case est libre et dans le plateau de jeu alors on la place sinon on redemande
        do{
            isEmpty = false;

            playerInput[0] = getIntInput("C'est à votre tour de jouer: ");
            playerInput[1] = getIntInput("");
            isInside = playerInput[0] >= 0 && playerInput[1] >= 0 && playerInput[0] < this.getGameboard().getHeight() && playerInput[1] < this.getGameboard().getWidth();
            if(isInside){
                isEmpty = this.getGameboard().getBoxBoard(playerInput[0], playerInput[1]).getValue() == 0;
            }

        }while(!(isEmpty && isInside ));

        this.insertValue(true, playerInput[0], playerInput[1]);
        this.updateWeight(playerInput[0], playerInput[1]);
        return this.hasWin(playerInput[0], playerInput[1]);
    }
    
    //Insere une valeur en fonction du joueur dans la case du plateau de jeu que ce joueur à décider de jouer
    public void insertValue(boolean isHuman, int x, int y){
        if(isHuman){
            this.getGameboard().getBoxBoard(x, y).setValue(1);
        }
        else{
            this.getGameboard().getBoxBoard(x, y).setLast(true);
            this.getGameboard().getBoxBoard(x, y).setValue(6);
        }
    }
    
    //Regarde si au moins un quintuplet gagnant se situe dans la case x y, si oui renvoie true sinon false
    public boolean hasWin(int x, int y){
        boolean hasWin = false;
        int noteQuintuplet;
        Box[][] quintuplets = this.getGameboard().getQuintuplets(x, y);
        for(int i = 0; i < this.getGameboard().getNbQuintuplets(x, y) ; i++ ){
            noteQuintuplet = GameBoard.noteQuintu(quintuplets[i]);
            
            if(noteQuintuplet == 5){
                System.out.println("Vous avez gagné un point");
                for(int j = 0; j < 5; j++){
                    quintuplets[i][j].setUsed(Game.usedValue);
                }
                this.updateBoard();
                Game.usedValue++;
                hasWin = true;
                this.score1++;
            }
            else if(noteQuintuplet == 30){
                System.out.println("Votre adversaire à gagné un point");
                for(int j = 0; j < 5; j++){
                    quintuplets[i][j].setUsed(Game.usedValue);
                }
                this.updateBoard();
                Game.usedValue++;
                hasWin = true;
                this.score2++;
            }
        }
        return hasWin;
    }
    
    public void print(){
        System.out.println("Player : " + this.score1 + "\t IA : " + this.score2);
        this.gameboard.print();
    }
    
    /********************************************************
                    Fonctions de gestion de weight
    ************************************************************/
    
    //Initialise les weights du plateau de jeu
    public void initWeight(){
        for(int i = 0; i < this.getGameboard().getHeight() ; i++){
            for(int j = 0; j < this.getGameboard().getWidth() ;  j++){
                
                this.getGameboard().setBoxWeight(i,j, Game.EMPTY_WEIGHT * this.getGameboard().getNbQuintuplets(i, j));
            }
        }
    }
    
    public void updateBoard(){
        for(int i = 0; i < this.gameboard.getHeight(); i++){
            for(int j = 0; j < this.gameboard.getWidth(); j++){
                if(this.gameboard.getBoxBoard(i, j).getWeight() > -10000){
                    this.gameboard.getBoxBoard(i, j).setWeight(Game.EMPTY_WEIGHT * this.getGameboard().getNbQuintuplets(i, j));
                }
            }
        }
        //this.gameboard.printW();
        for(int i = 0; i < this.gameboard.getHeight(); i++){
            for(int j = 0; j < this.gameboard.getWidth(); j++){
                if(this.gameboard.getBoxBoard(i, j).getWeight() < -100000){
                    this.updateWeightT(i, j);

                }
            }
            
        }
    }
    
    // Met à jour les poids du plateau en fonction de l'emplacement joué
    public void updateWeight(int x, int y){
        int nbUpdateQuintu = this.getGameboard().getNbQuintuplets(x, y);
        Box[][] UpdateQuintu = this.getGameboard().getQuintuplets(x, y);
        int noteQuintu;
        
        for(int i = 0; i < nbUpdateQuintu; i++){
            noteQuintu = GameBoard.noteQuintu(UpdateQuintu[i]);
            // On envoie la différence de poids du quintuplet avant et après qu'on es joué, qu'on ajoute au poids précédent de la case
            switch (noteQuintu) {
                case 1 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.ONE_PLAYER - Game.EMPTY_WEIGHT);
                case 2 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.TWO_PLAYER - Game.ONE_PLAYER);
                case 3 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.THREE_PLAYER - Game.TWO_PLAYER);
                case 4 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.FOUR_PLAYER - Game.THREE_PLAYER);

                case 6 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.ONE_IA - Game.EMPTY_WEIGHT);
                case 12 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.TWO_IA - Game.ONE_IA);
                case 18 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.THREE_IA - Game.TWO_IA);
                case 24 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.FOUR_IA - Game.THREE_IA);

                //Cas du quintuplet fermé et complet
                default ->  {
                    noteQuintu -= this.getGameboard().getBoxBoard(x, y).getValue();
                    switch (noteQuintu) {
                        case 1 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game.ONE_PLAYER);
                        case 2 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game.TWO_PLAYER);
                        case 3 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game.THREE_PLAYER);
                        case 4 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game.FOUR_PLAYER);

                        case 6 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game.ONE_IA);
                        case 12 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game.TWO_IA);
                        case 18 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game.THREE_IA);
                        case 24 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game.FOUR_IA);
                    }
                }
            }
        //Poids négatif dans les case déjà joué pour que l'ia n'y joue pas    
        this.getGameboard().setBoxWeight(x, y, -1000000);
        }
    }
    
    
    public void updateWeightT(int x, int y){
        int nbUpdateQuintu = this.getGameboard().getNbQuintuplets(x, y);
        Box[][] UpdateQuintu = this.getGameboard().getQuintuplets(x, y);
        int noteQuintu;
        
        for(int i = 0; i < nbUpdateQuintu; i++){
            noteQuintu = GameBoard.noteQuintu(UpdateQuintu[i]);
            // On envoie la différence de poids du quintuplet avant et après qu'on es joué, qu'on ajoute au poids précédent de la case
            switch (noteQuintu) {
                case 1 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.ONE_PLAYER);
                case 2 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.TWO_PLAYER);
                case 3 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.THREE_PLAYER);
                case 4 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.FOUR_PLAYER);

                case 6 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.ONE_IA);
                case 12 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.TWO_IA);
                case 18 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.THREE_IA);
                case 24 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game.FOUR_IA);

            }
        //Poids négatif dans les case déjà joué pour que l'ia n'y joue pas    
        this.getGameboard().setBoxWeight(x, y, -1000000);
        }
    }
}
