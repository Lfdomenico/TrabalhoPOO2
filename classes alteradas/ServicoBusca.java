package service;

import model.*;
import repository.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ServicoBusca {

    private final ElementoMusicalRepositorio elementoMusicalRepo;
    private final AcordeRepositorio acordeRepo;
    private final EscalaRepositorio escalaRepo;
    private final MusicaRepositorio musicaRepo;
    private final NotaRepositorio notaRepo;
    private final ProgressaoRepositorio progressaoRepo;
    
    public ServicoBusca(ElementoMusicalRepositorio elementoRepo, AcordeRepositorio acordeRepo, EscalaRepositorio escalaRepo, MusicaRepositorio musicaRepo, NotaRepositorio notaRepo, ProgressaoRepositorio progressaoRepo) {
        this.elementoMusicalRepo = elementoRepo;
        this.acordeRepo = acordeRepo;
        this.escalaRepo = escalaRepo;
        this.musicaRepo = musicaRepo;
        this.notaRepo = notaRepo;
        this.progressaoRepo = progressaoRepo;
    }

    public ElementoMusical buscarEntidadeMusical(int id) {
        return elementoMusicalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Entidade Musical não encontrada com o ID: " + id));
    }

    public Acorde buscarAcorde(int id) {
        return acordeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Acorde não encontrado com o ID: " + id));
    }

    public Escala buscarEscala(int id) {
        return escalaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Escala não encontrada com o ID: " + id));
    }
    
    public List<Escala> buscarEscalaPorNome(String nomeEscala) {
        if (nomeEscala == null || nomeEscala.trim().isEmpty()) {
            System.err.println("SERVICO: O termo de busca para escala não pode ser vazio.");
            return Collections.emptyList();
        }
        return escalaRepo.findByNameContaining(nomeEscala);
    }

    public Optional<Acorde> buscarAcordePorNomeETipo(String nomeCifra, String tipo) {
        if (nomeCifra == null || nomeCifra.trim().isEmpty() || tipo == null || tipo.trim().isEmpty()) {
            System.err.println("SERVICO: Nome e tipo do acorde não podem ser vazios para busca.");
            return Optional.empty();
        }
        return ((AcordeRepositorioMySQL) acordeRepo).findByNomeAndTipo(nomeCifra, tipo);
    }

    public List<Musica> buscarMusica(String query) {
        return musicaRepo.searchByTitleOrArtist(query);
    }
    
    public Nota buscarNota(int id) {
        return notaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada com o ID: " + id));
    }
    
    public Nota buscarNotaPorNomeCompleto(String nomeCompleto) {
        System.out.println("SERVICE: Usando o NotaRepository para encontrar '" + nomeCompleto + "'.");

        return notaRepo.findByNomeCompleto(nomeCompleto).orElse(null);
    }
    
    public List<Musica> buscarMusicasPorTituloOuArtista(String query) {
        if (query == null || query.trim().isEmpty()) {
            System.err.println("SERVICO: O termo de busca para música não pode ser vazio.");
            return Collections.emptyList();
        }
        return musicaRepo.searchByTitleOrArtist(query);
    }

   
    public Optional<Musica> buscarMusicaPorId(int id) {
        if (id <= 0) {
            System.err.println("SERVICO: ID da música inválido para busca.");
            return Optional.empty();
        }
        return musicaRepo.findById(id);
    }

    public List<Musica> listarTodasMusicas() {
        return musicaRepo.findAll();
    }

    public Musica criarMusica(String titulo, String artista, int ano, String tonica) {
        if (titulo == null || titulo.trim().isEmpty() || artista == null || artista.trim().isEmpty()) {
            System.err.println("SERVICO: Título e artista não podem ser vazios para criar uma música.");
            return null;
        }
        Musica novaMusica = new Musica(titulo, artista, ano, tonica);
        return musicaRepo.save(novaMusica);
    }


    public Musica atualizarMusica(int id, String novoTitulo, String novoArtista, int novoAno, String novaTonica) {
        if (id <= 0) {
            System.err.println("SERVICO: ID da música inválido para atualização.");
            return null;
        }
        if (novoTitulo == null || novoTitulo.trim().isEmpty() || novoArtista == null || novoArtista.trim().isEmpty()) {
            System.err.println("SERVICO: Título e artista não podem ser vazios para atualizar uma música.");
            return null;
        }

        Optional<Musica> musicaExistente = musicaRepo.findById(id);
        if (musicaExistente.isPresent()) {
            Musica musica = musicaExistente.get();
            musica.setTitulo(novoTitulo);
            musica.setArtista(novoArtista);
            musica.setAno(novoAno);
            musica.setTonica(novaTonica);
            return musicaRepo.save(musica);
        } else {
            System.out.println("SERVICO: Música com ID " + id + " não encontrada para atualização.");
            return null;
        }
    }

    public void removerMusica(int id) {
        if (id <= 0) {
            System.err.println("SERVICO: ID da música inválido para remoção.");
            return;
        }
        musicaRepo.deleteById(id);
    }
}