package com.listaprafazer.todolist.service;

import com.listaprafazer.todolist.dto.TarefaRequestDTO;
import com.listaprafazer.todolist.dto.TarefaRequestDTOatuaizacao;
import com.listaprafazer.todolist.dto.TarefaResponseDTO;
import com.listaprafazer.todolist.dto.TarefaResponseDTOfinalizada;
import com.listaprafazer.todolist.enums.StatusTarefaEnum;
import com.listaprafazer.todolist.mapper.TarefaMapper;
import com.listaprafazer.todolist.model.Tarefa;
import com.listaprafazer.todolist.repository.TarefaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository repository;
    @Autowired
    private TarefaMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(TarefaService.class);

    public TarefaResponseDTO savarTarefa(TarefaRequestDTO tarefaRequestDTO) {
        TarefaResponseDTO responseDTO = new TarefaResponseDTO();
        Tarefa tarefa = new Tarefa();
        tarefa.setDataInicio(LocalDate.now());
        tarefa.setStatus(StatusTarefaEnum.PENDENTE);
        BeanUtils.copyProperties(tarefaRequestDTO, tarefa);
        BeanUtils.copyProperties(tarefa, responseDTO);
        responseDTO.setId(tarefa.getId());
        repository.save(tarefa);
        log.info("Tarefa {} salva", tarefa.getTitulo());
        return responseDTO;
    }


    public List<TarefaResponseDTO> listaDeTarefas() {
        List<TarefaResponseDTO> listTarefaResponse = mapper.toListTarefaResponseDTO(verificarLista(repository.findAll()));
        log.info("buscou lista de tarefas");
        return listTarefaResponse;
    }

    private List<Tarefa> verificarLista(List<Tarefa> list) {
        if (list.isEmpty()) {
            throw new RuntimeException("Lista esta vazia");
        }
        return list;
    }

    public TarefaResponseDTO buscarPeloId(Long id) {
        existeTarefa(id);
        log.info("buscou tarefa com id [{}]", id);
        return mapper.toTarefaResponseDTO(repository.getReferenceById(id));
    }

    private void existeTarefa(Long id) {
        if (!repository.existsById(id)) {
            log.info("buca de id [{}] nao existente", id);
            throw new RuntimeException("id não existente");
        }
    }

    public Object atualizarTarefa(TarefaRequestDTOatuaizacao tarefaRequestDTOatuaizacao) {
        existeTarefa(tarefaRequestDTOatuaizacao.getId());
        Tarefa tarefa = repository.getReferenceById(tarefaRequestDTOatuaizacao.getId());

        tarefaRequestDTOatuaizacao.setDataInicio(tarefa.getDataInicio());
        repository.save(mapper.toTarefa(tarefaRequestDTOatuaizacao));
        if (tarefaRequestDTOatuaizacao.getStatus().equals(StatusTarefaEnum.COCLUIDA)) {
            TarefaResponseDTOfinalizada response = mapper.toTarefaResponseDTOfinalizada(tarefaRequestDTOatuaizacao);
            response.setDataFim(tarefa.getDataFim());
            log.info("Tarefa de id [{}] finalizada", tarefa.getId());
            return response;
        } else {
            log.info("Tarefa de id [{}] atualizada", tarefa.getId());
            return mapper.toTarefaResponseDTO(tarefaRequestDTOatuaizacao);
        }
    }

    public String deletarPorId(Long id) {
        existeTarefa(id);
        repository.deleteById(id);
        log.info("Tarefa de id [{}] deletada do banco", id);
        return "Deletado com sucesso!";
    }
}
