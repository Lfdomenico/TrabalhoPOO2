package repository;

import model.Escala;
import model.Nota;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryEscalaRepositorio implements EscalaRepositorio {

    private final Map<Integer, Escala> database = new HashMap<>();
    private int proximoId = 1;

    public InMemoryEscalaRepositorio() {
        // Pré-populando com dados de exemplo, usando o construtor correto
        // Assumindo: Escala(List<Nota> notas, String estrutura, String nome)
        List<Nota> notasDoMaior = List.of(
            new Nota("C", null, 4), new Nota("D", null, 4), new Nota("E", null, 4),
            new Nota("F", null, 4), new Nota("G", null, 4), new Nota("A", null, 4),
            new Nota("B", null, 4)
        );
        save(new Escala(notasDoMaior, "T-T-ST-T-T-T-ST", "Dó Maior"));

        List<Nota> notasDoMenor = List.of(
            new Nota("A", null, 4), new Nota("B", null, 4), new Nota("C", null, 5),
            new Nota("D", null, 5), new Nota("E", null, 5), new Nota("F", null, 5),
            new Nota("G", null, 5)
        );
        save(new Escala(notasDoMenor, "T-ST-T-T-ST-T-T", "Lá Menor Natural"));
    }

    @Override
    public Escala save(Escala escala) {
        if (escala == null) {
            throw new IllegalArgumentException("Escala não pode ser nula.");
        }
        
        // Usando um construtor de cópia para garantir o encapsulamento.
        Escala copiaParaSalvar = new Escala(escala);

        if (copiaParaSalvar.getId() == 0) {
            copiaParaSalvar.setId(proximoId++);
        }
        
        database.put(copiaParaSalvar.getId(), copiaParaSalvar);
        return new Escala(copiaParaSalvar); // Retorna outra cópia
    }

    @Override
    public Optional<Escala> findById(int id) {
        return Optional.ofNullable(database.get(id)).map(Escala::new);
    }

    @Override
    public List<Escala> findByEstrutura(String estrutura) {
        if (estrutura == null || estrutura.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return database.values().stream()
                .filter(escala -> estrutura.equalsIgnoreCase(escala.getEstrutura()))
                .map(Escala::new) // Retorna cópias
                .collect(Collectors.toList());
    }

    @Override
    public List<Escala> findByNameContaining(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String lowerCaseNome = nome.toLowerCase();
        return database.values().stream()
                .filter(escala -> escala.getNome().toLowerCase().contains(lowerCaseNome))
                .map(Escala::new) // Retorna cópias
                .collect(Collectors.toList());
    }

    @Override
    public List<Escala> findAll() {
        return database.values().stream()
                .map(Escala::new) // Retorna cópias
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id) {
        database.remove(id);
    }
}