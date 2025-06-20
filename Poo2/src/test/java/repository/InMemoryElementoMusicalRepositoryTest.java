package repository;

import model.*; // Importando todas as nossas classes do modelo
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryElementoMusicalRepositoryTest {

    private ElementoMusicalRepositorio elementoMusicalRepository;

    @BeforeEach
    void setUp() {
        // Cria uma nova instância limpa antes de cada teste.
        // O construtor deste repositório já pré-popula com um Acorde, uma Escala e uma Progressão.
        elementoMusicalRepository = new InMemoryElementoMusicalRepositorio();
    }

    @Test
    @DisplayName("Deve salvar uma nova entidade e atribuir um ID único")
    void deveSalvarNovaEntidade() {
        // Dado: O repositório já tem 3 itens do construtor (IDs 1, 2, 3)
        Acorde novoAcorde = new Acorde("Menor", new Nota("D", null, 4), "Dm", List.of());
        
        // Quando
        ElementoMusical acordeSalvo = elementoMusicalRepository.save(novoAcorde);
        
        // Então
        assertAll("Verifica se a nova entidade foi salva corretamente",
            () -> assertNotNull(acordeSalvo),
            () -> assertEquals(4, acordeSalvo.getId(), "O ID do novo elemento deve ser 4"),
            () -> assertEquals(4, elementoMusicalRepository.findAll().size(), "O total de elementos deve ser 4")
        );
    }

    @Test
    @DisplayName("Deve encontrar qualquer tipo de ElementoMusical pelo ID")
    void deveEncontrarQualquerElementoPorId() {
        // Dado: O construtor salva um Acorde (ID 1), uma Escala (ID 2), e uma Progressão (ID 3)
        
        // Quando
        Optional<ElementoMusical> resultadoAcorde = elementoMusicalRepository.findById(1);
        Optional<ElementoMusical> resultadoEscala = elementoMusicalRepository.findById(2);
        
        // Então
        assertTrue(resultadoAcorde.isPresent());
        assertTrue(resultadoAcorde.get() instanceof Acorde, "O elemento com ID 1 deve ser um Acorde");

        assertTrue(resultadoEscala.isPresent());
        assertTrue(resultadoEscala.get() instanceof Escala, "O elemento com ID 2 deve ser uma Escala");
    }

    @Test
    @DisplayName("Deve listar todos os diferentes tipos de elementos musicais")
    void deveListarTodosOsElementos() {
        // Quando
        List<ElementoMusical> todos = elementoMusicalRepository.findAll();

        // Então
        assertEquals(3, todos.size(), "Deve haver 3 elementos pré-populados");
        
        // Verifica se a lista contém um de cada tipo esperado
        assertTrue(todos.stream().anyMatch(e -> e instanceof Acorde));
        assertTrue(todos.stream().anyMatch(e -> e instanceof Escala));
        assertTrue(todos.stream().anyMatch(e -> e instanceof Progressao));
    }
    
    @Test
    @DisplayName("Deve encontrar elementos pelo nome")
    void deveEncontrarPeloNome() {
        // Dado: Existe uma "Progressão de Blues"
        
        // Quando
        List<ElementoMusical> resultado = elementoMusicalRepository.findByNameContaining("Blues");
        
        // Então
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0) instanceof Progressao);
    }           
}