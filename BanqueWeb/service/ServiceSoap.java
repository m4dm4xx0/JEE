package service;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import metier.BanqueEJBImpl;
import metier.Banquelocal;
import metier.entities.Compte;
@WebService 
public class ServiceSoap {
	@EJB
	
	Banquelocal metier = new BanqueEJBImpl();
	
	@WebMethod
	public Compte addCompte(@WebParam(name ="cpt") Compte cp) {
		return metier.addCompte(cp);
	}
	
	@WebMethod
	public Compte getCompte(@WebParam(name="code")Long code) {
		return metier.getCompte(code);
	}
	
	@WebMethod
	public List<Compte> listComptes() {
		return metier.listComptes();
	}
	
	@WebMethod
	public void verser(@WebParam(name="code") Long code,
						@WebParam(name="montant") double mt) {
		metier.verser(code, mt);
	}
	
	@WebMethod
	public void retirer(@WebParam(name="code")Long code,
						@WebParam(name="montant")double mt) {
		metier.retirer(code, mt);
	}
	
	@WebMethod
	public void virement(@WebParam(name="cp1")Long cp1,
						@WebParam(name="cp2")Long cp2,
						@WebParam(name="montant")double mt) {
		metier.virement(cp1, cp2, mt);
	}
	
	@WebMethod
	public void supprimerCompte(@WebParam(name="code")Long code) {
		metier.supprimerCompte(code);
	}
	
	
	
}
