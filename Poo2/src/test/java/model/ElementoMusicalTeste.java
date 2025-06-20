package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// --- INÍCIO DA CLASSE DE TESTE REAL ---
class ElementoMusicalTeste {

    /**
     * Esta é a nossa classe "dublê". Uma implementação mínima de ElementoMusical
     * que existe apenas para que possamos criar um objeto e testar a lógica da classe pai.
     */
    private static class ElementoMusicalConcretoParaTeste extends ElementoMusical {
        
        public ElementoMusicalConcretoParaTeste() {
            super();
        }

        public ElementoMusicalConcretoParaTeste(int id, String nome) {
            super(id, nome);
        }

        @Override
        public String getInfoComplementar() {
            // Para o teste, a implementação não precisa ser complexa.
            return "Informação complementar do teste.";
        }
    }

    @Test
    @DisplayName("Deve inicializar os atributos via construtor corretamente")
    void deveConstruirComIdENomeCorretamente() {
        // Quando criamos uma instância da nossa classe de teste concreta...
        ElementoMusical elemento = new ElementoMusicalConcretoParaTeste(42, "Elemento Teste");

        // Então podemos testar os getters da classe abstrata.
        assertAll("Verifica os dados do construtor",
            () -> assertEquals(42, elemento.getId()),
            () -> assertEquals("Elemento Teste", elemento.getNome())
        );
    }

    @Test
    @DisplayName("Deve permitir a alteração dos atributos via setters")
    void devePermitirAlteracaoComSetters() {
        // Dado um elemento criado com o construtor vazio
        ElementoMusical elemento = new ElementoMusicalConcretoParaTeste();

        // Quando usamos os setters da classe abstrata...
        elemento.setId(99);
        elemento.setNome("Novo Nome");

        // Então os getters devem retornar os novos valores.
        assertEquals(99, elemento.getId());
        assertEquals("Novo Nome", elemento.getNome());
    }

    @Test
    @DisplayName("O método abstrato deve ser implementado pela subclasse")
    void metodoAbstratoDeveSerImplementavel() {
        ElementoMusical elemento = new ElementoMusicalConcretoParaTeste();
        
        // Podemos verificar a implementação do nosso "dublê".
        // Isso confirma que o contrato do método abstrato está funcionando.
        assertEquals("Informação complementar do teste.", elemento.getInfoComplementar());
    }
}