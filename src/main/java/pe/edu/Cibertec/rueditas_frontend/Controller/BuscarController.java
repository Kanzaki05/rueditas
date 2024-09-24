package pe.edu.Cibertec.rueditas_frontend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.Cibertec.rueditas_frontend.dto.BuscarRequestDTO;
import pe.edu.Cibertec.rueditas_frontend.dto.BuscarResponseDTO;
import pe.edu.Cibertec.rueditas_frontend.viewmodel.BuscarModel;

@Controller
@RequestMapping("/buscarauto")
public class BuscarController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio(Model model){
        BuscarModel buscarModel = new BuscarModel("00","","","","","","");
        model.addAttribute("buscarModel", buscarModel);

        return "inicio";
    }

    @PostMapping("/datos")
    public String datos(@RequestParam("numeroPlaca") String numeroPlaca,
                        Model model) {

        //Validar campo
        if (numeroPlaca == null || numeroPlaca.trim().length() == 0){

            BuscarModel buscarModel = new BuscarModel("01","Error: Debe ingresar una placa correcta","", "","", "", "");
            model.addAttribute("buscarModel", buscarModel);

            return "inicio";
        }

        try {

            //Invocar Api
            String endpoint = "http://localhost:8081/verificacion/buscarauto";

            BuscarRequestDTO buscarRequestDTO = new BuscarRequestDTO(numeroPlaca);
            BuscarResponseDTO buscarResponseDTO = restTemplate.postForObject(endpoint, buscarRequestDTO, BuscarResponseDTO.class);

            //Validar datos
            if (buscarResponseDTO.codigo().equals("00")){

                BuscarModel buscarModel = new BuscarModel("00", "", buscarResponseDTO.marcaPlaca(), buscarResponseDTO.modeloPlaca(),
                        buscarResponseDTO.asientosPlaca(), buscarResponseDTO.precio(), buscarResponseDTO.color());
                model.addAttribute("buscarModel", buscarModel);
                return "principal";

            } else {

                BuscarModel buscarModel = new BuscarModel("02","Error: No se encontró un vehículo para la placa ingresada","", "","", "", "");
                model.addAttribute("buscarModel", buscarModel);
                return "inicio";

            }

        } catch (Exception e){

            BuscarModel buscarModel = new BuscarModel("99","Error: Ocurrio un error","", "","", "", "");
            model.addAttribute("buscarModel", buscarModel);
            System.out.println(e.getMessage());
            return "inicio";

        }
    }
}