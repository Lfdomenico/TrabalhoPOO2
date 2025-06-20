package repository;

import model.Anotacao;
import java.util.List;
import java.util.Optional;

public interface AnotacaoRepositorio {

    /**
     * Salva ou atualiza uma anotação.
     * @param anotacao A anotação a ser salva.
     * @return A anotação salva com seu ID gerenciado.
     */
    Anotacao save(Anotacao anotacao);

    /**
     * Busca uma anotação pelo seu ID numérico único.
     * @param id O ID da anotação.
     * @return um Optional contendo a anotação se encontrada.
     */
    Optional<Anotacao> findById(int id);

    /**
     * Busca todas as anotações associadas a uma entidade musical específica.
     * @param idElemento O ID da entidade musical.
     * @return Uma lista de anotações associadas.
     */
    List<Anotacao> findByElementoId(int idElemento);

    /**
     * Retorna todas as anotações salvas no sistema.
     * @return Uma lista de todas as anotações.
     */
    List<Anotacao> findAll();

    /**
     * Deleta uma anotação pelo seu ID.
     * @param id O ID da anotação a ser deletada.
     */
    void deleteById(int id);
}