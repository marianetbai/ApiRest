package com.mn.club.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mn.club.models.BarcoModel;
import com.mn.club.models.SocioModel;
import com.mn.club.services.BarcoService;
import com.mn.club.services.SocioService;

import java.util.List;
import java.util.Optional;

@RestController
public class BarcoController {

    @Autowired
    private BarcoService barcoService;
    
    @Autowired SocioService socioService;

    @GetMapping("/barco")
    public ResponseEntity<List<BarcoModel>> getAllBarcos() {
        List<BarcoModel> barco = barcoService.getAllBarco();
        return ResponseEntity.ok().body(barco);
    }

    @GetMapping("/barco/{id}")
    public ResponseEntity<BarcoModel> getBarcoById(@PathVariable("id") Long id) {
        Optional<BarcoModel> barco = barcoService.getBarcoBy(id);
        return barco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/barco")
    public ResponseEntity<BarcoModel> createBarco(@RequestBody BarcoModel nuevoBarco, @RequestParam("socioId") Long socioId) {
        //buscamos el socio por su ID
        Optional<SocioModel> socioOptional = socioService.getSocioById(socioId);
        if (!socioOptional.isPresent()) {
            return ResponseEntity.notFound().build(); //devuelve 404 Not Found si no se encuentra el socio
        }
        SocioModel socio = socioOptional.get();
        //asignamos el socio al nuevo barco
        nuevoBarco.setSocio(socio);
        //guardamos el nuevo barco en la base de datos usando el servicio BarcoService
        BarcoModel creadoBarco = barcoService.saveBarco(nuevoBarco);
        //y devolvemos una respuesta con el código 201 (CREATED) y el barco recién creado en el cuerpo de la respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(creadoBarco);
    }

    @PutMapping("/barco/{id}")
    public ResponseEntity<BarcoModel> updateBarco(@PathVariable(name="id") Long id, @RequestBody BarcoModel updatedBarco) {
        //buscamos el barco existente por su ID
        Optional<BarcoModel> existingBarcoOptional = barcoService.getBarcoBy(id);
        
        if (existingBarcoOptional.isPresent()) {
        	//actualizamos campos
            BarcoModel existingBarco = existingBarcoOptional.get();
            existingBarco.setNumeroMatricula(updatedBarco.getNumeroMatricula());
            existingBarco.setNombre(updatedBarco.getNombre());
            existingBarco.setNumeroAmarre(updatedBarco.getNumeroAmarre());
            existingBarco.setCuota(updatedBarco.getCuota());
            existingBarco.setSocio(updatedBarco.getSocio());
            
            //guardamos barco actualizado
            BarcoModel updatedBarcoEntity = barcoService.saveBarco(existingBarco);
            return ResponseEntity.ok(updatedBarcoEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/barco/{id}")
    public ResponseEntity<String> deleteBarco(@PathVariable("id") Long id) {
        boolean deleted = barcoService.deleteBarco(id);
        if (deleted) {
            String message = "Barco con ID " + id + " borrado correctamente";
            return ResponseEntity.ok(message);
        } else {
            String message = "No se encontró ningún barco con ID " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
