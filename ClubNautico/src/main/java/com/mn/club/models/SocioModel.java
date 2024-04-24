package com.mn.club.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
	
@Entity
@Table(name = "socio")
public class SocioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para comenzar desde 1
    private Long id;

    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "socio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BarcoModel> barcos = new ArrayList<>();

    public SocioModel() {
    	
    }
    
    public SocioModel(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.barcos = new ArrayList<>();
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<BarcoModel> getBarcos() {
		return barcos;
	}

	public void setBarcos(List<BarcoModel> barcos) {
		this.barcos = barcos;
	}	

}
