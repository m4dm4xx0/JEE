import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import metier.BanqueRemote;
import metier.entities.Compte;

public class ClientRemote {

	public static void main(String[] args) {
		try {
		    Context ctx = new InitialContext();
		    BanqueRemote proxy = (BanqueRemote) ctx.lookup("ejb:BanqueEAR/BanqueEJB/BK!metier.BanqueRemote?stateless");

		    // On crée les comptes et on RÉCUPÈRE l'objet retourné (qui contient l'ID officiel)
		    Compte c1 = proxy.addCompte(new Compte());
		    Compte c2 = proxy.addCompte(new Compte());
		    Compte c3 = proxy.addCompte(new Compte());

		    // On utilise les IDs récupérés dynamiquement
		    Long id1 = c1.getCode();
		    Long id2 = c2.getCode();

		    System.out.println("ID généré pour le premier compte : " + id1);

		    proxy.verser(id1, 10000);
		    proxy.retirer(id1, 500);
		    proxy.virement(id1, id2, 300);

		    

	        // 3. Affichage de la liste
	        List<Compte> cptes = proxy.listComptes();
	        for(Compte c : cptes) {
	            System.out.println("Code compte = " + c.getCode() + " | Solde = " + c.getSolde());
	        }

	        // 4. Suppression du premier compte
	        proxy.supprimerCompte(id1);
	        
	        System.out.println("--- Après suppression du compte " + id1 + " ---");
	        List<Compte> cptes1 = proxy.listComptes();
	        for(Compte c : cptes1) {
	            System.out.println("Code : " + c.getCode() + " | Solde : " + c.getSolde());
	        }

	    } catch (NamingException e) {
	        e.printStackTrace();
	    }
	}}
