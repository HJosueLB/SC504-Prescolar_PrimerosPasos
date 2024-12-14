package primeros_pasos.service;

import primeros_pasos.domain.Grupo;
import primeros_pasos.domain.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import java.sql.Types;

@Service
public class TareaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Grupo> obtenerGrupos() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("OBTENERGRUPOS")
                .returningResultSet("p_grupo", new BeanPropertyRowMapper<>(Grupo.class));

        Map<String, Object> result = jdbcCall.execute();
        return (List<Grupo>) result.get("p_grupo");
    }

    public List<Profesor> obtenerProfesores() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ObtenerProfesores")
                .returningResultSet("p_profesores", new BeanPropertyRowMapper<>(Profesor.class));

        Map<String, Object> result = jdbcCall.execute();
        return (List<Profesor>) result.get("p_profesores");
    }

    @Transactional
    public void asignarTarea(String description, java.util.Date dueDate, int idGrupo, int idProfesor) {
        try {
            System.out.println("Iniciando el proceso de asignación de tarea...");
            System.out.println("Descripción: " + description + ", Fecha Límite: " + dueDate + ", Grupo: " + idGrupo + ", Profesor: " + idProfesor);

            // Llamada al procedimiento almacenado con retorno del ID
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("ASIGNARTAREA")
                    .declareParameters(
                            new SqlParameter("p_Description", Types.VARCHAR),
                            new SqlParameter("p_DueDate", Types.DATE),
                            new SqlParameter("p_IdGrupo", Types.INTEGER),
                            new SqlParameter("p_IdProfesor", Types.INTEGER),
                            new SqlOutParameter("p_IdTarea", Types.INTEGER)
                    );

            // Ejecutar el procedimiento almacenado y capturar el ID generado
            Map<String, Object> out = jdbcCall.execute(
                    description,
                    new java.sql.Date(dueDate.getTime()),
                    idGrupo,
                    idProfesor
            );

            System.out.println("Tarea asignada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al asignar tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
