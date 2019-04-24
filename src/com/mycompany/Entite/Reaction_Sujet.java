package com.mycompany.Entite;

/**
 *
 * @author ROCH
 */
public class Reaction_Sujet {
    
    private Sujet sujet;
    private User user;
    private String reaction;


    public Reaction_Sujet() {
    }
   
    public Sujet getSujet() {
        return sujet;
    }

    public void setSujet(Sujet sujet) {
        this.sujet = sujet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Reaction_Sujet(Sujet sujet, User user, String reaction) {
        this.sujet = sujet;
        this.user = user;
        this.reaction = reaction;
    }
    
    
}
