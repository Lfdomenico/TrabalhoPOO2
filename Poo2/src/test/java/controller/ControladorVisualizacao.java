package controller;

import model.Anotacao;
import model.ElementoMusical;
import service.ServicoAnotacao;
import service.ServicoBusca;

import java.util.List;

/**
 * Controller responsável por preparar os dados para a visualização
 * detalhada de um elemento musical e suas informações associadas.
 */
public class ControladorVisualizacao {

    private final ServicoBusca servicoBusca;
    private final ServicoAnotacao servicoAnotacao;
    
    // Supondo que a View seja injetada para que o Controller possa atualizá-la.
    // private final PainelVisualizacao painelVisualizacao; 

    public ControladorVisualizacao(ServicoBusca servicoBusca, ServicoAnotacao servicoAnotacao) {
        this.servicoBusca = servicoBusca;
        this.servicoAnotacao = servicoAnotacao;
    }

    /**
     * Lida com a requisição do usuário para exibir a visualização completa
     * de um elemento musical.
     * @param idElemento O ID do elemento a ser exibido.
     */
    public void exibirElementoCompleto(int idElemento) {
        System.out.println("CONTROLLER: Recebida requisição para exibir detalhes do elemento " + idElemento);
        
        try {
            // 1. Busca a entidade principal
            ElementoMusical elemento = servicoBusca.buscarEntidadeMusical(idElemento);
            
            // 2. Busca os dados relacionados (anotações)
            List<Anotacao> anotacoes = servicoAnotacao.buscarAnotacoesPorEntidade(idElemento);
            
            System.out.println("CONTROLLER: Dados prontos para a view.");
            
            // 3. Em uma aplicação real, passaria os dados para a View renderizar
            System.out.println("\n--- DADOS ENVIADOS PARA A VIEW ---");
            System.out.println("Elemento: " + elemento.getNome() + " (ID: " + elemento.getId() + ")");
            System.out.println("Info Complementar: " + elemento.getInfoComplementar());
            System.out.println("Anotações Associadas (" + anotacoes.size() + "):");
            anotacoes.forEach(a -> System.out.println("- " + a.getTexto()));
            System.out.println("--------------------------------\n");

        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro ao buscar dados para visualização: " + e.getMessage());
            // Em uma aplicação real, exibiria uma mensagem de erro na View.
        }
    }
}