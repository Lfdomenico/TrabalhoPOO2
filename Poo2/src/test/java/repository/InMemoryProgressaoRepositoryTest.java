package repository;

import model.Acorde;
import model.Nota;
import model.Progressao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProgressaoRepositoryTest {

    private ProgressaoRepositorio progressaoRepository;

    @BeforeEach
    void setUp() {
        // Cria uma nova instância limpa antes de cada teste.
        // O construtor já pré-popula com uma "Progressão Padrão Pop" com ID 1.
        progressaoRepository = new InMemoryProgressaoRepositorio();
    }

    @Test
    @DisplayName("Deve salvar uma nova progressão e atribuir o próximo ID")
    void deveSalvarNovaProgressao() {
        // Dado
        Progressao novaProgressao = new Progressao(new ArrayList<>(), "Turnaround de Jazz");
        
        // Quando
        Progressao progressaoSalva = progressaoRepository.save(novaProgressao);
        
        // Então
        assertAll("Verifica se a nova progressao foi salva corretamente",
            () -> assertEquals(2, progressaoSalva.getId(), "O ID da nova progressão deve ser 2"),
            () -> assertEquals(2, progressaoRepository.findAll().size(), "O repositório deve ter 2 progressões no total")
        );
    }

    @Test
    @DisplayName("Deve encontrar uma progressão existente pelo seu ID")
    void deveEncontrarProgressaoPorId() {
        // Quando
        Optional<Progressao> resultado = progressaoRepository.findById(1); // Progressão Padrão Pop

        // Então
        assertTrue(resultado.isPresent(), "Deveria encontrar a progressão com ID 1");
        assertEquals("Progressão Padrão Pop", resultado.get().getNome());
    }

    @Test
    @DisplayName("Deve encontrar progressões pelo nome")
    void deveEncontrarProgressaoPeloNome() {
        // Quando
        List<Progressao> resultado = progressaoRepository.findAllByNome("Progressão Padrão Pop");
        
        // Então
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
    }

    @Test
    @DisplayName("Deve deletar uma progressão pelo ID")
    void deveDeletarProgressao() {
        // Garante que o item existe antes
        assertTrue(progressaoRepository.findById(1).isPresent());
        
        // Quando
        progressaoRepository.deleteById(1);
        
        // Então
        assertFalse(progressaoRepository.findById(1).isPresent(), "A progressão não deveria mais ser encontrada");
        assertEquals(0, progressaoRepository.findAll().size(), "O repositório deveria estar vazio");
    }
}