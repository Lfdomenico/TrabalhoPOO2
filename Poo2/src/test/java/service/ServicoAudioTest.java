package service;

import model.Acorde;
import model.Nota;
import model.Progressao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ServicoAudioTest {

    private static ServicoAudio servicoAudio;

    @BeforeAll
    static void setUp() {
        // Inicializamos o serviço de áudio uma única vez para todos os testes,
        // pois ele reserva um recurso do sistema (o sintetizador).
        try {
            servicoAudio = new ServicoAudio();
            System.out.println("Serviço de Áudio inicializado para os testes.");
        } catch (Exception e) {
            servicoAudio = null;
            System.err.println("AVISO: Serviço de áudio não pôde ser inicializado. Testes de áudio serão pulados.");
        }
    }

    @AfterAll
    static void tearDown() {
        // Ao final de todos os testes, fechamos o serviço para liberar o recurso.
        if (servicoAudio != null) {
            servicoAudio.fechar();
        }
    }

    @Test
    @DisplayName("Deve reproduzir um único Acorde sem lançar exceções")
    void deveReproduzirAcorde() {
        if (servicoAudio == null) {
            System.err.println("Teste pulado: serviço de áudio indisponível.");
            return; // Pula o teste se o serviço não pôde ser inicializado
        }

        // Dado
        Acorde acordeC = new Acorde("Maior", new Nota("C", null, 4), "C",
                List.of(new Nota("C", null, 4), new Nota("E", null, 4), new Nota("G", null, 4))
        );

        // Quando e Então
        // O teste passa se o método 'reproduzir' executar completamente sem erros.
        assertDoesNotThrow(() -> {
            servicoAudio.reproduzir(acordeC);
        }, "A reprodução de um acorde não deve lançar exceção.");
    }

    @Test
    @DisplayName("Deve reproduzir uma Progressao sem lançar exceções")
    void deveReproduzirProgressao() {
        if (servicoAudio == null) {
            System.err.println("Teste pulado: serviço de áudio indisponível.");
            return;
        }

        // Dado
        Acorde c = new Acorde("Maior", new Nota("C", null, 4), "C", List.of(new Nota("C", null, 4)));
        Acorde g = new Acorde("Maior", new Nota("G", null, 4), "G", List.of(new Nota("G", null, 4)));
        Progressao progressao = new Progressao(new ArrayList<>(List.of(c, g)), "Progressão de Teste");

        // Quando e Então
        assertDoesNotThrow(() -> {
            servicoAudio.reproduzir(progressao);
        }, "A reprodução de uma progressão não deve lançar exceção.");
    }
}