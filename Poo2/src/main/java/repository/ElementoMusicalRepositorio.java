package repository;

import model.ElementoMusical;

import java.util.List;
import java.util.Optional;

public interface ElementoMusicalRepositorio {

    ElementoMusical save(ElementoMusical entidade);

    Optional<ElementoMusical> findById(int id);
    
    List<ElementoMusical> findByNameContaining(String nome);
    
    List<ElementoMusical> findAll();

    void deleteById(int id);
}