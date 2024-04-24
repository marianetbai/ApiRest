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

import com.mn.club.controllers.BarcoController;
import com.mn.club.models.BarcoModel;
import com.mn.club.models.SocioModel;
import com.mn.club.services.BarcoService;
import com.mn.club.services.SocioService;

import java.util.List;
import java.util.Optional;

@WebMvcTest(BarcoController.class)
public class BarcoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarcoService barcoService;

    @MockBean
    private SocioService socioService;

    @Test
    void testGetAllBarcos() throws Exception {
        List<BarcoModel> barcosSimulados = List.of(
                new BarcoModel(1L, "Matricula1", "Barco1", 123, 100.0, new SocioModel(1L, "María")),
                new BarcoModel(2L, "Matricula2", "Barco2", 456, 200.0, new SocioModel(2L, "Pepe"))
        );

        when(barcoService.getAllBarco()).thenReturn(barcosSimulados);

        mockMvc.perform(MockMvcRequestBuilders.get("/barco")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Barco1"))
                .andExpect(jsonPath("$[1].nombre").value("Barco2"));
    }

    @Test
    void testGetBarcoById() throws Exception {
        BarcoModel barcoSimulado = new BarcoModel(1L, "Matricula1", "Barco1" , 123, 100.0, new SocioModel(1L, "María"));

        when(barcoService.getBarcoBy(1L)).thenReturn(Optional.of(barcoSimulado));

        mockMvc.perform(MockMvcRequestBuilders.get("/barco/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Barco1"));
    }

    @Test
    void testCreateBarco() throws Exception {
        //nos creamos un nuevo barco simulado
        BarcoModel nuevoBarco = new BarcoModel(null, "MatriculaNuevo", "NuevoBarco", 789, 300.0, new SocioModel(3L, "Ana"));
        
        //y simulamos que el socio existe
        when(socioService.getSocioById(3L)).thenReturn(Optional.of(new SocioModel(3L, "Ana")));

        //simulamos el comportamiento del servicio de barcos al guardar el nuevo barco
        BarcoModel barcoCreado = new BarcoModel(1L, "MatriculaNuevo", "NuevoBarco", 789, 300.0, new SocioModel(3L, "Ana"));
        when(barcoService.saveBarco(any(BarcoModel.class))).thenReturn(barcoCreado);

        //realizamos la solicitud POST para crear el nuevo barco
        mockMvc.perform(MockMvcRequestBuilders.post("/barco")
                .content("{\"numeroMatricula\":\"MatriculaNuevo\", \"nombre\":\"NuevoBarco\", \"numeroAmarre\":789, \"cuota\":300.0}")
                .param("socioId", "3") //le pasamos el ID del socio como parámetro
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("NuevoBarco")); //y verificamos que el nombre del barco creado sea correcto
    }


    @Test
    void testUpdateBarco() throws Exception {
        BarcoModel barcoExistente = new BarcoModel(1L, "Matricula1", "BarcoExistente", 123, 100.0, new SocioModel(1L, "María"));
        BarcoModel barcoActualizado = new BarcoModel(1L, "Matricula1", "BarcoActualizado", 123, 100.0, new SocioModel(2L, "Pepe"));

        when(barcoService.getBarcoBy(1L)).thenReturn(Optional.of(barcoExistente));
        when(barcoService.saveBarco(any(BarcoModel.class))).thenReturn(barcoActualizado);

        mockMvc.perform(MockMvcRequestBuilders.put("/barco/1")
                .content("{\"numeroMatricula\":\"Matricula1\", \"nombre\":\"BarcoActualizado\", \"numeroAmarre\":123, \"cuota\":100.0}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("BarcoActualizado"));
    }


    @Test
    void testDeleteBarco() throws Exception {
        when(barcoService.deleteBarco(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/barco/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Barco con ID 1 borrado correctamente"));
    }
}
