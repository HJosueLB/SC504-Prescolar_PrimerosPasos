package primeros_pasos.service;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import primeros_pasos.dao.AdministrarTareasDao;
import primeros_pasos.domain.AdministrarTareas;
import primeros_pasos.domain.Profesor;
import primeros_pasos.domain.Grupo;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdministrarTareasService {

    @Autowired
    private AdministrarTareasDao administrarTareasDao;

    public List<AdministrarTareas> obtenerTareas() {
        return administrarTareasDao.findAll();
    }

    public Optional<AdministrarTareas> obtenerTareaPorId(Long id) {
        return Optional.ofNullable(administrarTareasDao.findById(id));
    }

    public List<Grupo> obtenerGrupos() {
        return administrarTareasDao.findAllGrupos();
    }

    public List<Profesor> obtenerProfesores() {
        return administrarTareasDao.findAllProfesores();
    }

    public void eliminarTarea(Long id) {
        AdministrarTareas tareaExistente = administrarTareasDao.findById(id);

        if (tareaExistente != null) {
            administrarTareasDao.eliminarTarea(id);
            System.out.println("Se ha eliminado correctamente la tarea.");
        } else {
            System.out.println("Ha ocurrido un error al eliminar la tarea");
        }
    }

    public void actualizarTarea(Long codigoTarea, String descripcion, String fechaLimite, String grupo, String profesor) {
        try {
            // Obtener la tarea existente
            AdministrarTareas tareaExistente = administrarTareasDao.findById(codigoTarea);

            // Mapas para almacenar los datos previos y nuevos
            Map<String, String> datosPrevios = new HashMap<>();
            Map<String, String> datosNuevos = new HashMap<>();

            // Comparar descripción
            if (!tareaExistente.getDescripcion().equals(descripcion)) {
                datosPrevios.put("descripcion", tareaExistente.getDescripcion());
                datosNuevos.put("descripcion", descripcion);
            }

            // Comparar fecha límite
            if (!tareaExistente.getFechaLimite().toString().equals(fechaLimite)) {
                datosPrevios.put("fechaLimite", tareaExistente.getFechaLimite().toString());
                datosNuevos.put("fechaLimite", fechaLimite);
            }

            // Comparar grupo
            if (!tareaExistente.getGrupo().equals(grupo)) {
                datosPrevios.put("grupo", tareaExistente.getGrupo());
                datosNuevos.put("grupo", grupo);
            }

            // Comparar profesor
            if (!tareaExistente.getNombreProfesor().equals(profesor)) {
                datosPrevios.put("profesor", tareaExistente.getNombreProfesor());
                datosNuevos.put("profesor", profesor);
            }

            // Realizar la actualización
            administrarTareasDao.actualizarTarea(codigoTarea, descripcion, fechaLimite, grupo, profesor);

            System.out.println("Actualización exitosa.");
        } catch (Exception e) {
            System.err.println("Error al actualizar tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
