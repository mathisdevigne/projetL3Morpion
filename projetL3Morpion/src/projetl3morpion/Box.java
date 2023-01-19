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
    private int id;
    private int value; //1 pour le joueur et 6 pour l'adversaire (IA)
    private int weight;
    private boolean last;
    private int[] used;
    
    public Box(int id){
        this.id = id;
        this.value = 0;
        this.weight = 0;
        this.last = false;
        this.used = new int[8];
        for(int i = 0; i < 8; i++){
           this.used[i] = -1; 
        }
        
    }
    
    //Accesseur de value
    public int getValue(){
        return this.value;
    }
    
    //Accesseur de weight
    public int getWeight(){
        return this.weight;
    }
    
    //Accesseur de used
    public boolean isUsed(int[] ids){
        for(int i = 0; i < this.used.length; i++){
            for(int j = 0; j < ids.length; j++){
                if(this.used[i] == ids[j] && this.used[i] != -1)
                    return true;
            }  
        }
        return false;
    }
    
    public boolean isUsed(){
        for(int i = 0; i < this.used.length; i++){
            if(this.used[i] != -1)
                return true;
        }
        return false;
    }
    
    public int[] getUsed(){
        return this.used;
    }
    
    //Accesseur de id
    public int getId(){
        return this.id;
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
    
    //Setter de used
    public void setUsed(int v){
        for(int i = 0; i < this.used.length; i++){
            if(this.used[i] == -1){
                this.used[i] = v;
                break;
            }
        }
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
            default -> {}
        }
    }
}
