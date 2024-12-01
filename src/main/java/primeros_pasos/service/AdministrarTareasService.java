package primeros_pasos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import primeros_pasos.dao.AdministrarTareasDao;
import primeros_pasos.domain.AdministrarTareas;
import primeros_pasos.domain.Profesor;
import primeros_pasos.domain.Grupo;

import java.util.List;
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
        administrarTareasDao.eliminarTarea(id);
    }
    
    public void actualizarTarea(Long codigoTarea, String descripcion, String fechaLimite, String grupo, String profesor) {
        administrarTareasDao.actualizarTarea(codigoTarea, descripcion, fechaLimite, grupo, profesor);
    }

}
