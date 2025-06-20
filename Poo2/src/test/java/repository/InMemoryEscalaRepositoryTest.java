package repository;

import model.Escala;
import model.Nota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryEscalaRepositoryTest {

    private EscalaRepositorio escalaRepository;

    @BeforeEach
    void setUp() {
        // Cria uma nova instância limpa antes de cada teste.
        // O construtor já pré-popula com a escala de Dó Maior (ID 1)
        // e Lá Menor Natural (ID 2).
        escalaRepository = new InMemoryEscalaRepositorio();
    }

    @Test
    @DisplayName("Deve salvar uma nova escala e atribuir o próximo ID")
    void deveSalvarNovaEscala() {
        // Dado
        Escala escalaPentatonica = new Escala(
            Collections.emptyList(),
            "ST-T-T-ST-T", // Estrutura de exemplo
            "Pentatônica Menor de Lá"
        );
        
        // Quando
        Escala escalaSalva = escalaRepository.save(escalaPentatonica);
        
        // Então
        assertAll("Verifica se a nova escala foi salva corretamente",
            () -> assertEquals(3, escalaSalva.getId(), "O ID da nova escala deve ser 3"),
            () -> assertEquals(3, escalaRepository.findAll().size(), "O repositório deve ter 3 escalas no total")
        );
    }

    @Test
    @DisplayName("Deve encontrar uma escala existente pelo seu ID")
    void deveEncontrarEscalaPorId() {
        // Quando
        Optional<Escala> resultado = escalaRepository.findById(1); // Dó Maior

        // Então
        assertTrue(resultado.isPresent(), "Deveria encontrar a escala com ID 1");
        assertEquals("Dó Maior", resultado.get().getNome());
    }

    @Test
    @DisplayName("Deve encontrar escalas pela sua estrutura de intervalos")
    void deveEncontrarEscalaPelaEstrutura() {
        // Dado
        String estruturaMaior = "T-T-ST-T-T-T-ST";
        
        // Quando
        List<Escala> resultado = escalaRepository.findByEstrutura(estruturaMaior);
        
        // Então
        assertEquals(1, resultado.size(), "Deveria encontrar apenas 1 escala com a estrutura maior");
        assertEquals("Dó Maior", resultado.get(0).getNome());
    }
    
    @Test
    @DisplayName("Deve encontrar escalas pelo nome contendo um termo")
    void deveEncontrarEscalaPeloNome() {
        // Quando
        List<Escala> resultado = escalaRepository.findByNameContaining("Menor");

        // Então
        assertEquals(1, resultado.size());
        assertEquals("Lá Menor Natural", resultado.get(0).getNome());
    }

    @Test
    @DisplayName("Deve listar todas as escalas pré-populadas")
    void deveListarTodasAsEscalas() {
        // Quando
        List<Escala> todas = escalaRepository.findAll();
        
        // Então
        assertEquals(2, todas.size(), "Deveria listar as 2 escalas iniciais");
    }
}