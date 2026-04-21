package metier.entities;

import java.io.Serializable;
import java.util.Date; // MODIFIÉ : Utilisation de java.util.Date au lieu de java.sql.Date
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Compte
 */
@Entity
public class Compte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long code;
    
    private double solde;

    @Temporal(TemporalType.TIMESTAMP) // AJOUTÉ : Pour enregistrer Date + Heure dans phpMyAdmin
    private Date dateCreation = new Date(); // MODIFIÉ : Initialisation directe pour éviter le NULL
    
    private static final long serialVersionUID = 1L;

    public Compte() {
        super();
    }   

    public Long getCode() {
        return this.code;
    }

    public void setCode(Long code) {
        this.code = code;
    }   

    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }   

    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
