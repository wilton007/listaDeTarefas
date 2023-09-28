package com.listaprafazer.todolist.service;

import com.listaprafazer.todolist.repository.ParametrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametroService {


    @Autowired
    private ParametrosRepository parametrosRepository;

    public String buscarValor(String parametro){
        return parametrosRepository.buscarValorPorId(parametro);
    }
}
