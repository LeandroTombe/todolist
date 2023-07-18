package madstodolist.dto;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class UsuarioData {

    private Long id;
    private String email;
    private String nombre;
    private Date fechaNacimiento;
    private Set<TareaData> tareas;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Set<TareaData> getTareas() {
        return tareas;
    }

    public void setTareas(Set<TareaData> tareasIds) {
        this.tareas = tareasIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioData)) return false;
        UsuarioData that = (UsuarioData) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getNombre(), that.getNombre()) &&
                Objects.equals(getFechaNacimiento(), that.getFechaNacimiento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getNombre(), getFechaNacimiento());
    }
}
