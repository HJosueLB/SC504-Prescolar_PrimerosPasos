package primeros_pasos.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import primeros_pasos.domain.AdministrarTareas;
import primeros_pasos.domain.Profesor;
import primeros_pasos.domain.Grupo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdministrarTareasDao {

    private final JdbcTemplate jdbcTemplate;

    public AdministrarTareasDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Obtener todas las tareas desde la vista
    public List<AdministrarTareas> findAll() {
        String sql = "SELECT Código_Tarea, Descripcion, Fecha_Limite, Grupo, Nombre_Profesor FROM V_TAREAS_GRUPOS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AdministrarTareas tarea = new AdministrarTareas();
            tarea.setCodigoTarea(rs.getLong("Código_Tarea"));
            tarea.setDescripcion(rs.getString("Descripcion"));
            tarea.setFechaLimite(rs.getDate("Fecha_Limite"));
            tarea.setGrupo(rs.getString("Grupo"));
            tarea.setNombreProfesor(rs.getString("Nombre_Profesor"));
            return tarea;
        });
    }

    public AdministrarTareas findById(Long id) {
        String sql = "{call OBTENERTAREA(?, ?)}";
        return jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setLong(1, id);
            cs.registerOutParameter(2, java.sql.Types.REF_CURSOR); 
            cs.execute(); 

            ResultSet rs = (ResultSet) cs.getObject(2);
            if (rs.next()) {
                AdministrarTareas tarea = new AdministrarTareas();
                tarea.setCodigoTarea(rs.getLong("Código_Tarea"));
                tarea.setDescripcion(rs.getString("Descripcion"));
                tarea.setFechaLimite(rs.getDate("Fecha_Limite"));
                tarea.setGrupo(rs.getString("Grupo"));
                tarea.setNombreProfesor(rs.getString("Nombre_Profesor"));
                return tarea;
            } else {
                return null; 
            }
        });
    }

    public List<Grupo> findAllGrupos() {
        String sql = "SELECT Id, Nombre FROM Grupo";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Grupo grupo = new Grupo();
            grupo.setId(rs.getInt("Id"));
            grupo.setNombre(rs.getString("Nombre"));
            return grupo;
        });
    }

    public List<Profesor> findAllProfesores() {
        String sql = "SELECT Id, FirstName, LastName FROM Profesor";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Profesor profesor = new Profesor();
            profesor.setId(rs.getInt("Id"));
            profesor.setFirstName(rs.getString("FirstName"));
            profesor.setLastName(rs.getString("LastName"));
            return profesor;
        });
    }

    public void eliminarTarea(Long codigoTarea) {
        String sql = "BEGIN ELIMINARTAREA(?); END;";
        jdbcTemplate.update(sql, codigoTarea);
    }

    public void actualizarTarea(Long codigoTarea, String descripcion, String fechaLimite, String grupo, String profesor) {
        String sql = "{call ACTUALIZARTAREA(?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)}";
        jdbcTemplate.update(sql,
                codigoTarea,
                descripcion,
                fechaLimite,
                grupo,
                profesor);
    }
}
