package controller;

import java.util.ArrayList;
import model.Progressao;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ServicoProgressao;

import static org.mockito.Mockito.*;

/**
 * Teste unitário para o ControladorProgressao.
 * Usa Mockito para simular a camada de serviço.
 */
@ExtendWith(MockitoExtension.class) // Habilita a integração do Mockito com o JUnit 5
class ControladorProgressaoTest {

    @Mock // 1. Cria uma versão "dublê" (mock) do serviço
    private ServicoProgressao servicoProgressaoMock;

    @InjectMocks // 2. Cria uma instância real do controller e injeta o mock acima nele
    private ControladorProgressao controladorProgressao;

    private Progressao progressaoExemplo;

    @BeforeEach
    void setUp() {
        // Objeto de exemplo para ser retornado pelo mock
        progressaoExemplo = new Progressao(new ArrayList<>(), "Progressão de Teste");
        progressaoExemplo.setId(1);
    }

    @Test
    @DisplayName("Deve chamar o serviço de criação ao lidar com criação")
    void deveChamarServicoAoCriar() {
        // Dado (Given): Configuramos o mock para se comportar como queremos.
        // Quando o método criarProgressao for chamado com "Nova Progressão",
        // então ele deve retornar nosso objeto de exemplo.
        when(servicoProgressaoMock.criarProgressao("Nova Progressão")).thenReturn(progressaoExemplo);

        // Quando (When): Chamamos o método do controller
        controladorProgressao.lidarComCriacao("Nova Progressão");

        // Então (Then): Verificamos se o método do serviço foi chamado exatamente uma vez
        // com o parâmetro correto.
        verify(servicoProgressaoMock, times(1)).criarProgressao("Nova Progressão");
    }
    
    @Test
    @DisplayName("Deve chamar o serviço de adição de acorde ao lidar com adição")
    void deveChamarServicoAoAdicionarAcorde() {
        // Quando
        controladorProgressao.lidarComAdicaoAcorde(1, 101, 0);

        // Então: Verificamos se o serviço foi chamado com os IDs e a posição corretos.
        // Como o método do serviço retorna void (neste exemplo), não precisamos do when().
        verify(servicoProgressaoMock, times(1)).adicionarAcorde(1, 101, 0);
    }
    
    @Test
    @DisplayName("Deve chamar o serviço de exclusão ao lidar com exclusão")
    void deveChamarServicoAoExcluir() {
        // Quando
        controladorProgressao.lidarComExclusao(42);

        // Então
        verify(servicoProgressaoMock, times(1)).excluirProgressao(42);
    }
    
    @Test
    @DisplayName("Deve lidar com exceções vindas do serviço")
    void deveLidarComExcecaoDoServico() {
        // Dado: Configuramos o mock para LANÇAR uma exceção quando um método for chamado.
        when(servicoProgressaoMock.criarProgressao(anyString())).thenThrow(new IllegalArgumentException("Nome inválido"));
        
        // Quando e Então:
        // Verificamos se o controller não quebra, pois ele deve ter um bloco try-catch.
        // A lógica de imprimir o erro no console (System.err) é a "reação" do controller.
        assertDoesNotThrow(() -> {
            controladorProgressao.lidarComCriacao("Qualquer Nome");
        });
        
        // Verificamos que, mesmo com a exceção, o método do serviço foi chamado.
        verify(servicoProgressaoMock, times(1)).criarProgressao("Qualquer Nome");
    }
}