package service;

import model.Acorde;
import model.Progressao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AcordeRepositorio;
import repository.InMemoryAcordeRepositorio;
import repository.InMemoryProgressaoRepositorio;
import repository.ProgressaoRepositorio;

import static org.junit.jupiter.api.Assertions.*;

class ServicoProgressaoTest {

    private ServicoProgressao servicoProgressao;
    private ProgressaoRepositorio progressaoRepository;
    private AcordeRepositorio acordeRepository;

    @BeforeEach
    void setUp() {
        // 1. Criamos instâncias novas dos nossos repositórios em memória para cada teste.
        // Isso garante que um teste não interfira no outro.
        progressaoRepository = new InMemoryProgressaoRepositorio();
        acordeRepository = new InMemoryAcordeRepositorio();
        
        // 2. Injetamos os repositórios "fake" no serviço que vamos testar.
        servicoProgressao = new ServicoProgressao(progressaoRepository, acordeRepository);
    }

    @Test
    @DisplayName("Deve criar uma nova progressão com sucesso")
    void deveCriarProgressao() {
        // Quando
        Progressao p = servicoProgressao.criarProgressao("Progressão de Rock");

        // Então
        assertAll("Verifica a criação da progressão",
            () -> assertNotNull(p),
            () -> assertTrue(p.getId() > 0, "A progressão deve ter um ID válido"),
            () -> assertEquals("Progressão de Rock", p.getNome())
        );
        
        // Verifica se ela foi realmente salva no repositório
        assertTrue(progressaoRepository.findById(p.getId()).isPresent());
    }

    @Test
    @DisplayName("Deve carregar uma progressão existente pelo ID")
    void deveCarregarProgressao() {
        // Dado: O InMemoryProgressaoRepository já cria uma progressão com ID 1
        
        // Quando
        Progressao p = servicoProgressao.carregarProgressao(1);
        
        // Então
        assertNotNull(p);
        assertEquals(1, p.getId());
        assertEquals("Progressão Padrão Pop", p.getNome());
    }
    
    @Test
    @DisplayName("Deve adicionar um acorde a uma progressão existente")
    void deveAdicionarAcorde() {
        // Dado
        Progressao p = servicoProgressao.criarProgressao("Minha Progressão");
        // O InMemoryAcordeRepository tem um Acorde "C" com ID 1
        
        // Quando
        Progressao pAtualizada = servicoProgressao.adicionarAcorde(p.getId(), 1);
        
        // Então
        assertEquals(1, pAtualizada.getAcordes().size(), "A progressão deve conter 1 acorde");
        assertEquals("C", pAtualizada.getAcordes().get(0).getNome());
    }
    
    @Test
    @DisplayName("Deve remover um acorde de uma progressão")
    void deveRemoverAcorde() {
        // Dado
        Progressao p = servicoProgressao.criarProgressao("Progressão Editável");
        p = servicoProgressao.adicionarAcorde(p.getId(), 1); // Adiciona C
        p = servicoProgressao.adicionarAcorde(p.getId(), 2); // Adiciona G
        assertEquals(2, p.getAcordes().size(), "Setup: A progressão deve ter 2 acordes");
        
        // Quando
        Progressao pAtualizada = servicoProgressao.removerAcorde(p.getId(), 0); // Remove o primeiro acorde (C)

        // Então
        assertEquals(1, pAtualizada.getAcordes().size(), "A progressão agora deve ter apenas 1 acorde");
        assertEquals("G", pAtualizada.getAcordes().get(0).getNome(), "O acorde restante deve ser G");
    }

    @Test
    @DisplayName("Deve exportar uma progressão para o formato de string CSV correto")
    void deveExportarProgressaoParaCsv() {
        // Dado
        // O setup inicial já nos dá um repositório com C(1), G(2), Am(3)
        Progressao p = servicoProgressao.criarProgressao("Progressão para Exportar");
        p = servicoProgressao.adicionarAcorde(p.getId(), 1); // Adiciona C
        p = servicoProgressao.adicionarAcorde(p.getId(), 2); // Adiciona G
        p = servicoProgressao.adicionarAcorde(p.getId(), 3); // Adiciona Am

        // Montamos a String CSV exata que esperamos como resultado
        // Incluindo o cabeçalho e as quebras de linha (\n)
        String csvEsperado = "id_acorde,nome_acorde,tonica,notas\n" +
                             "1,C,C4,\"C4;E4;G4\"\n" +
                             "2,G,G4,\"G4;B4;D5\"\n" +
                             "3,Am,A4,\"A4;C5;E5\"\n";

        // Quando
        String resultadoCsv = servicoProgressao.exportarProgressao(p.getId());

        // Então
        assertEquals(csvEsperado, resultadoCsv, "A string gerada deve estar no formato CSV correto.");
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao tentar carregar progressão inexistente")
    void deveLancarExcecaoAoCarregarIdInexistente() {
        assertThrows(RuntimeException.class, () -> {
            servicoProgressao.carregarProgressao(999);
        });
    }
}