package repository;

import model.Acorde;
import model.Nota;
import model.Progressao;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProgressaoRepositorio implements ProgressaoRepositorio {

    private final Map<Integer, Progressao> database = new HashMap<>();
    private int proximoId = 1;

    public InMemoryProgressaoRepositorio() {
        // Pré-populando com uma progressão de exemplo
        // Construtor do modelo: Progressao(List<Acorde> acordes, String nome)
        Acorde c = new Acorde("Maior", new Nota("C", 4), "C", List.of());
        Acorde g = new Acorde("Maior", new Nota("G", 4), "G", List.of());
        Acorde am = new Acorde("Menor", new Nota("A", 4), "Am", List.of());
        Acorde f = new Acorde("Maior", new Nota("F", 4), "F", List.of());

        Progressao p1 = new Progressao(List.of(c, g, am, f), "Progressão Padrão Pop");
        save(p1);
    }

    @Override
    public Progressao save(Progressao progressao) {
        if (progressao == null) {
            throw new IllegalArgumentException("Progressão não pode ser nula.");
        }
        
        // Para garantir o encapsulamento, salvamos uma cópia do objeto.
        // Isso requer um construtor de cópia na classe Progressao.
        Progressao copiaParaSalvar = new Progressao(progressao);

        if (copiaParaSalvar.getId() == 0) {
            copiaParaSalvar.setId(proximoId++);
        }
        
        database.put(copiaParaSalvar.getId(), copiaParaSalvar);
        return new Progressao(copiaParaSalvar); // Retorna outra cópia
    }

    @Override
    public Optional<Progressao> findById(int id) {
        // Retorna uma cópia para que o objeto no "banco" não seja modificado externamente.
        return Optional.ofNullable(database.get(id)).map(Progressao::new);
    }

    @Override
    public List<Progressao> findAllByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return database.values().stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .map(Progressao::new) // Retorna cópias
                .collect(Collectors.toList());
    }

    @Override
    public List<Progressao> findAll() {
        return database.values().stream()
                .map(Progressao::new) // Retorna cópias
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id) {
        database.remove(id);
    }
}