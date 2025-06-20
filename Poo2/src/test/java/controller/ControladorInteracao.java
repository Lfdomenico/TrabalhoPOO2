package controller;

import model.ElementoMusical;
import service.*; // Importando todos os nossos serviços

import javax.sound.midi.MidiDevice;
import java.util.Map;
import java.util.Scanner;
import model.IElementoSonoro;
import model.Progressao;

/**
 * Controller central que lida com interações complexas do usuário,
 * orquestrando múltiplos serviços para realizar uma única ação.
 */
public class ControladorInteracao {

    // Múltiplas dependências, uma para cada responsabilidade
    private final ServicoFavoritos servicoFavoritos;
    private final ServicoAnotacao servicoAnotacao;
    private final ServicoAudio servicoAudio;
    private final ServicoMidi servicoMidi;
    private final ServicoBusca servicoBusca;

    public ControladorInteracao(ServicoFavoritos servicoFavoritos, ServicoAnotacao servicoAnotacao,
                                ServicoAudio servicoAudio, ServicoMidi servicoMidi, ServicoBusca servicoBusca) {
        this.servicoFavoritos = servicoFavoritos;
        this.servicoAnotacao = servicoAnotacao;
        this.servicoAudio = servicoAudio;
        this.servicoMidi = servicoMidi;
        this.servicoBusca = servicoBusca;
    }

    /**
     * Lida com a ação de favoritar ou desfavoritar um item.
     * @param idElemento O ID do elemento a ser modificado.
     */
    public void lidarComToggleFavorito(int idElemento) {
        System.out.println("CONTROLLER: Recebida requisição para 'toggle' favorito do elemento " + idElemento);
        try {
            ElementoMusical elemento = servicoBusca.buscarEntidadeMusical(idElemento);
            if (servicoFavoritos.ehFavorito(elemento)) {
                servicoFavoritos.removerFavorito(elemento);
                System.out.println("CONTROLLER: Elemento '" + elemento.getNome() + "' removido dos favoritos.");
            } else {
                servicoFavoritos.adicionarFavorito(elemento);
                System.out.println("CONTROLLER: Elemento '" + elemento.getNome() + "' adicionado aos favoritos.");
            }
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro ao favoritar/desfavoritar: " + e.getMessage());
        }
    }

    /**
     * Lida com a ação de tocar um elemento sonoro.
     * @param idElemento O ID do elemento a ser tocado.
     */
    public void lidarComTocarElemento(int idElemento) {
        System.out.println("CONTROLLER: Recebida requisição para tocar elemento " + idElemento);
        try {
            ElementoMusical elemento = servicoBusca.buscarEntidadeMusical(idElemento);

            // --- LÓGICA DE VERIFICAÇÃO DE TIPO ---
            if (elemento instanceof IElementoSonoro) {
                // Se o elemento é um Acorde ou Escala (que implementam IElementoSonoro)
                servicoAudio.reproduzir((IElementoSonoro) elemento);
            } else if (elemento instanceof Progressao) {
                // Se o elemento é uma Progressão
                servicoAudio.reproduzir((Progressao) elemento);
            } else {
                // Se for um tipo que não pode ser tocado
                System.err.println("CONTROLLER: O elemento '" + elemento.getNome() + "' não pode ser reproduzido.");
            }
            // ------------------------------------

        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro ao tentar tocar elemento: " + e.getMessage());
        }
    }

    /**
     * Inicia a escuta de um dispositivo MIDI.
     */
    public void iniciarEscutaMidi() {
        System.out.println("CONTROLLER: Iniciando escuta MIDI...");
        Map<String, MidiDevice.Info> dispositivos = servicoMidi.listarDispositivosDeEntrada();

        if (dispositivos.isEmpty()) {
            System.out.println("CONTROLLER: Nenhum dispositivo MIDI de entrada encontrado.");
            return;
        }
        
        // Conecta ao primeiro dispositivo encontrado (em uma app real, o usuário escolheria)
        MidiDevice.Info dispositivoInfo = dispositivos.values().iterator().next();
        if (servicoMidi.conectarDispositivo(dispositivoInfo)) {
            servicoMidi.onNotaRecebida(nota -> {
                System.out.println("CONTROLLER (via MIDI): Nota recebida -> " + nota.getNomeCompleto());
                // Poderia, por exemplo, usar o ServicoBusca para ver se a nota pertence a um acorde
            });
            System.out.println("CONTROLLER: Escuta MIDI ativa. Toque no seu dispositivo.");
        } else {
            System.err.println("CONTROLLER: Falha ao conectar ao dispositivo MIDI.");
        }
    }
    
    /**
     * Para a escuta do dispositivo MIDI, liberando o recurso.
     */
    public void pararEscutaMidi() {
        System.out.println("CONTROLLER: Parando escuta MIDI...");
        servicoMidi.close();
    }
    
    // lidarComCriacaoAnotacao e outros métodos seguiriam o mesmo padrão...
}