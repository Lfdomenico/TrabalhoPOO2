package repository;

import model.Acorde;
import model.Anotacao;
import model.Nota;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryAnotacaoRepositorio implements AnotacaoRepositorio {

    private final Map<Integer, Anotacao> database = new HashMap<>();
    private int proximoId = 1;

    public InMemoryAnotacaoRepositorio() {
        Nota tonicaDm7 = new Nota("D", null, 4);
        List<Nota> notasDoDm7 = List.of(
            tonicaDm7,
            new Nota("F", null, 4),
            new Nota("A", null, 4),
            new Nota("C", null, 5)
        );

        Acorde acordeDeExemplo = new Acorde("Menor com Sétima", tonicaDm7, "Dm7",  notasDoDm7);

        acordeDeExemplo.setId(99);

        save(new Anotacao("Usar este acorde na ponte da música", acordeDeExemplo));
        save(new Anotacao("Lembrar de comprar cordas novas para o violão", null));
    }

    @Override
    public Anotacao save(Anotacao anotacao) {
        if (anotacao == null) {
            throw new IllegalArgumentException("Anotação não pode ser nula.");
        }

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
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(int id) {
        database.remove(id);
    }
}