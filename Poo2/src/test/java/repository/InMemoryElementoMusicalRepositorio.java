package repository;

import model.Acorde;
import model.ElementoMusical;
import model.Escala;
import model.Nota;
import model.Progressao;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryElementoMusicalRepositorio implements ElementoMusicalRepositorio {

    private final Map<Integer, ElementoMusical> database = new HashMap<>();
    private int proximoId = 1;

    /**
     * O construtor agora pré-popula o "banco de dados" usando as assinaturas
     * exatas que você definiu para cada classe do modelo.
     */
    public InMemoryElementoMusicalRepositorio() {
        // --- Criando e salvando um Acorde ---
        Nota tonicaC = new Nota("C", null, 4);
        List<Nota> notasDoC = List.of(tonicaC, new Nota("E", null, 4), new Nota("G", null, 4));
        // Usando o construtor: Acorde(String tipo, Nota tonica, String nome, List<Nota> notas)
        Acorde acordeC = new Acorde("Maior", tonicaC, "C", notasDoC);
        save(acordeC); // O save vai atribuir o ID 1

        // --- Criando e salvando uma Escala ---
        List<Nota> notasDeGMaior = List.of(new Nota("G",null,4), new Nota("A",null,4));
        // Usando o construtor: Escala(List<Nota> notas, String estrutura, String nome)
        Escala escalaG = new Escala(notasDeGMaior, "T-T-ST-T-T-T-ST", "G Maior");
        save(escalaG); // O save vai atribuir o ID 2

        // --- Criando e salvando uma Progressão ---
        Nota tonicaG = new Nota("G", null, 4);
        Acorde acordeG = new Acorde("Maior", tonicaG, "G", List.of(tonicaG));
        
        // Usando o construtor: Progressao(List<Acorde> acordes, String nome)
        Progressao progressaoBlues = new Progressao(List.of(acordeC, acordeG), "Progressão de Blues");
        save(progressaoBlues); // O save vai atribuir o ID 3
    }

    /**
     * O método save funciona perfeitamente com essa abordagem.
     * Ele recebe uma entidade sem ID (id=0) e atribui um novo.
     */
    @Override
    public ElementoMusical save(ElementoMusical entidade) {
        if (entidade == null) {
            throw new IllegalArgumentException("Entidade Musical não pode ser nula.");
        }
        
        if (entidade.getId() == 0) { // Assumindo que ElementoMusical tem getId() e setId()
            entidade.setId(proximoId++);
        }
        
        database.put(entidade.getId(), entidade);
        return entidade;
    }

    @Override
    public Optional<ElementoMusical> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<ElementoMusical> findByNameContaining(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String lowerCaseNome = nome.toLowerCase();
        
        return database.values().stream()
                .filter(entidade -> entidade.getNome().toLowerCase().contains(lowerCaseNome))
                .collect(Collectors.toList());
    }

    @Override
    public List<ElementoMusical> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(int id) {
        database.remove(id);
    }
}