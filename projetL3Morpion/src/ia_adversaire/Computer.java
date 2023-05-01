package ia_adversaire;

import java.util.List;

import fr.IooGoZ.GomokolClient.interfaces.Player;

public class Computer implements Player{
	private char c;
	private Plateau p;
	private int id = -1;
	
	public Computer(Plateau p,char c) {
		this.p = p;
                this.p.initBoard();
		this.c = c;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int[] getStroke() {
		List<Quintuplet> lQuintu = p.getQuintupletTT();
		for(Quintuplet q : lQuintu) {
			 q.setNewVal(this.id);
		}
		
		int highest_value = 0;
		Case highest_case = p.getCase(0, 0);
		for(Case c : p.getCasesPlateau()) {
			c.setVal();
			if ( c.getValue() > highest_value ) {
				highest_value = c.getValue();
				highest_case = c;
			}
		}
		int pos[] = new int[2];
		pos[0] = highest_case.getPosX();
		pos[1] = highest_case.getPosY();
		return pos;
	}

	//déja récupérer dans le main
	@Override
	public void receiveNewStroke(int player_id, int[] stroke) {
		//On récupère les coordonnées du coup joué
		int x = stroke[0];
		int y = stroke[1];
		
		//On les place sur le plateau
		p.getCase(x, y).setStatut(player_id);
	}

}
