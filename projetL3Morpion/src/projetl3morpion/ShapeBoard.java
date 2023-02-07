/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

/**
 *
 * @author mathi
 */
public class ShapeBoard extends GameBoard {
    
    
    public ShapeBoard(){
        super(10 ,12);
        
    }
    
    public void setShape(String link){
           int[][] tab = new int[10][12];
        
        for(int i = 0; i <this.getHeight(); i++){
            for(int j = 0; j <this.getWidth(); j++){
                tab[i][j] = (int) (Math.random()*7);
            }
        }
        for(int i = 0; i <this.getHeight(); i++){
            for(int j = 0; j <this.getWidth(); j++){
                if(tab[i][j]==0){
                    this.getBoxBoard(i, j).setValue(50);
                }
            }
        }
    }
    
    @Override
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
   
}
