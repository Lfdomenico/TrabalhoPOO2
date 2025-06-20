package repository;

import model.Musica;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMusicaRepositorio implements MusicaRepositorio {

    // CORREÇÃO: O "banco de dados" agora usa Integer como chave.
    private final Map<Integer, Musica> database = new HashMap<>();
    
    // CORREÇÃO: Um contador para simular o AUTO_INCREMENT.
    private int proximoId = 1;

    public InMemoryMusicaRepositorio() {
        // Agora o construtor funciona perfeitamente com a lógica do save.
        save(new Musica("Bohemian Rhapsody", "Queen", new ArrayList<>()));
        save(new Musica("Stairway to Heaven", "Led Zeppelin", new ArrayList<>()));
        save(new Musica("Hotel California", "Eagles", new ArrayList<>()));
    }

    @Override
    public Musica save(Musica musica) {
        if (musica == null) {
            throw new IllegalArgumentException("Musica não pode ser nula.");
        }
        
        // CORREÇÃO FINAL: Lógica para gerenciar o ID inteiro.
        if (musica.getId() == 0) { // Assumindo que 0 significa "novo objeto".
            musica.setId(proximoId++); // Atribui o próximo ID e incrementa o contador.
        }
        
        database.put(musica.getId(), musica);
        return musica;
    }

    // A interface MusicaRepository também precisaria ser ajustada para usar 'int id'
    @Override
    public Optional<Musica> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }
    
    @Override
    public List<Musica> searchByTitleOrArtist(String query) {
        // ... a lógica de busca continua a mesma ...
        String lowerCaseQuery = query.toLowerCase();
        return database.values().stream()
                .filter(musica -> 
                     musica.getTitulo().toLowerCase().contains(lowerCaseQuery) ||
                     musica.getArtista().toLowerCase().contains(lowerCaseQuery)
                )
                .collect(Collectors.toList());
    }
}