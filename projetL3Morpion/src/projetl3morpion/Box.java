/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

/**
 *
 * @author fetiveau
 */
public class Box {
    private int value; //1 pour le joueur et 6 pour l'adversaire (IA)
    private float weight;
    
    public Box(){
        this.value = 0;
        this.weight = 0f;
    }
    
    //Accesseur de value
    public int getValue(){
        return this.value;
    }
    
    //Accesseur de weight
    public float getWeight(){
        return this.weight;
    }
    
    //Setter de value
    public void setValue(int newVal){
        this.value = newVal;
    }
    
    //Setter de weight
    public void setWeight(float newWeight){
        this.weight = newWeight;
    }
    
    //Affiche la value de box
    public void print(){
        switch (this.value) {
            case 0 -> System.out.print(" ");
            case 1 -> System.out.print("O");
            case 6 -> System.out.print("X");
            default -> {
            }
        
        
        }
    //System.out.print(this.getWeight());
    }
}
