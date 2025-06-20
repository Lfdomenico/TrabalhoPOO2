package controller;

import model.Progressao;
import service.ServicoProgressao;

/**
 * Controller responsável por receber as ações do usuário relacionadas a
 * progressões e delegá-las para a camada de serviço apropriada.
 */
public class ControladorProgressao {

    private final ServicoProgressao servicoProgressao;

    public ControladorProgressao(ServicoProgressao servicoProgressao) {
        this.servicoProgressao = servicoProgressao;
    }

    /**
     * Lida com a requisição do usuário para criar uma nova progressão.
     * @param nome O nome da progressão fornecido pelo usuário.
     */
    public void lidarComCriacao(String nome) {
        System.out.println("CONTROLLER: Recebida requisição para criar progressão com nome: '" + nome + "'");
        try {
            Progressao novaProgressao = servicoProgressao.criarProgressao(nome);
            System.out.println("CONTROLLER: Sucesso! Progressão criada com ID " + novaProgressao.getId() + ". Notificando a View para atualizar...");
            // Em uma aplicação real, aqui você atualizaria a interface do usuário.
        } catch (IllegalArgumentException e) {
            System.err.println("CONTROLLER: Erro! " + e.getMessage() + ". Exibindo mensagem de erro para o usuário.");
            // Exibiria um pop-up de erro na interface.
        }
    }

    /**
     * Lida com a requisição do usuário para adicionar um acorde a uma progressão.
     * @param idProgressao O ID da progressão a ser modificada.
     * @param idAcorde O ID do acorde a ser adicionado.
     * @param posicao A posição onde o acorde deve ser inserido.
     */
    public void lidarComAdicaoAcorde(int idProgressao, int idAcorde, int posicao) {
        System.out.println("CONTROLLER: Recebida requisição para adicionar acorde " + idAcorde + " na progressão " + idProgressao);
        try {
            servicoProgressao.adicionarAcorde(idProgressao, idAcorde, posicao);
            System.out.println("CONTROLLER: Sucesso! Acorde adicionado. Notificando a View...");
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro! " + e.getMessage() + ". Exibindo mensagem de erro para o usuário.");
        }
    }

    /**
     * Lida com a requisição para renomear uma progressão.
     * @param idProgressao O ID da progressão.
     * @param novoNome O novo nome para a progressão.
     */
    public void lidarComRenomeacao(int idProgressao, String novoNome) {
        System.out.println("CONTROLLER: Recebida requisição para renomear a progressão " + idProgressao);
        try {
            servicoProgressao.renomearProgressao(idProgressao, novoNome);
            System.out.println("CONTROLLER: Sucesso! Progressão renomeada. Notificando a View...");
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro! " + e.getMessage());
        }
    }
    
    /**
     * Lida com a requisição para excluir uma progressão.
     * @param idProgressao O ID da progressão a ser excluída.
     */
    public void lidarComExclusao(int idProgressao) {
        System.out.println("CONTROLLER: Recebida requisição para excluir a progressão " + idProgressao);
        try {
            servicoProgressao.excluirProgressao(idProgressao);
            System.out.println("CONTROLLER: Sucesso! Progressão excluída. Notificando a View...");
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro! " + e.getMessage());
        }
    }

    public String lidarComExportacao(int idProgressao) {
        System.out.println("CONTROLLER: Recebida requisição para exportar progressão " + idProgressao);
        try {
            String dadosCsv = servicoProgressao.exportarProgressao(idProgressao);
            System.out.println("CONTROLLER: Sucesso! Dados gerados para exportação.");
            return dadosCsv;
        } catch (Exception e) {
            System.err.println("CONTROLLER: Erro ao exportar progressão: " + e.getMessage());
            return null; // Retorna nulo em caso de erro
        }
    }

    public Progressao lidarComImportacao(String nomeNovaProgressao, String dadosCsv) {
        System.out.println("CONTROLLER: Recebida requisição para importar progressão '" + nomeNovaProgressao + "'");
        try {
            Progressao progressaoImportada = servicoProgressao.importarProgressao(nomeNovaProgressao, dadosCsv);
            System.out.println("CONTROLLER: Sucesso! Progressão importada com ID " + progressaoImportada.getId());
            return progressaoImportada;
        } catch (Exception e) {
            System.err.println("CONTROLLER: Erro ao importar progressão: " + e.getMessage());
            return null;
        }
    }
    
}