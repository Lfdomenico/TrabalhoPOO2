package service;

import model.Acorde;
import model.ElementoMusical;
import model.Escala;
import model.Musica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.*; // Importando todas as nossas interfaces e classes de repositório

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicoBuscaTest {

    private ServicoBusca servicoBusca;
    
    // Declaramos os repositórios para que o serviço possa ser instanciado
    private ElementoMusicalRepositorio elementoMusicalRepo;
    private AcordeRepositorio acordeRepo;
    private EscalaRepositorio escalaRepo;
    private MusicaRepositorio musicaRepo;
    private NotaRepositorio notaRepo;

    @BeforeEach
    void setUp() {
        // 1. Criamos as implementações em memória dos repositórios
        elementoMusicalRepo = new InMemoryElementoMusicalRepositorio();
        acordeRepo = new InMemoryAcordeRepositorio();
        escalaRepo = new InMemoryEscalaRepositorio();
        musicaRepo = new InMemoryMusicaRepositorio();
        notaRepo = new InMemoryNotaRepositorio();
        
        // 2. Injetamos esses repositórios no serviço que vamos testar
        servicoBusca = new ServicoBusca(elementoMusicalRepo, acordeRepo, escalaRepo, musicaRepo, notaRepo);
    }

    @Test
    @DisplayName("Deve buscar um Acorde específico pelo ID")
    void deveBuscarAcordePorId() {
        // Dado: O repositório já contém um Acorde com ID 1
        
        // Quando
        Acorde resultado = servicoBusca.buscarAcorde(1);

        // Então
        assertNotNull(resultado);
        assertEquals("C", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar Acorde com ID de outro tipo de elemento")
    void deveFalharAoBuscarAcordeComIdDeEscala() {
        // Dado: O ID 2 pertence a uma Escala, não a um Acorde
        
        // Quando e Então
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicoBusca.buscarAcorde(2);
        });
        
        assertTrue(exception.getMessage().contains("não pertence a um Acorde"));
    }

    @Test
    @DisplayName("Deve buscar uma Entidade Musical genérica de forma polimórfica")
    void deveBuscarEntidadeMusicalGenerica() {
        // Dado: ID 1 é um Acorde e ID 2 é uma Escala

        // Quando
        ElementoMusical resultado1 = servicoBusca.buscarEntidadeMusical(1);
        ElementoMusical resultado2 = servicoBusca.buscarEntidadeMusical(2);

        // Então
        assertTrue(resultado1 instanceof Acorde);
        assertTrue(resultado2 instanceof Escala);
    }
    
    @Test
    @DisplayName("Deve buscar músicas por título ou artista")
    void deveBuscarMusicasPorQuery() {
        // Dado: O repositório contém músicas do Queen e Eagles

        // Quando
        List<Musica> resultado = servicoBusca.buscarMusica("eagles");
        
        // Então
        assertEquals(1, resultado.size());
        assertEquals("Hotel California", resultado.get(0).getTitulo());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao buscar entidade com ID que não existe")
    void deveLancarExcecaoParaIdInexistente() {
        // Quando e Então
        assertThrows(RuntimeException.class, () -> {
            servicoBusca.buscarEntidadeMusical(999);
        });
    }
}