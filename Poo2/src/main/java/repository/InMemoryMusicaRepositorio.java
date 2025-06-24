package repository;

import model.Musica;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMusicaRepositorio implements MusicaRepositorio {

    private final Map<Integer, Musica> database = new HashMap<>();
    
    private int proximoId = 1;

    public InMemoryMusicaRepositorio() {
        save(new Musica("Bohemian Rhapsody", "Queen", new ArrayList<>()));
        save(new Musica("Stairway to Heaven", "Led Zeppelin", new ArrayList<>()));
        save(new Musica("Hotel California", "Eagles", new ArrayList<>()));
    }

    @Override
    public Musica save(Musica musica) {
        if (musica == null) {
            throw new IllegalArgumentException("Musica não pode ser nula.");
        }
        
        if (musica.getId() == 0) {
            musica.setId(proximoId++);
        }
        
        database.put(musica.getId(), musica);
        return musica;
    }

    @Override
    public Optional<Musica> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }
    
    @Override
    public List<Musica> searchByTitleOrArtist(String query) {
        String lowerCaseQuery = query.toLowerCase();
        return database.values().stream()
                .filter(musica -> 
                     musica.getTitulo().toLowerCase().contains(lowerCaseQuery) ||
                     musica.getArtista().toLowerCase().contains(lowerCaseQuery)
                )
                .collect(Collectors.toList());
    }

	@Override
	public List<Musica> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}
}