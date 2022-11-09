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
    private int weight;
    
    public Box(){
        this.value = 0;
        this.weight = 0;
    }
    
    //Accesseur de value
    public int getValue(){
        return this.value;
    }
    
    //Accesseur de weight
    public int getWeight(){
        return this.weight;
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
        switch (this.value) {
            case 0 -> System.out.print(" ");
            case 1 -> System.out.print("O");
            case 6 -> System.out.print("X");
            default -> {
            }
        }
    }
}
