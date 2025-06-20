package repository;

import model.Acorde;
import model.Anotacao;
import model.ElementoMusical;
import model.Nota;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação em memória do AnotacaoRepository para desenvolvimento e testes.
 * Simula o comportamento de um banco de dados usando um Map.
 */
public class InMemoryAnotacaoRepositorio implements AnotacaoRepositorio {

    private final Map<Integer, Anotacao> database = new HashMap<>();
    private int proximoId = 1;

    /**
     * O construtor pré-popula o "banco de dados" com dados de exemplo,
     * respeitando os construtores corretos das classes do modelo.
     */
    public InMemoryAnotacaoRepositorio() {
        // 1. Montar os componentes necessários para um acorde de exemplo (ex: Dm7)
        Nota tonicaDm7 = new Nota("D", null, 4);
        List<Nota> notasDoDm7 = List.of(
            tonicaDm7,
            new Nota("F", null, 4),
            new Nota("A", null, 4),
            new Nota("C", null, 5)
        );

        // 2. Criar a instância do Acorde usando o construtor correto
        // Assumindo: Acorde(String nome, String tipo, Nota tonica, List<Nota> notas)
        Acorde acordeDeExemplo = new Acorde("Menor com Sétima", tonicaDm7, "Dm7",  notasDoDm7);
        
        // Atribuímos um ID manualmente para este exemplo, pois ele não está sendo
        // salvo pelo seu próprio repositório, mas será usado por uma anotação.
        acordeDeExemplo.setId(99);

        // 3. Agora, criamos e salvamos as anotações usando os dados de exemplo
        // O método save() irá atribuir os IDs 1 e 2 a estas anotações.
        save(new Anotacao("Usar este acorde na ponte da música", acordeDeExemplo));
        save(new Anotacao("Lembrar de comprar cordas novas para o violão", null));
    }

    @Override
    public Anotacao save(Anotacao anotacao) {
        if (anotacao == null) {
            throw new IllegalArgumentException("Anotação não pode ser nula.");
        }
        
        // Atribui um novo ID se a anotação for nova (id=0).
        if (anotacao.getId() == 0) {
            anotacao.setId(proximoId++);
        }
        
        database.put(anotacao.getId(), anotacao);
        return anotacao;
    }

    @Override
    public Optional<Anotacao> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Anotacao> findByElementoId(int idElemento) {
        // Filtra todas as anotações no "banco" para encontrar aquelas
        // cujo elemento associado tem o ID correspondente.
        return database.values().stream()
                .filter(anotacao -> {
                    if (anotacao.getElementoSobre() != null) {
                        return anotacao.getElementoSobre().getId() == idElemento;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Anotacao> findAll() {
        // Retorna uma cópia da lista para proteger os dados internos.
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(int id) {
        database.remove(id);
    }
}