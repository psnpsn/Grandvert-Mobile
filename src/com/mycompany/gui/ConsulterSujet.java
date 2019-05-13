/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.components.WebBrowser;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.table.TableLayout;
import com.mycompany.Entite.Plante;
import com.mycompany.Entite.Reaction_Reponse;
import com.mycompany.Entite.Reaction_Sujet;
import com.mycompany.Entite.Reponse;
import com.mycompany.Entite.StageManager;
import com.mycompany.Entite.Sujet;
import com.mycompany.Entite.User;
import com.mycompany.Service.ServicePlante;
import com.mycompany.Service.ServiceReactionReponse;
import com.mycompany.Service.ServiceReactionSujet;
import com.mycompany.Service.ServiceReponse;
import com.mycompany.Service.ServiceSujet;
import com.mycompany.Service.ServiceTask;
import com.mycompany.Service.ServiceUser;
import static com.sun.javafx.fxml.expression.Expression.add;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ListView;


/**
 *
 * @author bhk
 */
public class ConsulterSujet {

    Form f;
    SpanLabel lb;
    Sujet selected_sujet;
    Plante selected_plante;
    com.codename1.ui.List list;
    com.codename1.ui.List listsujet;
    TextField TextReponse;
    Button btnajout;
    Button btnmodifier;

    StageManager sm = StageManager.getStageManager();
    EncodedImage ImgEtatSujet = null;
    Label etatFermer = null ;
    ComboBox<String> ActionSujet = new ComboBox<>();
    ComboBox<String> ActionReponse = new ComboBox<>();

