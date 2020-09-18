package org.sid.dao;

import java.util.List;

import org.sid.entities.Entreprise;
import org.sid.entities.Taxe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxeRepository extends JpaRepository<Taxe, Long> {

	public List<Taxe> findByEntreprise(Entreprise e);
}
