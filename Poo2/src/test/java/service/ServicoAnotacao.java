package service;

import model.Anotacao;
import model.ElementoMusical;
import repository.AnotacaoRepositorio;
import repository.ElementoMusicalRepositorio;

import java.util.Date;
import java.util.List;

/**
 * Contém a lógica de negócio para criar e gerenciar anotações e suas
 * associações com elementos musicais.
 */
public class ServicoAnotacao {

    private final AnotacaoRepositorio anotacaoRepository;
    private final ElementoMusicalRepositorio elementoMusicalRepository;

    // Injeção de Dependência via construtor
    public ServicoAnotacao(AnotacaoRepositorio anotacaoRepository, ElementoMusicalRepositorio elementoMusicalRepository) {
        this.anotacaoRepository = anotacaoRepository;
        this.elementoMusicalRepository = elementoMusicalRepository;
    }

    /**
     * Cria uma nova anotação, inicialmente não associada a nenhum elemento.
     * @param texto O conteúdo da anotação.
     * @return A anotação criada e salva.
     */
    public Anotacao criarAnotacao(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("O texto da anotação não pode ser vazio.");
        }
        // Assumindo que o construtor é Anotacao(String texto, ElementoMusical elemento)
        Anotacao novaAnotacao = new Anotacao(texto, null);
        return anotacaoRepository.save(novaAnotacao);
    }

    /**
     * Associa uma anotação existente a uma entidade musical.
     * @param idAnotacao O ID da anotação a ser modificada.
     * @param idElemento O ID do elemento musical ao qual a anotação será associada. Pode ser null para desassociar.
     * @return A anotação atualizada.
     */
    public Anotacao associarAnotacao(int idAnotacao, Integer idElemento) {
        // 1. Carrega a anotação do repositório
        Anotacao anotacao = anotacaoRepository.findById(idAnotacao)
                .orElseThrow(() -> new RuntimeException("Anotação não encontrada com o ID: " + idAnotacao));

        ElementoMusical elementoParaAssociar = null;
        
        // 2. Se um ID de elemento foi fornecido, busca o elemento
        if (idElemento != null) {
            elementoParaAssociar = elementoMusicalRepository.findById(idElemento)
                    .orElseThrow(() -> new RuntimeException("Elemento Musical não encontrado com o ID: " + idElemento));
        }

        // 3. Associa o elemento (ou null) à anotação
        anotacao.associarElemento(elementoParaAssociar);

        // 4. Salva o estado atualizado da anotação
        return anotacaoRepository.save(anotacao);
    }

    /**
     * Busca todas as anotações associadas a uma entidade musical específica.
     * @param idElemento O ID da entidade musical.
     * @return Uma lista de anotações.
     */
    public List<Anotacao> buscarAnotacoesPorEntidade(int idElemento) {
        return anotacaoRepository.findByElementoId(idElemento);
    }

    /**
     * Busca todas as anotações existentes no sistema.
     * @return Uma lista de todas as anotações.
     */
    public List<Anotacao> buscarTodasAnotacoes() {
        return anotacaoRepository.findAll();
    }
}