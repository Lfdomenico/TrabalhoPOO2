package repository;

import model.Escala;
import java.util.List;
import java.util.Optional;

public interface EscalaRepositorio {

    /**
     * Salva ou atualiza uma escala.
     * @param escala A escala a ser salva.
     * @return A escala salva com seu ID gerenciado.
     */
    Escala save(Escala escala);

    /**
     * Busca uma escala pelo seu ID numérico.
     * @param id O ID da escala.
     * @return Um Optional contendo a escala se encontrada.
     */
    Optional<Escala> findById(int id);

    /**
     * Busca escalas que possuem uma determinada estrutura de intervalos.
     * @param estrutura A estrutura a ser buscada (ex: "T-T-ST-T-T-T-ST").
     * @return Uma lista de escalas com a estrutura correspondente.
     */
    List<Escala> findByEstrutura(String estrutura);

    /**
     * Busca escalas cujo nome contenha o termo da consulta.
     * @param nome O termo a ser buscado no nome da escala.
     * @return Uma lista de escalas que correspondem à busca.
     */
    List<Escala> findByNameContaining(String nome);

    /**
     * Retorna todas as escalas salvas.
     * @return Uma lista de todas as escalas.
     */
    List<Escala> findAll();

    /**
     * Deleta uma escala pelo seu ID.
     * @param id O ID da escala a ser deletada.
     */
    void deleteById(int id);
}