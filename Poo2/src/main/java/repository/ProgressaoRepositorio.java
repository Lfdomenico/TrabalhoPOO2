package repository;

import model.Progressao;
import java.util.List;
import java.util.Optional;

public interface ProgressaoRepositorio {

    Progressao save(Progressao progressao);

    Optional<Progressao> findById(int id);

    List<Progressao> findAllByNome(String nome);

    List<Progressao> findAll();

    void deleteById(int id);
}