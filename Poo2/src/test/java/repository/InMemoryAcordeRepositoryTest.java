package repository;

import model.Acorde;
import model.Nota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAcordeRepositoryTest {

    private AcordeRepositorio acordeRepository;

    @BeforeEach
    void setUp() {
        // Antes de cada teste, uma nova instância limpa do repositório é criada.
        // O construtor já pré-popula com C (ID 1), G (ID 2), e Am (ID 3).
        acordeRepository = new InMemoryAcordeRepositorio();
    }

    @Test
    @DisplayName("Deve salvar um novo acorde e atribuir o próximo ID")
    void deveSalvarNovoAcorde() {
        // Dado
        Nota tonicaD = new Nota("D", null, 4);
        Acorde acordeDm = new Acorde("Menor", tonicaD, "Dm", List.of(tonicaD));
        
        // Quando
        Acorde acordeSalvo = acordeRepository.save(acordeDm);
        
        // Então
        assertAll("Verifica se o novo acorde foi salvo corretamente",
            () -> assertEquals(4, acordeSalvo.getId(), "O ID do novo acorde deve ser 4"),
            () -> assertEquals(4, acordeRepository.findAll().size(), "O repositório deve ter 4 acordes no total")
        );
    }

    @Test
    @DisplayName("Deve encontrar um acorde existente pelo seu ID")
    void deveEncontrarAcordePorId() {
        // Quando
        Optional<Acorde> resultado = acordeRepository.findById(1); // C

        // Então
        assertTrue(resultado.isPresent(), "Deveria encontrar o acorde com ID 1");
        assertEquals("C", resultado.get().getNome());
    }

    @Test
    @DisplayName("Deve encontrar acordes pelo nome contendo um termo")
    void deveEncontrarAcordesPeloNome() {
        // Quando
        List<Acorde> resultado = acordeRepository.findByNameContaining("m"); // Deve encontrar "Am"

        // Então
        assertEquals(1, resultado.size());
        assertEquals("Am", resultado.get(0).getNome());
    }
    
    @Test
    @DisplayName("Deve encontrar acordes pela sua nota tônica")
    void deveEncontrarAcordesPelaTonica() {
        // Dado
        Nota tonicaG = new Nota("G", null, 4);
        
        // Quando
        List<Acorde> resultado = acordeRepository.findByTonica(tonicaG);
        
        // Então
        assertEquals(1, resultado.size(), "Deveria encontrar apenas 1 acorde com tônica G");
        assertEquals("G", resultado.get(0).getNome());
    }

    @Test
    @DisplayName("Deve listar todos os acordes pré-populados")
    void deveListarTodosOsAcordes() {
        // Quando
        List<Acorde> todos = acordeRepository.findAll();
        
        // Então
        assertEquals(3, todos.size(), "Deveria listar os 3 acordes iniciais");
    }
}