package com.mn.club.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mn.club.models.BarcoModel;
import com.mn.club.repositories.BarcoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BarcoService {

    @Autowired	
    private BarcoRepository barcoRepository;

    public List<BarcoModel> getAllBarco() {
        return barcoRepository.findAll();
    }

    public Optional<BarcoModel> getBarcoBy(Long id) {
        return barcoRepository.findById(id);
    }

    public BarcoModel saveBarco(BarcoModel barco) {
        return barcoRepository.save(barco);
    }

    public boolean deleteBarco(Long id) {
        Optional<BarcoModel> barcoOp = barcoRepository.findById(id);
        if (barcoOp.isPresent()) {
        	BarcoModel barco = barcoOp.get();
        	barcoRepository.delete(barco);
            return true;
        } else {
            return false;
        }
    }
}

