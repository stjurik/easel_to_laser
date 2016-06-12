/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.fabricator.easel_to_laser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import static me.fabricator.easel_to_laser.Gcode.*;

/**
 *
 * @author jura
 */
public class Translator {
    private static int lineNumber = 0;
    private static boolean laserOn = false;
    private static Logger log = Logger.getLogger(Translator.class.getName());
    
    
    public static String translate(String codeLine){
       
        lineNumber++;
        
        String z = "Z";
        if(codeLine.contains(z)){
            //System.out.println("Z= "+codeLine.charAt(codeLine.indexOf(z)+1));
            if(codeLine.charAt(codeLine.indexOf(z)+1) != '-'){
                laserOn = false;
                log.log(Level.INFO, "M5 added at line#"+lineNumber);
                return M5.getContent();
            } else if(!laserOn){
                laserOn = true;
                log.log(Level.INFO, "M3 addedat line#"+lineNumber);
                return M3.getContent();
            }
            
            return "";
        }
        return codeLine;
    }
    public static void readFile(File fin) {
	FileInputStream fis;
        
        try {
            fis = new FileInputStream(fin);
                    String location = fin.getAbsolutePath();
        
        File changedFile = new File(fin.getAbsolutePath().substring(0, fin.getAbsolutePath().lastIndexOf('.'))+"_laser.nc");
        BufferedWriter writer = new BufferedWriter(new FileWriter(changedFile));
 	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
 
	String line = null;
	while ((line = br.readLine()) != null) {
            writer.write(translate(line)+"\n");
	}
 	br.close();
        writer.close();
        ErrorWindow.display(fin.getName()+" was successfully converted!");
        } catch (Exception ex) {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE, null, ex);
        }

}
}
