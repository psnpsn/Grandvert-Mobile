/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entite.Sujet;
import com.mycompany.Entite.Task;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceSujet {

    public void ajoutSujet(Sujet s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        //String Url = "http://localhost/symfony-api/web/app_dev.php/api/sujet/new?name="+ta.getNom()+"&status="+ta.getEtat();// création de l'URL
        String Url = "http://localhost/GrandVert/web/app_dev.php/forumapi/sujet/ajouter?id_user="+s.getUser().getId()+"&id="+s.getPlante_id()+"&postoriginal="+s.getSujet_original();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
    public void modifiernbViewSujet(Sujet s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        //String Url = "http://localhost/symfony-api/web/app_dev.php/api/sujet/new?name="+ta.getNom()+"&status="+ta.getEtat();// création de l'URL
        String Url = "http://localhost/symfony-api/web/app_dev.php/api/sujet/new?name=";// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }    
    
    public void supprimerSujet(Sujet s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        //String Url = "http://localhost/symfony-api/web/app_dev.php/api/sujet/new?name="+ta.getNom()+"&status="+ta.getEtat();// création de l'URL
        String Url = "http://localhost/GrandVert/web/app_dev.php/forum/sujet/etat/archiver?id="+s.getId();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }    

    public ArrayList<Sujet> parseListTaskJson(String json) {

        ArrayList<Sujet> listSujets = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
            */
            Map<String, Object> Sujets = j.parseJSON(new CharArrayReader(json.toCharArray()));
                       
            
            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
            */
            List<Map<String, Object>> list = (List<Map<String, Object>>) Sujets.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Sujet e = new Sujet();

                float id = Float.parseFloat(obj.get("id").toString());
                float nbviews = Float.parseFloat(obj.get("nbviews").toString());
                
                Object user = obj.get("User");
                String chaine = user.toString();
                String user_id= chaine.substring(chaine.indexOf("id=")+3, chaine.indexOf(".0,"));
                String user_nom= chaine.substring(chaine.indexOf("nom=")+4, chaine.indexOf(", ", chaine.indexOf("nom="))); 
                String user_prenom= chaine.substring(chaine.indexOf("prenom=")+7, chaine.indexOf(", ", chaine.indexOf("prenom="))); 
                String user_avatar= chaine.substring(chaine.indexOf("avatar=")+7, chaine.indexOf(", ", chaine.indexOf("avatar="))); 
                String user_email= chaine.substring(chaine.indexOf("email=")+6, chaine.indexOf(", ", chaine.indexOf("email=")));                 
                User u = new User(Integer.parseInt(user_id), user_nom, user_prenom, user_email, user_avatar);
                e.setId((int) id);
                e.setSujet_original(obj.get("sujetOriginal").toString());
                
                /*System.out.println("!!!!!!!!!!!!! : "+obj.get("dateOriginal").toString());
                String datechaine= obj.get("dateOriginal").toString();
                
                String datee= datechaine.substring(datechaine.indexOf("timestamp=")+10, datechaine.length()-1);                 
                System.out.println(datee);
                String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (1556563602*1000));
                System.out.println(date);
                
                //e.setDate_original(obj.get("dateOriginal"));
                */
                e.setOpen(obj.get("open").toString());
                e.setNbviews((int) nbviews);
                e.setResolu(obj.get("resolu").toString());
                String archive = obj.get("archive").toString();
                if ( archive.equals("false"))
                    e.setArchive(0);
                else
                    e.setArchive(1);
                
                e.setUser(u);
                System.out.println(e);
                
                listSujets.add(e);

            }

        } catch (IOException ex) {
        }
        
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        System.out.println(listSujets);
        return listSujets;

    }
    
    
    ArrayList<Sujet> listSujet = new ArrayList<>();
    Sujet S = new Sujet();
    
    public ArrayList<Sujet> getList(int plante_id){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/GrandVert/web/app_dev.php/forum/api/sujet/all/?id="+plante_id);  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceSujet ser = new ServiceSujet();
                listSujet = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listSujet;
    }
    
    public Sujet getById(int id){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/GrandVert/web/app_dev.php/forum/api/sujet/return/?id="+id);  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceSujet ser = new ServiceSujet();
                S = ser.parseListTaskJson(new String(con.getResponseData())).get(0);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return S;
    }   
    
    public void signalerSujet(Sujet s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/GrandVert/web/app_dev.php/forum/sujet/etat/signaler?id="+s.getId();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    } 
    
    public void SetFermer(Sujet s , int id_user) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/GrandVert/web/app_dev.php/forumapi/sujet/etat/fermer?id_user="+id_user+"&id="+s.getId();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    } 
    
    public void SetOuvert(Sujet s , int id_user) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/GrandVert/web/app_dev.php/forumapi/sujet/etat/ouvert?id_user="+id_user+"&id="+s.getId();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
    public void SetResolu(Sujet s , int id_user) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/GrandVert/web/app_dev.php/forumapi/sujet/etat/resolut?id_user="+id_user+"&id="+s.getId();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }   
    
    public void SetNonResolu(Sujet s , int id_user) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        //String Url = "http://localhost/symfony-api/web/app_dev.php/api/sujet/new?name="+ta.getNom()+"&status="+ta.getEtat();// création de l'URL
        String Url = "http://localhost/GrandVert/web/app_dev.php/forumapi/sujet/etat/nonresolut?id_user="+id_user+"&id="+s.getId();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    } 
   
}
