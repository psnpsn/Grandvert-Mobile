/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Entite.Plante;
import com.mycompany.Entite.Sujet;
import com.mycompany.Entite.User;
import com.mycompany.Service.ServiceTask;
import java.io.IOException;
import javafx.scene.control.ListView;

/**
 *
 * @author bhk
 */
public class ContactUser {

    Form f;
  
    public ContactUser(User u, Sujet sujet , Plante plante) {
        
        f = new Form("Contacter");
        
        TextField titre = new TextField("", "Tire de sujet");
        TextField Subject = new TextField("", "Sujet");
        Button btnenvoyer = new Button("Envoyer");
        btnenvoyer.addActionListener((evt) -> {
            if((Subject.getText().length()!=0) && (titre.getText().length()!=0) ){
                Message m = new Message(titre.getText());
                /*
                try {
                    Display.getInstance().sendSMS("+21653880881", "Hi rochdi it's me !!");
                } catch (IOException ex) {
                }
                */
                Display.getInstance().sendMessage(new String[] {"keeptooui@gmail.com"}, Subject.getText(), m);
                ToastBar.showMessage("Le message a été envoyer avec succés", FontImage.MATERIAL_INFO);
                ContactUser cu = new ContactUser(u , sujet , plante);
                cu.getF();    
            }
            else{
                ToastBar.showErrorMessage("Sujet ne doit pas etre vide !!");
            }
        });
        
        /*
        String[] Actions = { "Supprimer", "Resolu", "Fermer"};
        MultiButton mb = new MultiButton("Action");
        mb.addActionListener(e -> {
        Dialog d = new Dialog();
        d.setLayout(BoxLayout.y());
        d.getContentPane().setScrollableY(true);
        for(int i=0 ; i<Actions.length;i++){
            MultiButton choix = new MultiButton(Actions[i]);
            d.add(choix);
            choix.addActionListener(ee -> {
                mb.setTextLine1(mb.getTextLine1());
                mb.setTextLine2(mb.getTextLine2());
                mb.setIcon(mb.getIcon());
                d.dispose();
                mb.revalidate();
            });
        }
        
            d.showPopupDialog(mb);
        });
        
        f.add(mb);
        */
        
        f.add(titre);
        f.add(Subject);
        f.add(btnenvoyer);
        
                Toolbar tb = f.getToolbar();
                tb.addCommandToOverflowMenu("Déconnecter", null, (evt) -> {
                ConsulterSujet cs =new ConsulterSujet(sujet , plante);
                    cs.getF();
                });
                Image icon = null; 
                Container topBar = BorderLayout.east(new Label(icon));
                topBar.setUIID("SideCommand");

        try {
            tb.addCommandToLeftBar("", EncodedImage.create("/back.png"), (evt) -> {
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
