/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import board.ShapeBoard;
import board.GameBoard;
import board.Box;
import java.util.Arrays; 
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author fetiveau
 */
public class Game1 {
    private GameBoard gameboard;
    private BufferedImage bi = null;
    private boolean isHumanTurn;
    private boolean isFinish;
    private boolean isGameWin = false;
    
    private Data datas;
    
    private int scoreJoueurTotal = 0;
    private int scoreIATotal = 0;
    private int scoreJoueur = 0;
    private int scoreIA = 0;
    
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
    
    //Constructeur de base pour l'UI
    public Game1(int width, int height){
        
        datas = Data.getInstance();
        
        this.gameboard = new GameBoard(height, width);
        
        this.initWeight();
        
        //isAIFirst();
    }
    
    //Constructeur pour le ShapeBoard
    public Game1(ShapeBoard board){
        datas = Data.getInstance();
        
        this.gameboard = board;
        
        this.initWeight();
        this.updateAllWeight();
        
        //isAIFirst();
    }
    
    //Constructeur pour les images
    public Game1(String link){
        
        datas = Data.getInstance();
        
        try {
            bi = ImageIO.read(getClass().getResourceAsStream(link));
        } catch (IOException ex) {
            Logger.getLogger(Game1.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.gameboard = new ShapeBoard(bi.getHeight(), bi.getWidth());
        this.initWeight();
        if(this.bi != null){
            this.getShapeboard().setShape(bi);
            datas.setShapeBoard((ShapeBoard) this.gameboard);
        }
        this.updateAllWeight();
        isAIFirst();
        
    }
    
    public final void isAIFirst(){
        this.isHumanTurn = datas.getIsHumanBegin();
        if(!isHumanTurn){
            this.iaTurn();
            this.print();
        } 
    }
    
    public void resetBoard(){
        this.gameboard.resetBoard();
        this.gameboard.print();
        this.initWeight();
        this.isFinish = false;
        this.scoreIA = this.scoreJoueur = 0;
        
        if(datas.getExtension3()){
            this.scoreIATotal = this.scoreJoueurTotal = 0;
        }
        
        if(datas.getExtension4()){
            this.updateAllWeight();
        }
    }
    
    public GameBoard getGameboard(){
        return this.gameboard;
    }
    
    public void setGameBoard(GameBoard board){
        this.gameboard = board;
    }
    
    public final ShapeBoard getShapeboard(){
        return (ShapeBoard) this.gameboard;
    }
    
    public int getScoreJoueur(){
        return scoreJoueurTotal;
    }
    
    public int getScoreIA(){
        return scoreIATotal;
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

    public void play(int x, int y){
        
        if(!isFinish){
            boolean multi = this.humanTurn(x, y);
            if(multi){
                this.iaTurn();
            }
        }
        
        this.getGameboard().print();
    }
    
    //Simule un tour de jeu
    public void playOneRound(boolean isHuman) throws IOException{
        if(isHuman){
            
            if(this.humanTurn()){
                //this.scoreJoueurTotal++;
            }
        }
        else{
            if(this.iaTurn()){
               //this.scoreIATotal++;
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
        int[] bestBox = {-1,-1};
        float bestWeight = -1f;

        
        
        
            
                for(int i = 0; i < this.getGameboard().getBoardHeight(); i++){
                    for(int j = 0 ; j < this.getGameboard().getBoardWidth() ; j++){
                        if(this.getGameboard().getVal(i, j) == 0){
                            bestBox[0] = i;
                            bestBox[1] = j;
                            return bestBox;
                        }
                    }
                }
            
        
        
        return bestBox;
    }
    
    public boolean canPlay(){
        boolean verif = false;
        int cptJ = 0;
        int cptIA = 0;
        
        for(int i = 0; i < this.getGameboard().getBoardHeight(); i++){
            for(int j = 0 ; j < this.getGameboard().getBoardWidth() ; j++){
                if(this.getGameboard().getVal(i, j) == 0 && this.getGameboard().getBoxWeight(i, j) > 0){
                    Box[][] quintuplets = this.getGameboard().getQuintuplets(i, j);
                    for(int k = 0; k < this.getGameboard().getNbQuintuplets(i, j) ; k++ ){
                        int note = GameBoard.noteQuintu(quintuplets[k]);
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
            isInside = playerInput[0] >= 0 && playerInput[1] >= 0 && playerInput[0] < this.getGameboard().getBoardHeight() && playerInput[1] < this.getGameboard().getBoardWidth();
            if(isInside){
                isEmpty = this.getGameboard().getBoxBoard(playerInput[0], playerInput[1]).getValue() == 0;
            }

        }while(!(isEmpty && isInside ));

        this.insertValue(true, playerInput[0], playerInput[1]);
        this.updateWeight(playerInput[0], playerInput[1]);
        return this.hasWin(playerInput[0], playerInput[1]);
    }
    
    public boolean humanTurn(int x, int y){
        
        boolean verif = false;
        
        if(this.getGameboard().getBoxBoard(y, x).getValue() == 0){
            
            this.insertValue(true, y, x);
            this.updateWeight(y, x);
            verif = true;
        }
        
        return verif && !this.hasWin(y, x);
        
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
        
        //this.getGameboard().print();
    }
    
    //Regarde si au moins un quintuplet gagnant se situe dans la case x y, si oui renvoie true sinon false
    public boolean hasWin(int x, int y){
        boolean hasWin = false;
        String messages[] = new String[2];
        
        int noteQuintuplet;
        Box[][] quintuplets = this.getGameboard().getQuintuplets(x, y);
        for(int i = 0; i < this.getGameboard().getNbQuintuplets(x, y) ; i++ ){
            noteQuintuplet = GameBoard.noteQuintu(quintuplets[i]);
            
            if(noteQuintuplet == 5){
                System.out.println("Vous avez gagné un point");
                for(int j = 0; j < 5; j++){
                    quintuplets[i][j].setUsed(Game1.usedValue);
                }
                this.updateBoard();
                Game1.usedValue++;
                hasWin = true;
                scoreJoueurTotal += 1;
                scoreJoueur += 1;
            }
            else if(noteQuintuplet == 30){
                System.out.println("Votre adversaire à gagné un point");
                for(int j = 0; j < 5; j++){
                    quintuplets[i][j].setUsed(Game1.usedValue);
                }
                this.updateBoard();
                Game1.usedValue++;
                hasWin = true;
                scoreIATotal += 1;
                scoreIA += 1;
            }
        }
        
        /*if(datas.getExtension3()){
            isFinish = !this.canPlay();
        }else{
            isFinish = hasWin || !this.canPlay();
            this.isGameWin = true;
        }
        if(isFinish){
            
            if(this.isGameWin){ //Score Jeu simple
                if(this.scoreIA > this.scoreJoueur){
                    messages[0] = "Vous avez perdu";
                    messages[1] = "Dommage, vous vous êtes fait démolir ^^";
                }
                else if(this.scoreIA < this.scoreJoueur){
                    messages[0] = "Vous avez gagné";
                    messages[1] = "Félicitation, vous avez réussi à vaincre la féroce IA. Vous méritez une médaille !";
                }
                else{
                    messages[0] = "Egalité";
                    messages[1] = "Bravo ! Vous avez réussis à tenir bon !";
                }
            }
            else{ //Score extension 3
                if(this.scoreIATotal > this.scoreJoueurTotal){
                    messages[0] = "Vous avez perdu";
                    messages[1] = "Dommage, vous vous êtes fait démolir ^^";
                }
                else if(this.scoreIATotal < this.scoreJoueurTotal){
                    messages[0] = "Vous avez gagné";
                    messages[1] = "Félicitation, vous avez réussi à vaincre la féroce IA. Vous méritez une médaille !";
                }
                else{
                    messages[0] = "Egalité";
                    messages[1] = "Bravo ! Vous avez réussis à tenir bon !";
                }
            }
            
            
        }*/
        return hasWin;
    }
    
    public void print(){
        //System.out.println("Player : " + this.scoreJoueurTotal + "\t IA : " + this.scoreIATotal);
        this.gameboard.print();
    }
    
    /********************************************************
                    Fonctions de gestion de weight
    ************************************************************/
    
    //Initialise les weights du plateau de jeu
    public final void initWeight(){
        for(int i = 0; i < this.getGameboard().getBoardHeight() ; i++){
            for(int j = 0; j < this.getGameboard().getBoardWidth() ;  j++){
                
                this.getGameboard().setBoxWeight(i,j, Game1.EMPTY_WEIGHT * this.getGameboard().getNbQuintuplets(i, j));
            }
        }
    }
    
    public void updateBoard(){
        for(int i = 0; i < this.gameboard.getBoardHeight(); i++){
            for(int j = 0; j < this.gameboard.getBoardWidth(); j++){
                if(this.gameboard.getBoxBoard(i, j).getWeight() > -10000){
                    this.gameboard.getBoxBoard(i, j).setWeight(Game1.EMPTY_WEIGHT * this.getGameboard().getNbQuintuplets(i, j));
                }
            }
        }
        //this.gameboard.printW();
        for(int i = 0; i < this.gameboard.getBoardHeight(); i++){
            for(int j = 0; j < this.gameboard.getBoardWidth(); j++){
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
                case 1 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.ONE_PLAYER - Game1.EMPTY_WEIGHT);
                case 2 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.TWO_PLAYER - Game1.ONE_PLAYER);
                case 3 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.THREE_PLAYER - Game1.TWO_PLAYER);
                case 4 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.FOUR_PLAYER - Game1.THREE_PLAYER);

                case 6 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.ONE_IA - Game1.EMPTY_WEIGHT);
                case 12 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.TWO_IA - Game1.ONE_IA);
                case 18 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.THREE_IA - Game1.TWO_IA);
                case 24 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.FOUR_IA - Game1.THREE_IA);

                //Cas du quintuplet fermé et complet + CAS VAL = 50
                default ->  {
                    noteQuintu -= this.getGameboard().getBoxBoard(x, y).getValue();
                    if(this.getGameboard().getBoxBoard(x, y).getValue() == 50){
                        noteQuintu = 50;
                    }
                    switch (noteQuintu) {
                        case 1 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.ONE_PLAYER);
                        case 2 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.TWO_PLAYER);
                        case 3 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.THREE_PLAYER);
                        case 4 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.FOUR_PLAYER);

                        case 6 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.ONE_IA);
                        case 12 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.TWO_IA);
                        case 18 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.THREE_IA);
                        case 24 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.FOUR_IA);
                        
                        //Cas -= 50
                        default -> {GameBoard.updateWeightQuintu(UpdateQuintu[i], -Game1.EMPTY_WEIGHT);break;}

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
                case 1 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.ONE_PLAYER);
                case 2 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.TWO_PLAYER);
                case 3 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.THREE_PLAYER);
                case 4 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.FOUR_PLAYER);

                case 6 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.ONE_IA);
                case 12 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.TWO_IA);
                case 18 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.THREE_IA);
                case 24 -> GameBoard.updateWeightQuintu(UpdateQuintu[i], Game1.FOUR_IA);

            }
        //Poids négatif dans les case déjà joué pour que l'ia n'y joue pas    
        this.getGameboard().setBoxWeight(x, y, -1000000);
        }
    }
    
    // Met à jour les poids du plateau en fonction de l'emplacement joué
    public final void updateAllWeight(){
        for(int x = 0; x < this.getGameboard().getBoardHeight(); x++){
            for(int y = 0; y < this.getGameboard().getBoardWidth(); y++){
                if(this.getGameboard().getBoxBoard(x, y).getValue() == 50){
                    this.updateWeight(x, y);
                }
            }
        }
    }
    
    //Rien tqt
    public static BufferedImage rotateClockwise90(BufferedImage src) {

        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        boolean hasAlphaChannel = src.getAlphaRaster() != null;
        int pixelLength = hasAlphaChannel ? 4 : 3;
        byte[] srcPixels = ((DataBufferByte)src.getRaster().getDataBuffer()).getData();

        // Create the destination buffered image
        BufferedImage dest = new BufferedImage(srcHeight, srcWidth, src.getType());
        byte[] destPixels = ((DataBufferByte)dest.getRaster().getDataBuffer()).getData();
        int destWidth = dest.getWidth();

        int srcPos = 0; // We can just increment this since the data pack order matches our loop traversal: left to right, top to bottom. (Just like reading a book.)   
        for(int srcY = 0; srcY < srcHeight; srcY++) {
            for(int srcX = 0; srcX < srcWidth; srcX++) {

                int destX = ((srcHeight - 1) - srcY);
                int destY = srcX;

                int destPos = (((destY * destWidth) + destX) * pixelLength);

                if(hasAlphaChannel) {
                    destPixels[destPos++] = srcPixels[srcPos++];    // alpha
                }
                destPixels[destPos++] = srcPixels[srcPos++];        // blue
                destPixels[destPos++] = srcPixels[srcPos++];        // green
                destPixels[destPos++] = srcPixels[srcPos++];        // red
            }
        }

        return dest;
    }
}
