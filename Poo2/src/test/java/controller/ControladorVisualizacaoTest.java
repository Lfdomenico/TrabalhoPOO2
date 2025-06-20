package controller;

import model.Acorde;
import model.Anotacao;
import model.ElementoMusical;
import model.Nota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ServicoAnotacao;
import service.ServicoBusca;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControladorVisualizacaoTest {

    @Mock
    private ServicoBusca servicoBuscaMock;

    @Mock
    private ServicoAnotacao servicoAnotacaoMock;

    @InjectMocks
    private ControladorVisualizacao controladorVisualizacao;

    @Test
    @DisplayName("Deve buscar o elemento e suas anotações ao exibir detalhes")
    void deveBuscarElementoEanotacoes() {
        // Dado (Given)
        int idElementoParaExibir = 42;

        // --- CORREÇÃO: Criando o Acorde de exemplo da forma correta ---
        Nota tonicaC = new Nota("C", null, 4);
        Acorde acordeExemplo = new Acorde(
            "Maior", // tipo
            tonicaC, // tonica
            "C",     // nome
            List.of(tonicaC, new Nota("E", null, 4), new Nota("G", null, 4)) // notas
        );
        acordeExemplo.setId(idElementoParaExibir); // Atribuímos o ID que será usado na busca
        // -------------------------------------------------------------

        // Criamos a lista de anotações de exemplo
        List<Anotacao> anotacoesExemplo = List.of(new Anotacao("Tocar com swing", acordeExemplo));

        // Configuramos os mocks para retornar os dados de exemplo quando forem chamados
        when(servicoBuscaMock.buscarEntidadeMusical(idElementoParaExibir)).thenReturn(acordeExemplo);
        when(servicoAnotacaoMock.buscarAnotacoesPorEntidade(idElementoParaExibir)).thenReturn(anotacoesExemplo);

        // Quando (When)
        // Chamamos o método do controller que estamos testando
        controladorVisualizacao.exibirElementoCompleto(idElementoParaExibir);

        // Então (Then)
        // Verificamos se o controller chamou os métodos corretos nos serviços, com o ID correto.
        verify(servicoBuscaMock, times(1)).buscarEntidadeMusical(42);
        verify(servicoAnotacaoMock, times(1)).buscarAnotacoesPorEntidade(42);
    }
    
    @Test
    @DisplayName("Deve lidar com erro se o elemento não for encontrado")
    void deveLidarComElementoNaoEncontrado() {
        // Dado
        int idInexistente = 99;
        
        // Configuramos o mock para LANÇAR uma exceção
        when(servicoBuscaMock.buscarEntidadeMusical(idInexistente))
            .thenThrow(new RuntimeException("Elemento não encontrado"));

        // Quando
        controladorVisualizacao.exibirElementoCompleto(idInexistente);
        
        // Então
        // Verificamos que o serviço de busca foi chamado
        verify(servicoBuscaMock, times(1)).buscarEntidadeMusical(idInexistente);
        // E garantimos que, por causa do erro, o serviço de anotações NÃO foi chamado
        verify(servicoAnotacaoMock, never()).buscarAnotacoesPorEntidade(anyInt());
    }
}