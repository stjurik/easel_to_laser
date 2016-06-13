/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.fabricator.easel_to_laser;

/**
 *
 * @author jura
 */
public enum Gcode {
    M3("M3 S12000"),
    M5("M5");
    private String content;
    
    private Gcode(String str){
        this.content = str;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String newContent){
        if(newContent.length()<2)
            return;
        this.content = newContent;
    }
    

}
