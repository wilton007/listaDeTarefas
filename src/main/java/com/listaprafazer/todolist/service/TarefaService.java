package com.listaprafazer.todolist.service;

import com.listaprafazer.todolist.dto.*;
import com.listaprafazer.todolist.enums.ParametrosEnum;
import com.listaprafazer.todolist.enums.StatusTarefaEnum;
import com.listaprafazer.todolist.mapper.TarefaMapper;
import com.listaprafazer.todolist.model.Tarefa;
import com.listaprafazer.todolist.repository.ParametrosRepository;
import com.listaprafazer.todolist.repository.TarefaRepository;
import com.listaprafazer.todolist.repository.UsuarioRepository;
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
    private TarefaRepository tarefaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TarefaMapper mapper;

    @Autowired
    private ParametroService parametroService;


    private static final Logger log = LoggerFactory.getLogger(TarefaService.class);

    public Object savarTarefa(TarefaRequestDTO tarefaRequestDTO) {
        if (verificarQuantidadeTarefa(tarefaRequestDTO.getUsuarioId())) {
            TarefaResponseDTO responseDTO = new TarefaResponseDTO();
            Tarefa tarefa = new Tarefa();
            Tarefa tarefa2 = new Tarefa();
            tarefa.setDataInicio(LocalDate.now());
            tarefa.setStatus(StatusTarefaEnum.PENDENTE);
            tarefa.setUsuario(usuarioRepository.getReferenceById(tarefaRequestDTO.getUsuarioId()));
            BeanUtils.copyProperties(tarefaRequestDTO, tarefa);
            tarefa2 = tarefaRepository.save(tarefa);
            BeanUtils.copyProperties(tarefa2, responseDTO);
            log.info("Tarefa {} salva", tarefa.getTitulo());
            return responseDTO;
        } else {
            return "Usuario ja alcançou limite diario de " + Integer.parseInt(parametroService.buscarValor(ParametrosEnum.QUANTIDADE_MAXIMA_TAREFA_DIARIA.name())) + " tarefas";
        }

    }

    private boolean verificarQuantidadeTarefa(Long usuarioId) {
        return (tarefaRepository.countTask(usuarioId, LocalDate.now()) <= Integer.parseInt(
                parametroService.buscarValor(ParametrosEnum.QUANTIDADE_MAXIMA_TAREFA_DIARIA.name()))) ?
                true : false;
    }


    public List<TarefaResponseDTO> listaDeTarefas() {
        List<TarefaResponseDTO> listTarefaResponse = mapper.toListTarefaResponseDTO(verificarLista(tarefaRepository.findAll()));
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
        return mapper.toTarefaResponseDTO(tarefaRepository.getReferenceById(id));
    }

    private void existeTarefa(Long id) {
        if (!tarefaRepository.existsById(id)) {
            log.info("buca de id [{}] nao existente", id);
            throw new RuntimeException("id não existente");
        }
    }

    public Object atualizarTarefa(TarefaRequestDTOatuaizacao tarefaRequestDTOatuaizacao) {
        existeTarefa(tarefaRequestDTOatuaizacao.getId());
        tarefaRequestDTOatuaizacao.setDataInicio(tarefaRepository.getReferenceById(tarefaRequestDTOatuaizacao.getId()).getDataInicio());
        Tarefa tarefa = tarefaRepository.save(mapper.toTarefa(tarefaRequestDTOatuaizacao));
        if (tarefaRequestDTOatuaizacao.getStatus().equals(StatusTarefaEnum.COCLUIDA)) {
            TarefaResponseDTOfinalizada response = mapper.toTarefaResponseDTOfinalizada(tarefa);
            log.info("Tarefa de id [{}] finalizada", tarefa.getId());
            return response;
        } else {
            log.info("Tarefa de id [{}] atualizada", tarefa.getId());
            return mapper.toTarefaResponseDTO(tarefa);
        }
    }

    public String deletarPorId(Long id) {
        existeTarefa(id);
        tarefaRepository.deleteById(id);
        log.info("Tarefa de id [{}] deletada do banco", id);
        return "Deletado com sucesso!";
    }
}