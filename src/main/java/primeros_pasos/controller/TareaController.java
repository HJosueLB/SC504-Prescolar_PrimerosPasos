 package primeros_pasos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import primeros_pasos.domain.Grupo;
import primeros_pasos.domain.Profesor;
import primeros_pasos.service.TareaService;

import java.util.Date;
import java.util.List;

@Controller
public class TareaController {

    @Autowired
    private TareaService tareaService;

    // Mostrar el formulario de tarea con la lista de grupos y profesores
    @GetMapping("/tarea")
    public String showTareaForm(Model model) {
        List<Grupo> grupos = tareaService.obtenerGrupos();  // Obtiene los grupos desde el servicio
        List<Profesor> profesores = tareaService.obtenerProfesores();  // Obtiene los profesores desde el servicio
        model.addAttribute("grupos", grupos);  // Pasa los grupos al html de tareas
        model.addAttribute("profesores", profesores);  // Pasa los profesores al html de tareas
        return "AsignarTarea/tarea";  // Redirecciona al html de tareas
    }

    // Procesamiento de registro de tarea en base de datos
    @PostMapping("/tarea")
    public ResponseEntity<?> asignarTarea(@RequestParam String description,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dueDate,
                                          @RequestParam int idGrupo,
                                          @RequestParam int idProfesor) {
        try {
            // Llamada al servicio para asignar la tarea
            tareaService.asignarTarea(description, dueDate, idGrupo, idProfesor);

            // Retornar una respuesta en formato JSON (indicando éxito)
            return ResponseEntity.ok(new ApiResponse("success", "Tarea asignada correctamente"));

        } catch (Exception e) {
            // Retornar una respuesta en formato JSON (indicando Fallido)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse("error", "Hubo un problema al asignar la tarea"));
        }
    }

    // Respuesta JSON
    public static class ApiResponse {
        private String status;
        private String message;

        public ApiResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
