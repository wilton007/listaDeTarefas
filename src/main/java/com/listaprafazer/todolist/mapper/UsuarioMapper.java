package com.listaprafazer.todolist.mapper;

import com.listaprafazer.todolist.dto.UsuarioRequestDTO;
import com.listaprafazer.todolist.dto.UsuarioResponseDTO;
import com.listaprafazer.todolist.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioRequestDTO usuarioRequestDTO);

    UsuarioResponseDTO toUsuarioResponse(Usuario usuario);
}
