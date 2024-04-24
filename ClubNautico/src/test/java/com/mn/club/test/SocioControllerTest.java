package com.mn.club.test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mn.club.controllers.SocioController;
import com.mn.club.models.SocioModel;
import com.mn.club.services.SocioService;

import java.util.List;
import java.util.Optional;

@WebMvcTest(SocioController.class) //esta prueba está basada en la clae socioController
public class SocioControllerTest {

    @Autowired
    private MockMvc mockMvc; //inyectamos instancia de mockmvc para similar las solicitudes http

    @MockBean
    private SocioService socioService; //simulamos comportamiento del socioService aquí

    @Test
    void testGetAllSocios() throws Exception {
        //me creo una lista de socios simulados
        List<SocioModel> sociosSimulados = List.of(
                new SocioModel(1L, "Juan"),
                new SocioModel(2L, "María")
        );

        //configuro el comportamiento esperado del socioService
        when(socioService.getAllSocios()).thenReturn(sociosSimulados);

        //realizo la solicitud GET y compruebo la respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/socio") //realizamos solicitud get al endpoint socio usando la instancia de antes mockmvc
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[1].nombre").value("María"));
    }

    @Test
    void testGetSocioById() throws Exception {
        //creo un socio simulado
        SocioModel socioSimulado = new SocioModel(1L, "Juan");

        //confiruo el comportamiento esperado del socioService
        when(socioService.getSocioById(1L)).thenReturn(Optional.of(socioSimulado)); //contiene socio simulado cuando lo llamo

        //realizo la solicitud GET y compruebo la respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/socio/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }
    
    @Test
    void testSaveSocio() throws Exception {
        SocioModel nuevoSocio = new SocioModel(1L, "Pedro");

        when(socioService.saveSocio(any(SocioModel.class))).thenReturn(nuevoSocio);

        mockMvc.perform(MockMvcRequestBuilders.post("/socio")
                .content("{\"nombre\":\"Pedro\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Pedro"));
    }

    @Test
    void testUpdateSocio() throws Exception {
        SocioModel socioExistente = new SocioModel(1L, "Juan");
        SocioModel socioActualizado = new SocioModel(1L, "Pedro");

        when(socioService.getSocioById(1L)).thenReturn(Optional.of(socioExistente));
        when(socioService.saveSocio(any(SocioModel.class))).thenReturn(socioActualizado);

        mockMvc.perform(MockMvcRequestBuilders.put("/socio/1")
                .content("{\"nombre\":\"Pedro\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro"));
    }

    @Test
    void testDeleteSocioById() throws Exception {
        when(socioService.deleteSocioById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/socio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Socio con ID 1 borrado correctamente"));
    }

    

}
