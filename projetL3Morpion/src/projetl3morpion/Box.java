/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

/**
 *
 * @author fetiveau
 */
public class Box { //Rajouter un cas pour les cases prises mais non joueur
    // Affichage
    // Constructeur avec un tab de bool
    // Prise en compte d'une image
    private int value; //1 pour le joueur et 6 pour l'adversaire (IA)
    private int weight;
    private boolean last;
    
    public Box(){
        this.value = 0;
        this.weight = 0;
        this.last = false;
    }
    
    //Accesseur de value
    public int getValue(){
        return this.value;
    }
    
    //Accesseur de weight
    public int getWeight(){
        return this.weight;
    }
    
    //Setter de last
    public void setLast(boolean newBool){
        this.last = newBool;
    }
    
    //Setter de value
    public void setValue(int newVal){
        this.value = newVal;
    }
    
    //Setter de weight
    public void setWeight(int newWeight){
        this.weight = newWeight;
    }
    
    //Affiche la value de box
    public void print(){
        
        if(this.last){
            System.out.print(ConsoleColors.GREEN);
            this.last = false;
        }
           
        
                
        switch (this.value) {
            // Case vide
            case 0 -> System.out.print(" ");
            //Case Joueur
            case 1 -> System.out.print("O");
            //Case IA
            case 6 -> System.out.print("X" + ConsoleColors.RESET);
            //Case pas util
            //case 50 -> System.out.print(ConsoleColors.RED_BACKGROUND+" "+ConsoleColors.RESET);
            default -> {}
        }
        System.out.print(this.weight < 0 ? ConsoleColors.RED+"N"+ConsoleColors.RESET : this.weight);
    }
}
