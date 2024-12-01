package primeros_pasos.controller;

import primeros_pasos.domain.AdministrarTareas;
import primeros_pasos.domain.Grupo;
import primeros_pasos.domain.Profesor;
import primeros_pasos.service.AdministrarTareasService;
import primeros_pasos.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import primeros_pasos.dao.AdministrarTareasDao;

@Controller
@RequestMapping("/administrarTareas")
public class AdministrarTareasController {

    @Autowired
    private AdministrarTareasService administrarTareasService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private AdministrarTareasDao administrarTareasDao;

    @GetMapping
    public String listarTareas(Model model) {
        List<AdministrarTareas> tareas = administrarTareasService.obtenerTareas();
        model.addAttribute("tareas", tareas);
        return "AdministrarTareas/administrarTareas"; 
    }

    @ResponseBody
    @GetMapping("/eliminar/{id}")
    public Map<String, String> eliminarTarea(@PathVariable("id") Long id) {
        try {
            administrarTareasService.eliminarTarea(id);
            return Collections.singletonMap("status", "success");
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("status", "error");
        }
    }

    @GetMapping("/editar/{id}")
    public String editarTarea(@PathVariable("id") Long id, Model model) {
        Optional<AdministrarTareas> tareaOptional = administrarTareasService.obtenerTareaPorId(id);

        if (tareaOptional.isPresent()) {
            AdministrarTareas tarea = tareaOptional.get();
            List<Grupo> grupos = tareaService.obtenerGrupos(); 
            List<Profesor> profesores = tareaService.obtenerProfesores();  

            model.addAttribute("tarea", tarea);
            model.addAttribute("grupos", grupos);
            model.addAttribute("profesores", profesores);

            return "AdministrarTareas/editarTarea";
        } else {
            model.addAttribute("error", "Tarea no encontrada");
            return "redirect:/administrarTareas"; 
        }
    }

    @ResponseBody
    @PostMapping("/actualizar")
    public Map<String, String> actualizarTarea(@RequestParam Long codigoTarea,
            @RequestParam String descripcion,
            @RequestParam String fechaLimite,
            @RequestParam String grupo,
            @RequestParam String profesor) {
        try {
            administrarTareasService.actualizarTarea(codigoTarea, descripcion, fechaLimite, grupo, profesor);
            return Collections.singletonMap("status", "success");
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("status", "error");
        }
    }
    
}
