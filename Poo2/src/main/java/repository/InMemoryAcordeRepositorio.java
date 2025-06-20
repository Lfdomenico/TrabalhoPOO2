package repository;

import model.Acorde;
import model.Nota;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryAcordeRepositorio implements AcordeRepositorio {

    private final Map<Integer, Acorde> database = new HashMap<>();
    private int proximoId = 1;

    public InMemoryAcordeRepositorio() {
        Nota c4 = new Nota("C", null, 4);
        Nota g4 = new Nota("G", null, 4);
        Nota a4 = new Nota("A", null, 4);

        save(new Acorde("Maior", c4, "C", List.of(c4, new Nota("E", null, 4), new Nota("G", null, 4))));
        save(new Acorde("Maior", g4, "G", List.of(g4, new Nota("B", null, 4), new Nota("D", null, 5))));
        save(new Acorde("Menor", a4, "Am", List.of(a4, new Nota("C", null, 5), new Nota("E", null, 5))));
    }

    @Override
    public Acorde save(Acorde acorde) {
        if (acorde == null) {
            throw new IllegalArgumentException("Acorde não pode ser nulo.");
        }
        if (acorde.getId() == 0) {
            acorde.setId(proximoId++);
        }
        database.put(acorde.getId(), acorde);
        return acorde;
    }

    @Override
    public Optional<Acorde> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Acorde> findByNameContaining(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String lowerCaseNome = nome.toLowerCase();
        return database.values().stream().filter(acorde -> acorde.getNome().toLowerCase().contains(lowerCaseNome)).collect(Collectors.toList());
    }
    
    @Override
    public List<Acorde> findByTonica(Nota tonica) {
        if (tonica == null) {
            return Collections.emptyList();
        }

        return database.values().stream().filter(acorde -> tonica.equals(acorde.getTonica())).collect(Collectors.toList());
    }

    @Override
    public List<Acorde> findAll() {
        return new ArrayList<>(database.values());
    }
    
    @Override
    public void deleteById(int id) {
        database.remove(id);
    }

	@Override
	public List<Acorde> findByTipo(String tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllTipos() {
		// TODO Auto-generated method stub
		return null;
	}
}