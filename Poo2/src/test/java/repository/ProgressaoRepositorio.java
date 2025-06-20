package repository;

import model.Progressao;
import java.util.List;
import java.util.Optional;

public interface ProgressaoRepositorio {

    /**
     * Salva ou atualiza uma progressão.
     * @param progressao A progressão a ser salva.
     * @return A progressão salva com seu ID gerenciado.
     */
    Progressao save(Progressao progressao);

    /**
     * Busca uma progressão pelo seu ID numérico.
     * @param id O ID da progressão.
     * @return Um Optional contendo a progressão se encontrada.
     */
    Optional<Progressao> findById(int id);

    /**
     * Busca todas as progressões com um determinado nome.
     * @param nome O nome a ser buscado.
     * @return Uma lista de progressões com o nome correspondente.
     */
    List<Progressao> findAllByNome(String nome);

    /**
     * Retorna todas as progressões salvas.
     * @return Uma lista de todas as progressões.
     */
    List<Progressao> findAll();

    /**
     * Deleta uma progressão pelo seu ID.
     * @param id O ID da progressão a ser deletada.
     */
    void deleteById(int id);
}