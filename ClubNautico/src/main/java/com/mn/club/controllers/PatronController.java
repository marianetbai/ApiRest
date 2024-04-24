package com.mn.club.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mn.club.models.PatronModel;
import com.mn.club.services.PatronService;

import java.util.List;
import java.util.Optional;

@RestController
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping("/patron")
    public ResponseEntity<List<PatronModel>> getAllPatrones() {
        List<PatronModel> patrones = patronService.getAllPatron();
        return ResponseEntity.ok().body(patrones);
    }

    @GetMapping("/patron/{id}")
    public ResponseEntity<PatronModel> getPatronById(@PathVariable("id") Long id) {
        Optional<PatronModel> patron = patronService.getPatronById(id);
        return patron.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/patron")
    public ResponseEntity<PatronModel> createPatron(@RequestBody PatronModel nuevoPatron) {
        PatronModel creadoPatron = patronService.savePatron(nuevoPatron);
        return ResponseEntity.status(HttpStatus.CREATED).body(creadoPatron);
    }

    @PutMapping("/patron/{id}")
    public ResponseEntity<PatronModel> updatePatron(@PathVariable(name = "id") Long id, @RequestBody PatronModel updatedPatron) {
        Optional<PatronModel> existingPatronOptional = patronService.getPatronById(id);

        if (existingPatronOptional.isPresent()) {
            PatronModel existingPatron = existingPatronOptional.get();
            //actualizamos los campos
            existingPatron.setNombre(updatedPatron.getNombre());
            existingPatron.setApellido(updatedPatron.getApellido());
            existingPatron.setDni(updatedPatron.getDni());
            existingPatron.setDireccion(updatedPatron.getDireccion());

            //guardamos el patrón actualizado
            PatronModel updatedPatronEntity = patronService.savePatron(existingPatron);
            return ResponseEntity.ok(updatedPatronEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/patron/{id}")
    public ResponseEntity<String> deletePatron(@PathVariable("id") Long id) {
        boolean deleted = patronService.deletePatron(id);
        if (deleted) {
            String message = "Patron con ID " + id + " borrado correctamente";
            return ResponseEntity.ok(message);
        } else {
            String message = "No se encontró ningún patron con ID " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
