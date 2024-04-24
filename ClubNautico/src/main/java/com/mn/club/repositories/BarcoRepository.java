package com.mn.club.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mn.club.models.BarcoModel;

@Repository
public interface BarcoRepository extends JpaRepository<BarcoModel, Long> {
	@Query("SELECT b FROM BarcoModel b WHERE b.socio.id = :socioId")
    List<BarcoModel> findBarcosBySocioId(@Param("socioId") Long socioId);
	//consulta jpql para buscar a todos los barcos asociados a un socio específico en función del ID del socio proporcionado
} 