package ia_adversaire;

import java.util.ArrayList;
import java.util.List;

public class Plateau {
	
	private int maxX = 0;
	private int maxY = 0;
	private List<Quintuplet> quintupletsTT = new ArrayList<Quintuplet>();
	private List<Case> casesPlateau = new ArrayList<Case>();
	private Case plateau[][] = new Case[0][0];
	
	public Plateau(int posx, int posy) {
		maxX = posx-1;
		maxY = posy-1;
		this.plateau = new Case[posx][posy];
	}
	
	
	public Case getCase(int posx , int posy) {
		
		for(int i = 0; i < this.plateau.length; i++) {
			if (i == posx) {
				for(int j = 0; j < this.plateau[i].length; j++) {
					if (j == posy) {
						return plateau[i][j];
					}
				}
			}
		}
		return plateau[0][0];
	}
	
	public List<Quintuplet> initBoard(){
		
		//init cases
		for(int i = 0; i<maxY+1;i++) {
			for(int j = 0; j<maxX+1; j++) {
				Case c = new Case(j,i,0); 
				plateau[j][i] = c;
				casesPlateau.add(c);
			}
		}
		
		
		//int ii = 0;
		for(int i = 0; i < this.plateau.length; i++ ) {
			
			for(int j = 0; j < plateau[i].length ; j++) {
				Case c = plateau[i][j];
				
				//on verifie les cases par rapport à la droite
				if(c.getPosX() + 4 <= maxX) {
					Quintuplet q = new Quintuplet(c, plateau[i+1][j], plateau[i+2][j], plateau[i+3][j], plateau[i+4][j]);
					quintupletsTT.add(q);
					c.addQuint(q);
					plateau[i+1][j].addQuint(q);
					plateau[i+2][j].addQuint(q);
					plateau[i+3][j].addQuint(q);
					plateau[i+4][j].addQuint(q);
				}
				
				//on vérifie les cases par rapport au bas
				if(c.getPosY() + 4 <= maxY) {
					Quintuplet q = new Quintuplet(c, plateau[i][j+1], plateau[i][j+2], plateau[i][j+3], plateau[i][j+4]);
					quintupletsTT.add(q);
					c.addQuint(q);
					plateau[i][j+1].addQuint(q);
					plateau[i][j+2].addQuint(q);
					plateau[i][j+3].addQuint(q);
					plateau[i][j+4].addQuint(q);
				}
				
				//on vérifie maintenant les diagonales supérieures Bas-Droite 
				if(c.getPosX() + 4 <= maxX && c.getPosY() + 4 <= maxY) {
					Quintuplet q = new Quintuplet(c, plateau[i+1][j+1], plateau[i+2][j+2], plateau[i+3][j+3], plateau[i+4][j+4]);
					quintupletsTT.add(q);
					c.addQuint(q);
					plateau[i+1][j+1].addQuint(q);
					plateau[i+2][j+2].addQuint(q);
					plateau[i+3][j+3].addQuint(q);
					plateau[i+4][j+4].addQuint(q);
				}
				
				//on vérifie pour finir les diagonales supérieures Haut-Droite 
				if(c.getPosX() + 4 <= maxX && c.getPosY() - 4 >= 0) {
					Quintuplet q = new Quintuplet(c, plateau[i+1][j-1], plateau[i+2][j-2], plateau[i+3][j-3], plateau[i+4][j-4]);
					quintupletsTT.add(q);
					c.addQuint(q);
					plateau[i+1][j-1].addQuint(q);
					plateau[i+2][j-2].addQuint(q);
					plateau[i+3][j-3].addQuint(q);
					plateau[i+4][j-4].addQuint(q);
				}
			}

		}
		
		return quintupletsTT;
	}


	public List<Case> getCasesPlateau() {
		return casesPlateau;
	}
	
	public List<Quintuplet> getQuintupletTT(){
		return this.quintupletsTT;
	}
	
	public int getX() {
		return this.maxX;
	}
	public int getY() {
		return this.maxY;
	}
}