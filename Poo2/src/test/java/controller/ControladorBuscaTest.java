package controller;

import model.Acorde;
import model.Musica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ServicoBusca;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControladorBuscaTest {

    @Mock
    private ServicoBusca servicoBuscaMock;

    @InjectMocks
    private ControladorBusca controladorBusca;

    private Acorde acordeExemplo;
    private List<Musica> listaMusicasExemplo;

    @BeforeEach
    void setUp() {
        // Criando objetos de exemplo que serão retornados pelo nosso mock
        acordeExemplo = new Acorde("Maior", null, "C", Collections.emptyList());
        acordeExemplo.setId(1);
        
        listaMusicasExemplo = List.of(new Musica("Bohemian Rhapsody", "Queen", Collections.emptyList()));
    }

    @Test
    @DisplayName("Deve chamar o serviço para buscar um acorde e retornar o resultado")
    void deveBuscarAcordeCorretamente() {
        // Dado (Given): um mapa de filtros para buscar um acorde por ID
        Map<String, String> filtros = Map.of("tipo", "acorde", "id", "1");
        
        // Configuramos o mock: "Quando buscarAcorde(1) for chamado, retorne nosso acorde de exemplo"
        when(servicoBuscaMock.buscarAcorde(1)).thenReturn(acordeExemplo);

        // Quando (When): chamamos o método do controller
        List<?> resultado = controladorBusca.realizarBusca(filtros);

        // Então (Then)
        assertAll("Verifica a busca por acorde",
            () -> verify(servicoBuscaMock, times(1)).buscarAcorde(1), // Verifica se o método certo do serviço foi chamado
            () -> assertEquals(1, resultado.size(), "A lista de resultado deve conter 1 item"),
            () -> assertSame(acordeExemplo, resultado.get(0), "O item na lista deve ser o acorde retornado pelo serviço")
        );
    }

    @Test
    @DisplayName("Deve chamar o serviço para buscar músicas e retornar a lista")
    void deveBuscarMusicaCorretamente() {
        // Dado
        Map<String, String> filtros = Map.of("tipo", "musica", "query", "Queen");
        when(servicoBuscaMock.buscarMusica("Queen")).thenReturn(listaMusicasExemplo);

        // Quando
        List<?> resultado = controladorBusca.realizarBusca(filtros);

        // Então
        verify(servicoBuscaMock, times(1)).buscarMusica("Queen");
        assertEquals(1, resultado.size());
        assertEquals("Bohemian Rhapsody", ((Musica)resultado.get(0)).getTitulo());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando o serviço lança uma exceção")
    void deveLidarComExcecaoDoServico() {
        // Dado: um filtro para um ID que não existe
        Map<String, String> filtros = Map.of("tipo", "escala", "id", "999");
        
        // Configuramos o mock para LANÇAR uma exceção quando o método for chamado
        when(servicoBuscaMock.buscarEscala(999)).thenThrow(new RuntimeException("Escala 999 não encontrada"));

        // Quando
        List<?> resultado = controladorBusca.realizarBusca(filtros);

        // Então: O controller deve capturar a exceção e retornar uma lista vazia, sem quebrar
        assertTrue(resultado.isEmpty(), "O resultado deve ser uma lista vazia em caso de erro no serviço");
    }

    @Test
    @DisplayName("Deve retornar lista vazia para tipo de busca desconhecido")
    void deveRetornarVazioParaTipoDesconhecido() {
        // Dado
        Map<String, String> filtros = Map.of("tipo", "instrumento", "nome", "Violão");

        // Quando
        List<?> resultado = controladorBusca.realizarBusca(filtros);

        // Então
        assertTrue(resultado.isEmpty());
        // Garante que NENHUM método de busca do serviço foi chamado
        verify(servicoBuscaMock, never()).buscarAcorde(anyInt());
        verify(servicoBuscaMock, never()).buscarEscala(anyInt());
        verify(servicoBuscaMock, never()).buscarMusica(anyString());
    }
}