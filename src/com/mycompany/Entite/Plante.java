package com.mycompany.Entite;

/**
 *
 * @author ROCH
 */
public class Plante {
    private int id;
    private String nom;
    private String Description;
    private String categorie;
    private String photo;

    public Plante(int id, String nom, String Description, String categorie, String photo) {
        this.id = id;
        this.nom = nom;
        this.Description = Description;
        this.categorie = categorie;
        this.photo = photo;
    }

    public Plante() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Plante{" + "id=" + id + ", nom=" + nom + ", Description=" + Description + ", categorie=" + categorie + ", photo=" + photo + '}';
    }


    
    
}
