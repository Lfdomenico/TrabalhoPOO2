package repository;

import model.Musica;
import java.util.List;
import java.util.Optional;

public interface MusicaRepositorio {

    /**
     * Busca músicas cujo título ou nome do artista contenha o termo da consulta.
     * @param query O termo a ser buscado.
     * @return Uma lista de MusicaInfo que correspondem à busca.
     */
    List<Musica> searchByTitleOrArtist(String query);

    /**
     * Busca uma música pelo seu ID único.
     * @param id O ID da música.
     * @return um Optional contendo a música se encontrada.
     */
    Optional<Musica> findById(int id);
    
    /**
     * Salva ou atualiza uma música.
     * @param musica A música a ser salva.
     * @return A música salva.
     */
    Musica save(Musica musica);
}