package repository;

import model.Nota;

import java.util.*;

public class InMemoryNotaRepositorio implements NotaRepositorio {

    private final Map<Integer, Nota> database = new HashMap<>();
    
    private int proximoId = 1;

    public InMemoryNotaRepositorio() {
        save(new Nota("C", 4));
        save(new Nota("G", 4));
        save(new Nota("F", "#", 5));
    }

    @Override
    public Nota save(Nota nota) {
        if (nota == null) {
            throw new IllegalArgumentException("Nota não pode ser nula.");
        }
        
        if (nota.getId() == 0) {
            nota.setId(proximoId++);
        }
        
        database.put(nota.getId(), nota);
        return nota;
    }

    @Override
    public Optional<Nota> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<Nota> findByNomeCompleto(String nomeCompleto) {
        if (nomeCompleto == null) {
            return Optional.empty();
        }
        return database.values().stream()
                .filter(nota -> nomeCompleto.equalsIgnoreCase(nota.getNomeCompleto()))
                .findFirst();
    }

    @Override
    public List<Nota> findAll() {
        return new ArrayList<>(database.values());
    }
}