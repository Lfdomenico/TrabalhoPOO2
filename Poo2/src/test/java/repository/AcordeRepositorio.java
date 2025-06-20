package repository;

import model.Acorde;
import model.Nota;
import java.util.List;
import java.util.Optional;

public interface AcordeRepositorio {

    /**
     * Salva ou atualiza um acorde.
     * @param acorde O acorde a ser salvo.
     * @return O acorde salvo com seu ID gerenciado.
     */
    Acorde save(Acorde acorde);

    /**
     * Busca um acorde pelo seu ID numérico único.
     * @param id O ID do acorde.
     * @return um Optional contendo o acorde se encontrado.
     */
    Optional<Acorde> findById(int id);

    /**
     * Busca acordes cujo nome contenha o termo da consulta.
     * @param nome O termo a ser buscado no nome do acorde.
     * @return Uma lista de acordes que correspondem à busca.
     */
    List<Acorde> findByNameContaining(String nome);
    
    /**
     * Busca todos os acordes que possuem uma determinada tônica.
     * @param tonica A nota tônica a ser buscada.
     * @return Uma lista de acordes com a tônica especificada.
     */
    List<Acorde> findByTonica(Nota tonica);
    
    /**
     * Retorna todos os acordes salvos.
     * @return Uma lista de todos os acordes.
     */
    List<Acorde> findAll();
}