package repository;

import model.Acorde;
import model.Nota;
import java.util.List;
import java.util.Optional;

public interface AcordeRepositorio {

    Acorde save(Acorde acorde);

    Optional<Acorde> findById(int id);

    List<Acorde> findByNameContaining(String nome);
    
    List<Acorde> findByTonica(Nota tonica);

    List<Acorde> findByTipo(String tipo); 

    List<String> findAllTipos(); 

    List<Acorde> findAll();
    
    void deleteById(int id);
}