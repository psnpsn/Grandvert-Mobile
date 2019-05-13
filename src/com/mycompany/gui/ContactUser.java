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
//import com.codename1.messaging.Message;
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
import com.mycompany.Entite.SendGrid;
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
                
                Message ms = new Message(Subject.getText());
                Display.getInstance().sendMessage(new String[]{u.getEmail()}, Subject.getText(), ms);
                SendGrid s = SendGrid.create("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");
                
                s.sendSync(u.getEmail(),"rachid.arafa@esprit.tn", titre.getText(), Subject.getText());
                ToastBar.showMessage("L'email a été envoyer avec succés", FontImage.MATERIAL_INFO);
                ContactUser cu = new ContactUser(u , sujet , plante);
                cu.getF();    
            }
            else{
                ToastBar.showErrorMessage("Sujet ne doit pas etre vide !!");
            }
        });
        
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
