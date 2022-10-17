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
                this.board[i][j].print();
            }
            
            
            System.out.println("|");
        }
    }
    
    //A finir
    public String[] getQuintuplets(int x, int y){
        // 20 * 5 car au maximum 20 quintuplets
        //On met les quintuplets les un a la suite des autres
        String[] quintupletList = new String[20*5];
        return quintupletList;
    }
}
