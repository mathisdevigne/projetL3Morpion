package ia_adversaire;

import java.util.ArrayList;
import java.util.List;

public class Symbol {
	//EMPTY(0,' '),CIRCLE(1,'O'),CROSS(2,'X'),lettreA(3,'A');

	static private List<Symbol> liste = new ArrayList<Symbol>();
	
	static private boolean filled = false;
	
	private int val;
	private char charac;
	
	Symbol(int nb, char c) {
		val = nb;
		charac = c;
		liste.add(this);
	}
	
	public int getVal() {
		return this.val;
	}
	
	/*private static void Fill() {
		if(!filled) {
			liste.add(EMPTY);
			liste.add(CIRCLE);
			liste.add(CROSS);
			liste.add(lettreA);
		}
	}*/
	
	public static Symbol getForm(int nb) {
		
		for(Symbol s : liste) {
			if(s.val == nb) {
				return s;
			}
		}
		return null;
		
	}
	
	public char getChar() {
		
		return charac;
	}
	
}