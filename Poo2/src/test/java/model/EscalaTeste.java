package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// --- INÍCIO DA CLASSE DE TESTE REAL ---
class EscalaTeste {

    private Escala escalaDeDoMaior;
    private List<Nota> notasDeDoMaior;

    @BeforeEach
    void setUp() {
        // Criando as notas da escala de Dó Maior
        notasDeDoMaior = List.of(
            new Nota("C", 4), new Nota("D", 4), new Nota("E", 4), new Nota("F", 4),
            new Nota("G", 4), new Nota("A", 4), new Nota("B", 4)
        );

        // Criando a escala a ser testada
        escalaDeDoMaior = new Escala(
            notasDeDoMaior,
            "T-T-ST-T-T-T-ST", // Estrutura da escala maior (Tom, Tom, Semitom...)
            201, 
            "Escala de Dó Maior"
        );
    }

    @Test
    @DisplayName("Deve criar uma escala com todos os atributos corretamente")
    void deveCriarEscalaCorretamente() {
        assertAll("Verifica as propriedades da escala",
            () -> assertEquals(201, escalaDeDoMaior.getId()),
            () -> assertEquals("Escala de Dó Maior", escalaDeDoMaior.getNome()),
            () -> assertEquals("T-T-ST-T-T-T-ST", escalaDeDoMaior.getEstrutura())
        );
    }

    @Test
    @DisplayName("Deve conter a lista correta de notas")
    void deveConterListaDeNotasCorreta() {
        List<Nota> notasDaEscala = escalaDeDoMaior.getNotas();

        assertEquals(7, notasDaEscala.size(), "A escala maior deve ter 7 notas");
        assertEquals("C", notasDaEscala.get(0).getNome(), "A primeira nota deve ser Dó");
        assertEquals("B", notasDaEscala.get(6).getNome(), "A última nota deve ser Si");
    }

    @Test
    @DisplayName("Deve ser um IElementoSonoro e retornar suas notas")
    void deveImplementarElementoSonoro() {
        // Este teste verifica a conformidade com a interface do diagrama
        assertTrue(escalaDeDoMaior instanceof IElementoSonoro, "Escala deve ser um IElementoSonoro");
        
        // Verifica se o método da interface retorna a lista correta
        IElementoSonoro elementoSonoro = escalaDeDoMaior;
        assertSame(escalaDeDoMaior.getNotas(), elementoSonoro.getNotas(), "getNotas() da interface deve retornar a mesma lista");
    }
}