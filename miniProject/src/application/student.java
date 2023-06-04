package application;


public class student {
	
    private String prenom;
    private String nom;
    private String filiere;
    private String email;
    
    public student(String prenom, String nom, String filiere, String email) {
        this.prenom = prenom;
        this.nom = nom;
        this.filiere = filiere;
        this.email = email;
    }
    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getFiliere() {
          return filiere;
}
    
    public String getEmail() {
        return email;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
