/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.components.WebBrowser;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
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
import com.mycompany.Entite.StageManager;
import com.mycompany.Entite.Sujet;
import com.mycompany.Entite.User;
import com.mycompany.Service.ServicePlante;
import com.mycompany.Service.ServiceSujet;
import com.mycompany.Service.ServiceTask;
import com.mycompany.Service.ServiceUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ListView;


/**
 *
 * @author bhk
 */
public class ListSujet {

    Form f;
    SpanLabel lb;
    Plante Selectplante;
    TextField TextSujet;
    Button btnajout;
    StageManager sm = StageManager.getStageManager();
    com.codename1.ui.List list;
    ComboBox<String> Action = new ComboBox<>();

    public ListSujet(Plante plante) {

        Selectplante = plante;
        ServiceSujet ss = new ServiceSujet();
        ArrayList<Sujet> listsujet = new ArrayList<>(ss.getList(plante.getId()));
        list = new com.codename1.ui.List(createGenericListCellRendererModelData());
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer()));
        list.addActionListener((evt) -> {
            DefaultListModel dlm=(DefaultListModel)Action.getModel();
            dlm.removeAll();
            if(listsujet.get(list.getSelectedIndex()).getUser().getId() == sm.getUser().getId()){
                if(listsujet.get(list.getSelectedIndex()).getResolu().equals("true"))
                    Action.addItem("Non Resolu");
                else
                    Action.addItem("Resolu");

                if(listsujet.get(list.getSelectedIndex()).getOpen().equals("true"))
                    Action.addItem("Fermer");
                else
                    Action.addItem("Ouvert");   
                
                Action.addItem("Supprimer");
            }
            else
               Action.addItem("Signaler"); 
        });
        f = new Form("Sujets");
        
        TextSujet = new TextField("","Publier sujet");
        btnajout = new Button("ajouter");
        btnajout.addActionListener((evt) -> {
            if(TextSujet.getText().length()!=0){
                ss.ajoutSujet(new Sujet(plante.getId(), new User(3, "", "", "", ""), TextSujet.getText()));
                ToastBar.showMessage("Le sujet a été ajouter avec succés", FontImage.MATERIAL_INFO);
                ListSujet l = new ListSujet(plante);
                l.getF();    
            }
            else{
                ToastBar.showErrorMessage("Sujet ne doit pas etre vide !!");
            }
        });
        Action.addActionListener((evt) -> {
            ServiceSujet ssAction = new ServiceSujet();
            if(Action.getSelectedItem().equals("Supprimer")){
                    ssAction.supprimerSujet(listsujet.get(list.getSelectedIndex()));
                    ToastBar.showMessage("Le sujet a été Supprimer avec succés", FontImage.MATERIAL_INFO);
                    refresh();
                
            }    
            else if(Action.getSelectedItem().equals("Non Resolu")){
                ss.SetNonResolu(listsujet.get(list.getSelectedIndex()), sm.getUser().getId());
                ToastBar.showMessage("L'etat de sujet a été changer avec succés", FontImage.MATERIAL_INFO);
                refresh();        
            }    
            else if(Action.getSelectedItem().equals("Resolu")){
                ss.SetResolu(listsujet.get(list.getSelectedIndex()), sm.getUser().getId());
                ToastBar.showMessage("L'etat de sujet a été changer avec succés", FontImage.MATERIAL_INFO);
                refresh();
            }    
            else if(Action.getSelectedItem().equals("Fermer")){
                ss.SetFermer(listsujet.get(list.getSelectedIndex()), sm.getUser().getId()); 
                ToastBar.showMessage("L'etat de sujet a été changer avec succés", FontImage.MATERIAL_INFO);
                refresh();
            }    
            else if(Action.getSelectedItem().equals("Ouvert")){
                ss.SetOuvert(listsujet.get(list.getSelectedIndex()), sm.getUser().getId());
                    ToastBar.showMessage("L'etat de sujet a été changer avec succés", FontImage.MATERIAL_INFO);
                refresh();
            }    
            else if(Action.getSelectedItem().equals("Signaler")){
                ss.signalerSujet(listsujet.get(list.getSelectedIndex()));
                ToastBar.showMessage("Le sujet a été signaler avec succés", FontImage.MATERIAL_INFO);
                refresh();    
            }    
        });
        
        f.add(TextSujet);
        
        f.add(btnajout);
        f.add(Action);
        f.add(list);  
        
        
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
                    PlanteCategorie ls =new PlanteCategorie(plante.getCategorie());
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
    ServiceSujet ss2 = new ServiceSujet();
    ArrayList<Sujet> listsujet = new ArrayList<>(ss2.getList(Selectplante.getId()));
    
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
    surname.addPointerPressedListener((evt) -> {
        ConsulterSujet cs = new ConsulterSujet(listsujet.get(list.getSelectedIndex()) , Selectplante);
        cs.getF();
    });
    
    Label image = new Label();
    image.setName("image");
    image.setFocusable(true);
    
    Label etatSujet = new Label();
    etatSujet.setFocusable(true);
    etatSujet.setName("etatSujet");
    
    Label nbView = new Label();
    nbView.setFocusable(true);
    nbView.setName("nbView");
    
    ComboBox<String> cm = new ComboBox<>("Supprimer", "Resolu", "Fermer");
    cm.setFocusable(true);
    cm.setName("action");
    cm.addActionListener((evt) -> {  
        ss2.supprimerSujet(listsujet.get(list.getSelectedIndex()));
        ToastBar.showMessage("Le sujet a été supprimer avec succés", FontImage.MATERIAL_INFO);

        ListSujet ls = new ListSujet(Selectplante);
        ls.getF();
    });
   
    Container blocketatsujet = BorderLayout.center(etatSujet).
        add(BorderLayout.SOUTH, nbView);
    /*    
    Container blockright = BorderLayout.center(blocketatsujet).
            add(BorderLayout.EAST, cm);
    */    
    Container c = BorderLayout.center(name).
            add(BorderLayout.SOUTH, surname).
            add(BorderLayout.WEST, image).
            add(BorderLayout.EAST, blocketatsujet);
    c.setUIID("ListRenderer");
    return c;
}

private Object[] createGenericListCellRendererModelData() {
    
    ServiceSujet ss = new ServiceSujet();
    Map<String,Object>[] data = new HashMap[ss.getList(Selectplante.getId()).size()];
    int i=0;
    for(Sujet p : ss.getList(Selectplante.getId())){
        
        data[i] = new HashMap<>();
        ServiceUser su = new ServiceUser();
        
        data[i].put("Name", su.getById(p.getUser().getId()).getNom() + " "+ su.getById(p.getUser().getId()).getPrenom());
        data[i].put("Surname", p.getSujet_original());

        try {
            data[i].put("image", new Label(EncodedImage.create("/user.jpeg").scaledEncoded(300, 300)));
            //data[i].put("action", new MultiButton("Action"));
            if(p.getResolu().equals("true"))
                data[i].put("etatSujet", new Label(EncodedImage.create("/resolut.png").scaled(100, 100)));
            else
                data[i].put("etatSujet", new Label(EncodedImage.create("/nonresolut.png").scaled(100, 100)));
            
            data[i].put("nbView", new Label(p.getNbviews()+"",EncodedImage.create("/view.png").scaledEncoded(100, 100)));

        } catch (IOException ex) {
        }

        i++;
    }
        

    return data;
}

    public void refresh(){
        ListSujet ls = new ListSujet(Selectplante);
        ls.getF();
    } 

}
