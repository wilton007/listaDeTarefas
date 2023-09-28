package com.listaprafazer.todolist.service;

import com.listaprafazer.todolist.dto.UsuarioRequestDTO;
import com.listaprafazer.todolist.dto.UsuarioResponseDTO;
import com.listaprafazer.todolist.mapper.UsuarioMapper;
import com.listaprafazer.todolist.model.Usuario;
import com.listaprafazer.todolist.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Equals;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;
    @Mock
    private UsuarioRepository repository;

    @Mock
    UsuarioMapper mapper;
    private Usuario usuario;
    private UsuarioResponseDTO responseDTO;
    private UsuarioRequestDTO requestDTO;

    private final Integer TIME = 1;
    private String nome = "junior";
    private String ID_NAO_EXISTE = "id não existente!";
    private Long id = 1l;
    private final String USUARIO_APAGADO_SUCESSO  = "Usuario Deletado";

    @BeforeEach
    void setUp() {
        startUsuarioResponseDTO();
        startUsuarioRequestDTO();
        startUsuario();
    }

    @Test
    void deveSalvarUsuario() {
        when(repository.save(any())).thenReturn(usuario);
        when(mapper.toUsuarioResponse(usuario)).thenReturn(responseDTO);

        UsuarioResponseDTO responseDTO = service.salvarUsuario(requestDTO);

        assertEquals(UsuarioResponseDTO.class, responseDTO.getClass());
        assertNotNull(responseDTO);
        assertEquals(requestDTO.getNome(), responseDTO.getNome());
        verify(repository, times(TIME)).save(any());
    }

    @Test
    void verificarSeExisteOUsuarioNoBanco(){
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);
        String response = service.apagarPorId(id);
        assertEquals(USUARIO_APAGADO_SUCESSO, response);
        verify(repository, times(TIME)).existsById(any());
    }

    @Test
    void verificarUsuarioNaoExistenteNoBanco(){
        when(repository.existsById(id)).thenReturn(false);
        RuntimeException exception =  assertThrows(RuntimeException.class, () -> {
            service.apagarPorId(id);
        });

        verify(repository, times(TIME)).existsById(any());
        assertEquals(ID_NAO_EXISTE, exception.getMessage());
    }


    private void startUsuarioResponseDTO() {
        responseDTO = new UsuarioResponseDTO();
        responseDTO.setNome(nome);
        responseDTO.setId(id);
    }

    private void startUsuarioRequestDTO() {
        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome(nome);
    }

    private void startUsuario() {
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
    }



}
