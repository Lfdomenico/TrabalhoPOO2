package controller;

import model.ElementoMusical;
import service.ServicoBusca;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Controller responsável por receber requisições de busca, interpretá-las
 * e delegar para o ServicoBusca apropriado.
 */
public class ControladorBusca {

    private final ServicoBusca servicoBusca;

    public ControladorBusca(ServicoBusca servicoBusca) {
        this.servicoBusca = servicoBusca;
    }

    /**
     * Realiza uma busca genérica com base em um mapa de filtros.
     * O filtro "tipo" é obrigatório para determinar o que buscar.
     * @param filtros Um mapa contendo os critérios da busca. Ex: {"tipo":"acorde", "id":"1"}
     * @return Uma lista de resultados. O tipo dos objetos na lista depende do tipo da busca.
     */
    public List<?> realizarBusca(Map<String, String> filtros) {
        if (filtros == null || !filtros.containsKey("tipo")) {
            System.err.println("CONTROLLER: Erro - O tipo da busca não foi especificado nos filtros.");
            return Collections.emptyList();
        }

        String tipoBusca = filtros.get("tipo");
        System.out.println("CONTROLLER: Recebida requisição de busca para o tipo: '" + tipoBusca + "'");

        try {
            switch (tipoBusca.toLowerCase()) {
                case "acorde":
                    int idAcorde = Integer.parseInt(filtros.get("id"));
                    // Para manter um tipo de retorno consistente, colocamos o resultado único em uma lista.
                    return Collections.singletonList(servicoBusca.buscarAcorde(idAcorde));
                
                case "escala":
                    int idEscala = Integer.parseInt(filtros.get("id"));
                    return Collections.singletonList(servicoBusca.buscarEscala(idEscala));

                case "entidademusical":
                    int idEntidade = Integer.parseInt(filtros.get("id"));
                    return Collections.singletonList(servicoBusca.buscarEntidadeMusical(idEntidade));

                case "musica":
                    String query = filtros.get("query");
                    return servicoBusca.buscarMusica(query);

                default:
                    System.err.println("CONTROLLER: Tipo de busca desconhecido: '" + tipoBusca + "'");
                    return Collections.emptyList();
            }
        } catch (NumberFormatException e) {
            System.err.println("CONTROLLER: Erro - O ID fornecido para a busca não é um número válido.");
            return Collections.emptyList();
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro durante a busca: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}