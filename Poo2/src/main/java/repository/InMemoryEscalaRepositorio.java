package repository;

import model.Escala;
import model.Nota;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryEscalaRepositorio implements EscalaRepositorio {

    private final Map<Integer, Escala> database = new HashMap<>();
    private int proximoId = 1;

    public InMemoryEscalaRepositorio() {
        // --- População de dados de exemplo ---

        List<Nota> notasDoCMaior = List.of(
            new Nota("C", null, 4), new Nota("D", null, 4), new Nota("E", null, 4),
            new Nota("F", null, 4), new Nota("G", null, 4), new Nota("A", null, 4),
            new Nota("B", null, 4)
        );
        save(new Escala(notasDoCMaior, "C-D-E-F-G-A-B", "Maior", "C")); // Nome da cifra "C"

        List<Nota> notasDoAMenor = List.of(
            new Nota("A", null, 4), new Nota("B", null, 4), new Nota("C", null, 5),
            new Nota("D", null, 5), new Nota("E", null, 5), new Nota("F", null, 5),
            new Nota("G", null, 5)
        );
        save(new Escala(notasDoAMenor, "A-B-C-D-E-F-G", "Menor Natural", "Am")); // Nome da cifra "Am"

        List<Nota> notasDoDMaior = List.of(
            new Nota("D", null, 4), new Nota("E", null, 4), new Nota("F#", null, 4),
            new Nota("G", null, 4), new Nota("A", null, 4), new Nota("B", null, 4),
            new Nota("C#", null, 5)
        );
        save(new Escala(notasDoDMaior, "D-E-F#-G-A-B-C#", "Maior", "D")); // Nome da cifra "D"

        List<Nota> notasDoGMaior = List.of(
            new Nota("G", null, 4), new Nota("A", null, 4), new Nota("B", null, 4),
            new Nota("C", null, 5), new Nota("D", null, 5), new Nota("E", null, 5),
            new Nota("F#", null, 5)
        );
        save(new Escala(notasDoGMaior, "G-A-B-C-D-E-F#", "Maior", "G")); // Nome da cifra "G"
    }

    @Override
    public Escala save(Escala escala) {
        if (escala == null) {
            throw new IllegalArgumentException("Escala não pode ser nula.");
        }
        
        Escala copiaParaSalvar = new Escala(escala); 

        if (copiaParaSalvar.getId() == 0) {
            copiaParaSalvar.setId(proximoId++);
        }
        
        database.put(copiaParaSalvar.getId(), copiaParaSalvar);
        return new Escala(copiaParaSalvar); 
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
                .map(Escala::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Escala> findByNameContaining(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String lowerCaseNome = nome.toLowerCase();
        return database.values().stream()
                .filter(escala -> escala.getNome().toLowerCase().contains(lowerCaseNome) ||
                                 escala.getEstrutura().toLowerCase().contains(lowerCaseNome) ||
                                 escala.getTipo().toLowerCase().contains(lowerCaseNome))
                .map(Escala::new)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Escala> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Optional.empty();
        }
        String lowerCaseNome = nome.toLowerCase();
        return database.values().stream()
                .filter(escala -> escala.getNome().toLowerCase().equals(lowerCaseNome))
                .map(Escala::new)
                .findFirst();
    }

    // >>> NOVO MÉTODO IMPLEMENTADO: findEstruturaByNome <<<
    @Override
    public Optional<String> findEstruturaByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Optional.empty();
        }
        String lowerCaseNome = nome.toLowerCase();
        return database.values().stream()
                .filter(escala -> escala.getNome().toLowerCase().equals(lowerCaseNome)) // Busca exata no campo 'nome'
                .map(Escala::getEstrutura) // Mapeia para a estrutura da escala
                .findFirst(); // Retorna a primeira estrutura encontrada
    }


    @Override
    public List<Escala> findAll() {
        return database.values().stream()
                .map(Escala::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id) {
        database.remove(id);
    }
}