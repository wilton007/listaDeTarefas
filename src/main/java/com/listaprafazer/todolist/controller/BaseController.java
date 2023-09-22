package com.listaprafazer.todolist.controller;

import com.listaprafazer.todolist.dto.BaseResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    static final String SUCESSO = "sucesso!";
    static final String ERRO = "erro!";

    static ResponseEntity<BaseResponseDTO> sucess( Object dados){
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCodigo(HttpStatus.OK.value());
        responseDTO.setMensagem(SUCESSO);
        responseDTO.setDados(dados);
        return ResponseEntity.ok(responseDTO);
    }

    static ResponseEntity<BaseResponseDTO> sucess(){
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCodigo(HttpStatus.OK.value());
        responseDTO.setMensagem(SUCESSO);
        return ResponseEntity.ok(responseDTO);
    }

    static ResponseEntity<BaseResponseDTO> err( Object dados){
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCodigo(HttpStatus.NOT_FOUND.value());
        responseDTO.setMensagem(ERRO);
        responseDTO.setDados(dados);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    static ResponseEntity<BaseResponseDTO> ok(Object dados){
        return sucess(dados);
    }
    static ResponseEntity<BaseResponseDTO> ok(){
        return sucess();
    }
}
