package com.mycompany.Entite;

/**
 *
 * @author ROCH
 */
public class Reaction_Reponse {
    
    private Reponse reponse;
    private User user;
    private String reaction;

    public Reaction_Reponse(Reponse reponse, User user, String reaction) {
        this.reponse = reponse;
        this.user = user;
        this.reaction = reaction;
    }

    public Reaction_Reponse() {
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
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
    
    
}
