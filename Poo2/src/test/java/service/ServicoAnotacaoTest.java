package service;

import model.Anotacao;
import model.ElementoMusical;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AnotacaoRepositorio;
import repository.ElementoMusicalRepositorio;
import repository.InMemoryAnotacaoRepositorio;
import repository.InMemoryElementoMusicalRepositorio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicoAnotacaoTest {

    private ServicoAnotacao servicoAnotacao;
    private AnotacaoRepositorio anotacaoRepository;
    private ElementoMusicalRepositorio elementoMusicalRepository;

    @BeforeEach
    void setUp() {
        // Criamos instâncias novas dos repositórios em memória para cada teste.
        anotacaoRepository = new InMemoryAnotacaoRepositorio();
        elementoMusicalRepository = new InMemoryElementoMusicalRepositorio();
        
        // Injetamos os repositórios "fake" no serviço que vamos testar.
        servicoAnotacao = new ServicoAnotacao(anotacaoRepository, elementoMusicalRepository);
    }

    @Test
    @DisplayName("Deve criar uma nova anotação com sucesso")
    void deveCriarAnotacao() {
        // Quando
        Anotacao anotacao = servicoAnotacao.criarAnotacao("Revisar a modulação para o refrão.");

        // Então
        assertAll("Verifica a criação da anotação",
            () -> assertNotNull(anotacao),
            () -> assertTrue(anotacao.getId() > 0, "A anotação deve ter um ID atribuído pelo repositório"),
            () -> assertEquals("Revisar a modulação para o refrão.", anotacao.getTexto()),
            () -> assertNull(anotacao.getElementoSobre(), "A anotação deve ser criada sem elemento associado")
        );
    }

    @Test
    @DisplayName("Deve associar uma anotação a um elemento musical existente")
    void deveAssociarAnotacaoAElemento() {
        // Dado
        Anotacao anotacao = servicoAnotacao.criarAnotacao("Este acorde funciona como dominante secundário.");
        // O InMemoryElementoMusicalRepository já tem um Acorde com ID 1
        int idAcorde = 1;

        // Quando
        Anotacao anotacaoAtualizada = servicoAnotacao.associarAnotacao(anotacao.getId(), idAcorde);
        
        // Então
        assertNotNull(anotacaoAtualizada.getElementoSobre(), "O elemento associado não deve ser nulo");
        assertEquals(idAcorde, anotacaoAtualizada.getElementoSobre().getId());
    }
    
    @Test
    @DisplayName("Deve desassociar um elemento de uma anotação")
    void deveDesassociarElemento() {
        // Dado
        Anotacao anotacao = servicoAnotacao.criarAnotacao("Anotação inicial");
        anotacao = servicoAnotacao.associarAnotacao(anotacao.getId(), 1); // Associa
        assertNotNull(anotacao.getElementoSobre(), "Garante que a associação inicial foi feita");

        // Quando
        Anotacao anotacaoAtualizada = servicoAnotacao.associarAnotacao(anotacao.getId(), null); // Desassocia

        // Então
        assertNull(anotacaoAtualizada.getElementoSobre(), "O elemento associado deve voltar a ser nulo");
    }

    @Test
    @DisplayName("Deve buscar todas as anotações de um elemento específico")
    void deveBuscarAnotacoesPorEntidade() {
        // Dado: Criamos duas anotações e associamos ambas ao mesmo acorde (ID 1)
        Anotacao anotacao1 = servicoAnotacao.criarAnotacao("Primeiro comentário sobre o acorde C.");
        servicoAnotacao.associarAnotacao(anotacao1.getId(), 1);
        
        Anotacao anotacao2 = servicoAnotacao.criarAnotacao("Segundo comentário sobre o acorde C.");
        servicoAnotacao.associarAnotacao(anotacao2.getId(), 1);
        
        // Criamos uma terceira anotação para outro elemento para garantir que o filtro funciona
        servicoAnotacao.associarAnotacao(servicoAnotacao.criarAnotacao("Comentário aleatório").getId(), 2);

        // Quando
        List<Anotacao> resultado = servicoAnotacao.buscarAnotacoesPorEntidade(1);

        // Então
        assertEquals(2, resultado.size(), "Deveria encontrar exatamente 2 anotações para o elemento de ID 1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao associar com elemento musical inexistente")
    void deveLancarExcecaoAoAssociarComElementoInexistente() {
        Anotacao anotacao = servicoAnotacao.criarAnotacao("Teste de falha");
        int idInexistente = 999;

        // Quando e Então
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicoAnotacao.associarAnotacao(anotacao.getId(), idInexistente);
        });

        assertTrue(exception.getMessage().contains("Elemento Musical não encontrado"));
    }
}