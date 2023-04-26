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
public class JoueurExt31 implements Player{
        
    Game1 monJeu;
    Data data = Data.getInstance();
	
    public JoueurExt31(Game1 jeu){
        this.monJeu = jeu;
        data.setExtension3(true);
    }

    private int id = -1;
	
    @Override
    public void setId(int id) {
            this.id = id;
            System.out.println("Je suis joueur numéro : " + id);
    }

    @Override
    public int getId() {
            return this.id;
    }

    @Override
    public int[] getStroke() {
        int[] stroke = monJeu.bestBox();
        //System.out.println(stroke[0] + "/" + stroke[1]);
        return stroke;
    }

    @Override
    public void receiveNewStroke(int player_id, int[] stroke) {
        //System.out.println("ID:" + player_id + " " + stroke[0] + "/" + stroke[1]); // A desactiver pour plus de rapidité
        if(player_id == this.getId()){
            monJeu.insertValue(true, stroke[0], stroke[1]);
        }else{
            monJeu.insertValue(false, stroke[0], stroke[1]);
        }
        monJeu.updateWeight(stroke[0], stroke[1]);
        monJeu.getGameboard().print();
    }
    
}
