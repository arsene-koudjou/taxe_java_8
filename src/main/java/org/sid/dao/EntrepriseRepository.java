package org.sid.dao;

import org.sid.entities.Entreprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {

	@Query("select e from Entreprise e where e.nom like :x")
	public Page<Entreprise> chercher(@Param("x") String e,Pageable pageable);
}
