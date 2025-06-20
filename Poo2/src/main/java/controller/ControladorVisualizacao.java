package controller;

import model.Anotacao;
import model.ElementoMusical;
import service.ServicoAnotacao;
import service.ServicoBusca;
import java.util.List;

public class ControladorVisualizacao {

    private final ServicoBusca servicoBusca;
    private final ServicoAnotacao servicoAnotacao;
    

    public ControladorVisualizacao(ServicoBusca servicoBusca, ServicoAnotacao servicoAnotacao) {
        this.servicoBusca = servicoBusca;
        this.servicoAnotacao = servicoAnotacao;
    }

    public void exibirElementoCompleto(int idElemento) {
        System.out.println("CONTROLLER: Recebida requisição para exibir detalhes do elemento " + idElemento);
        
        try {
            ElementoMusical elemento = servicoBusca.buscarEntidadeMusical(idElemento);
            
            List<Anotacao> anotacoes = servicoAnotacao.buscarAnotacoesPorEntidade(idElemento);
            
            System.out.println("CONTROLLER: Dados prontos para a view.");
            
            System.out.println("\n--- DADOS ENVIADOS PARA A VIEW ---");
            System.out.println("Elemento: " + elemento.getNome() + " (ID: " + elemento.getId() + ")");
            System.out.println("Info Complementar: " + elemento.getInfoComplementar());
            System.out.println("Anotações Associadas (" + anotacoes.size() + "):");
            anotacoes.forEach(a -> System.out.println("- " + a.getTexto()));
            System.out.println("--------------------------------\n");

        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro ao buscar dados para visualização: " + e.getMessage());
        }
    }
}