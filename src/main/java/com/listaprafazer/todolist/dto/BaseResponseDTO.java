package com.listaprafazer.todolist.dto;

import lombok.Data;

@Data
public class BaseResponseDTO {
    private Integer codigo;
    private String mensagem;
    private Object dados;
}
