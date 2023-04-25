/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import fr.IooGoZ.GomokolClient.interfaces.Player;

/**
 *
 * @author theodusehu
 */
public class Joueur implements Player{
        
    Game monJeu;
	
    public Joueur(Game jeu){
        this.monJeu = jeu;
    }

    private int id = -1;
	
    @Override
    public void setId(int id) {
            this.id = id;
    }

    @Override
    public int getId() {
            return this.id;
    }

    @Override
    public int[] getStroke() {
        return monJeu.bestBox();
    }

    @Override
    public void receiveNewStroke(int player_id, int[] stroke) {
        if(player_id == this.getId()){
            monJeu.insertValue(true, stroke[0], stroke[1]);
        }else{
            monJeu.insertValue(false, stroke[0], stroke[1]);
        }
        monJeu.updateWeight(stroke[0], stroke[1]);
        monJeu.getGameboard().print();
    }
    
}
