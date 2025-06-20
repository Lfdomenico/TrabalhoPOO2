package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AcordeTeste {

    private Acorde cMaior;
    private Nota nC4, nE4, nG4;

    @BeforeEach
    void setUp() {
        // Este método roda antes de CADA @Test
        nC4 = new Nota("C", 4);
        nE4 = new Nota("E", 4);
        nG4 = new Nota("G", 4);
        
        // Criando o acorde na posição fundamental
        // 2. Cria-se um Acorde vazio
        cMaior = new Acorde();

        // 3. Usa-se os setters para construir o estado do objeto
        cMaior.setId(1); // Supondo que este setter é herdado de ElementoMusical
        cMaior.setNome("C");      // Supondo que este setter é herdado de ElementoMusical
        cMaior.setNotas(new ArrayList<>(List.of(nC4, nE4, nG4)));
        cMaior.setTonica(nC4);
    }

    @Test
    @DisplayName("Deve criar um acorde fundamental corretamente")
    void deveCriarAcordeFundamental() {
        assertAll("Propriedades do Acorde Fundamental",
            () -> assertEquals(3, cMaior.getNotas().size(), "Deve ter 3 notas"),
            () -> assertSame(nC4, cMaior.getTonica(), "A tônica de C Maior deve ser C4"),
            () -> assertSame(nC4, cMaior.getNotas().get(0), "O baixo (nota mais grave) deve ser C4")
        );
    }

    @Test
    @DisplayName("Deve inverter para a 1ª inversão corretamente")
    void deveFazerPrimeiraInversao() {
        // Quando
        Acorde cMaiorInv1 = cMaior.inverter(1);
        
        // Então...
        assertAll("Propriedades da 1ª Inversão",
            () -> assertNotSame(cMaior, cMaiorInv1, "Inverter deve retornar uma NOVA instância de Acorde"),
            () -> assertSame(nE4, cMaiorInv1.getNotas().get(0), "O baixo da 1ª inversão deve ser E4"),
            () -> assertSame(nC4, cMaiorInv1.getTonica(), "A TÔNICA deve permanecer C4, mesmo na inversão"),
            () -> assertEquals(List.of(nE4, nG4, nC4), cMaiorInv1.getNotas(), "A ordem das notas deve ser E-G-C")
        );
    }
     
    @Test
    @DisplayName("Deve inverter para a 2ª inversão corretamente")
    void deveFazerSegundaInversao() {
        // Quando
        Acorde cMaiorInv2 = cMaior.inverter(2);
        
        // Então...
        assertAll("Propriedades da 2ª Inversão",
            () -> assertSame(nG4, cMaiorInv2.getNotas().get(0), "O baixo da 2ª inversão deve ser G4"),
            () -> assertSame(nC4, cMaiorInv2.getTonica(), "A TÔNICA ainda deve ser C4"),
            () -> assertEquals(List.of(nG4, nC4, nE4), cMaiorInv2.getNotas(), "A ordem das notas deve ser G-C-E")
        );
    }
}