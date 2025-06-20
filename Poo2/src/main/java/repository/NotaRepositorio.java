package repository;

import model.Nota;

import java.util.List;
import java.util.Optional;

public interface NotaRepositorio {

    Nota save(Nota nota);

    Optional<Nota> findById(int id);

    Optional<Nota> findByNomeCompleto(String nomeCompleto);
    
    List<Nota> findAll();
}