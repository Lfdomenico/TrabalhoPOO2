package view;

import controller.*;
import model.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Representa a camada de View (Interface do Usuário) para uma aplicação de console.
 * É responsável por exibir menus, ler a entrada do usuário e delegar as ações
 * para as classes de Controller apropriadas.
 */
public class ConsoleView {

    private final Scanner scanner;
    // A View depende dos controllers para delegar as ações do usuário
    private final ControladorAnotacao ctrlAnotacao;
    private final ControladorAudio ctrlAudio;
    private final ControladorBusca ctrlBusca;
    private final ControladorFavoritos ctrlFavoritos;
    private final ControladorMidi ctrlMidi;
    private final ControladorProgressao ctrlProgressao;
    private final ControladorVisualizacao ctrlVisualizacao;

    public ConsoleView(ControladorAnotacao ca, ControladorAudio cAu, ControladorBusca cB, ControladorFavoritos cF,
                       ControladorMidi cM, ControladorProgressao cP, ControladorVisualizacao cV) {
        this.scanner = new Scanner(System.in);
        this.ctrlAnotacao = ca;
        this.ctrlAudio = cAu;
        this.ctrlBusca = cB;
        this.ctrlFavoritos = cF;
        this.ctrlMidi = cM;
        this.ctrlProgressao = cP;
        this.ctrlVisualizacao = cV;
    }

    /**
     * Inicia o loop principal da aplicação de console.
     */
    public void iniciar() {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: gerenciarBuscas(); break;
                    case 2: gerenciarProgressoes(); break;
                    case 3: gerenciarAnotacoes(); break;
                    case 4: gerenciarInteracoes(); break;
                    case 5: visualizarElemento(); break;
                    case 0: System.out.println("Saindo do sistema..."); break;
                    default: System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Erro: Por favor, digite um número válido.");
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Buscar (Acordes, Escalas, Músicas, etc.)");
        System.out.println("2. Gerenciar Progressões");
        System.out.println("3. Gerenciar Anotações");
        System.out.println("4. Ações Interativas (Tocar, Favoritar, MIDI)");
        System.out.println("5. Visualizar Detalhes de um Elemento");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void gerenciarBuscas() {
        System.out.print("Digite o tipo da busca (acorde, escala, musica, progressao): ");
        String tipo = scanner.nextLine();
        System.out.print("Digite o termo da busca (ID para acorde/escala, nome para os outros): ");
        String valor = scanner.nextLine();

        Map<String, String> filtros;
        if (tipo.equalsIgnoreCase("musica") || tipo.equalsIgnoreCase("progressao")) {
            filtros = Map.of("tipo", tipo, "nome", valor);
        } else {
            filtros = Map.of("tipo", tipo, "id", valor);
        }

        List<?> resultados = ctrlBusca.realizarBusca(filtros);
        System.out.println("--- Resultados da Busca (" + resultados.size() + " encontrados) ---");
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    private void gerenciarProgressoes() {
        System.out.println("\n--- Gerenciar Progressões ---");
        System.out.println("1. Criar Nova Progressão");
        System.out.println("2. Adicionar Acorde a uma Progressão");
        System.out.println("3. Exportar Progressão para Arquivo");
        System.out.println("4. Importar Progressão de Arquivo");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        int opcao = Integer.parseInt(scanner.nextLine());

        switch(opcao) {
            case 1:
                System.out.print("Digite o nome da nova progressão: ");
                String nome = scanner.nextLine();
                ctrlProgressao.lidarComCriacao(nome);
                break;
            case 2:
                // ... lógica para adicionar acorde ...
                break;
            case 3: // EXPORTAR
                System.out.print("Digite o ID da progressão para exportar: ");
                int idExportar = Integer.parseInt(scanner.nextLine());
                System.out.print("Digite o caminho completo para o arquivo (ex: progressao.txt): ");
                String caminhoExportar = scanner.nextLine();
                ctrlProgressao.lidarComExportacaoParaArquivo(idExportar, caminhoExportar);
                break;
            case 4: // IMPORTAR
                System.out.print("Digite o nome para a NOVA progressão a ser criada: ");
                String nomeImportar = scanner.nextLine();
                System.out.print("Digite o caminho completo do arquivo para importar: ");
                String caminhoImportar = scanner.nextLine();
                ctrlProgressao.lidarComImportacaoDeArquivo(nomeImportar, caminhoImportar);
                break;
            case 0:
                return;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void gerenciarAnotacoes() {
        System.out.print("Digite o texto da nova anotação: ");
        String texto = scanner.nextLine();
        Anotacao novaAnotacao = ctrlAnotacao.lidarComCriacao(texto);
        if (novaAnotacao != null) {
            System.out.print("Deseja associar a anotação a um elemento? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Digite o ID do Elemento Musical: ");
                int elementoId = Integer.parseInt(scanner.nextLine());
                ctrlAnotacao.lidarComAssociacao(novaAnotacao.getId(), elementoId);
            }
        }
    }

    private void gerenciarInteracoes() {
        System.out.println("\n--- Menu Interativo ---");
        System.out.println("1. Tocar Elemento por ID");
        System.out.println("2. Favoritar/Desfavoritar por ID");
        System.out.println("3. Ligar Monitor MIDI");
        System.out.print("Escolha uma opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        int idElemento;
        switch(opcao) {
            case 1:
                System.out.print("Digite o ID do Elemento Musical para TOCAR: ");
                idElemento = Integer.parseInt(scanner.nextLine());
                ctrlAudio.lidarComTocarElemento(idElemento);
                break;
            case 2:
                System.out.print("Digite o ID do Elemento Musical para FAVORITAR/DESFAVORITAR: ");
                idElemento = Integer.parseInt(scanner.nextLine());
                ctrlFavoritos.lidarComToggleFavorito(idElemento);
                break;
            case 3:
                ctrlMidi.iniciarMonitoramento();
                System.out.println("Monitor MIDI ativo. Pressione Enter para parar.");
                scanner.nextLine(); // Espera o usuário pressionar Enter
                ctrlMidi.pararMonitoramento();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void visualizarElemento() {
        System.out.print("Digite o ID do Elemento Musical para ver seus detalhes: ");
        int id = Integer.parseInt(scanner.nextLine());
        ctrlVisualizacao.exibirElementoCompleto(id);
    }
}