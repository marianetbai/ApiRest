package com.mn.club.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mn.club.models.SocioModel;
import com.mn.club.services.SocioService;

import java.util.List;
import java.util.Optional;

@RestController
public class SocioController {

    @Autowired
    private SocioService socioService;

    @GetMapping("/socio")
    public ResponseEntity<List<SocioModel>> getAllSocios() {
        List<SocioModel> socios = socioService.getAllSocios();
        return ResponseEntity.ok().body(socios);
    }

    @GetMapping("/socio/{id}")
    public ResponseEntity<SocioModel> getSocioById(@PathVariable("id") Long id) {
        Optional<SocioModel> socio = socioService.getSocioById(id);
        if (socio.isPresent()) {
            return ResponseEntity.ok().body(socio.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/socio")
    public ResponseEntity<SocioModel> saveSocio(@RequestBody SocioModel socio) {
        SocioModel savedSocio = socioService.saveSocio(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSocio);
    }

    @PutMapping("/socio/{id}")
    public ResponseEntity<SocioModel> updateSocio(@PathVariable("id") Long id, @RequestBody SocioModel socio) {
        Optional<SocioModel> existingSocio = socioService.getSocioById(id);
        if (existingSocio.isPresent()) {
            socio.setId(id); //asignamos el ID del socio existente al socio recibido
            SocioModel updatedSocio = socioService.saveSocio(socio);
            return ResponseEntity.ok().body(updatedSocio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/socio/{id}")
    public ResponseEntity<String> deleteSocioById(@PathVariable("id") Long id) {
        boolean deleted = socioService.deleteSocioById(id);
        if (deleted) {
            String message = "Socio con ID " + id + " borrado correctamente";
            return ResponseEntity.ok(message);
        } else {
            String message = "No se encontró ningún socio con ID " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

}

