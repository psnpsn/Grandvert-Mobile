/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Service.ServiceTask;

/**
 *
 * @author bhk
 */
public class Categorie {

    Form f;
    SpanLabel lb;
  
    public Categorie() {
        
        f = new Form("Catégorie", new BoxLayout(BoxLayout.Y_AXIS));
                f.add(new Label("Fruit")).
                    add(new Label("Herbre")).
                    add(new Label("Fleur")).
                    add(new Label("Légume"));
                
                Toolbar tb = f.getToolbar();
                Image icon = null; 
                Container topBar = BorderLayout.east(new Label(icon));
                //topBar.add(BorderLayout.SOUTH, new Label("Grand Vert", "SidemenuTagline")); 
                topBar.setUIID("SideCommand");
                tb.addComponentToSideMenu(topBar);

                tb.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {}); 
                tb.addMaterialCommandToSideMenu("Site web", FontImage.MATERIAL_WEB, e -> {});
                tb.addMaterialCommandToSideMenu("Déconnecter", FontImage.MATERIAL_LOCK, e -> {});
                tb.addMaterialCommandToSideMenu("à propos", FontImage.MATERIAL_INFO, e -> {});                
                
                f.show();
          
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
