/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package board;

import board.Box;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import projetl3morpion.Data;

/**
 *
 * @author fetiveau
 */
public class GameBoard extends GridPane{
    private final static int DEFAULT_HEIGHT = 10;
    private final static int DEFAULT_WIDTH = 10;
    private final int height;
    private final int width;
    private double size = 40;
    private Data datas = Data.getInstance();
            
    private BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderWidths.DEFAULT);
    
    private Box[][] board;
    
    public GameBoard(int height, int width){
        this.height = height;
        this.width = width;
        this.setBorder(new Border(bs));
        this.setMaxSize(width*size, height*size);
        this.setMaxHeight(height*size);
        
        this.zoom(0.1, 0.03);
        
        board = new Box[height][width];
        for(int i = 0 ; i < height ; i++){
            for(int j = 0; j < width ; j++){
                Box b = new Box(i+j);
                board[i][j] = b;
                this.add(b, j, i);
            }
        }
    }
    
    
    public GameBoard(){
        this(GameBoard.DEFAULT_HEIGHT, GameBoard.DEFAULT_WIDTH);
    }
    
    public void zoom(double min, double step){
        
        double minValue;
        if(min < 0.1){minValue = 0.1;}else if(min >= 1){minValue = 0.9;}else{minValue = min;}
        
        this.setOnZoom(e-> 
        {
            
                if(e.getTotalZoomFactor() > 1){
                    this.setScaleX(this.getScaleX() + step);
                    this.setScaleY(this.getScaleX() + step);
                }
                else{
                    this.setScaleX(this.getScaleX() - step);
                    this.setScaleY(this.getScaleX() - step);
                }
                
                if(this.getScaleX() >= 1 && this.getScaleY() >= 1){
                    this.setScaleX(1);
                    this.setScaleY(1);
                }
                
                if(this.getScaleX() <= minValue && this.getScaleY() <= minValue){
                    this.setScaleX(minValue);
                    this.setScaleY(minValue);
                }
                
            
            //this.print();
            //System.out.println(this.getScaleX() + ", " + this.getScaleY());
        });
    }
    
    public void resetBoard(){
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                
                if(this.board[i][j].getValue() != 50){
                    this.board[i][j].setText("");
                    this.board[i][j].resetBox();
                }
            }
        }
    }
    
    public double getSize(){
        return this.size;
    }
    
    //Accesseur de height
    public int getBoardHeight(){
        return this.height;
    }
    
    //Accesseur de width
    public int getBoardWidth(){
        return this.width;
    }
    
    //Accesseur d'une case de board
    public Box getBoxBoard(int i, int j){
        return this.board[i][j];
    }
    
    //Accesseur de weight
    public int getBoxWeight(int x, int y){
        return this.getBoxBoard(x,y).getWeight();
    }
    
    //Setter de weight
    public void setBoxWeight(int x, int y, int val){
        this.getBoxBoard(x,y).setWeight(val);
    }
    
    // Affichage du jeu (plateau) besoin d'ajustement
    public void print(){
        System.out.print("  ");
        for(int k = 0; k < this.getBoardWidth(); k++){
            if(k <= 9){
                System.out.print(" ");
            }         
            System.out.print( " " + k + " ");
        }
        System.out.println();
        System.out.print("  +");
        for(int k = 0; k < this.getBoardWidth(); k++){
            System.out.print( "---+");     
        }
        
        System.out.println();
        for(int i = 0; i < this.getBoardHeight(); i++){
            System.out.print(i);
            if(i <= 9){
                System.out.print(" ");
            }
            System.out.print("| ");
            for(int j = 0; j < this.getBoardWidth(); j++){
                this.getBoxBoard(i,j).print();
                System.out.print(" | ");
            }
            System.out.println();
            System.out.print("  +");
            for(int k = 0; k < this.getBoardWidth(); k++){
                System.out.print( "---+");     
            }
            System.out.println();
        }
    }
    
    public void printW(){
        System.out.print("  ");
        for(int k = 0; k < this.getWidth(); k++){
            if(k <= 9){
                System.out.print(" ");
            }         
            System.out.print( "    " + k + "    ");
        }
        System.out.println();
        System.out.print("  +");
        for(int k = 0; k < this.getWidth(); k++){
            System.out.print( "----------+");     
        }
        
        System.out.println();
        for(int i = 0; i < this.getHeight(); i++){
            System.out.print(i);
            if(i <= 9){
                System.out.print(" ");
            }
            System.out.print("| ");
            for(int j = 0; j < this.getWidth(); j++){
                for(int k = 0; k < 8; k++){
                    System.out.print(this.getBoxBoard(i,j).getUsed()[k]);
                }
                System.out.print(" | ");
            }
            System.out.println();
            System.out.print("  +");
            for(int k = 0; k < this.getWidth(); k++){
                System.out.print( "----------+");     
            }
            System.out.println();
        }
    }
    
    public void printUI(){
        for(int i = 0; i < this.getBoardWidth(); i++){
            for(int j = 0; j < this.getBoardHeight(); j++){
                
                if(this.board[j][i].getValue() == 50){
                    this.board[j][i].setStyle("-fx-text-fill: red; -fx-font-size: 20px;");
                    this.board[j][i].setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }
    }
    
    // Permet l'insertion d'une valeur dans une case de board
    public void putVal(int val, int x, int y){
        //if(this.getBoxBoard(x,y).getValue() == 0){
            this.getBoxBoard(x, y).setValue(val);
        //}
    }
    
    public int getVal(int x, int y){
        return this.getBoxBoard(x, y).getValue();
    }
    
    
    /* ************************************************************
                            FONCTION QUINTUPLETS
    *****************************************************************/
    
    public int[] getQuintuCoord(int quintuId){
        int id = 1;
        for(int i = 0 ; i < height ; i++){
            for(int j = 0; j < width ; j++){
                if(id == quintuId){
                    return new int[] {i,j};
                }
                id++;
            }
        }
        return new int[] {-1, -1};
    }
    
    // Permet d'avoir le nombre de quintuplet formable au max sur une case (donc exclue les quintuplets partiellement en dehors du plateau de jeu)
    public int getNbQuintuplets(int x, int y){
        int nbQuintuplets = 0;
        if(x >= 0 && x < this.getBoardHeight() && y >= 0 && y < this.getBoardWidth()){
            
            for(int i = 0; i > -5; i--){
                //Les 5 quintuplets horizontaux
                //On vérifie que les quintuplets se trouve dans le plateau de jeu, sinon on ne le rajoute pas
                if(x + i >= 0 && x + i + 4 < this.getBoardHeight()){
                    nbQuintuplets++;
                }
                
                 // Les 5 quintuplets verticaux
                if(y + i >= 0 && y + i + 4 < this.getBoardWidth()){
                    nbQuintuplets++;
                }
                    
                for(int j = 0; j > -5; j--){
                    
                    //les diagonales haut-droit / bas-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getBoardHeight() && y - j < this.getBoardWidth() && y - j - 4 >= 0){
                        nbQuintuplets++;
                    }

                    //les diagonales bas-droit / haut-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getBoardHeight() && y + j >= 0 && y + j + 4 < this.getBoardWidth()){
                        nbQuintuplets++;
                            
                        
                    }
                }
            }
        }
        return nbQuintuplets;
    }

    //Renvoie tout les quintuplet dans un tableau de Box d'une case de coordonnées x y (ne prends que les quintuplets compris dans le plateau de jeu)
    public Box[][] getQuintuplets(int x, int y){
        //La case doit être dans le plateau de jeu
        int nbQuint = this.getNbQuintuplets(x,y);
        Box[][] quintupletList = new Box[nbQuint][5];
        if(x >= 0 && x < this.getBoardHeight() && y >= 0 && y < this.getBoardWidth()){
            
            int indiceList = 0;
            for(int i = 0; i > -5; i--){
                // Les 5 quintuplets verticaux
                //On vérifie que les quintuplets se trouve dans le plateau de jeu, sinon on ne le rajoute pas
                if(x + i >= 0 && x + i + 4 < this.getBoardHeight()){

                    for(int k = 0; k < 5 ; k++){
                        quintupletList[indiceList][k] = this.getBoxBoard(x+i+k, y);
                        
                    }
                    indiceList++;

                }
                
                 // Les 5 quintuplets horizontaux
                if(y + i >= 0 && y + i + 4 < this.getBoardWidth()){

                    for(int k = 0; k < 5 ; k++){
                        quintupletList[indiceList][k] = this.getBoxBoard(x,y+i+k);
                            
                    }
                    indiceList++;
                }

                for(int j = 0; j > -5; j--){
                    
                    //les 5 diagonales haut-droit / bas-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getBoardHeight() && y - j < this.getBoardWidth() && y - j - 4 >= 0){

                        for(int k = 0; k < 5; k++){
                            quintupletList[indiceList][k] = this.getBoxBoard(x+i+k, y-j-k);
                            
                        }
                        indiceList++;
                    }

                    //les 5 diagonales bas-droit / haut-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getBoardHeight() && y + j >= 0 && y + j + 4 < this.getBoardWidth()){
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
        
    
        
    //Retourne la somme des valeurs d'un quintuplet
    public static int noteQuintu(Box[] monQuintu){
        int somme = 0;
        int[] quintuUsed = {0};
        for(int i = 0 ; i < 5 ; i++){
            if(!monQuintu[i].isUsed() || !monQuintu[i].isUsed(quintuUsed)){
                somme += monQuintu[i].getValue();
            }
            quintuUsed = monQuintu[i].getUsed();
        }
        return somme;
    }
    
    //Change le poids des cases contenu dans le quintuplet
    public static void updateWeightQuintu(Box[] monQuintu, int newWeight){
        for(int k = 0; k < 5; k++){
            monQuintu[k].setWeight(monQuintu[k].getWeight() + newWeight);
        }
    }
    
}