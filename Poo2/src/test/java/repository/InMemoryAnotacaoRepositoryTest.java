package repository;

import model.Acorde;
import model.Anotacao;
import model.ElementoMusical;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAnotacaoRepositoryTest {

    private AnotacaoRepositorio anotacaoRepository;

    @BeforeEach
    void setUp() {
        // Cria uma nova instância limpa antes de cada teste.
        // O construtor já pré-popula com duas anotações.
        anotacaoRepository = new InMemoryAnotacaoRepositorio();
    }

    @Test
    @DisplayName("Deve salvar uma nova anotação e atribuir o próximo ID")
    void deveSalvarNovaAnotacao() {
        // Dado
        Anotacao novaAnotacao = new Anotacao("Revisar a harmonia desta seção", null);
        
        // Quando
        Anotacao anotacaoSalva = anotacaoRepository.save(novaAnotacao);
        
        // Então
        assertAll("Verifica se a nova anotação foi salva corretamente",
            () -> assertEquals(3, anotacaoSalva.getId(), "O ID da nova anotação deve ser 3"),
            () -> assertEquals(3, anotacaoRepository.findAll().size(), "O repositório deve ter 3 anotações no total")
        );
    }

    @Test
    @DisplayName("Deve encontrar anotações por ID do elemento associado")
    void deveEncontrarAnotacaoPorElementoId() {
        // Dado: O construtor cria uma anotação e a associa a um Acorde com ID 99.
        
        // Quando
        List<Anotacao> resultado = anotacaoRepository.findByElementoId(99);
        
        // Então
        assertEquals(1, resultado.size(), "Deveria encontrar 1 anotação para o elemento de ID 99");
        assertEquals("Usar este acorde na ponte da música", resultado.get(0).getTexto());
    }

    @Test
    @DisplayName("Deve retornar lista vazia se nenhum elemento for encontrado pelo ID")
    void deveRetornarListaVaziaParaElementoIdInexistente() {
        // Quando
        List<Anotacao> resultado = anotacaoRepository.findByElementoId(111); // ID que não existe
        
        // Então
        assertTrue(resultado.isEmpty(), "A lista deveria estar vazia");
    }

    @Test
    @DisplayName("Deve encontrar uma anotação existente pelo seu ID")
    void deveEncontrarAnotacaoPorId() {
        // Dado: O repositório pré-popula uma anotação não associada com ID 2.
        
        // Quando
        Optional<Anotacao> resultado = anotacaoRepository.findById(2);

        // Então
        assertTrue(resultado.isPresent());
        assertEquals("Lembrar de comprar cordas novas para o violão", resultado.get().getTexto());
    }
}