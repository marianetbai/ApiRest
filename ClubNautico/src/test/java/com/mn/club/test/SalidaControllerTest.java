package com.mn.club.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mn.club.controllers.SalidaController;
import com.mn.club.models.BarcoModel;
import com.mn.club.models.PatronModel;
import com.mn.club.models.SalidaModel;
import com.mn.club.services.BarcoService;
import com.mn.club.services.PatronService;
import com.mn.club.services.SalidaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalidaController.class)
@AutoConfigureJson
class SalidaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalidaService salidaService;

    @MockBean
    private BarcoService barcoService;

    @MockBean
    private PatronService patronService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllSalidas() throws Exception {
        List<SalidaModel> salidas = new ArrayList<>();
        salidas.add(new SalidaModel(1L, LocalDateTime.now(), LocalTime.now(), "Destino 1", new BarcoModel(), new PatronModel()));
        salidas.add(new SalidaModel(2L, LocalDateTime.now(), LocalTime.now(), "Destino 2", new BarcoModel(), new PatronModel()));

        when(salidaService.getAllSalidas()).thenReturn(salidas);

        mockMvc.perform(get("/salida"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getSalidaById() throws Exception {
        SalidaModel salida = new SalidaModel(1L, LocalDateTime.now(), LocalTime.now(), "Destino", new BarcoModel(), new PatronModel());

        when(salidaService.getSalidaById(1L)).thenReturn(Optional.of(salida));

        mockMvc.perform(get("/salida/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void saveSalida() throws Exception {
        SalidaModel salida = new SalidaModel(1L, LocalDateTime.now(), LocalTime.now(), "Destino", new BarcoModel(), new PatronModel());
        BarcoModel barco = new BarcoModel();
        PatronModel patron = new PatronModel();

        when(barcoService.getBarcoBy(1L)).thenReturn(Optional.of(barco));
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));
        when(salidaService.saveSalida(any(SalidaModel.class))).thenReturn(salida);

        mockMvc.perform(post("/salida")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salida))
                .param("barcoId", "1")
                .param("patronId", "1"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateSalida() throws Exception {
        SalidaModel salidaExistente = new SalidaModel(1L, LocalDateTime.now(), LocalTime.now(), "Destino Original", new BarcoModel(), new PatronModel());
        SalidaModel salidaActualizada = new SalidaModel(1L, LocalDateTime.now(), LocalTime.now(), "Destino Actualizado", new BarcoModel(), new PatronModel());

        when(salidaService.getSalidaById(1L)).thenReturn(Optional.of(salidaExistente));
        when(salidaService.saveSalida(any(SalidaModel.class))).thenReturn(salidaActualizada);

        mockMvc.perform(put("/salida/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salidaActualizada)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.destino").value("Destino Actualizado"));
    }

    @Test
    void deleteSalida() throws Exception {
        when(salidaService.deleteSalida(1L)).thenReturn(true);

        mockMvc.perform(delete("/salida/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Salida con ID 1 borrada correctamente"));
    }

    @Test
    void deleteSalidaNotFound() throws Exception {
        when(salidaService.deleteSalida(1L)).thenReturn(false);

        mockMvc.perform(delete("/salida/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se encontró ninguna salida con ID 1"));
    }

}
