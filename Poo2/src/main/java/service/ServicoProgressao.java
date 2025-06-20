package service;

import model.Acorde;
import model.Progressao;
import repository.AcordeRepositorio;
import repository.AcordeRepositorioMySQL;
import repository.ProgressaoRepositorio;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Nota;

public class ServicoProgressao {

    private final ProgressaoRepositorio progressaoRepository;
    private final AcordeRepositorio acordeRepository; 
    public ServicoProgressao(ProgressaoRepositorio progressaoRepository, AcordeRepositorio acordeRepository) {
        this.progressaoRepository = progressaoRepository;
        this.acordeRepository = acordeRepository;
    }

    public Progressao criarProgressao(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da progressão não pode ser vazio.");
        }

        Progressao novaProgressao = new Progressao(nome);
        return progressaoRepository.save(novaProgressao);
    }

    public List<Progressao> listarProgressoes() {
        return progressaoRepository.findAll();
    }
    
    public List<Progressao> buscarProgressaoPorNome(String termoBusca) {
        if (termoBusca == null || termoBusca.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return progressaoRepository.findAllByNome(termoBusca);
    }


    public Progressao carregarProgressao(int idProgressao) {
        return progressaoRepository.findById(idProgressao)
                .orElseThrow(() -> new RuntimeException("Progressão não encontrada com o ID: " + idProgressao));
    }

    public void excluirProgressao(int idProgressao) {
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
        Progressao progressao = carregarProgressao(idProgressao);
        Acorde acorde = acordeRepository.findById(idAcorde)
                .orElseThrow(() -> new RuntimeException("Acorde não encontrado com o ID: " + idAcorde));
        
        progressao.inserirAcorde(acorde, posicao);
        
        return progressaoRepository.save(progressao);
    }
    
    public Progressao adicionarAcorde(int idProgressao, int idAcorde) {
        Progressao progressao = carregarProgressao(idProgressao);
        return adicionarAcorde(idProgressao, idAcorde, progressao.getAcordes().size());
    }

    public Progressao adicionarAcordePorCifraETipo(int idProgressao, String cifra, String tipo, int posicao) {
        Progressao progressao = carregarProgressao(idProgressao);

        Optional<Acorde> acordeOpt = ((AcordeRepositorioMySQL) acordeRepository).findByNomeAndTipo(cifra, tipo);
        
        if (acordeOpt.isEmpty()) {
            throw new RuntimeException("Acorde '" + cifra + " " + tipo + "' não encontrado para adicionar à progressão.");
        }
        
        progressao.inserirAcorde(acordeOpt.get(), posicao);
        return progressaoRepository.save(progressao);
    }
    
    public Progressao adicionarAcordePorCifraETipo(int idProgressao, String cifra, String tipo) {
        Progressao progressao = carregarProgressao(idProgressao);
        return adicionarAcordePorCifraETipo(idProgressao, cifra, tipo, progressao.getAcordes().size());
    }


    public Progressao removerAcorde(int idProgressao, int posicao) {
        Progressao progressao = carregarProgressao(idProgressao);
        progressao.removerAcorde(posicao);
        return progressaoRepository.save(progressao);
    }

    public String exportarProgressao(int idProgressao) {
        Progressao progressao = carregarProgressao(idProgressao);

        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("id_acorde,nome_acorde,tipo_acorde,tonica,notas\n"); 

        for (Acorde acorde : progressao.getAcordes()) {
            String notasFormatadas = acorde.getNotas().stream()
                                         .map(Nota::getNomeCompleto)
                                         .collect(Collectors.joining(";"));

            csvBuilder.append(String.format("%d,%s,%s,%s,\"%s\"\n",
                    acorde.getId(),
                    acorde.getNome(),
                    acorde.getTipo(), 
                    acorde.getTonica() != null ? acorde.getTonica().getNomeCompleto() : "N/A",
                    notasFormatadas
            ));
        }
        
        return csvBuilder.toString();
    }

    public Progressao importarProgressao(String nomeNovaProgressao, String dadosCsv) {
        if (dadosCsv == null || dadosCsv.trim().isEmpty()) {
            throw new IllegalArgumentException("Os dados CSV para importação não podem ser vazios.");
        }

        Progressao novaProgressao = criarProgressao(nomeNovaProgressao);

        String[] linhas = dadosCsv.split("\n");

        for (int i = 1; i < linhas.length; i++) { 
            String linha = linhas[i].trim();
            if (linha.isEmpty()) {
                continue;
            }

            String[] colunas = linha.split(",");
            if (colunas.length < 3) { 
                System.err.println("AVISO: Linha mal formada no CSV, pulando: " + linha);
                continue;
            }

            try {
                int idAcorde = Integer.parseInt(colunas[0]);


                Optional<Acorde> acordeEncontrado = acordeRepository.findById(idAcorde);

                if (acordeEncontrado.isPresent()) {
                    novaProgressao.adicionarAcorde(acordeEncontrado.get());
                } else {
                    System.err.println("AVISO: Acorde com ID " + idAcorde + " não encontrado. Ele não será adicionado à progressão.");
                }
            } catch (NumberFormatException e) {
                System.err.println("AVISO: ID de acorde inválido na linha, pulando: '" + linha + "'");
            }
        }
        
        return progressaoRepository.save(novaProgressao);
    }
}