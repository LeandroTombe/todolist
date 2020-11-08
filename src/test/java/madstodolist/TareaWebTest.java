package madstodolist;

import madstodolist.authentication.ManagerUserSesion;
import madstodolist.controller.TareaController;
import madstodolist.model.Tarea;
import madstodolist.model.Usuario;
import madstodolist.service.TareaService;
import madstodolist.service.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO
// Modificar los tests de web para que se utilicen el mínimo
// número de mocks. Estos tests también cargan los datos
// de la base de datos datos-tests.sql, no hace falta añadir
// los datos a mano, ni mockear los servicios para que devuelvan
// los datos que nos interesan.

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TareaWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private TareaService tareaService;

    // Al mocker el manegerUserSession, no lanza la excepción cuando
    // se intenta comprobar si un usuario está logeado
    @MockBean
    private ManagerUserSesion managerUserSesion;

    @Test
    public void getNuevaTareaDevuelveForm() throws Exception {
        Usuario usuario = new Usuario("domingo@ua.es");
        usuario.setId(1L);

        when(usuarioService.findById(1L)).thenReturn(usuario);

        this.mockMvc.perform(get("/usuarios/1/tareas/nueva"))
                //.andDo(print())
                .andExpect(content().string(containsString("action=\"/usuarios/1/tareas/nueva\"")));
    }

    @Test
    public void getNuevaTareaDevuelveNotFound() throws Exception {

        when(usuarioService.findById(1L)).thenReturn(null);

        this.mockMvc.perform(get("/usuarios/1/tareas/nueva"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postNuevaTareaDevuelveRedirectYAñadeTarea() throws Exception {
        Usuario usuario = new Usuario("Usuario");
        usuario.setId(1L);

        when(usuarioService.findById(1L)).thenReturn(usuario);

        this.mockMvc.perform(post("/usuarios/1/tareas/nueva")
                    .param("titulo", "Estudiar examen MADS"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/usuarios/1/tareas"));

        // Verificamos que se ha añadido el método para
        // añadir una tarea con los parámetros correctos
        verify(tareaService).nuevaTareaUsuario(1L, "Estudiar examen MADS");
    }

    @Test
    public void editarTareaDevuelveForm() throws Exception {
        Tarea tarea = new Tarea(new Usuario("domingo@ua.es"), "Lavar el coche");
        tarea.setId(1L);
        tarea.getUsuario().setId(1L);

        when(tareaService.findById(1L)).thenReturn(tarea);

        this.mockMvc.perform(get("/tareas/1/editar"))
                .andExpect(content().string(allOf(
                    // Contiene la acción para enviar el post a la URL correcta
                    containsString("action=\"/tareas/1/editar\""),
                    // Contiene el texto de la tarea a editar
                    containsString("Lavar coche"),
                    // Contiene enlace a listar tareas del usuario si se cancela la edición
                    containsString("href=\"/usuarios/1/tareas\""))));
    }
}
