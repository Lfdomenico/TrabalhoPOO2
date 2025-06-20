package service;

import model.Anotacao;
import model.ElementoMusical;
import repository.AnotacaoRepositorio;
import repository.ElementoMusicalRepositorio;
import java.util.List;

public class ServicoAnotacao {

    private final AnotacaoRepositorio anotacaoRepository;
    private final ElementoMusicalRepositorio elementoMusicalRepository;

    public ServicoAnotacao(AnotacaoRepositorio anotacaoRepository, ElementoMusicalRepositorio elementoMusicalRepository) {
        this.anotacaoRepository = anotacaoRepository;
        this.elementoMusicalRepository = elementoMusicalRepository;
    }

    public Anotacao criarAnotacao(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("O texto da anotação não pode ser vazio.");
        }

        Anotacao novaAnotacao = new Anotacao(texto, null);
        return anotacaoRepository.save(novaAnotacao);
    }

    public Anotacao associarAnotacao(int idAnotacao, Integer idElemento) { //int como objeto
        Anotacao anotacao = anotacaoRepository.findById(idAnotacao)
                .orElseThrow(() -> new RuntimeException("Anotação não encontrada com o ID: " + idAnotacao));

        ElementoMusical elementoParaAssociar = null;
        
        if (idElemento != null) {
            elementoParaAssociar = elementoMusicalRepository.findById(idElemento)
                    .orElseThrow(() -> new RuntimeException("Elemento Musical não encontrado com o ID: " + idElemento));
        }

        anotacao.associarElemento(elementoParaAssociar);

        return anotacaoRepository.save(anotacao);
    }

    public List<Anotacao> buscarAnotacoesPorEntidade(int idElemento) {
        return anotacaoRepository.findByElementoId(idElemento);
    }

    public List<Anotacao> buscarTodasAnotacoes() {
        return anotacaoRepository.findAll();
    }
   
    public Anotacao editarTextoAnotacao(int idAnotacao, String novoTexto) {
        Anotacao anotacaoParaEditar = anotacaoRepository.findById(idAnotacao)
                .orElseThrow(() -> new RuntimeException("Anotação não encontrada!"));

        anotacaoParaEditar.setTexto(novoTexto);

        Anotacao anotacaoAtualizada = anotacaoRepository.save(anotacaoParaEditar);

        return anotacaoAtualizada;
    }
    
    public void excluirAnotacao(int idAnotacao) {
        boolean existe = anotacaoRepository.findById(idAnotacao).isPresent();
        
        if (!existe) {
            throw new RuntimeException("Não é possível excluir. Anotação com ID " + idAnotacao + " não encontrada.");
        }
        
        anotacaoRepository.deleteById(idAnotacao);
    }
}