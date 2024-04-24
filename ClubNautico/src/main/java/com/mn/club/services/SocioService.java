package com.mn.club.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mn.club.models.SocioModel;
import com.mn.club.repositories.SocioRepository;



@Service
public class SocioService {
	
	@Autowired 
	SocioRepository socioRepository;
	
	public List<SocioModel> getAllSocios() {
        List<SocioModel> socios = socioRepository.findAll(); //obtengo todos los socios y sus barcos asociados
        for (SocioModel socio : socios) {
            socio.getBarcos().size(); //accedo a la lista de barcos para forzar la carga perezosa
        }
        return socios;
    }

    public Optional<SocioModel> getSocioById(Long id) {
        return socioRepository.findById(id);
    }

    public SocioModel saveSocio(SocioModel socio) {
        return socioRepository.save(socio);
    }  	

    public boolean deleteSocioById(Long id) {
        Optional<SocioModel> socioOptional = socioRepository.findById(id);
        if (socioOptional.isPresent()) {
            SocioModel socio = socioOptional.get();
            socioRepository.delete(socio);
            return true;
        } else {
            return false;
        }
    }

}
