package com.mn.club.test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mn.club.controllers.PatronController;
import com.mn.club.models.PatronModel;
import com.mn.club.services.PatronService;

import java.util.List;
import java.util.Optional;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Test
    void testGetAllPatrones() throws Exception {
        List<PatronModel> patronesSimulados = List.of(
                new PatronModel(1L, "Juan", "Perez", "12345678A", "Calle A"),
                new PatronModel(2L, "Maria", "Lopez", "87654321B", "Calle B")
        );

        when(patronService.getAllPatron()).thenReturn(patronesSimulados);

        mockMvc.perform(MockMvcRequestBuilders.get("/patron")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[1].nombre").value("Maria"));
    }

    @Test
    void testGetPatronById() throws Exception {
        PatronModel patronSimulado = new PatronModel(1L, "Juan", "Perez", "12345678A", "Calle A");

        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patronSimulado));

        mockMvc.perform(MockMvcRequestBuilders.get("/patron/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }
    
    @Test
    void testUpdatePatron() throws Exception {
        PatronModel patronExistente = new PatronModel(1L, "Juan", "Perez", "12345678A", "Calle A");
        PatronModel patronActualizado = new PatronModel(1L, "Pedro", "Gomez", "87654321B", "Calle B");

        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patronExistente));
        when(patronService.savePatron(any(PatronModel.class))).thenReturn(patronActualizado);

        mockMvc.perform(MockMvcRequestBuilders.put("/patron/1")
                .content("{\"nombre\":\"Pedro\", \"apellido\":\"Gomez\", \"dni\":\"87654321B\", \"direccion\":\"Calle B\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro"));
    }

    @Test
    void testDeletePatron() throws Exception {
        when(patronService.deletePatron(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/patron/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Patron con ID 1 borrado correctamente"));
    }

}
