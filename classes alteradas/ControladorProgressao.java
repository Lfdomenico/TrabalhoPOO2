package controller;

import model.Progressao;
import service.ServicoProgressao;
import java.io.IOException;

public class ControladorProgressao {

    private final ServicoProgressao servicoProgressao;

    public ControladorProgressao(ServicoProgressao servicoProgressao) {
        this.servicoProgressao = servicoProgressao;
    }

    public void lidarComCriacao(String nome) {
        System.out.println("CONTROLLER: Recebida requisição para criar progressão com nome: '" + nome + "'");
        try {
            Progressao novaProgressao = servicoProgressao.criarProgressao(nome);
            System.out.println("CONTROLLER: Sucesso! Progressão criada com ID " + novaProgressao.getId() + ". Notificando a View para atualizar...");
        } catch (IllegalArgumentException e) {
            System.err.println("CONTROLLER: Erro! " + e.getMessage() + ". Exibindo mensagem de erro para o usuário.");
        }
    }

    public void lidarComAdicaoAcorde(int idProgressao, int idAcorde, int posicao) {
        System.out.println("CONTROLLER: Recebida requisição para adicionar acorde " + idAcorde + " na progressão " + idProgressao);
        try {
            servicoProgressao.adicionarAcorde(idProgressao, idAcorde, posicao);
            System.out.println("CONTROLLER: Sucesso! Acorde adicionado. Notificando a View...");
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro! " + e.getMessage() + ". Exibindo mensagem de erro para o usuário.");
        }
    }

    public void lidarComRenomeacao(int idProgressao, String novoNome) {
        System.out.println("CONTROLLER: Recebida requisição para renomear a progressão " + idProgressao);
        try {
            servicoProgressao.renomearProgressao(idProgressao, novoNome);
            System.out.println("CONTROLLER: Sucesso! Progressão renomeada. Notificando a View...");
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro! " + e.getMessage());
        }
    }

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
            return null;
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
    
    public void lidarComExportacaoParaArquivo(int idProgressao, String caminhoArquivo) {
        System.out.println("CONTROLLER: Recebida requisição para exportar para arquivo: " + caminhoArquivo);
        try {
            servicoProgressao.exportarParaArquivo(idProgressao, caminhoArquivo);
            System.out.println("CONTROLLER: Sucesso! Arquivo de exportação gerado.");
        } catch (IOException e) {
            System.err.println("CONTROLLER: Erro de arquivo! Não foi possível escrever em " + caminhoArquivo);
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro de negócio! " + e.getMessage());
        }
    }
    
    public void lidarComImportacaoDeArquivo(String nomeProgressao, String caminhoArquivo) {
        System.out.println("CONTROLLER: Recebida requisição para importar do arquivo: " + caminhoArquivo);
        try {
            Progressao p = servicoProgressao.importarDeArquivo(nomeProgressao, caminhoArquivo);
            System.out.println("CONTROLLER: Sucesso! Progressão importada com ID: " + p.getId());
        } catch (IOException e) {
            System.err.println("CONTROLLER: Erro de arquivo! Não foi possível ler de " + caminhoArquivo);
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro de negócio! " + e.getMessage());
        }
    }
}