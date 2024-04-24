package com.mn.club.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "barco")
public class BarcoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String numeroMatricula;

    @NotBlank
    private String nombre;

    @NotNull
    private Integer numeroAmarre;

    @NotNull
    private Double cuota;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="socio_id")
    private SocioModel socio; //relación con SocioModel  
    
    @OneToMany(mappedBy = "barco",  cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private List<SalidaModel> salida = new ArrayList<>();

    public BarcoModel() {
    	//
    }
    
    public BarcoModel(Long id, @NotBlank String numeroMatricula, @NotBlank String nombre, @NotNull Integer numeroAmarre, @NotNull Double cuota, SocioModel socio) {
        this.id = id;
        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.numeroAmarre = numeroAmarre;
        this.cuota = cuota;
        this.socio = socio;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumeroAmarre() {
		return numeroAmarre;
	}

	public void setNumeroAmarre(Integer numeroAmarre) {
		this.numeroAmarre = numeroAmarre;
	}

	public Double getCuota() {
		return cuota;
	}

	public void setCuota(Double cuota) {
		this.cuota = cuota;
	}

	public SocioModel getSocio() {
		return socio;
	}

	public void setSocio(SocioModel socio) {
		this.socio = socio;
	}

	public List<SalidaModel> getSalida() {
		return salida;
	}

	public void setSalida(List<SalidaModel> salida) {
		this.salida = salida;
	}

    
}
