package madstodolist.service;

import madstodolist.model.Tarea;
import madstodolist.model.TareaRepository;
import madstodolist.model.Usuario;
import madstodolist.model.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TareaService {

    Logger logger = LoggerFactory.getLogger(TareaService.class);

    private UsuarioRepository usuarioRepository;
    private TareaRepository tareaRepository;

    @Autowired
    public TareaService(UsuarioRepository usuarioRepository, TareaRepository tareaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tareaRepository = tareaRepository;
    }

    @Transactional
    public Tarea nuevaTareaUsuario(Long idUsuario, String tituloTarea) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new TareaServiceException("Usuario " + idUsuario + " no existe al crear tarea " + tituloTarea);
        }
        Tarea tarea = new Tarea(usuario, tituloTarea);
        tareaRepository.save(tarea);
        return tarea;
    }
}