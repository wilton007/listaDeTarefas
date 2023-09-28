package com.listaprafazer.todolist.controller;

import com.listaprafazer.todolist.dto.BaseResponseDTO;
import com.listaprafazer.todolist.dto.UsuarioRequestDTO;
import com.listaprafazer.todolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
@Autowired
    UsuarioService service;

    @PostMapping
    public ResponseEntity<BaseResponseDTO> salvarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        try {
            return BaseController.ok(service.salvarUsuario(usuarioRequestDTO));
        }catch (RuntimeException r){
            return BaseController.err(r.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<BaseResponseDTO> deletarUsuarioPorId(@RequestParam Long id){
        try {
            return BaseController.ok(service.apagarPorId(id));
        }catch (RuntimeException r){
            return   BaseController.err(r.getMessage());
        }
    }

}
