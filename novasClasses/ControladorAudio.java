package controller;

import model.ElementoMusical;
import model.IElementoSonoro;
import model.Progressao;
import service.ServicoAudio;
import service.ServicoBusca;

public class ControladorAudio {

    private final ServicoAudio servicoAudio;
    private final ServicoBusca servicoBusca;

    public ControladorAudio(ServicoAudio servicoAudio, ServicoBusca servicoBusca) {
        this.servicoAudio = servicoAudio;
        this.servicoBusca = servicoBusca;
    }

    public void lidarComTocarElemento(int idElemento) {
        System.out.println("CONTROLLER AUDIO: Recebida requisição para tocar elemento " + idElemento);
        try {
            ElementoMusical elemento = servicoBusca.buscarEntidadeMusical(idElemento);

            if (elemento instanceof IElementoSonoro) {
                servicoAudio.reproduzir((IElementoSonoro) elemento);
            } else if (elemento instanceof Progressao) {
                servicoAudio.reproduzir((Progressao) elemento);
            } else {
                System.err.println("CONTROLLER AUDIO: O elemento '" + elemento.getNome() + "' não pode ser reproduzido.");
            }
        } catch (RuntimeException e) {
            System.err.println("CONTROLLER AUDIO: Erro ao tentar tocar elemento: " + e.getMessage());
        }
    }
}