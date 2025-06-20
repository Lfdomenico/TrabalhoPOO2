package service;

import model.Nota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ServicoMidiTest {

    private ServicoMidi servicoMidi;

    @BeforeEach
    void setUp() {
        // Para os testes de unidade, não precisamos conectar a um dispositivo real.
        // Apenas criamos a instância do serviço para testar seu método send().
        servicoMidi = new ServicoMidi();
    }

    @Test
    @DisplayName("Deve chamar o callback onNotaRecebida ao receber uma mensagem NOTE_ON")
    void deveChamarCallbackOnNotaRecebida() throws InvalidMidiDataException {
        // Dado
        AtomicReference<Nota> notaCapturada = new AtomicReference<>();
        servicoMidi.onNotaRecebida(nota -> {
            notaCapturada.set(nota);
        });

        // Criamos uma mensagem MIDI simulando o pressionar da tecla Dó central (valor 60)
        ShortMessage noteOnMessage = new ShortMessage();
        noteOnMessage.setMessage(ShortMessage.NOTE_ON, 0, 60, 100); // Canal 0, Nota 60, Velocidade 100

        // Quando
        servicoMidi.send(noteOnMessage, -1); // O timestamp -1 é comum em testes

        // Então
        assertNotNull(notaCapturada.get(), "O callback deveria ter capturado uma nota.");
        assertEquals("C4", notaCapturada.get().getNomeCompleto(), "A nota capturada deveria ser C4.");
    }

    @Test
    @DisplayName("Deve chamar o callback onNotaFinalizada com a duração correta")
    void deveCalcularDuracaoEChamarCallbackOnNotaFinalizada() throws InvalidMidiDataException, InterruptedException {
        // Dado
        AtomicReference<Nota> notaFinalizada = new AtomicReference<>();
        AtomicBoolean callbackFoiChamado = new AtomicBoolean(false);
        long duracaoMinimaEsperada = 100; // 100ms

        servicoMidi.onNotaFinalizada((nota, duracao) -> {
            notaFinalizada.set(nota);
            // Verificamos se a duração calculada é pelo menos o tempo que esperamos
            assertTrue(duracao >= duracaoMinimaEsperada);
            callbackFoiChamado.set(true);
        });

        // Criamos as mensagens de NOTE_ON e NOTE_OFF para a nota Mi (valor 64)
        ShortMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, 0, 64, 100);
        ShortMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, 0, 64, 0);

        // Quando
        servicoMidi.send(noteOn, -1); // Simula "pressionar a tecla"
        Thread.sleep(duracaoMinimaEsperada); // Simula a tecla sendo mantida pressionada
        servicoMidi.send(noteOff, -1); // Simula "soltar a tecla"

        // Então
        assertTrue(callbackFoiChamado.get(), "O callback de nota finalizada deveria ter sido chamado.");
        assertNotNull(notaFinalizada.get());
        assertEquals("E4", notaFinalizada.get().getNomeCompleto(), "A nota finalizada deveria ser E4.");
    }
}