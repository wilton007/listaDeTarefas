package com.listaprafazer.todolist.service;

import com.listaprafazer.todolist.dto.UsuarioRequestDTO;
import com.listaprafazer.todolist.dto.UsuarioResponseDTO;
import com.listaprafazer.todolist.mapper.UsuarioMapper;
import com.listaprafazer.todolist.model.Usuario;
import com.listaprafazer.todolist.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    UsuarioMapper mapper;

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = repository.save(mapper.toUsuario(usuarioRequestDTO));

        log.info("usuario criado {}",usuario);
        return mapper.toUsuarioResponse(usuario);
    }

    public String apagarPorId(Long id) {
        existeUsuario(id);
        repository.deleteById(id);
        return "Usuario Deletado";
    }

    private void existeUsuario(Long id) {
        if (!repository.existsById(id)) {
            log.info("busca de id [{}] nao existente!", id);
            throw new RuntimeException("id não existente!");
        }
    }
}
