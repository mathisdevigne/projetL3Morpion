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
    
    
    public boolean playOneRound(int nbJoueur) throws IOException{
        int tab[] = new int[2];
        // Si la case est libre alors on place sinon on redemande
        do{
            
        tab[0] = getIntInput("Joueur "+ nbJoueur +", à vous de jouer: ");
        tab[1] = getIntInput("");
        
        }while(!(" ".equals(this.getGameboard().getBoxBoard(tab[0], tab[1]).getValue()) && tab[0] >= 0 && tab[1] >= 0 && tab[0] < this.getGameboard().getHeight() && tab[1] < this.getGameboard().getWidth()));
        
        this.insertValue(nbJoueur, tab[0], tab[1]);
        this.getGameboard().print();
        return this.hasWin(tab[0], tab[1]);
    }
    
    public void insertValue(int nbJoueur, int x, int y){
        if(nbJoueur == 1){
            this.getGameboard().getBoxBoard(x, y).setValue("X");
        }
        else{
            this.getGameboard().getBoxBoard(x, y).setValue("O");
        }
    }
    
    //Va regarder le dernier coup jouer et renvoyé true si ce coup permet de gagner
    public boolean hasWin(int x, int y){
        boolean hasWin = false;
        Box[][] quintuplets = this.getGameboard().getQuintuplets(x, y);
        for(int i = 0; i < this.getGameboard().getNbQuintuplets(x, y) ; i++ ){
            if("X".equals(quintuplets[i][0].getValue()) && "X".equals(quintuplets[i][1].getValue()) && "X".equals(quintuplets[i][2].getValue()) && "X".equals(quintuplets[i][3].getValue()) && "X".equals(quintuplets[i][4].getValue())){
                System.out.println("Joueur 1 a gagné");
                hasWin = true;
            }
            else if("O".equals(quintuplets[i][0].getValue()) && "O".equals(quintuplets[i][1].getValue()) && "O".equals(quintuplets[i][2].getValue()) && "O".equals(quintuplets[i][3].getValue()) && "O".equals(quintuplets[i][4].getValue())){
                System.out.println("Joueur 2 a gagné");
                hasWin = true;
            }
        }
        return hasWin;
    }
    
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
