package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// --- INÍCIO DA CLASSE DE TESTE REAL ---
class MusicaTeste {

    private Acorde c, g, am, f;
    private List<Acorde> acordesExemplo;

    @BeforeEach
    void setUp() {
        // Criando acordes reutilizáveis para os testes
        c = new Acorde(); c.setNome("C"); c.setId(1);
        g = new Acorde(); g.setNome("G"); g.setId(2);
        am = new Acorde(); am.setNome("Am"); am.setId(3);
        f = new Acorde(); f.setNome("F"); f.setId(4);
        
        acordesExemplo = new ArrayList<>(List.of(c, g, am, f));
    }

    @Test
    @DisplayName("Deve criar objeto Musica usando o construtor completo e manter o estado")
    void deveCriarMusicaComConstrutorCompleto() {
        // Quando criamos uma música passando todos os dados
        Musica musica = new Musica(101, "Let It Be", "The Beatles", acordesExemplo);

        // Então todos os seus atributos devem ser os que passamos
        assertAll("Verifica as propriedades da música criada",
            () -> assertEquals(101, musica.getId()),
            () -> assertEquals("Let It Be", musica.getTitulo()),
            () -> assertEquals("The Beatles", musica.getArtista()),
            () -> assertEquals(4, musica.getAcordes().size(), "A lista de acordes deve ter 4 elementos"),
            () -> assertSame(c, musica.getAcordes().get(0), "O primeiro acorde deve ser C")
        );
    }

    @Test
    @DisplayName("Deve criar Musica com construtor vazio e ser populada com setters")
    void deveCriarMusicaVaziaEPreencherComSetters() {
        // Quando criamos uma música vazia
        Musica musica = new Musica();
        
        // E usamos os setters
        musica.setId(202);
        musica.setTitulo("Stairway to Heaven");
        musica.setArtista("Led Zeppelin");
        musica.setAcordes(acordesExemplo);

        // Então os getters devem retornar os valores definidos
        assertEquals(202, musica.getId());
        assertEquals("Stairway to Heaven", musica.getTitulo());
        assertEquals("Led Zeppelin", musica.getArtista());
        assertEquals(4, musica.getAcordes().size());
    }
    
    @Test
    @DisplayName("Deve inicializar a lista de acordes como vazia no construtor padrão")
    void deveInicializarListaDeAcordesVazia() {
        // Quando criamos uma música com o construtor padrão
        Musica musica = new Musica();
        
        // Então a lista de acordes não deve ser nula
        assertNotNull(musica.getAcordes(), "A lista de acordes nunca deve ser nula");
        assertTrue(musica.getAcordes().isEmpty(), "A lista de acordes deve começar vazia");
    }
}