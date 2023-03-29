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
    
    Data datas = Data.getInstance();
    
    boolean isMe = false;
        
    projetl3morpion.Game monJeu = datas.getGame();
	

    private int id = -1;
	
    @Override
    public void setId(int id) {
            this.id = id;
            datas.setIsHumanBegin(id != 1);
    }

    @Override
    public int getId() {
            return this.id;
    }

    @Override
    public int[] getStroke() {
        isMe = true;
        return monJeu.bestBox();
    }

    @Override
    public void receiveNewStroke(int player_id, int[] stroke) {
        if(isMe){
            monJeu.insertValue(false, id, id);
            isMe = true;
        }else{
            monJeu.insertValue(true, id, id);
            isMe = false;
        }
    }
    
}
