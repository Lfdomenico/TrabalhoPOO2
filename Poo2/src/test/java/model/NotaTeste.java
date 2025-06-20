package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

// --- INÍCIO DA CLASSE DE TESTE REAL ---
class NotaTeste {

    @Test
    @DisplayName("Deve criar uma nota e retornar seus atributos")
    void deveCriarNotaCorretamente() {
        Nota nota = new Nota("A", "b", 4);
        assertAll("Atributos da Nota",
            () -> assertEquals("Ab4", nota.getNomeCompleto()),
            () -> assertEquals(68, nota.getValorMidi())
        );
    }

    @ParameterizedTest(name = "Nota {0}{1}{2} deve ter valor MIDI {3}")
    @CsvSource({
            "C, '',  4, 60",
            "A, '',  4, 69",
            "F, '#', 5, 78",
            "B, 'b', 3, 58"
    })
    @DisplayName("Deve calcular o valor MIDI para várias notas")
    void deveCalcularValorMidiCorretamente(String nome, String acidente, int oitava, int esperado) {
        Nota nota = new Nota(nome, acidente.isEmpty() ? null : acidente, oitava);
        assertEquals(esperado, nota.getValorMidi());
    }

    @Test
    @DisplayName("Deve transpor uma nota por um intervalo de semitons")
    void deveTransporNota() {
        Nota c4 = new Nota("C", null, 4); // MIDI 60

        // Transpondo +7 semitons (quinta justa) -> G4 (MIDI 67)
        Nota g4 = c4.transpor(7);
        assertEquals("G4", g4.getNomeCompleto());
        assertEquals(67, g4.getValorMidi());

        // Transpondo -2 semitons (tom inteiro) -> A#3 (MIDI 58)
        Nota aSharp3 = c4.transpor(-2);
        assertEquals("A#3", aSharp3.getNomeCompleto());
        assertEquals(58, aSharp3.getValorMidi());
    }

    @Test
    @DisplayName("Deve lançar exceção ao transpor para fora do alcance MIDI")
    void deveLancarExcecaoParaTransporteInvalido() {
        Nota b7 = new Nota("B", null, 7); // MIDI 107
        assertThrows(IllegalArgumentException.class, () -> {
            b7.transpor(25); // Tentando ir além da nota 127
        });
    }
}