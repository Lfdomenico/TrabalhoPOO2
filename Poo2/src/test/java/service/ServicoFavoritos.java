package service;

import model.ElementoMusical;
import repository.ElementoMusicalRepositorio;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Gerencia a lógica de negócio para favoritar e desfavoritar
 * qualquer tipo de ElementoMusical.
 */
public class ServicoFavoritos {

    // Simula uma tabela no banco de dados que armazena apenas os IDs dos itens favoritos.
    private final Set<Integer> idsFavoritos = new HashSet<>();

    // Dependência do repositório genérico para buscar os elementos completos.
    private final ElementoMusicalRepositorio elementoMusicalRepository;

    public ServicoFavoritos(ElementoMusicalRepositorio elementoMusicalRepository) {
        this.elementoMusicalRepository = elementoMusicalRepository;
    }

    /**
     * Adiciona um item à lista de favoritos.
     * @param item O ElementoMusical a ser favoritado.
     */
    public void adicionarFavorito(ElementoMusical item) {
        if (item != null && item.getId() != 0) {
            System.out.println("SERVICE: Adicionando '" + item.getNome() + "' aos favoritos.");
            idsFavoritos.add(item.getId());
        }
    }

    /**
     * Remove um item da lista de favoritos.
     * @param item O ElementoMusical a ser desfavoritado.
     */
    public void removerFavorito(ElementoMusical item) {
        if (item != null) {
            System.out.println("SERVICE: Removendo '" + item.getNome() + "' dos favoritos.");
            idsFavoritos.remove(item.getId());
        }
    }

    /**
     * Verifica se um determinado item está na lista de favoritos.
     * @param item O ElementoMusical a ser verificado.
     * @return true se for um favorito, false caso contrário.
     */
    public boolean ehFavorito(ElementoMusical item) {
        if (item == null) {
            return false;
        }
        return idsFavoritos.contains(item.getId());
    }

    /**
     * Retorna a lista completa de objetos ElementoMusical que foram favoritados.
     * @return Uma lista com os favoritos.
     */
    public List<ElementoMusical> listarFavoritos() {
        System.out.println("SERVICE: Carregando todos os objetos favoritos...");
        if (idsFavoritos.isEmpty()) {
            return Collections.emptyList();
        }
        
        // Usa o stream para buscar cada item pelo seu ID no repositório.
        return idsFavoritos.stream()
                .map(id -> elementoMusicalRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}