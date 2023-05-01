package ia_adversaire;

import java.util.ArrayList;
import java.util.List;

public class Quintuplet {

	public static final int PLAYER_WIN = -1;
	public static final int AI_WIN = -2;
	public static final int QUINTUPLET_CLOSED = 0;
	public static final int QUINTUPLET_EMPTY = 1;
	public static final int QUINTUPLET_AI = 6;
	public static final int QUINTUPLET_PLAYER = 5; 
	public static final int QUINTUPLET_AI2 = 10; 
	public static final int QUINTUPLET_AI3 = 25; 
	public static final int QUINTUPLET_AI4 = 1000; 
	public static final int QUINTUPLET_PLAYER2 = 7; 
	public static final int QUINTUPLET_PLAYER3 = 35; 
	public static final int QUINTUPLET_PLAYER4 = 100; 
	
	
	private int value = 0;
	private List<Case> cases = new ArrayList<Case>();
	private boolean open = true;
	
	public Quintuplet(Case c1, Case c2,Case c3,Case c4,Case c5) {
		cases.add(c1);
		cases.add(c2);
		cases.add(c3);
		cases.add(c4);
		cases.add(c5);
	}
	
	public void setNewVal(int numOrdi) {
		int nbS = 0;
		int numS = 0;
		for(Case c : cases) {
			if(c.getStatut() != 0) {
				if(nbS != 0 && c.getStatut() != numS) { //deux case de deux symbole différent
					open = false;
					value = QUINTUPLET_CLOSED;
					break;
				}
				
				if(nbS == 0) {  //premiére case avec un symbole
					numS = c.getStatut();
				}
				
				nbS++;  // deux case de meme symbole
				
			}
		}
		if(open) {			//calcule valeur
			if(nbS == 0){
				value = QUINTUPLET_EMPTY;
			}
			else {
				if(numS == numOrdi) {
					switch(nbS) { // switch pour varier les valeurs des quintuplets par rapport au nb de symbole côté AI / Player
						case 1 : 
							value = QUINTUPLET_AI;
							break;
						case 2 : 
							value = QUINTUPLET_AI2;
							break;
						case 3 : 
							value = QUINTUPLET_AI3;
							break;
						case 4 : 
							value = QUINTUPLET_AI4;
							break;
						case 5 :
							value = AI_WIN;
							break;
					}
				}
				else {
					switch(nbS) {
					case 1 : 
						value = QUINTUPLET_PLAYER;
						break;
					case 2 : 
						value = QUINTUPLET_PLAYER2;
						break;
					case 3 : 
						value = QUINTUPLET_PLAYER3;
						break;
					case 4 : 
						value = QUINTUPLET_PLAYER4;
						break;
					case 5 : 
						value = PLAYER_WIN;
						break;
					}						
				}
			}	
		}
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int x) {
		this.value = x;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public List<Case> getCases(){
		return this.cases;
	}
	
	public void reinit() {
		open = true;
		this.setValue(QUINTUPLET_EMPTY);
		
	}
	
}