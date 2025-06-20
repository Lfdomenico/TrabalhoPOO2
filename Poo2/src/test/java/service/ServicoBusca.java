package service;

import model.*;
import repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Fornece uma fachada unificada para realizar buscas por diferentes
 * tipos de entidades no sistema.
 */
public class ServicoBusca {

    // O serviço depende de todos os repositórios relevantes para a busca.
    private final ElementoMusicalRepositorio elementoMusicalRepo;
    private final AcordeRepositorio acordeRepo;
    private final EscalaRepositorio escalaRepo;
    private final MusicaRepositorio musicaRepo;
    private final NotaRepositorio notaRepo;

    // Injeção de Dependência via construtor
    public ServicoBusca(ElementoMusicalRepositorio elementoRepo, AcordeRepositorio acordeRepo,
                        EscalaRepositorio escalaRepo, MusicaRepositorio musicaRepo, NotaRepositorio notaRepo) {
        this.elementoMusicalRepo = elementoRepo;
        this.acordeRepo = acordeRepo;
        this.escalaRepo = escalaRepo;
        this.musicaRepo = musicaRepo;
        this.notaRepo = notaRepo;
    }

    /**
     * Busca qualquer tipo de ElementoMusical pelo seu ID.
     * @param id O ID da entidade.
     * @return O ElementoMusical encontrado.
     * @throws RuntimeException se nenhuma entidade for encontrada.
     */
    public ElementoMusical buscarEntidadeMusical(int id) {
        return elementoMusicalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Entidade Musical não encontrada com o ID: " + id));
    }

    /**
     * Busca um Acorde específico pelo seu ID. Garante que o tipo retornado é Acorde.
     * @param id O ID do acorde.
     * @return O Acorde encontrado.
     * @throws RuntimeException se nenhum elemento for encontrado ou se o elemento não for um Acorde.
     */
    public Acorde buscarAcorde(int id) {
        Optional<ElementoMusical> elementoOpt = elementoMusicalRepo.findById(id);
        
        return elementoOpt
                .filter(el -> el instanceof Acorde) // Garante que o elemento é um Acorde
                .map(el -> (Acorde) el) // Faz o cast seguro para Acorde
                .orElseThrow(() -> new RuntimeException("Acorde não encontrado ou o ID " + id + " não pertence a um Acorde."));
    }

    /**
     * Busca uma Escala específica pelo seu ID. Garante que o tipo retornado é Escala.
     * @param id O ID da escala.
     * @return A Escala encontrada.
     * @throws RuntimeException se nenhum elemento for encontrado ou se o elemento não for uma Escala.
     */
    public Escala buscarEscala(int id) {
        return elementoMusicalRepo.findById(id)
                .filter(Escala.class::isInstance) // Outra forma de checar o tipo
                .map(Escala.class::cast) // Outra forma de fazer o cast
                .orElseThrow(() -> new RuntimeException("Escala não encontrada ou o ID " + id + " não pertence a uma Escala."));
    }

    /**
     * Busca músicas por título ou artista.
     * @param query O termo para a busca.
     * @return Uma lista de MusicaInfo correspondente.
     */
    public List<Musica> buscarMusica(String query) {
        return musicaRepo.searchByTitleOrArtist(query);
    }
}