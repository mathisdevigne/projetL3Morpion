/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import java.io.IOException;
/**
 *
 * @author theodusehu
 */
public class Jeu{
    
    private Data datas;
    
    public Jeu() throws IOException {
            
        datas = Data.getInstance();
                
        Game monJeu;
        if(datas.getExtension4()){
            if(datas.getPlayStyle() == 0){
                monJeu = new Game(datas.getLink());
            }else{
                monJeu = new Game(datas.getShapeBoard());
                monJeu.updateAllWeight();
            }
            
        }
        else{
            monJeu = new Game(20, 20);
        }
        
        datas.setGame(monJeu);
        
       
    }
    
}
