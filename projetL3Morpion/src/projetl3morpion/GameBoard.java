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
    
    //Accesseur de height
    public int getHeight(){
        return this.height;
    }
    
    //Accesseur de board
    public Box[][] getBoard(){
        return this.board;
    }
    
    //Accesseur d'une case de board
    public Box getBoxBoard(int i, int j){
        return this.board[i][j];
    }
    
    //Accesseur de width
    public int getWidth(){
        return this.width;
    }
    
    
    // Affichage du jeu (plateau) besoin d'ajustement
    public void print(){
        System.out.print("  ");
        for(int k = 0; k < this.getHeight(); k++){
            if(k <= 9){
                System.out.print(" ");
                System.out.print( " " + k + " "); 
            }         
        }
        System.out.println();
        System.out.print("  +");
        for(int k = 0; k < this.getWidth(); k++){
            System.out.print( "---+");     
        }
        
        System.out.println();
        for(int i = 0; i < this.getHeight(); i++){
            System.out.print(i);
            if(i <= 9){
                System.out.print(" ");
            }
            System.out.print("| ");
            for(int j = 0; j < this.getWidth(); j++){
                this.getBoxBoard(i,j).print();
                System.out.print(" | ");
            }
            System.out.println();
            System.out.print("  +");
            for(int k = 0; k < this.getWidth(); k++){
                System.out.print( "---+");     
            }
            System.out.println();
        }
    }
    
    // Permet l'insertion d'une valeur dans une case de board
    public void putVal(int val, int x, int y){
        if(this.getBoxBoard(x,y).getValue() == 0){
            this.getBoxBoard(x, y).setValue(val);
        }
    }
    /*
    public boolean existeQuintu(int x, int y, int dirHorizon, int dirVertical){
        return ((0 <= x+5*dirVertical) && (x+5*dirVertical < this.getHeight()) && (0 <= x) && (x < this.getHeight()) && (0 <= y+5*dirHorizon) && (y+5*dirHorizon < this.getWidth()) && (0 <= y) && (y < this.getWidth())); 
    }
    
    public Box[] getQuintuplet(int x, int y, int dirHorizon, int dirVertical){
        Box[] monQuintuplet = new Box[5];
        if(existeQuintu(x,y,dirHorizon,dirVertical)){
            for(int i = 0; i < 5; i++){
                monQuintuplet[i] = this.getBoxBoard(x+(i*dirVertical), y+(i*dirHorizon));
            }
        }
        return monQuintuplet;
    }
    
    public int noteQuintu(int x, int y, int dirHorizon, int dirVertical){
        int somme = 0;
        if(existeQuintu(x,y,dirHorizon,dirVertical)){
            Box[] leQuintuplet = getQuintuplet(x,y,dirHorizon,dirVertical);
            for(int i = 0; i < 5; i++){
                somme += leQuintuplet[i].getValue();
            }
        }
        return somme;
    }
    
    */
    
    // Permet d'avoir le nombre de quintuplet formable au max sur une case (donc exclue les quintuplets partiellement en dehors du plateau de jeu)
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
                    
                    //les diagonales haut-droit / bas-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y - j < this.getWidth() && y - j - 4 >= 0){
                        nbQuintuplets++;
                    }

                    //les diagonales bas-droit / haut-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y + j >= 0 && y + j + 4 < this.getWidth()){
                        nbQuintuplets++;
                            
                        
                    }
                }
            }
        }
        return nbQuintuplets;
    }
    
    //Retourne la somme des valeurs d'un quintuplet
    public static int noteQuintu(Box[] monQuintu){
        int somme = 0;
        for(int i = 0 ; i < 5 ; i++){
            somme += monQuintu[i].getValue();
        }
        return somme;
    }
    
    //Renvoie tout les quintuplet (avec les valeurs) d'une case de coordonnées x y (ne prends que les quintuplets dans le plateau de jeu)
    public Box[][] getQuintuplets(int x, int y){
        //La case doit être dans le plateau de jeu
        int nbQuint = this.getNbQuintuplets(x,y);
        Box[][] quintupletList = new Box[nbQuint][5];
        if(x >= 0 && x < this.getHeight() && y >= 0 && y < this.getWidth()){
            
            int indiceList = 0;
            for(int i = 0; i > -5; i--){
                // Les 5 quintuplets verticaux
                //On vérifie que les quintuplets se trouve dans le plateau de jeu, sinon on ne le rajoute pas
                if(x + i >= 0 && x + i + 4 < this.getHeight()){

                    for(int k = 0; k < 5 ; k++){
                        quintupletList[indiceList][k] = this.getBoxBoard(x+i+k, y);
                        
                    }
                    indiceList++;

                }
                
                 // Les 5 quintuplets horizontaux
                if(y + i >= 0 && y + i + 4 < this.getWidth()){

                    for(int k = 0; k < 5 ; k++){
                        quintupletList[indiceList][k] = this.getBoxBoard(x,y+i+k);
                            
                    }
                    indiceList++;
                }

                for(int j = 0; j > -5; j--){
                    
                    //les 5 diagonales haut-droit / bas-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y - j < this.getWidth() && y - j - 4 >= 0){

                        for(int k = 0; k < 5; k++){
                            quintupletList[indiceList][k] = this.getBoxBoard( y-j-k, x+i+k);
                            
                        }
                        indiceList++;
                    }

                    //les 5 diagonales bas-droit / haut-gauche
                    if(j+i == 2*i && x + i >= 0 && x + i + 4 < this.getHeight() && y + j >= 0 && y + j + 4 < this.getWidth()){
                        for(int k = 0; k < 5; k++){
                            quintupletList[indiceList][k] = this.getBoxBoard(y+j+k, x+i+k);
                            
                        }
                        indiceList++;
                    }
                }
            }
        }
        return quintupletList;
    }
        
}
