package com.mn.club.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mn.club.models.SalidaModel;

@Repository
public interface SalidaRepository extends JpaRepository<SalidaModel, Long> {

} 
