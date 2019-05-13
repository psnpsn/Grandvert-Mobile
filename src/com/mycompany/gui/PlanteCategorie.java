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
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.table.TableLayout;
import com.mycompany.Entite.Plante;
import com.mycompany.Service.ServicePlante;
import com.mycompany.Service.ServiceSujet;
import com.mycompany.Service.ServiceTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ListView;


/**
 *
 * @author bhk
 */
public class PlanteCategorie {

    Form f;
    SpanLabel lb;
  
    public PlanteCategorie(String Categorie) {
                
        ServicePlante sp = new ServicePlante();
        ArrayList<Plante> lp = new ArrayList<Plante>(sp.getListPlantSelonCategorie(Categorie));
        
        com.codename1.ui.List list = new com.codename1.ui.List(createGenericListCellRendererModelData());
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer()));
        list.addActionListener((evt) -> {
            ListSujet ls = new ListSujet(lp.get(list.getSelectedIndex()));
            ls.getF();
        });
        f = new Form("Plante", new BorderLayout());
        f.add(BorderLayout.CENTER, list);
        
        
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
                    Categorie ls =new Categorie();
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
    SpanLabel surname = new SpanLabel();
    surname.setFocusable(true);
    surname.setName("Surname");
    Label image = new Label();
    image.setName("image");
    image.setFocusable(true);
    
    Label icon = new Label();
    icon.addPointerPressedListener((evt) -> {
    });
    icon.setFocusable(true);
    icon.setName("icon");
    
    Container c = BorderLayout.center(name).
            add(BorderLayout.SOUTH, surname).
            add(BorderLayout.WEST, image).
            add(BorderLayout.EAST, icon);
    c.setUIID("ListRenderer");
    return c;
}

private Object[] createGenericListCellRendererModelData() {
    ServicePlante sp = new ServicePlante();
    
    Map<String,Object>[] data = new HashMap[sp.getListPlantSelonCategorie("Fruit").size()];
    int i=0;
    for(Plante p : sp.getListPlantSelonCategorie("Fruit")){
        ServiceSujet ss = new ServiceSujet();
        data[i] = new HashMap<>();
        data[i].put("Name", p.getNom());
        try {
            data[i].put("image", new Label(EncodedImage.create("/plante2.jpg").scaled(300, 300)));
            data[i].put("icon", new Label(ss.getList(p.getId()).size()+"", EncodedImage.create("/comment.png").scaledEncoded(100, 100)));
        } catch (IOException ex) {
        }

        i++;
    }
        

    return data;
}

}
