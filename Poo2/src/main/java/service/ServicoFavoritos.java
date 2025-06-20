package service;

import model.ElementoMusical;
import repository.ElementoMusicalRepositorio;
import java.util.*;
import java.util.stream.Collectors;

public class ServicoFavoritos {

    private final Set<Integer> idsFavoritos = new HashSet<>();

    private final ElementoMusicalRepositorio elementoMusicalRepository;

    public ServicoFavoritos(ElementoMusicalRepositorio elementoMusicalRepository) {
        this.elementoMusicalRepository = elementoMusicalRepository;
    }
    
    public void adicionarFavorito(ElementoMusical item) {
        if (item != null && item.getId() != 0) {
            System.out.println("SERVICE: Adicionando '" + item.getNome() + "' aos favoritos.");
            idsFavoritos.add(item.getId());
        }
    }

    public void removerFavorito(ElementoMusical item) {
        if (item != null) {
            System.out.println("SERVICE: Removendo '" + item.getNome() + "' dos favoritos.");
            idsFavoritos.remove(item.getId());
        }
    }

    public boolean ehFavorito(ElementoMusical item) {
        if (item == null) {
            return false;
        }
        return idsFavoritos.contains(item.getId());
    }

    public List<ElementoMusical> listarFavoritos() {
        System.out.println("SERVICE: Carregando todos os objetos favoritos...");
        if (idsFavoritos.isEmpty()) {
            return Collections.emptyList();
        }
        
        return idsFavoritos.stream()
                .map(id -> elementoMusicalRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}