    public ConsulterSujet(Sujet sujet , Plante plante) {
        
        selected_sujet = sujet;
        selected_plante = plante;
        ServiceSujet ss = new ServiceSujet();
        ServiceReponse sr = new ServiceReponse();
        ArrayList<Reponse> listrep = new ArrayList<Reponse>(sr.getList(selected_sujet.getId()));
        
        //********** Combobox Sujet *******************/
        if(selected_sujet.getUser().getId() == sm.getUser().getId()){
            if(selected_sujet.getResolu().equals("true"))
                ActionSujet.addItem("Non Resolu");
            else
                ActionSujet.addItem("Resolu");

        if(selected_sujet.getOpen().equals("true"))
            ActionSujet.addItem("Fermer");
        else
            ActionSujet.addItem("Ouvert");   
                
        ActionSujet.addItem("Supprimer");
        }
        else
            ActionSujet.addItem("Signaler"); 
           
        
        f = new Form("Réponse");
        if(sujet.getResolu().equals("true"))
            try {
                ImgEtatSujet= (EncodedImage) EncodedImage.create("/sujet_resolut.png").scaled(1600, 200);
                f.add(ImgEtatSujet);
        } catch (IOException ex) {
        }
        
        if(sujet.getOpen().equals("false")){
            etatFermer = new Label("Sujet Fermer !!                                  ");
            etatFermer.getAllStyles().setFgColor(0xef5350);
            etatFermer.setSize(new Dimension(30, 30));
            f.add(etatFermer);
        }
        listsujet = new com.codename1.ui.List(createGenericListCellRendererModelDataSujet());
        listsujet.setRenderer(new GenericListCellRenderer(createGenericRendererContainerSujet(), createGenericRendererContainerSujet()));

        list = new com.codename1.ui.List(createGenericListCellRendererModelData());
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer()));
        list.addActionListener((evt) -> {
            //********** Combobox Reponse *******************/
            DefaultListModel dlm=(DefaultListModel)ActionReponse.getModel();
            dlm.removeAll();
            if(listrep.get(list.getSelectedIndex()).getUser().getId() == sm.getUser().getId()){
                ActionReponse.addItem("Modifier");
                ActionReponse.addItem("Supprimer");
            }    
            else
                ActionReponse.addItem("Signaler");      
            });
        
        ActionSujet.addActionListener((evt) -> {
            ServiceSujet ssAction = new ServiceSujet();
            if(ActionSujet.getSelectedItem().equals("Supprimer")){
                    ssAction.supprimerSujet(selected_sujet);
                    ToastBar.showMessage("Le sujet a été Supprimer avec succés", FontImage.MATERIAL_INFO);
                    refresh();  
            }    
            else if(ActionSujet.getSelectedItem().equals("Non Resolu")){
                ss.SetNonResolu(selected_sujet, sm.getUser().getId());
                ToastBar.showMessage("L'etat de sujet a été changer avec succés", FontImage.MATERIAL_INFO);
                refresh();        
            }    
            else if(ActionSujet.getSelectedItem().equals("Resolu")){
                ss.SetResolu(selected_sujet, sm.getUser().getId());
                ToastBar.showMessage("L'etat de sujet a été changer avec succés", FontImage.MATERIAL_INFO);
                refresh();
            }    
            else if(ActionSujet.getSelectedItem().equals("Fermer")){
                ss.SetFermer(selected_sujet, sm.getUser().getId()); 
                ToastBar.showMessage("L'etat de sujet a été changer avec succés", FontImage.MATERIAL_INFO);
                refresh();
            }    
            else if(ActionSujet.getSelectedItem().equals("Ouvert")){
                ss.SetOuvert(selected_sujet, sm.getUser().getId());
                ToastBar.showMessage("L'etat de sujet a été changer avec succés", FontImage.MATERIAL_INFO);
                refresh();
            }    
            else if(ActionSujet.getSelectedItem().equals("Signaler")){
                ss.signalerSujet(selected_sujet);
                ToastBar.showMessage("Le sujet a été signaler avec succés", FontImage.MATERIAL_INFO);
                refresh();    
            }            
        });
        
        ActionReponse.addActionListener((evt) -> {
            if(ActionReponse.getSelectedItem().equals("Supprimer")){
                    sr.supprimerReponse(listrep.get(list.getSelectedIndex()));
                    ToastBar.showMessage("La réponse a été Supprimer avec succés", FontImage.MATERIAL_INFO);
                    refresh();
            } 
            if(ActionReponse.getSelectedItem().equals("Modifier")){
                TextReponse.setVisible(true);
                btnajout.setVisible(false);
                btnmodifier.setVisible(true);
                TextReponse.setText(listrep.get(list.getSelectedIndex()).getReponse_original());  
            }   
            else if(ActionReponse.getSelectedItem().equals("Signaler")){
                System.out.println("sleected to signaler :"+listrep.get(list.getSelectedIndex()));
                sr.signalerReponse(listrep.get(list.getSelectedIndex()), selected_sujet.getId());
                ToastBar.showMessage("La réponse a été signaler avec succés", FontImage.MATERIAL_INFO);
                refresh();    
            }            
        });        
        
        
        TextReponse = new TextField("","Publier Réponse");
        btnajout = new Button("Repondre");
        btnmodifier = new Button("Modifier");
        btnmodifier.setVisible(false);
        btnajout.addActionListener((evt) -> {
            if(TextReponse.getText().length()!=0){
                sr.ajoutReponse(new Reponse(selected_sujet, sm.getUser(), TextReponse.getText()));
                ToastBar.showMessage("La réponse a été ajouter avec succés", FontImage.MATERIAL_INFO);
                ConsulterSujet cs = new ConsulterSujet(selected_sujet , plante);
                cs.getF();
            }
            else{
                ToastBar.showErrorMessage("reponse ne doit pas etre vide !!");
            }
        });
        
        btnmodifier.addActionListener((evt) -> {
            if(TextReponse.getText().length()!=0){
                Reponse r = listrep.get(list.getSelectedIndex());
                r.setReponse_original(TextReponse.getText());
                sr.modifierReponse(r , selected_sujet.getId());
                ToastBar.showMessage("La réponse a été modifier avec succés", FontImage.MATERIAL_INFO);
                ConsulterSujet cs = new ConsulterSujet(selected_sujet , plante);
                cs.getF();
            }
            else{
                ToastBar.showErrorMessage("reponse ne doit pas etre vide !!");
            }
        });
        f.add(ActionSujet);
        f.add(listsujet);
        f.add(ActionReponse);
        f.add(list);  
        f.add(TextReponse);

        if(selected_sujet.getOpen().equals("true")){
            f.add(btnajout);
            f.add(btnmodifier);
        }
        else
            TextReponse.setVisible(false);

            Toolbar tb = f.getToolbar();
            tb.addCommandToOverflowMenu("Déconnecter", null, (evt) -> {
                HomeForm f =new HomeForm();
                f.getHi();
            });
                Image icon = null; 
                Container topBar = BorderLayout.east(new Label(icon));
                topBar.setUIID("SideCommand");
                
            try {
                tb.addCommandToLeftBar("", EncodedImage.create("/back.png").scaledEncoded(100, 100), (evt) -> {
                    ListSujet ls =new ListSujet(plante);
                    ls.getF();
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
   
private Container createGenericRendererContainer() {
    Label name = new Label();
    name.addPointerPressedListener((evt) -> {
        System.out.println(name.getText()); 
    });
    name.setFocusable(true);
    name.setName("Name");
    
    //WebBrowser surname = new WebBrowser();
    Label surname = new Label();
    surname.setFocusable(true);
    surname.setName("Surname");
    
    Label image = new Label();
    image.setName("image");
    image.setFocusable(true);

    
    ServiceReponse sr2 = new ServiceReponse();
    ArrayList<Reponse> listreponse = new ArrayList<>(sr2.getList(selected_sujet.getId()));
    
    Label like = new Label("like");

    like.setName("like");
    like.setFocusable(true);
    like.addPointerPressedListener((evt) -> {
        ServiceReponse sr = new ServiceReponse();
        ServiceReactionReponse srr = new ServiceReactionReponse();
        Reaction_Reponse rr = new Reaction_Reponse(sr.getList(selected_sujet.getId()).get(list.getSelectedIndex()), sm.getUser(), "like");
        srr.setLike(rr, selected_sujet.getId());
        ConsulterSujet cs = new ConsulterSujet(selected_sujet,selected_plante);
        cs.getF();
    });
    
    Label dislike = new Label("dislike");

    dislike.setName("dislike");
    dislike.setFocusable(true);
    dislike.addPointerPressedListener((evt) -> {
        ServiceReponse sr = new ServiceReponse();
        ServiceReactionReponse srr = new ServiceReactionReponse();
        Reaction_Reponse rr = new Reaction_Reponse(sr.getList(selected_sujet.getId()).get(list.getSelectedIndex()), sm.getUser(), "dislike");
        srr.setDisLike(rr, selected_sujet.getId());
        ConsulterSujet cs = new ConsulterSujet(selected_sujet , selected_plante);
        cs.getF();
    });
    
    Label contact = new Label("contact");

    contact.setName("contact");
    contact.setFocusable(true);
    contact.addPointerPressedListener((evt) -> {
        ContactUser cu = new ContactUser(new User("rochdi", "arafa", "keeptooui@gmail.com", "image") , selected_sujet , selected_plante);
        cu.getF();  
    });
    
    Container reactionc = BorderLayout.center(dislike).
        add(BorderLayout.EAST, contact).
        add(BorderLayout.WEST, like);
    
    Container imageEtSujet = BorderLayout.center(surname).
        add(BorderLayout.WEST, image);
    
    Container c = BorderLayout.center(imageEtSujet).
        add(BorderLayout.SOUTH, reactionc).
        add(BorderLayout.NORTH, name);
        //add(BorderLayout.EAST, cm);

    
    c.setUIID("ListRenderer");
    return c;
}

private Object[] createGenericListCellRendererModelData() {
    
    ServiceReponse sr = new ServiceReponse();
    Map<String,Object>[] data = new HashMap[sr.getList(selected_sujet.getId()).size()];
    int i=0;
    for(Reponse p : sr.getList(selected_sujet.getId())){
        
        data[i] = new HashMap<>();
        ServiceUser su = new ServiceUser();

        data[i].put("Name", su.getById(p.getUser().getId()).getNom() + " "+ su.getById(p.getUser().getId()).getPrenom());
        
        data[i].put("Surname", p.getReponse_original());

        try {
            data[i].put("image", new Label(EncodedImage.create("/user.jpeg").scaled(300, 300)));
            ServiceReactionReponse srr = new ServiceReactionReponse();
            
            int nbLike =0;
            int nbDisLike =0;
            for(Reaction_Reponse rr  : srr.getList(p.getId())){
                if(rr.getReaction().equals("like"))
                    nbLike++;
                else
                    nbDisLike++;
            }
            
            Label like = new Label(nbLike+"" ,EncodedImage.create("/Like.png").scaled(90, 90));
            
            Label dislike = new Label(nbDisLike+"" ,EncodedImage.create("/Deslike2.png").scaled(90, 90));

            data[i].put("like", like);
            data[i].put("dislike", dislike);
            data[i].put("contact", new Label(EncodedImage.create("/email.png").scaled(120, 100)));

        } catch (IOException ex) {
        }

        i++;
    }

    return data;
}

  
private Container createGenericRendererContainerSujet() {
    Label name = new Label();
    name.addPointerPressedListener((evt) -> {
        System.out.println(name.getText()); 
    });
    name.setFocusable(true);
    name.setName("Name");
    
    //WebBrowser surname = new WebBrowser();
    Label surname = new Label();
    surname.setFocusable(true);
    surname.setName("Surname");
    
    Label image = new Label();
    image.setName("image");
    image.setFocusable(true);

    ComboBox<String> cm = new ComboBox<>();
    cm.setFocusable(true);
    cm.setName("action");
    cm.addItem("Supprimer");
    cm.addItem("Resolu");
    cm.addItem("Fermer");
    cm.addActionListener((evt) -> {
        System.out.println(cm.getSelectedItem());
    });
    
    
    Label like = new Label("like");

    like.setName("like");
    like.setFocusable(true);
    like.addPointerPressedListener((evt) -> {
        ServiceSujet ss = new ServiceSujet();
        ServiceReactionSujet srs = new ServiceReactionSujet();
        Reaction_Sujet rr = new Reaction_Sujet(selected_sujet, sm.getUser(), "like");
        srs.setLike(rr);
        ConsulterSujet cs = new ConsulterSujet(selected_sujet , selected_plante);
        cs.getF();
    });
    
    Label dislike = new Label("dislike");

    dislike.setName("dislike");
    dislike.setFocusable(true);
    dislike.addPointerPressedListener((evt) -> {
        ServiceSujet ss = new ServiceSujet();
        ServiceReactionSujet srs = new ServiceReactionSujet();
        Reaction_Sujet rr = new Reaction_Sujet(selected_sujet, sm.getUser(), "dislike");
        srs.setDisLike(rr);
        ConsulterSujet cs = new ConsulterSujet(selected_sujet , selected_plante);
        cs.getF();
    });
    
    Label share = new Label("share");

    share.setName("share");
    share.setFocusable(true);
    share.addPointerPressedListener((evt) -> {

        Display.getInstance().execute("https://www.facebook.com/sharer/sharer.php?u="+"http://127.0.0.1/GrandVert/web/app_dev.php/forum/sujet/consulter?id="+selected_sujet.getId());
    });
    
    Container reactionc = BorderLayout.center(dislike).
        add(BorderLayout.EAST, share).
        add(BorderLayout.WEST, like);
    
    Container imageEtSujet = BorderLayout.center(surname).
        add(BorderLayout.WEST, image);
    
    Container c = BorderLayout.center(imageEtSujet).
        add(BorderLayout.SOUTH, reactionc).
        add(BorderLayout.NORTH, name);
        //add(BorderLayout.EAST, cm);

    
    c.setUIID("ListRenderer");
    return c;
}

private Object[] createGenericListCellRendererModelDataSujet() {
    
    Sujet p = selected_sujet;
    Map<String,Object>[] data = new HashMap[1];
        data[0] = new HashMap<>();
        ServiceUser su = new ServiceUser();

        data[0].put("Name", su.getById(p.getUser().getId()).getNom() + " "+ su.getById(p.getUser().getId()).getPrenom());
        data[0].put("Surname", p.getSujet_original());

        try {
            Label image = new Label(EncodedImage.create("/user.jpeg").scaled(300, 300));
            data[0].put("image",image );
            ServiceReactionSujet srr = new ServiceReactionSujet();
            
            int nbLike =0;
            int nbDisLike =0;
            for(Reaction_Sujet rr  : srr.getList(p.getId())){
                if(rr.getReaction().equals("like"))
                    nbLike++;
                else
                    nbDisLike++;
            }
            
            Label like = new Label(nbLike+"" ,EncodedImage.create("/Like.png").scaled(90, 90));
                      
            Label dislike = new Label(nbDisLike+"" ,EncodedImage.create("/Deslike2.png").scaled(90, 90));
  
            data[0].put("like", like);
            data[0].put("dislike", dislike);
            data[0].put("share", new Label(EncodedImage.create("/fb.png").scaled(180, 100)));
            
            ComboBox<String> cm = new ComboBox<>();
            cm.setFocusable(true);
            cm.addItem("Supprimer");
            cm.addItem("Resolu");
            cm.addItem("Fermer");
            data[0].put("action", new ComboBox<>());
    
        } catch (IOException ex) {
        }

    return data;
}
    public void refresh(){
        ConsulterSujet ls = new ConsulterSujet(selected_sujet, selected_plante);
        ls.getF();
    } 
}
