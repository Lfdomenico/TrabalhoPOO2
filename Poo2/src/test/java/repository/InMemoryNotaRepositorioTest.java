package repository;

import model.Nota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryNotaRepositorioTest {

    private NotaRepositorio notaRepository;

    @BeforeEach
    void setUp() {
        // Cria uma nova instância limpa do repositório antes de cada teste
        // para garantir que os testes sejam independentes.
        notaRepository = new InMemoryNotaRepositorio();
    }

    @Test
    @DisplayName("Deve salvar uma nota e atribuir um ID")
    void deveSalvarNota() {
        // Dado
        Nota novaNota = new Nota("E", "b", 3);
        
        // Quando
        Nota notaSalva = notaRepository.save(novaNota);
        
        // Então
        assertAll("Verifica se a nota foi salva corretamente",
            () -> assertNotNull(notaSalva, "A nota salva não deve ser nula"),
            () -> assertTrue(notaSalva.getId() > 0, "A nota salva deve ter um ID atribuído"),
            () -> assertEquals("Eb3", notaSalva.getNomeCompleto())
        );
    }

    @Test
    @DisplayName("Deve encontrar uma nota pelo seu ID")
    void deveEncontrarNotaPorId() {
        // Dado: O repositório já é pré-populado com uma nota "C4" com ID 1
        
        // Quando
        Optional<Nota> notaEncontrada = notaRepository.findById(1);

        // Então
        assertTrue(notaEncontrada.isPresent(), "Deveria encontrar a nota com ID 1");
        assertEquals("C4", notaEncontrada.get().getNomeCompleto());
    }
    
    @Test
    @DisplayName("Deve retornar Optional vazio para ID inexistente")
    void deveRetornarVazioParaIdInexistente() {
        // Quando
        Optional<Nota> resultado = notaRepository.findById(999);

        // Então
        assertFalse(resultado.isPresent(), "Não deveria encontrar uma nota com um ID que não existe");
    }

    @Test
    @DisplayName("Deve encontrar uma nota pelo nome completo")
    void deveEncontrarNotaPeloNomeCompleto() {
        // Dado: O repositório já contém "G4"
        
        // Quando
        Optional<Nota> notaEncontrada = notaRepository.findByNomeCompleto("G4");
        
        // Então
        assertTrue(notaEncontrada.isPresent());
        assertEquals(2, notaEncontrada.get().getId()); // ID 2 foi atribuído no construtor
    }
    
    @Test
    @DisplayName("Deve deletar uma nota pelo ID")
    void deveDeletarNota() {
        // Dado: O repositório contém a nota com ID 1 ("C4")
        assertTrue(notaRepository.findById(1).isPresent(), "Garante que a nota existe antes de deletar");
        
        // Quando
        notaRepository.deleteById(1);
        
        // Então
        assertFalse(notaRepository.findById(1).isPresent(), "A nota não deveria mais ser encontrada após ser deletada");
    }
}   