package repository;

import model.Anotacao;
import java.util.List;
import java.util.Optional;

public interface AnotacaoRepositorio {

    //cria e edita
    Anotacao save(Anotacao anotacao);

    Optional<Anotacao> findById(int id);

    List<Anotacao> findByElementoId(int idElemento);

    List<Anotacao> findAll();

    void deleteById(int id);
}