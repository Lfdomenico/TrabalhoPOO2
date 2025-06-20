package repository;

import model.ElementoMusical;

import java.util.List;
import java.util.Optional;

public interface ElementoMusicalRepositorio {

    /**
     * Salva ou atualiza qualquer entidade musical.
     * @param entidade O objeto a ser salvo (pode ser Acorde, Escala, etc.).
     * @return A entidade salva com seu ID gerenciado.
     */
    ElementoMusical save(ElementoMusical entidade);

    /**
     * Busca qualquer entidade musical pelo seu ID numérico único.
     * @param id O ID da entidade.
     * @return um Optional contendo a entidade se encontrada.
     */
    Optional<ElementoMusical> findById(int id);
    
    /**
     * Busca entidades musicais cujo nome contenha o termo da consulta.
     * @param nome O termo a ser buscado no nome da entidade.
     * @return Uma lista de entidades que correspondem à busca.
     */
    List<ElementoMusical> findByNameContaining(String nome);
    
    /**
     * Retorna todas as entidades musicais salvas.
     * @return Uma lista de todas as entidades.
     */
    List<ElementoMusical> findAll();
    
    /**
     * Deleta uma entidade pelo seu ID.
     * @param id O ID da entidade a ser deletada.
     */
    void deleteById(int id);
}