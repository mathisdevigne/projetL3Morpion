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
    private String value;
    private int weight;
    
    public Box(){
        this.value = " ";
        this.weight = 0;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public int getWeight(){
        return this.weight;
    }
    
    public void setValue(String newVal){
        this.value = newVal;
    }
    
    public void setWeight(int newWeight){
        this.weight = newWeight;
    }
    
    public void print(){
        System.out.print(this.getValue());
    }
}
