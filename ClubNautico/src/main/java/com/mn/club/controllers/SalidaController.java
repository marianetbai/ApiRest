package com.mn.club.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mn.club.models.BarcoModel;
import com.mn.club.models.PatronModel;
import com.mn.club.models.SalidaModel;
import com.mn.club.services.BarcoService;
import com.mn.club.services.PatronService;
import com.mn.club.services.SalidaService;

import java.util.List;
import java.util.Optional;

@RestController
public class SalidaController {

    @Autowired
    private SalidaService salidaService;
    
    @Autowired 
    private BarcoService barcoService;
    
    @Autowired
    private PatronService patronService;

    @GetMapping(path="/salida")
    public ResponseEntity<List<SalidaModel>> getAllSalidas() {
        List<SalidaModel> salidas = salidaService.getAllSalidas();
        return ResponseEntity.ok().body(salidas);
    }

    @GetMapping("/salida/{id}")
    public ResponseEntity<SalidaModel> getSalidaById(@PathVariable("id") Long id) {
        Optional<SalidaModel> salida = salidaService.getSalidaById(id);
        return salida.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path="/salida")
    public ResponseEntity<SalidaModel> saveSalida(@RequestBody SalidaModel salida, @RequestParam("barcoId") Long barcoId, @RequestParam("patronId") Long patronId) {
        Optional<BarcoModel> barcoOptional = barcoService.getBarcoBy(barcoId);
        Optional<PatronModel> patronOptional = patronService.getPatronById(patronId);
        
        if(!barcoOptional.isPresent() || !patronOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        BarcoModel barco = barcoOptional.get();
        PatronModel patron = patronOptional.get();
        
        salida.setBarco(barco);
        salida.setPatron(patron);
        
        SalidaModel salidaCreada = salidaService.saveSalida(salida);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(salidaCreada);
    }



    @PutMapping("/salida/{id}")
    public ResponseEntity<SalidaModel> updateSalida(@PathVariable Long id, @RequestBody SalidaModel updatedSalida) {
        Optional<SalidaModel> existingSalidaOptional = salidaService.getSalidaById(id);
        
        if (existingSalidaOptional.isPresent()) {
            SalidaModel existingSalida = existingSalidaOptional.get();
            //actualizamos los campos
            existingSalida.setFecha(updatedSalida.getFecha());
            existingSalida.setHoraSalida(updatedSalida.getHoraSalida());
            existingSalida.setDestino(updatedSalida.getDestino());
            existingSalida.setBarco(updatedSalida.getBarco());
            
            SalidaModel updatedSalidaEntity = salidaService.saveSalida(existingSalida);
            return ResponseEntity.ok(updatedSalidaEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/salida/{id}")
    public ResponseEntity<String> deleteSalida(@PathVariable("id") Long id) {
        boolean deleted = salidaService.deleteSalida(id);
        if (deleted) {
            String message = "Salida con ID " + id + " borrada correctamente";
            return ResponseEntity.ok(message);
        } else {
            String message = "No se encontró ninguna salida con ID " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
