package service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import metier.Banquelocal;
import metier.entities.Compte;
@Stateless
@Path("/")
public class BanqueServiceRest {
	
	@EJB
	Banquelocal service;
	
	private Response.ResponseBuilder activeCORS(Response.ResponseBuilder rb) {
        return rb.header("Access-Control-Allow-Origin", "*")
                 .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                 .header("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");}
	
	@POST //le verbe HTTP
	@Path("/comptes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Compte addCompte(Compte cp) {
		return service.addCompte(cp);
	}
	
	@GET
	@Path("/comptes/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Compte getCompte(@PathParam(value="code") Long code) {
		return service.getCompte(code);
	}
	
	@GET
	@Path("/comptes")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Compte> listComptes() {
		return service.listComptes();
	}
	
	@PUT
	@Path("/comptes/verser")
	public void verser(@FormParam(value="code") Long code,
						@FormParam(value="montant") double mt) {
		service.verser(code, mt);
	}
	@PUT
	@Path("/comptes/retirer")
	public Response retirer(@FormParam(value = "code") Long code,
						@FormParam(value = "montant") double mt) {
		try {
	        service.retirer(code, mt);
	        return Response.ok("Retrait effectué").build(); // 200 OK
	    } catch (Exception e) {
	        // On renvoie un code 400 (Bad Request) avec le message de l'erreur
	        return Response.status(Response.Status.BAD_REQUEST)
	                       .entity(e.getMessage())
	                       .build();
	    }
	}
	
	@PUT
	@Path("/comptes/virement")
	public Response virement(@FormParam(value = "cp1") Long cp1,
	                         @FormParam(value = "cp2") Long cp2,
	                         @FormParam(value = "montant") double mt) {
	    try {
	        service.virement(cp1, cp2, mt);
	        // On retourne une réponse explicite 200 OK
	        return activeCORS(Response.ok("Virement effectué avec succès")).build();
	    } catch (Exception e) {
	        // On capture l'exception "Solde insuffisant" et on renvoie un code 400
	        return activeCORS(Response.status(Response.Status.BAD_REQUEST)
	                       .entity(e.getMessage()))
	                       .build();
	    }
	}
	@DELETE
	@Path("/comptes/{code}")
	public boolean supprimerCompte(@PathParam(value = "code") Long code) {
		return service.supprimerCompte(code);
	}
	
	// Gérer la requête de pré-vérification du navigateur (OPTIONS)
    @OPTIONS
    @Path("{path : .*}")
    public Response options() {
        return activeCORS(Response.ok()).build();
    }
}
