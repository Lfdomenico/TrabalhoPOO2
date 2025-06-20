package repository;

import model.Nota;

import java.util.*;

public class InMemoryNotaRepositorio implements NotaRepositorio {

    // CORREÇÃO: O "banco de dados" agora usa Integer como chave.
    private final Map<Integer, Nota> database = new HashMap<>();
    
    // CORREÇÃO: Um contador para simular a funcionalidade de AUTO_INCREMENT do banco de dados.
    private int proximoId = 1;

    public InMemoryNotaRepositorio() {
        // Pré-populando com algumas notas. O método save vai atribuir os IDs.
        save(new Nota("C", 4)); // Receberá o ID 1 int id, String nome, String acidente, int oitava
        save(new Nota("G", 4)); // Receberá o ID 2
        save(new Nota("F", "#", 5));  // Receberá o ID 3
    }

    @Override
    public Nota save(Nota nota) {
        if (nota == null) {
            throw new IllegalArgumentException("Nota não pode ser nula.");
        }
        
        // CORREÇÃO: Lógica para gerenciar o ID inteiro.
        if (nota.getId() == 0) { // Assumindo que 0 significa que a nota é nova (default de int).
            nota.setId(proximoId++); // Atribui o próximo ID disponível e incrementa o contador.
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

    @Override
    public void deleteById(int id) {
        database.remove(id);
    }
}