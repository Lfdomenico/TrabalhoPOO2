package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// --- INÍCIO DA CLASSE DE TESTE REAL ---
class ProgressaoTeste {

    private Progressao progressao;
    private Acorde c, g, am, f;

    @BeforeEach
    void setUp() {
        // Criando acordes de exemplo
        c = new Acorde(); c.setNome("C"); c.setId(1);
        g = new Acorde(); g.setNome("G"); g.setId(2);
        am = new Acorde(); am.setNome("Am"); am.setId(3);
        f = new Acorde(); f.setNome("F"); f.setId(4);
        
        // Criando a progressão com o construtor exato da sua classe Java
        List<Acorde> acordesIniciais = new ArrayList<>(List.of(c, g, am));
        progressao = new Progressao(acordesIniciais, 101, "Progressão Pop");
    }

    @Test
    @DisplayName("Deve criar progressão com os dados do construtor")
    void deveCriarProgressaoCorretamente() {
        assertEquals(101, progressao.getId());
        assertEquals("Progressão Pop", progressao.getNome());
        assertEquals(3, progressao.getAcordes().size());
    }

    @Test
    @DisplayName("Deve adicionar um acorde ao final da lista")
    void deveAdicionarAcorde() {
        progressao.adicionarAcorde(f);
        
        List<Acorde> acordes = progressao.getAcordes();
        
        assertEquals(4, acordes.size());
        assertEquals(f, acordes.get(3));
    }

    @Test
    @DisplayName("Deve remover um acorde da progressão pela posição")
    void deveRemoverAcorde() {
        progressao.removerAcorde(1); // Remove G
        
        List<Acorde> acordes = progressao.getAcordes();
        
        assertEquals(2, acordes.size());
        assertEquals(am, acordes.get(1)); // O antigo índice 2 agora é o 1
    }

    @Test
    @DisplayName("Deve lançar IndexOutOfBoundsException ao tentar remover com índice inválido")
    void deveLancarExcecaoAoRemoverComIndiceInvalido() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            progressao.removerAcorde(99); // Tenta remover com uma posição que não existe.
        });
    }

    @Test
    @DisplayName("Deve inserir um acorde no meio da progressão")
    void deveInserirAcordeNoMeio() {
        // Progressão inicial: [C, G, Am]
        f = new Acorde(); f.setNome("F"); f.setId(4);

        // Quando inserimos F na posição 1
        progressao.inserirAcorde(f, 1);

        // Então a progressão deve ser [C, F, G, Am]
        List<Acorde> acordes = progressao.getAcordes();

        assertAll("Verifica a inserção no meio",
            () -> assertEquals(4, acordes.size(), "O tamanho da lista deve ser 4"),
            () -> assertSame(f, acordes.get(1), "O acorde F deve estar no índice 1"),
            () -> assertSame(g, acordes.get(2), "O acorde G deve ter sido movido para o índice 2")
        );
    }

    @Test
    @DisplayName("Deve inserir um acorde no final da progressão (caso de borda)")
    void deveInserirAcordeNoFinal() {
        // Progressão inicial: [C, G, Am] (tamanho 3)
        f = new Acorde(); f.setNome("F"); f.setId(4);
        int tamanhoOriginal = progressao.getAcordes().size();

        // Quando inserimos F na posição 3 (que é igual ao tamanho da lista)
        // Isso deve funcionar e adicionar o acorde ao final.
        assertDoesNotThrow(() -> {
            progressao.inserirAcorde(f, tamanhoOriginal);
        }, "Não deve lançar exceção ao inserir no final da lista");

        // Então a progressão deve ser [C, G, Am, F]
        List<Acorde> acordes = progressao.getAcordes();
        assertEquals(4, acordes.size());
        assertSame(f, acordes.get(3));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar inserir com índice inválido")
    void deveLancarExcecaoAoInserirComIndiceInvalido() {
        f = new Acorde(); f.setNome("F"); f.setId(4);

        // Verificando índice negativo
        assertThrows(IndexOutOfBoundsException.class, () -> {
            progressao.inserirAcorde(f, -1);
        });

        // Verificando índice muito maior que o tamanho
        assertThrows(IndexOutOfBoundsException.class, () -> {
            progressao.inserirAcorde(f, 99);
        });
    }
}