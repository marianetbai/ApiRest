package com.mn.club.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mn.club.models.PatronModel;
import com.mn.club.repositories.PatronRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired	
    private PatronRepository patronRepository;

    public List<PatronModel> getAllPatron() {
        return patronRepository.findAll();
    }

    public Optional<PatronModel> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public PatronModel savePatron(PatronModel patron) {
        return patronRepository.save(patron);
    }

    public boolean deletePatron(Long id) {
        Optional<PatronModel> patronOp = patronRepository.findById(id);
        if (patronOp.isPresent()) {
        	PatronModel patron = patronOp.get();
        	patronRepository.delete(patron);
            return true;
        } else {
            return false;
        }
    }
}


