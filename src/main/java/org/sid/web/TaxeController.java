package org.sid.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.sid.dao.EntrepriseRepository;
import org.sid.dao.TaxeRepository;
import org.sid.entities.Entreprise;
import org.sid.entities.IR;
import org.sid.entities.TVA;
import org.sid.entities.Taxe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaxeController extends SpringBootServletInitializer {

	@Autowired
	private EntrepriseRepository entrepriseRepository;
	
	@Autowired
	private TaxeRepository taxeRepository;
	
	private Taxe t;
	
	@RequestMapping(value="/entreprises",method=RequestMethod.GET)
	public String index(Model model,
			@RequestParam(name="motCle",defaultValue="") String motCle,
		 @RequestParam(name="page",defaultValue="0") int p,
		 @RequestParam(name="size",defaultValue="4") int s) {
		Page<Entreprise> pageEntreprises = entrepriseRepository.chercher("%"+motCle+"%",new PageRequest(p, s));
		model.addAttribute("listEntreprises", pageEntreprises.getContent());
		int[] pages = new int[pageEntreprises.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", motCle);
		return "entreprises";
	}
	
	@RequestMapping(value="/formEntreprise")
	public String form (Model model) {
		model.addAttribute("entreprise", new Entreprise());
		
		return "formEntreprise";
	}
	
	@RequestMapping(value="/saveEntreprise")
	public String save (Model model,@Valid Entreprise e,BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "formEntreprise";
		entrepriseRepository.save(e);
		return "redirect:/entreprises";
	}
	
	
	@RequestMapping(value="/taxes")
	public String taxes (Model model,
		@RequestParam(name="code",defaultValue="-1")	Long code) {
		
		model.addAttribute("entreprises", entrepriseRepository.findAll());
		
		if(code==-1) model.addAttribute("taxes",new ArrayList<Taxe>());
		else {
			Entreprise e = new Entreprise();
			e.setCode(code);
			
			model.addAttribute("taxes", taxeRepository.findByEntreprise(e));
		}
		
		return "taxes";
	}
	
	@RequestMapping(value="/formTaxe")
	public String formT (Model model) {
		
		
		
		model.addAttribute("taxe", new IR("IR 2019", new Date(), 4500, new Entreprise("testEpse","tes@gmail.com","Sas")));
		return "formTaxe";
	}
	
	
	@RequestMapping(value="/saveTaxe")
	public String saveT (Model model,@Valid TVA tx,IR i,String type,BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "formTaxe";
		tx.setDateTaxe(new Date());
		tx.setEntreprise(new Entreprise("entCt","entCt@gmail.com","Sas"));
		
		i.setDateTaxe(new Date());
		i.setEntreprise(new Entreprise("entCt","entCt@gmail.com","Sas"));
		
		try {
			if(type.equals("TVA")) {
				entrepriseRepository.save(tx.getEntreprise());
				taxeRepository.save(tx);
			} else if(type.equals("IR")) {
				entrepriseRepository.save(i.getEntreprise());
				taxeRepository.save(i);
				                         }

			
		} catch (Exception e) {
			model.addAttribute("error", e);
			
		}

		
		return "redirect:/formTaxe";
	}
	
	
}
