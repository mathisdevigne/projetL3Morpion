/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *
 * @author fetiveau
 */
public class Game {
    public GameBoard gameboard;
    
    private final static int EMPTY_WEIGHT = 1;
    private final static int ONE_CROSS = EMPTY_WEIGHT*3;
    private final static int ONE_CIRCLE = ONE_CROSS*3;
    private final static int TWO_CROSS = ONE_CIRCLE*3;
    private final static int TWO_CIRCLE = TWO_CROSS*3;
    private final static int THREE_CROSS = TWO_CIRCLE*3;
    private final static int THREE_CIRCLE = THREE_CROSS*3;
    private final static int FOUR_CROSS = THREE_CIRCLE*3;
    private final static int FOUR_CIRCLE = FOUR_CROSS*3;
    
    
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
    
    // Simule un tour de jeu d'un joueur humain, renvoie true si un joueur a gagné
    public boolean playOneRound(int nbJoueur) throws IOException{
        int playerInput[] = new int[2];
        // Si la case est libre alors on place sinon on redemande
        do{
            
        playerInput[0] = getIntInput("Joueur "+ nbJoueur +", à vous de jouer: ");
        playerInput[1] = getIntInput("");
        
        }while(!(this.getGameboard().getBoxBoard(playerInput[0], playerInput[1]).getValue()) == 0 && playerInput[0] >= 0 && playerInput[1] >= 0 && playerInput[0] < this.getGameboard().getHeight() && playerInput[1] < this.getGameboard().getWidth()));
        
        this.insertValue(nbJoueur, playerInput[0], playerInput[1]);
        this.getGameboard().print();
        return this.hasWin(playerInput[0], playerInput[1]);
    }
    
    //Insere une valeur en fonction du joueur dans la case du plateau de jeu que ce joueur à décider de jouer
    public void insertValue(int nbJoueur, int x, int y){
        if(nbJoueur == 1){
            this.getGameboard().getBoxBoard(x, y).setValue(1);
        }
        else{
            this.getGameboard().getBoxBoard(x, y).setValue(6);
        }
    }
    
    //Regarde si au moins un quintuplet gagnant se situe dans la case x y, si oui renvoie true sinon false
    public boolean hasWin(int x, int y){
        boolean hasWin = false;
        int noteQuintuplet = 0;
        Box[][] quintuplets = this.getGameboard().getQuintuplets(x, y);
        for(int i = 0; i < this.getGameboard().getNbQuintuplets(x, y) ; i++ ){
            noteQuintuplet = 5;
            
            if(noteQuintuplet == 5){
                System.out.println("Joueur 1 a gagné");
                hasWin = true;
            }
            else if(noteQuintuplet == 30){
                System.out.println("Joueur 2 a gagné");
                hasWin = true;
            }
        }
        return hasWin;
    }
    
    // Simule le jeu complet: initialisation du plateau, affichage, coup joué et victoire.
    public void play() throws IOException{
        boolean ended = false;
        int joueur = 1;
        this.getGameboard().print();
        while(!(ended)){
            ended = playOneRound(joueur);
            if(joueur == 1){
                joueur = 2;
            }
            else{
                joueur = 1;
            }
        }
        
        
    }
}
