package repository;

import model.Musica;
import java.util.List;
import java.util.Optional;

public interface MusicaRepositorio {

    Musica save(Musica musica);
    
    //busca pelo titulo ou artista
    List<Musica> searchByTitleOrArtist(String query);

    //busca pelo id
    Optional<Musica> findById(int id);  

    // Novo: Listar todas as músicas
    List<Musica> findAll();

    // Novo: Deletar música por ID
    void deleteById(int id);
}