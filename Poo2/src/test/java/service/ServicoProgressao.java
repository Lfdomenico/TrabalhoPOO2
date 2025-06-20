package service;

import model.Acorde;
import model.Progressao;
import repository.AcordeRepositorio;
import repository.ProgressaoRepositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import model.Nota;

/**
 * Contém a lógica de negócio para criar e manipular progressões de acordes.
 * Orquestra as operações entre o modelo e a camada de persistência.
 */
public class ServicoProgressao{

    private final ProgressaoRepositorio progressaoRepository;
    private final AcordeRepositorio acordeRepository;

    // Injeção de Dependência via construtor
    public ServicoProgressao(ProgressaoRepositorio progressaoRepository, AcordeRepositorio acordeRepository) {
        this.progressaoRepository = progressaoRepository;
        this.acordeRepository = acordeRepository;
    }

    public Progressao criarProgressao(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da progressão não pode ser vazio.");
        }
        // Cria uma nova progressão com um ID único.
        Progressao novaProgressao = new Progressao(nome); // ID será gerenciado pelo save
        return progressaoRepository.save(novaProgressao);
    }

    public List<Progressao> listarProgressoes() {
        return progressaoRepository.findAll();
    }

    public Progressao carregarProgressao(int idProgressao) {
        return progressaoRepository.findById(idProgressao)
                .orElseThrow(() -> new RuntimeException("Progressão não encontrada com o ID: " + idProgressao));
    }

    public void excluirProgressao(int idProgressao) {
        // Verifica se a progressão existe antes de tentar deletar
        if (!progressaoRepository.findById(idProgressao).isPresent()) {
            throw new RuntimeException("Progressão não encontrada com o ID: " + idProgressao);
        }
        progressaoRepository.deleteById(idProgressao);
    }

    public Progressao renomearProgressao(int idProgressao, String novoNome) {
        Progressao progressao = carregarProgressao(idProgressao);
        progressao.setNome(novoNome);
        return progressaoRepository.save(progressao);
    }

    public Progressao adicionarAcorde(int idProgressao, int idAcorde, int posicao) {
        // 1. Carrega as entidades do repositório
        Progressao progressao = carregarProgressao(idProgressao);
        Acorde acorde = acordeRepository.findById(idAcorde)
                .orElseThrow(() -> new RuntimeException("Acorde não encontrado com o ID: " + idAcorde));
        
        // 2. Usa o método do modelo para manipular o estado
        progressao.inserirAcorde(acorde, posicao);
        
        // 3. Salva o estado atualizado da progressão
        return progressaoRepository.save(progressao);
    }
    
    // Sobrecarga para adicionar no final
    public Progressao adicionarAcorde(int idProgressao, int idAcorde) {
        Progressao progressao = carregarProgressao(idProgressao);
        return adicionarAcorde(idProgressao, idAcorde, progressao.getAcordes().size());
    }

    public Progressao removerAcorde(int idProgressao, int posicao) {
        Progressao progressao = carregarProgressao(idProgressao);
        progressao.removerAcorde(posicao);
        return progressaoRepository.save(progressao);
    }

    public String exportarProgressao(int idProgressao) {
        // 1. Carrega a progressão como antes
        Progressao progressao = carregarProgressao(idProgressao);

        // 2. Usa um StringBuilder para construir a string CSV eficientemente
        StringBuilder csvBuilder = new StringBuilder();

        // 3. Adiciona a linha do cabeçalho
        csvBuilder.append("id_acorde,nome_acorde,tonica,notas\n");

        // 4. Itera sobre cada acorde na progressão para criar uma linha no CSV
        for (Acorde acorde : progressao.getAcordes()) {
            // Formata a lista de notas do acorde em uma única string separada por ";"
            String notasFormatadas = acorde.getNotas().stream()
                                         .map(Nota::getNomeCompleto)
                                         .collect(Collectors.joining(";"));

            // Adiciona a linha formatada ao StringBuilder
            csvBuilder.append(String.format("%d,%s,%s,\"%s\"\n",
                    acorde.getId(),
                    acorde.getNome(),
                    acorde.getTonica().getNomeCompleto(),
                    notasFormatadas
            ));
        }

        // 5. Retorna a string CSV completa
        return csvBuilder.toString();
    }

    public Progressao importarProgressao(String nomeNovaProgressao, String dadosCsv) {
        // 1. Validação básica da entrada
        if (dadosCsv == null || dadosCsv.trim().isEmpty()) {
            throw new IllegalArgumentException("Os dados CSV para importação não podem ser vazios.");
        }

        // 2. Cria a nova progressão que será populada
        Progressao novaProgressao = criarProgressao(nomeNovaProgressao);

        // 3. Processa o conteúdo do CSV, dividindo em linhas
        String[] linhas = dadosCsv.split("\n");

        // 4. Itera sobre as linhas, pulando o cabeçalho (que é a linha 0)
        for (int i = 1; i < linhas.length; i++) {
            String linha = linhas[i].trim();
            if (linha.isEmpty()) {
                continue; // Pula linhas em branco
            }

            // Divide a linha em colunas pela vírgula
            String[] colunas = linha.split(",");
            if (colunas.length < 1) {
                System.err.println("AVISO: Linha mal formada no CSV, pulando: " + linha);
                continue;
            }

            try {
                // 5. Extrai o ID do acorde da primeira coluna
                int idAcorde = Integer.parseInt(colunas[0]);

                // 6. Busca o acorde no repositório usando o ID
                Optional<Acorde> acordeEncontrado = acordeRepository.findById(idAcorde);

                if (acordeEncontrado.isPresent()) {
                    // 7. Se encontrado, adiciona à nova progressão
                    // (Supondo que Progressao tem um método para adicionar ao final)
                    novaProgressao.adicionarAcorde(acordeEncontrado.get());
                } else {
                    // 8. Se um acorde do CSV não for encontrado no "banco de dados",
                    // ele é ignorado e um aviso é exibido.
                    System.err.println("AVISO: Acorde com ID " + idAcorde + " não encontrado. Ele não será adicionado à progressão.");
                }
            } catch (NumberFormatException e) {
                System.err.println("AVISO: ID de acorde inválido na linha, pulando: '" + linha + "'");
            }
        }

        // 9. Salva a progressão final, agora populada, no repositório.
        return progressaoRepository.save(novaProgressao);
    }
}