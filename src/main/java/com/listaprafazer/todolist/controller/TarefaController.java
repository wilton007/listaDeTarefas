package com.listaprafazer.todolist.controller;

import com.listaprafazer.todolist.dto.BaseResponseDTO;
import com.listaprafazer.todolist.dto.TarefaRequestDTO;
import com.listaprafazer.todolist.dto.TarefaRequestDTOatuaizacao;
import com.listaprafazer.todolist.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    @Autowired
    TarefaService service;
    @PostMapping
    public ResponseEntity<BaseResponseDTO> salvar(@Valid @RequestBody TarefaRequestDTO tarefaRequestDTO){
            return BaseController.ok(service.savarTarefa(tarefaRequestDTO));
    }

    @GetMapping
    public ResponseEntity<BaseResponseDTO> listarTarefas(){
        try{
            return BaseController.ok(service.listaDeTarefas());
        }catch (RuntimeException r){
            return BaseController.err(r.getMessage());
        }
    }

    @GetMapping("/id")
    public ResponseEntity<BaseResponseDTO> buscarPorId(@RequestParam Long id){
        try{
            return BaseController.ok(service.buscarPeloId(id));
        }catch (RuntimeException r){
            return BaseController.err(r.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponseDTO> atualizarTarefa(@RequestBody TarefaRequestDTOatuaizacao tarefaRequestDTOatuaizacao){
        try{
            return BaseController.ok(service.atualizarTarefa(tarefaRequestDTOatuaizacao));
        }catch (RuntimeException r){
            return BaseController.err(r.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<BaseResponseDTO> apagarPorId(@RequestParam Long id){
        try{
            return BaseController.ok(service.deletarPorId(id));
        }catch (RuntimeException r){
            return BaseController.err(r.getMessage());
        }
    }

}
