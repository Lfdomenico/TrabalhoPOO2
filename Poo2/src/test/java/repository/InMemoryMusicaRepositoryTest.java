package repository;

import model.Musica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMusicaRepositoryTest {

    private MusicaRepositorio musicaRepository;

    @BeforeEach
    void setUp() {
        // Cria uma nova instância limpa antes de cada teste
        musicaRepository = new InMemoryMusicaRepositorio();
    }

    @Test
    @DisplayName("Deve salvar uma música e atribuir um ID")
    void deveSalvarMusica() {
        // Dado
        Musica novaMusica = new Musica("Smells Like Teen Spirit", "Nirvana", new ArrayList<>());

        // Quando
        Musica musicaSalva = musicaRepository.save(novaMusica);

        // Então
        assertAll("Verifica se a música foi salva corretamente",
            () -> assertNotNull(musicaSalva),
            () -> assertTrue(musicaSalva.getId() > 0, "A música salva deve ter um ID atribuído"),
            () -> assertEquals("Nirvana", musicaSalva.getArtista())
        );
    }

    @Test
    @DisplayName("Deve encontrar músicas na busca por título ou artista")
    void deveEncontrarMusicaNaBusca() {
        // Dado: O repositório já é pré-populado com dados de exemplo

        // Quando buscamos por um termo que corresponde a um artista
        List<Musica> resultadoQueen = musicaRepository.searchByTitleOrArtist("Queen");
        
        // Quando buscamos por um termo que corresponde a parte de um título
        List<Musica> resultadoHotel = musicaRepository.searchByTitleOrArtist("Hotel");

        // Então
        assertEquals(1, resultadoQueen.size(), "Deveria encontrar 1 música do Queen");
        assertEquals("Bohemian Rhapsody", resultadoQueen.get(0).getTitulo());

        assertEquals(1, resultadoHotel.size(), "Deveria encontrar 1 música contendo 'Hotel'");
        assertEquals("Eagles", resultadoHotel.get(0).getArtista());
    }

    @Test
    @DisplayName("Deve retornar lista vazia se a busca não encontrar resultados")
    void deveRetornarListaVaziaParaBuscaSemResultado() {
        // Quando
        List<Musica> resultado = musicaRepository.searchByTitleOrArtist("TermoInexistente");

        // Então
        assertTrue(resultado.isEmpty(), "A lista de resultados deveria estar vazia");
    }

    @Test
    @DisplayName("Deve encontrar uma música pelo seu ID")
    void deveEncontrarMusicaPorId() {
        // Dado: O repositório pré-populado atribui IDs sequenciais (1, 2, 3...)
        // "Hotel California" é o terceiro item salvo, então seu ID deve ser 3.
        
        // Quando
        Optional<Musica> musicaEncontrada = musicaRepository.findById(3);

        // Então
        assertTrue(musicaEncontrada.isPresent());
        assertEquals("Hotel California", musicaEncontrada.get().getTitulo());
    }
}