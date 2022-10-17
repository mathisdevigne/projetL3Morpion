/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

/**
 *
 * @author fetiveau
 */
public class GameBoard {
    private final static int DEFAULT_HEIGHT = 10;
    private final static int DEFAULT_WIDTH = 10;
    private final int height;
    private final int width;
    
    private Box[][] board;
    
    public GameBoard(int height, int width){
        this.height = height;
        this.width = width;
        
        board = new Box[height][width];
        for(int i = 0 ; i < height ; i++){
            for(int j = 0; j < width ; j++){
                board[i][j] = new Box();
            }
        }
    }
    
    public GameBoard(){
        this(GameBoard.DEFAULT_HEIGHT, GameBoard.DEFAULT_WIDTH);
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public Box[][] getBoard(){
        return this.board;
    }
    
    public Box getBoxBoard(int i, int j){
        return this.board[i][j];
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public void print(){
        System.out.print(" ");
        for(int k = 0; k < this.getHeight(); k++){
            System.out.print( " " + k);
            
        }
        System.out.println();
        for(int i = 0; i < this.getHeight(); i++){
            System.out.print(i);
            for(int j = 0; j < this.getWidth(); j++){
                System.out.print("|");
                this.getBoxBoard(i,j).print();
            }
            
            
            System.out.println("|");
        }
    }
    
    public void putVal(String val, int x, int y){
        if(" ".equals(this.getBoxBoard(x,y).getValue())){
            this.getBoxBoard(x, y).setValue(val);
        }
    }
    
    public int getNbQuintuplets(int x, int y){
        int nbQuintuplets = 0;
        if(x >= 0 && x < this.getHeight() && y >= 0 && y < this.getWidth()){
            
            for(int i = 0; i > -5; i--){
                //Les 5 quintuplets horizontaux
                //On vérifie que les quintuplets se trouve dans le plateau de jeu, sinon on ne le rajoute pas
                if(x + i >= 0 && x + i + 4 < this.getHeight()){
                    nbQuintuplets++;
                }
                
                 // Les 5 quintuplets verticaux
                if(y + i >= 0 && y + i + 4 < this.getWidth()){
                    nbQuintuplets++;
                }
                    
                for(int j = 0; j > -5; j--){
                    
                    //la diagonale haut-droit / bas-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y - j < this.getWidth() && y - j - 4 >= 0){
                        nbQuintuplets++;
                    }

                    //la diagonale bas-droit / haut-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y + j >= 0 && y + j + 4 < this.getWidth()){
                        nbQuintuplets++;
                            
                        
                    }
                }
            }
        }
        return nbQuintuplets;
    }
    
    //// A debug
    //Renvoie tout les quintuplet possible sur une case de coordonnées x y
    public Box[][] getQuintuplets(int x, int y){
        //La case doit être dans le plateau de jeu
        int nbQuint = this.getNbQuintuplets(x,y);
        Box[][] quintupletList = new Box[nbQuint][5];
        if(x >= 0 && x < this.getHeight() && y >= 0 && y < this.getWidth()){
            
            int indiceList = 0;
            for(int i = 0; i > -5; i--){
                // Les 5 quintuplets horizontaux
                //On vérifie que les quintuplets se trouve dans le plateau de jeu, sinon on ne le rajoute pas
                if(x + i >= 0 && x + i + 4 < this.getHeight()){

                    for(int k = 0; k < 5 ; k++){
                        quintupletList[indiceList][k] = this.getBoxBoard(x+i+k, 0);
                        
                    }
                    indiceList++;

                }
                
                 // Les 5 quintuplets verticaux
                if(y + i >= 0 && y + i + 4 < this.getWidth()){

                    for(int k = 0; k < 5 ; k++){
                        quintupletList[indiceList][k] = this.getBoxBoard(0,y+i+k);
                            
                    }
                    indiceList++;
                }

                for(int j = 0; j > -5; j--){
                    
                    //la diagonale haut-droit / bas-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y - j < this.getWidth() && y - j - 4 >= 0){

                        for(int k = 0; k < 5; k++){
                            quintupletList[indiceList][k] = this.getBoxBoard(x+i+k, y-j-k);
                            
                        }
                        indiceList++;
                    }

                    //la diagonale bas-droit / haut-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y + j >= 0 && y + j + 4 < this.getWidth()){
                        for(int k = 0; k < 5; k++){
                            quintupletList[indiceList][k] = this.getBoxBoard(x+i+k, y+j+k);
                            
                        }
                        indiceList++;
                    }
                }
            }
        }
        return quintupletList;
    }
        
}
