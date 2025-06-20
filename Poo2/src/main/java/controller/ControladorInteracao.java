package controller;

import model.ElementoMusical;
import service.*;
import javax.sound.midi.MidiDevice;
import java.util.Map;
import model.Anotacao;
import model.IElementoSonoro;
import model.Progressao;

public class ControladorInteracao {
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

    public void lidarComTocarElemento(int idElemento) {
        System.out.println("CONTROLLER: Recebida requisição para tocar elemento " + idElemento);
        try {
            ElementoMusical elemento = servicoBusca.buscarEntidadeMusical(idElemento);

            if (elemento instanceof IElementoSonoro) {
                servicoAudio.reproduzir((IElementoSonoro) elemento);
            } else if (elemento instanceof Progressao) {
                servicoAudio.reproduzir((Progressao) elemento);
            } else {
                System.err.println("CONTROLLER: O elemento '" + elemento.getNome() + "' não pode ser reproduzido.");
            }

        } catch (RuntimeException e) {
            System.err.println("CONTROLLER: Erro ao tentar tocar elemento: " + e.getMessage());
        }
    }

    public void iniciarEscutaMidi() {
        System.out.println("CONTROLLER: Iniciando escuta MIDI...");
        Map<String, MidiDevice.Info> dispositivos = servicoMidi.listarDispositivosDeEntrada();

        if (dispositivos.isEmpty()) {
            System.out.println("CONTROLLER: Nenhum dispositivo MIDI de entrada encontrado.");
            return;
        }

        MidiDevice.Info dispositivoInfo = dispositivos.values().iterator().next();
        if (servicoMidi.conectarDispositivo(dispositivoInfo)) {
            servicoMidi.onNotaRecebida(nota -> {
                System.out.println("CONTROLLER (via MIDI): Nota recebida -> " + nota.getNomeCompleto());
            });
            System.out.println("CONTROLLER: Escuta MIDI ativa. Toque no seu dispositivo.");
        } else {
            System.err.println("CONTROLLER: Falha ao conectar ao dispositivo MIDI.");
        }
    }

    public void pararEscutaMidi() {
        System.out.println("CONTROLLER: Parando escuta MIDI...");
        servicoMidi.close();
    }
    
    public void lidarComCriacaoAnotacao(String texto, Integer idElementoAssociado) {
        System.out.println("CONTROLLER: Recebida requisição para criar anotação.");
        try {
            Anotacao novaAnotacao = servicoAnotacao.criarAnotacao(texto);
            System.out.println("CONTROLLER: Anotação criada com ID: " + novaAnotacao.getId());
            
            if (idElementoAssociado != null) {
                servicoAnotacao.associarAnotacao(novaAnotacao.getId(), idElementoAssociado);
                System.out.println("CONTROLLER: Anotação associada ao elemento " + idElementoAssociado);
            }
            System.out.println("CONTROLLER: Operação concluída com sucesso.");
        } catch (Exception e) {
            System.err.println("CONTROLLER: Erro - " + e.getMessage());
        }
    }

    public void lidarComEdicaoAnotacao(int idAnotacao, String novoTexto) {
        System.out.println("CONTROLLER: Recebida requisição para editar anotação " + idAnotacao);
        try {
            servicoAnotacao.editarTextoAnotacao(idAnotacao, novoTexto);
            System.out.println("CONTROLLER: Sucesso! Anotação editada.");
        } catch (Exception e) {
            System.err.println("CONTROLLER: Erro - " + e.getMessage());
        }
    }
    
    public void lidarComExclusaoAnotacao(int idAnotacao) {
        System.out.println("CONTROLLER: Recebida requisição para excluir anotação " + idAnotacao);
        try {
            servicoAnotacao.excluirAnotacao(idAnotacao);
            System.out.println("CONTROLLER: Sucesso! Anotação excluída.");
        } catch (Exception e) {
            System.err.println("CONTROLLER: Erro - " + e.getMessage());
        }
    }
}