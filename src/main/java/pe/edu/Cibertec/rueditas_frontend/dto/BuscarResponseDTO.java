package pe.edu.Cibertec.rueditas_frontend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BuscarResponseDTO(String codigo, String mensaje, String marcaPlaca, String modeloPlaca, String asientosPlaca,
                                String precio, String color) {
}
