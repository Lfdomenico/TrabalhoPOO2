package service;

import model.Nota;
import javax.sound.midi.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ServicoMidi implements Receiver {

    private Consumer<Nota> onNotaRecebidaCallback;
    private BiConsumer<Nota, Long> onNotaFinalizadaCallback;

    private final Map<Integer, Long> notasAtivas = new ConcurrentHashMap<>();
    
    private MidiDevice dispositivoConectado;
    private Transmitter transmissorAtivo;

    public ServicoMidi() {
    }

    public Map<String, MidiDevice.Info> listarDispositivosDeEntrada() {
        Map<String, MidiDevice.Info> dispositivos = new HashMap<>();
        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getMaxTransmitters() != 0) {
                    dispositivos.put(info.getName() + " - " + info.getDescription(), info);
                }
            } catch (MidiUnavailableException e) {
            }
        }
        return dispositivos;
    }


    public boolean conectarDispositivo(MidiDevice.Info info) {
        close();
        try {
            this.dispositivoConectado = MidiSystem.getMidiDevice(info);
            this.dispositivoConectado.open();
            this.transmissorAtivo = this.dispositivoConectado.getTransmitter();
            this.transmissorAtivo.setReceiver(this);
            
            System.out.println("Conectado com sucesso ao dispositivo: " + info.getName());
            return true;
        } catch (MidiUnavailableException e) {
            System.err.println("Não foi possível conectar ao dispositivo: " + info.getName());
            e.printStackTrace();
            return false;
        }
    }

    public void onNotaRecebida(Consumer<Nota> callback) {
        this.onNotaRecebidaCallback = callback;
    }
    
    public void onNotaFinalizada(BiConsumer<Nota, Long> callback) {
        this.onNotaFinalizadaCallback = callback;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) message;
            int valorMidi = sm.getData1();
            int velocidade = sm.getData2();

            if (sm.getCommand() == ShortMessage.NOTE_ON && velocidade > 0) {
                notasAtivas.put(valorMidi, System.currentTimeMillis());
                if (onNotaRecebidaCallback != null) {
                    onNotaRecebidaCallback.accept(Nota.fromMidiValue(valorMidi));
                }
            } else if (sm.getCommand() == ShortMessage.NOTE_OFF || (sm.getCommand() == ShortMessage.NOTE_ON && velocidade == 0)) {
                if (notasAtivas.containsKey(valorMidi)) {
                    long tempoInicio = notasAtivas.remove(valorMidi);
                    long duracao = System.currentTimeMillis() - tempoInicio;
                    if (onNotaFinalizadaCallback != null) {
                        onNotaFinalizadaCallback.accept(Nota.fromMidiValue(valorMidi), duracao);
                    }
                }
            }
        }
    }

    @Override
    public void close() {
        if (transmissorAtivo != null) {
            transmissorAtivo.close();
        }
        if (dispositivoConectado != null && dispositivoConectado.isOpen()) {
            dispositivoConectado.close();
            System.out.println("Conexão MIDI finalizada.");
        }
    }
}