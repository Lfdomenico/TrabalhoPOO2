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

    public ServicoBusca(ElementoMusicalRepositorio elementoRepo, AcordeRepositorio acordeRepo,
                        EscalaRepositorio escalaRepo, MusicaRepositorio musicaRepo, NotaRepositorio notaRepo) {
        this.elementoMusicalRepo = elementoRepo;
        this.acordeRepo = acordeRepo;
        this.escalaRepo = escalaRepo;
        this.musicaRepo = musicaRepo;
        this.notaRepo = notaRepo;
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
}