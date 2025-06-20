package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// --- INÍCIO DA CLASSE DE TESTE REAL ---
class AnotacaoTeste {

    private ElementoMusical acordeExemplo;

    @BeforeEach
    void setUp() {
        // Cria uma entidade musical que será usada em múltiplos testes.
        acordeExemplo = new Acorde(); acordeExemplo.setNome("F"); acordeExemplo.setId(4);
    }

    @Test
    @DisplayName("Deve criar uma anotação simples sem elemento associado")
    void deveCriarAnotacaoSimples() {
        // Usando um construtor e setters que sua classe teria.
        Anotacao anotacao = new Anotacao();
        anotacao.setId(1);
        anotacao.setTexto("Estudar este acorde para a introdução.");

        assertAll("Verifica as propriedades da anotação simples",
            () -> assertEquals(1, anotacao.getId()),
            () -> assertEquals("Estudar este acorde para a introdução.", anotacao.getTexto()),
            () -> assertNull(anotacao.getElementoSobre(), "O elemento associado deve ser nulo inicialmente")
        );
    }

    @Test
    @DisplayName("Deve associar uma entidade musical a uma anotação existente")
    void deveAssociarElementoAposCriacao() {
        // Dado
        Anotacao anotacao = new Anotacao();
        anotacao.setTexto("Análise da função de dominante secundária.");
        
        // Quando
        anotacao.associarElemento(acordeExemplo);

        // Então
        ElementoMusical elementoAssociado = anotacao.getElementoSobre();
        
        assertNotNull(elementoAssociado, "O elemento associado não deve mais ser nulo");
        assertSame(acordeExemplo, elementoAssociado, "O elemento associado deve ser a instância correta do acorde");
        assertEquals(4, elementoAssociado.getId());
    }
    
    @Test
    @DisplayName("Deve permitir desassociar um elemento (definir como nulo)")
    void devePermitirDesassociarElemento() {
        // Dado
        Anotacao anotacao = new Anotacao();
        anotacao.associarElemento(acordeExemplo);
        assertNotNull(anotacao.getElementoSobre(), "Garante que a associação foi feita antes do teste.");
        
        // Quando
        anotacao.associarElemento(null);

        // Então
        assertNull(anotacao.getElementoSobre(), "O elemento associado deve voltar a ser nulo");
    }
}