/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jframeexamen;
import java.awt.Color;
/**
 *
 * @author Rob
 */
public class RandomText {
    public String texto;
    public Color col;
    public int posX;
    public int posY;
    public int cont;
    
    public RandomText(){
        texto = "wow";
        col = Color.black;
        posX = 50;
        posY = 50;
        cont = 0;
    }
    
    public RandomText(String s, Color c, int px, int py){
        texto = s;
        col = c;
        posX = px;
        posY = py;
        cont = 0; 
    }
}
