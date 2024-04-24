package com.mn.club.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mn.club.models.SalidaModel;
import com.mn.club.repositories.SalidaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SalidaService {

    @Autowired
    private SalidaRepository salidaRepository;

    public List<SalidaModel> getAllSalidas() {
        return salidaRepository.findAll();
    }

    public Optional<SalidaModel> getSalidaById(Long id) {
        return salidaRepository.findById(id);
    }

    public SalidaModel saveSalida(SalidaModel salida) {
        return salidaRepository.save(salida);
    }

    public boolean deleteSalida(Long id) {
        Optional<SalidaModel> salidaOp = salidaRepository.findById(id);
        if (salidaOp.isPresent()) {
            SalidaModel salida = salidaOp.get();
            salidaRepository.delete(salida);
            return true;
        } else {
            return false;
        }
    }
}

