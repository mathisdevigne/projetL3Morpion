/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author theodusehu
 */
public class SliderBetter extends HBox {
    
    private Label l;
    private TextField tfValue;
    private Slider s;
    
    public SliderBetter(String sLabel, double min, double max, double value) {
        
        l = new Label("" + sLabel + " : ");
        l.setPadding(new Insets(0, 5, 0, 5));
        tfValue = new TextField();
        s = new Slider(min, max, value);
        
        s.setPadding(new Insets(0, 5, 0, 5));
        s.setShowTickLabels(true);
        s.setShowTickMarks(true);
        
        Bindings.bindBidirectional(tfValue.textProperty(), s.valueProperty(), new NumberStringConverter());
        
        this.getChildren().addAll(l, tfValue, s);
        
    }
    
    public Slider getSlider(){
        return this.s;
    }
    
    public double getMinSlider(){
        return s.getMin();
    }
    
    public double getMaxSlider(){
        return s.getMax();
    }
    
    public double getValSlider(){
        return s.getValue();
    }
    
    public DoubleProperty valueProperty(){
        return s.valueProperty();
    }
    
    public DoubleProperty valueMaxProperty(){
        return s.maxProperty();
    }
    
}
