package primeros_pasos.service;

import primeros_pasos.domain.Grupo;
import primeros_pasos.domain.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

@Service
public class TareaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // Configuración para llamar procedimiento almacenado OBTENERGRUPOS
    public List<Grupo> obtenerGrupos() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("OBTENERGRUPOS")
                .returningResultSet("p_grupo", new BeanPropertyRowMapper<>(Grupo.class));

        // Ejecutar el procedimiento y obtiene los resultados
        Map<String, Object> result = jdbcCall.execute();
        // Obtener la lista desde el cursor
        return (List<Grupo>) result.get("p_grupo");
    }

    // Configuración para llamar procedimiento almacenado OBTENERPROFESORES
    public List<Profesor> obtenerProfesores() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ObtenerProfesores")
                .returningResultSet("p_profesores", new BeanPropertyRowMapper<>(Profesor.class));

        // Ejecutar el procedimiento y obtiene los resultados
        Map<String, Object> result = jdbcCall.execute();
        // Obtener la lista desde el cursor
        return (List<Profesor>) result.get("p_profesores");
    }

    // Método para asignar una tarea
    @Transactional
    public void asignarTarea(String description, java.util.Date dueDate, int idGrupo, int idProfesor) {
        try {
            String sql = "{call ASIGNARTAREA(?, ?, ?, ?)}";
            jdbcTemplate.update(sql, description, new java.sql.Date(dueDate.getTime()), idGrupo, idProfesor);
            System.out.println("Tarea asignada con éxito");
        } catch (Exception e) {
            System.err.println("Error al asignar tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
