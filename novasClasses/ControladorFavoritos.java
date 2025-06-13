package controller;

import model.ElementoMusical;
import service.ServicoBusca;
import service.ServicoFavoritos;

public class ControladorFavoritos {

    private final ServicoFavoritos servicoFavoritos;
    private final ServicoBusca servicoBusca;

    public ControladorFavoritos(ServicoFavoritos servicoFavoritos, ServicoBusca servicoBusca) {
        this.servicoFavoritos = servicoFavoritos;
        this.servicoBusca = servicoBusca;
    }

    public void lidarComToggleFavorito(int idElemento) {
        System.out.println("CONTROLLER FAVORITOS: Requisição para favoritar/desfavoritar elemento " + idElemento);
        try {
            ElementoMusical elemento = servicoBusca.buscarEntidadeMusical(idElemento);
            if (servicoFavoritos.ehFavorito(elemento)) {
                servicoFavoritos.removerFavorito(elemento);
                System.out.println("CONTROLLER FAVORITOS: '" + elemento.getNome() + "' removido dos favoritos.");
            } else {
                servicoFavoritos.adicionarFavorito(elemento);
                System.out.println("CONTROLLER FAVORITOS: '" + elemento.getNome() + "' adicionado aos favoritos.");
            }
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER FAVORITOS: Erro - " + e.getMessage());
        }
    }
}