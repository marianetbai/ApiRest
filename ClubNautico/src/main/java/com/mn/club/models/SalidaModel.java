package com.mn.club.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "salida")
public class SalidaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha;
    
    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horaSalida;

    @NotBlank
    private String destino;

    @ManyToOne
    @JoinColumn(name="barco_id")
    private BarcoModel barco; //relación con BarcoModel
    
    @ManyToOne
    @JoinColumn(name="patron_id")
    private PatronModel patron;

    public SalidaModel() {
    	
    }
    
    public SalidaModel(Long id, @NotNull LocalDateTime fecha, @NotNull LocalTime horaSalida, @NotBlank String destino, BarcoModel barco, PatronModel patron) {
        this.id = id;
        this.fecha = fecha;
        this.horaSalida = horaSalida;
        this.destino = destino;
        this.barco = barco;
        this.patron = patron;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(LocalTime horaSalida) {
		this.horaSalida = horaSalida;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public BarcoModel getBarco() {
		return barco;
	}

	public void setBarco(BarcoModel barco) {
		this.barco = barco;
	}

	public PatronModel getPatron() {
		return patron;
	}

	public void setPatron(PatronModel patron) {
		this.patron = patron;
	}
}
