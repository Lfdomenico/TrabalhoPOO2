package repository;

import model.Escala;
import java.util.List;
import java.util.Optional;

public interface EscalaRepositorio {

    Escala save(Escala escala);

    Optional<Escala> findById(int id);

    List<Escala> findByEstrutura(String estrutura);

    List<Escala> findByNameContaining(String nome);

    Optional<Escala> findByNome(String nome); // Mantém este para buscar o objeto Escala completo

    // NOVO MÉTODO: Para buscar apenas a estrutura de uma escala por seu nome exato
    Optional<String> findEstruturaByNome(String nome);

    List<Escala> findAll();

    void deleteById(int id);
}