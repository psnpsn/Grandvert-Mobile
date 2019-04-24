/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author psn
 */
public class StageManager {
    
    private static StageManager stager = new StageManager();
    private User user;
    
    private StageManager() {
    }
    
    public static StageManager getStageManager(){
        return stager;
    }
    
    public String getUsername(){
        return user.getUsername();
    }
    
    public String getRoles(){
        return user.getRoles();
    }
    
    public void setUser(User user){
        this.user=user;
    }
    
    public User getUser(){
        return this.user;
    }
    
    
    
}
