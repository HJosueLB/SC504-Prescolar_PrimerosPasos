package primeros_pasos.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "V_TAREAS_GRUPOS") 
public class AdministrarTareas implements Serializable {

    @Id
    private Long codigoTarea;
    private String descripcion;
    private Date fechaLimite;  
    private String grupo;
    private String nombreProfesor;

    // Getters y Setters
    public Long getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(Long codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }
}
