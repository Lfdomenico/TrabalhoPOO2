package controller;

import model.Anotacao;
import service.ServicoAnotacao;
import service.ServicoBusca;

public class ControladorAnotacao {

    private final ServicoAnotacao servicoAnotacao;
    private final ServicoBusca servicoBusca;
    public ControladorAnotacao(ServicoAnotacao servicoAnotacao, ServicoBusca servicoBusca) {
        this.servicoAnotacao = servicoAnotacao;
        this.servicoBusca = servicoBusca;
    }

    public Anotacao lidarComCriacao(String texto) {
        System.out.println("CONTROLLER ANOTAÇÃO: Recebida requisição para criar anotação.");
        try {
            Anotacao novaAnotacao = servicoAnotacao.criarAnotacao(texto);
            System.out.println("CONTROLLER ANOTAÇÃO: Sucesso! Anotação criada com ID: " + novaAnotacao.getId());
            return novaAnotacao;
        } catch (Exception e) {
            System.err.println("CONTROLLER ANOTAÇÃO: Erro - " + e.getMessage());
            return null;
        }
    }

    public void lidarComEdicao(int idAnotacao, String novoTexto) {
        System.out.println("CONTROLLER ANOTAÇÃO: Recebida requisição para editar anotação " + idAnotacao);
        try {
            servicoAnotacao.editarTextoAnotacao(idAnotacao, novoTexto);
            System.out.println("CONTROLLER ANOTAÇÃO: Sucesso! Anotação editada.");
        } catch (Exception e) {
            System.err.println("CONTROLLER ANOTAÇÃO: Erro - " + e.getMessage());
        }
    }

    public void lidarComAssociacao(int idAnotacao, Integer idElemento) {
        String acao = (idElemento != null) ? "associar ao elemento " + idElemento : "desassociar";
        System.out.println("CONTROLLER ANOTAÇÃO: Recebida requisição para " + acao + " a anotação " + idAnotacao);
        try {
            servicoAnotacao.associarAnotacao(idAnotacao, idElemento);
            System.out.println("CONTROLLER ANOTAÇÃO: Sucesso! Associação modificada.");
        } catch (Exception e) {
            System.err.println("CONTROLLER ANOTAÇÃO: Erro - " + e.getMessage());
        }
    }

    public void lidarComExclusao(int idAnotacao) {
        System.out.println("CONTROLLER ANOTAÇÃO: Recebida requisição para excluir anotação " + idAnotacao);
        try {
            servicoAnotacao.excluirAnotacao(idAnotacao);
            System.out.println("CONTROLLER ANOTAÇÃO: Sucesso! Anotação excluída.");
        } catch (Exception e) {
            System.err.println("CONTROLLER ANOTAÇÃO: Erro - " + e.getMessage());
        }
    }
}