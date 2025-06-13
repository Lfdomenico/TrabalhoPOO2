package controller;

import service.ServicoAudio;
import service.ServicoMidi;
import javax.sound.midi.MidiDevice;
import java.util.Map;

public class ControladorMidi {

    private final ServicoMidi servicoMidi;
    private final ServicoAudio servicoAudio;

    public ControladorMidi(ServicoMidi servicoMidi, ServicoAudio servicoAudio) {
        this.servicoMidi = servicoMidi;
        this.servicoAudio = servicoAudio;
    }

    public void iniciarMonitoramento() {
        System.out.println("CONTROLLER MIDI: Iniciando monitoramento...");
        Map<String, MidiDevice.Info> dispositivos = servicoMidi.listarDispositivosDeEntrada();
        if (dispositivos.isEmpty()) {
            System.out.println("CONTROLLER MIDI: Nenhum dispositivo encontrado.");
            return;
        }
        MidiDevice.Info primeiro = dispositivos.values().iterator().next();
        if (servicoMidi.conectarDispositivo(primeiro)) {
            servicoMidi.onNotaRecebida(servicoAudio::pressionarNota);
            servicoMidi.onNotaFinalizada((nota, duracao) -> servicoAudio.soltarNota(nota));
            System.out.println("CONTROLLER MIDI: Monitoramento ativo.");
        }
    }

    public void pararMonitoramento() {
        System.out.println("CONTROLLER MIDI: Parando monitoramento...");
        servicoMidi.close();
    }
}