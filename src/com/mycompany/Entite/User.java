package com.mycompany.Entite;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ROCH
 */
public class User {
    
    private int id;
    private String username;
    private String email;
    private int enabled;
    private String password;
    private Date last_login;
    private String roles;
    private String nom;
    private String prenom;
    private int tel;
    private int level;
    private int score;
    private String avatar;
    private String adresse;

    public User() {
    }

    public User(int id, String nom, String prenom,String email,  String avatar) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.avatar = avatar;
    }

    public User(String nom , String prenom ,String email, String avatar) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.avatar = avatar;
    }
    
    

    public User(int id, String username, String email, int enabled, Date last_login, String roles, String nom, String prenom, int tel, int level, int score, String avatar, String adresse) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.last_login = last_login;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.level = level;
        this.score = score;
        this.avatar = avatar;
        this.adresse = adresse;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        if (this.tel != other.tel) {
            return false;
        }
        if (this.level != other.level) {
            return false;
        }
        if (this.score != other.score) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.avatar, other.avatar)) {
            return false;
        }
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.last_login, other.last_login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", email=" + email + ", enabled=" + enabled + ", password=" + password + ", last_login=" + last_login + ", roles=" + roles + ", nom=" + nom + ", prenom=" + prenom + ", tel=" + tel + ", level=" + level + ", score=" + score + ", avatar=" + avatar + ", adresse=" + adresse + '}';
    }
    
    
}
