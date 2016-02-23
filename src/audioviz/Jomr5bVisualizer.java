/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Jared
 */
public class Jomr5bVisualizer implements Visualizer
{
    private final String name = "J-Squiggle";
    
    private Integer numBands = 0;
    private AnchorPane pane = null;
    private Canvas canvas = null;
    private GraphicsContext context = null;
    private Double width;
    private Double height;
    

    @Override
    public void start(Integer numBands, AnchorPane vizPane)
    {
        this.numBands = numBands;
        this.pane = vizPane;
        this.width = vizPane.getWidth();
        this.height = vizPane.getHeight();
        
        //ensure no children currently inhabit the pane
        this.pane.getChildren().clear();
        
        this.canvas = new Canvas(this.width, this.height);
        this.context = canvas.getGraphicsContext2D();
        this.pane.getChildren().add(canvas);
    }

    @Override
    public void end()
    {
        this.pane.getChildren().clear();
        
        //clear references to canvas
        canvas = null;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases)
    {
        if (magnitudes.length < this.numBands)
        {
            this.numBands = magnitudes.length;
        }
        
        this.context.setFill(Color.BLACK);
        this.context.fillRect(0, 0, this.width, this.height);
        
        //now update the Canvas with lines...
        
        this.context.setStroke(Color.AQUAMARINE);
        this.context.setLineWidth(3);
        this.context.beginPath();

        Reflection ref = new Reflection();
        this.context.setEffect(ref);
        
        Double hOffset = this.height / 2;
        Double mult = this.height / 100;
        
        if (numBands == 1)
        {
            this.context.moveTo(0, hOffset - (magnitudes[0] + 60) * mult);
            this.context.lineTo(this.width, hOffset - (magnitudes[0] + 60) * mult);
        }
        else
        {
            Double w = this.width / (this.numBands - 1);

            for (int i = 0; i < this.numBands; ++i)
            {
                double x = w * i;
                double y = hOffset - (magnitudes[i] + 60) * mult;

                this.context.lineTo(x, y);
            }

        }
        
        this.context.stroke();
        this.context.closePath();
    }
    
}
