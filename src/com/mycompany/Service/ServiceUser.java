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
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entite.Sujet;
import com.mycompany.Entite.Task;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceUser {

    public void ajoutUser(User s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        //String Url = "http://localhost/symfony-api/web/app_dev.php/api/user/new?name="+ta.getNom()+"&status="+ta.getEtat();// création de l'URL
        String Url = "http://localhost/symfony-api/web/app_dev.php/api/user/new?name=";// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
    public void modifierUser(User s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        //String Url = "http://localhost/symfony-api/web/app_dev.php/api/User/new?name="+ta.getNom()+"&status="+ta.getEtat();// création de l'URL
        String Url = "http://localhost/symfony-api/web/app_dev.php/api/User/new?name=";// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }    
    
    public void supprimerUser(User s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        //String Url = "http://localhost/symfony-api/web/app_dev.php/api/user/new?name="+ta.getNom()+"&status="+ta.getEtat();// création de l'URL
        String Url = "http://localhost/symfony-api/web/app_dev.php/api/user/new?name=";// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }    

    public ArrayList<User> parseListTaskJson(String json) {

        ArrayList<User> listUsers = new ArrayList<>();

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
            Map<String, Object> Users = j.parseJSON(new CharArrayReader(json.toCharArray()));
                       
            
            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
            */
            List<Map<String, Object>> list = (List<Map<String, Object>>) Users.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                User e = new User();

                float id = Float.parseFloat(obj.get("id").toString());

                e.setId((int) id);
                e.setNom(obj.get("nom").toString());
                e.setPrenom(obj.get("prenom").toString());
                e.setAvatar(obj.get("avatar").toString());
                
                listUsers.add(e);

            }

        } catch (IOException ex) {
        }
        
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        System.out.println(listUsers);
        return listUsers;

    }
    
    
    ArrayList<User> listUser = new ArrayList<>();
    User u = new User();
    
    public ArrayList<User> getList(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/GrandVert/web/app_dev.php/forum/api/user/return/");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceUser ser = new ServiceUser();
                listUser = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listUser;
    }
    
    public User getById(int id){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/GrandVert/web/app_dev.php/forum/api/user/return/?id="+id);  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceUser ser = new ServiceUser();
                listUser = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listUser.get(0);
    }    
   

}
