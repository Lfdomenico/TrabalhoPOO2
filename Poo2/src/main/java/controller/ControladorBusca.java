package controller;

import service.ServicoBusca;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional; 

import model.Acorde;

public class ControladorBusca {

    public final ServicoBusca servicoBusca;

    public ControladorBusca(ServicoBusca servicoBusca) {
        this.servicoBusca = servicoBusca;
    }

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
                    if (filtros.containsKey("id")) {
                        int idAcorde = Integer.parseInt(filtros.get("id"));
                        return Collections.singletonList(servicoBusca.buscarAcorde(idAcorde));
                    } else if (filtros.containsKey("nome") && filtros.containsKey("tipoAcorde")) { 
                        String nomeAcorde = filtros.get("nome");
                        String tipoAcorde = filtros.get("tipoAcorde");
                        Optional<Acorde> acorde = servicoBusca.buscarAcordePorNomeETipo(nomeAcorde, tipoAcorde);
                        return acorde.map(Collections::singletonList).orElse(Collections.emptyList());
                    } else {
                        throw new IllegalArgumentException("Filtros de busca para acorde inválidos. Especifique 'id' OU 'nome' e 'tipoAcorde'.");
                    }
                
                case "escala":
                    if (filtros.containsKey("id")) {
                        int idEscala = Integer.parseInt(filtros.get("id"));
                        return Collections.singletonList(servicoBusca.buscarEscala(idEscala));
                    } else if (filtros.containsKey("nome")) {
                        String nomeEscala = filtros.get("nome");
                        return servicoBusca.buscarEscalaPorNome(nomeEscala);
                    } else {
                        throw new IllegalArgumentException("Filtro de busca para escala inválido. Especifique 'id' ou 'nome'.");
                    }

                case "entidademusical":
                    if (!filtros.containsKey("id")) {
                        throw new IllegalArgumentException("ID da entidade musical não fornecido para busca por ID.");
                    }
                    int idEntidade = Integer.parseInt(filtros.get("id"));
                    return Collections.singletonList(servicoBusca.buscarEntidadeMusical(idEntidade));

                case "musica":
                    if (!filtros.containsKey("query")) {
                        throw new IllegalArgumentException("Termo de busca (query) para música não fornecido.");
                    }
                    String query = filtros.get("query");
                    return servicoBusca.buscarMusica(query);

                default:
                    System.err.println("CONTROLLER: Tipo de busca desconhecido: '" + tipoBusca + "'");
                    return Collections.emptyList();
            }
        } catch (NumberFormatException e) {
            System.err.println("CONTROLLER: Erro - O ID fornecido para a busca não é um número válido. Detalhe: " + e.getMessage());
            return Collections.emptyList();
        } catch (IllegalArgumentException e) {
            System.err.println("CONTROLLER: Erro nos filtros de busca: " + e.getMessage());
            return Collections.emptyList();
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro durante a busca: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}