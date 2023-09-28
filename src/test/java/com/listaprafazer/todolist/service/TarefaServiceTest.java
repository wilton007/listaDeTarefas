package com.listaprafazer.todolist.service;

import com.listaprafazer.todolist.dto.TarefaRequestDTO;
import com.listaprafazer.todolist.dto.TarefaRequestDTOatuaizacao;
import com.listaprafazer.todolist.dto.TarefaResponseDTO;
import com.listaprafazer.todolist.dto.TarefaResponseDTOfinalizada;
import com.listaprafazer.todolist.enums.ParametrosEnum;
import com.listaprafazer.todolist.enums.PrioridadesEnum;
import com.listaprafazer.todolist.enums.StatusTarefaEnum;
import com.listaprafazer.todolist.mapper.TarefaMapper;
import com.listaprafazer.todolist.model.Tarefa;
import com.listaprafazer.todolist.model.Usuario;
import com.listaprafazer.todolist.repository.TarefaRepository;
import com.listaprafazer.todolist.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @InjectMocks
    private TarefaService service;
    @Mock
    private TarefaRepository repository;
    @Mock
    private ParametroService parametroService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private TarefaMapper mapper;
    private Tarefa tarefa;
    private TarefaRequestDTO requestDTO;
    private TarefaResponseDTO responseDTO;
    private Usuario usuario;
    private TarefaRequestDTOatuaizacao requestDTOatuaizacao;
    private TarefaRequestDTOatuaizacao tarefaRequestDTOatuaizacao;
    private TarefaResponseDTOfinalizada responseDTOfinalizada;
    private final int TIME = 1;
    private final String NOSAVE = "Usuario ja alcançou limite diario de 3 tarefas";
    private final String LISTAVAZIA = "Lista esta vazia";
    private final String IDNAOEXISTENTE = "id não existente";
    private final String DELETADO = "Deletado com sucesso!";

    @BeforeEach
    void setUp() {
        startUsuario();
        startTarefa();
        startTarefaRequest();
        startTarefaResponse();
        startRequestDTOAtualizacao();
        startResponseDTOFinalizado();
        startRequestDTOAtualizacaoSemCocluir();
    }

    @Test
    void verificarSalvamentoDeTarefas() {
        when(repository.save(any())).thenReturn(tarefa);
        when(usuarioRepository.getReferenceById(requestDTO.getUsuarioId())).thenReturn(usuario);
        when(repository.countTask(any(), any())).thenReturn(1);
        when(parametroService.buscarValor(any())).thenReturn("2");
        Object response = service.savarTarefa(requestDTO);
        assertEquals(TarefaResponseDTO.class, response.getClass());
        assertNotNull(response);
        verify(repository, times(TIME)).save(any());
        verify(usuarioRepository, times(TIME)).getReferenceById(any());
    }

    @Test
    void naoSalvandoTarefa() {
        when(repository.countTask(any(), any())).thenReturn(4);
        when(parametroService.buscarValor(any())).thenReturn("3");
        Object response = service.savarTarefa(requestDTO);
        assertEquals(NOSAVE, response);
        verify(repository, times(TIME)).countTask(any(), any());
        verify(parametroService, times(2)).buscarValor(any());
    }

    @Test
    void pegarLIstaDeTarefas() {
        List<Tarefa> list = new ArrayList<>();
        list.add(tarefa);
        when(repository.findAll()).thenReturn(list);
        List<TarefaResponseDTO> listResponse = service.listaDeTarefas();
        assertEquals(mapper.toListTarefaResponseDTO(list), listResponse);
        verify(repository, times(TIME)).findAll();
    }

    @Test
    void tentarPegarListaVazia() {
        List<Tarefa> list = new ArrayList<>();
        when(repository.findAll()).thenReturn(list);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.listaDeTarefas();
        });
        verify(repository, times(TIME)).findAll();
        assertEquals(LISTAVAZIA, exception.getMessage());
    }

    @Test
    void buscarTarefaPorId(){
        when(repository.existsById(1l)).thenReturn(true);
        when(repository.getReferenceById(1l)).thenReturn(tarefa);
        TarefaResponseDTO response = service.buscarPeloId(1l);
        verify(repository, times(TIME)).existsById(1l);
        verify(repository, times(TIME)).getReferenceById(1l);
        assertEquals(TarefaResponseDTO.class, response.getClass());
    }
    @Test
    void buscandoUsuarioNoaExistentePeloId(){
        when(repository.existsById(1l)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPeloId(1l);
        });
        verify(repository, times(TIME)).existsById(1l);
        assertEquals(IDNAOEXISTENTE, exception.getMessage());
    }

    @Test
    void atualizarUmaTarefa(){
        when(repository.existsById(any())).thenReturn(true);
        when(repository.getReferenceById(any())).thenReturn(tarefa);
        when(repository.save(any())).thenReturn(tarefa);
        when(mapper.toTarefaResponseDTOfinalizada(any())).thenReturn(new TarefaResponseDTOfinalizada());
        Object response = service.atualizarTarefa(requestDTOatuaizacao);
        verify(repository, times(TIME)).existsById(any());
        verify(repository, times(TIME)).getReferenceById(any());
        verify(repository, times(TIME)).save(any());
        assertEquals(TarefaResponseDTOfinalizada.class, response.getClass());
    }

    @Test
    void atualizarUmaTarefaSemCocluir(){
        when(repository.existsById(any())).thenReturn(true);
        when(repository.getReferenceById(any())).thenReturn(tarefa);
        when(repository.save(any())).thenReturn(tarefa);
        when(mapper.toTarefaResponseDTO(any())).thenReturn(new TarefaResponseDTO());
        Object response = service.atualizarTarefa(tarefaRequestDTOatuaizacao);
        verify(repository, times(TIME)).existsById(any());
        verify(repository, times(TIME)).getReferenceById(any());
        verify(repository, times(TIME)).save(any());
        assertEquals(TarefaResponseDTO.class, response.getClass());
    }

    @Test
    void deletarPorId(){
        when(repository.existsById(any())).thenReturn(true);
        doNothing().when(repository).deleteById(any());
        String response = service.deletarPorId(1l);
        assertEquals(DELETADO, response );
    }



    private void startTarefaRequest() {
        requestDTO = new TarefaRequestDTO();
        requestDTO.setTitulo("ingles");
        requestDTO.setUsuarioId(1l);
        requestDTO.setDescricao("how to do");
        requestDTO.setPrioridades(PrioridadesEnum.BAIXA);
    }

    private void startTarefaResponse() {
        responseDTO = new TarefaResponseDTO();
        responseDTO.setId(1l);
        responseDTO.setTitulo("ingles");
        responseDTO.setDescricao("how to do");
        responseDTO.setPrioridades(PrioridadesEnum.BAIXA);
        responseDTO.setStatus(StatusTarefaEnum.PENDENTE);
        responseDTO.setDataInicio(LocalDate.now());
    }

    private void startTarefa() {
        tarefa = new Tarefa();
        tarefa.setId(1l);
        tarefa.setTitulo("ingles");
        tarefa.setDescricao("how to do");
        tarefa.setPrioridades(PrioridadesEnum.BAIXA);
        tarefa.setStatus(StatusTarefaEnum.PENDENTE);
        tarefa.setDataInicio(LocalDate.now());
        tarefa.setUsuario(usuario);
    }

    private void startUsuario() {
        usuario = new Usuario();
        usuario.setId(1l);
        usuario.setNome("junior");
    }

    private void startRequestDTOAtualizacao(){
        requestDTOatuaizacao = new TarefaRequestDTOatuaizacao();
        requestDTOatuaizacao.setId(1l);
        requestDTOatuaizacao.setTitulo("ingles");
        requestDTOatuaizacao.setDescricao("how to do");
        requestDTOatuaizacao.setPrioridades(PrioridadesEnum.BAIXA);
        requestDTOatuaizacao.setStatus(StatusTarefaEnum.COCLUIDA);
        requestDTOatuaizacao.setDataInicio(tarefa.getDataInicio());
    }
    private void startRequestDTOAtualizacaoSemCocluir(){

        tarefaRequestDTOatuaizacao = new TarefaRequestDTOatuaizacao();
        tarefaRequestDTOatuaizacao.setId(1l);
        tarefaRequestDTOatuaizacao.setTitulo("ingles");
        tarefaRequestDTOatuaizacao.setDescricao("how to do");
        tarefaRequestDTOatuaizacao.setPrioridades(PrioridadesEnum.BAIXA);
        tarefaRequestDTOatuaizacao.setStatus(StatusTarefaEnum.EM_ANDAMENTO);
        tarefaRequestDTOatuaizacao.setDataInicio(tarefa.getDataInicio());
    }

    private void startResponseDTOFinalizado(){
        responseDTOfinalizada = new TarefaResponseDTOfinalizada();
        responseDTOfinalizada.setId(1l);
        responseDTOfinalizada.setTitulo("ingles");
        responseDTOfinalizada.setDescricao("how to do");
        responseDTOfinalizada.setPrioridades(PrioridadesEnum.BAIXA);
        responseDTOfinalizada.setStatus(StatusTarefaEnum.COCLUIDA);
        responseDTOfinalizada.setDataFim(LocalDateTime.now());
    }

}
