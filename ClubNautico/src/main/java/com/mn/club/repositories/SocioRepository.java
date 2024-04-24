package com.mn.club.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mn.club.models.SocioModel;

@Repository
public interface SocioRepository extends JpaRepository<SocioModel, Long> {
	
	@EntityGraph(attributePaths = {"barcos"}) //esto asegura la carga  de los barcos asociados
    List<SocioModel> findAll(); //método para recuperar todos los socios con sus barcos asociados
} 
