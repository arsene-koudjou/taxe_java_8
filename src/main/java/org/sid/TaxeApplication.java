package org.sid;

import java.util.Date;

import org.sid.dao.EntrepriseRepository;
import org.sid.dao.TaxeRepository;
import org.sid.entities.Entreprise;
import org.sid.entities.IR;
import org.sid.entities.TVA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaxeApplication implements CommandLineRunner {

	@Autowired
	private EntrepriseRepository entrepriseRepository;
	
	@Autowired
	private TaxeRepository taxeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(TaxeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Entreprise e1 = entrepriseRepository.save(new Entreprise("PME","R1@gmail.com","Sa"));
	//	Entreprise e2 = entrepriseRepository.save(new Entreprise("Gre2","Gre2@yahoo.fr","SA"));
		taxeRepository.save(new TVA("TVA Transport", new Date(),454, e1));
	/*	taxeRepository.save(new TVA("TVA Automobile", new Date(), 780, e1));
		taxeRepository.save(new IR("IR 2019", new Date(), 4500, e1));
		taxeRepository.save(new TVA("TVA Immobiliere", new Date(), 900, e2));*/
	}

}
