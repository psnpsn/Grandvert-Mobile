/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Service.ServiceTask;
import java.io.IOException;
import javafx.scene.control.ListView;

/**
 *
 * @author bhk
 */
public class Categorie {

    Form f;
    SpanLabel lb;
  
    public Categorie() {
        
        f = new Form("Categorie" , new BorderLayout());
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        MultiButton Fruit = new MultiButton("Fruit");
        Fruit.setTextLine2("");
        
        Fruit.addActionListener((evt) -> {
            PlanteCategorie pc = new PlanteCategorie("Fruit");
            pc.getF();
            
        });
        MultiButton Herbre = new MultiButton("Herbre");
        Herbre.setTextLine2("");
        Herbre.addActionListener((evt) -> {
            PlanteCategorie pc = new PlanteCategorie("Herbre");
            pc.getF();
        });
        MultiButton Fleur = new MultiButton("Fleur");
        Fleur.setTextLine2("");
        Fleur.addActionListener((evt) -> {
            PlanteCategorie pc = new PlanteCategorie("Fleur");
            pc.getF();
        });
        MultiButton Legume = new MultiButton("Légume");
        Legume.setTextLine2("");
        Legume.addActionListener((evt) -> {
            PlanteCategorie pc = new PlanteCategorie("Légume");
            pc.getF();
        });
        list.add(Fruit);
        list.add(Herbre);
        list.add(Fleur);
        list.add(Legume);
        f.add(CENTER , list);
        
                Toolbar tb = f.getToolbar();
                tb.addCommandToOverflowMenu("Déconnecter", null, (evt) -> {
                HomeForm f =new HomeForm();
                    f.getHi();
                });
                Image icon = null; 
                Container topBar = BorderLayout.east(new Label(icon));
                //topBar.add(BorderLayout.SOUTH, new Label("Grand Vert", "SidemenuTagline")); 
                topBar.setUIID("SideCommand");

        try {
            tb.addCommandToLeftBar("", EncodedImage.create("/back.png").scaledEncoded(100, 100), (evt) -> {
                HomeForm f =new HomeForm();
                f.getHi();
            });
        } catch (IOException ex) {
        }
                f.show();
          
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
