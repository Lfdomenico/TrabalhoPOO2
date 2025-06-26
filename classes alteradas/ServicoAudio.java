package service;

import model.IElementoSonoro;
import model.Nota;
import model.Progressao;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;


public class ServicoAudio {

    private final Synthesizer synthesizer;
    private final MidiChannel midiChannel;
    private static final int VELOCIDADE_PADRAO = 100;
    private static final int DURACAO_NOTA_MS = 2000;

    public ServicoAudio() {
        try {
            this.synthesizer = MidiSystem.getSynthesizer();
            this.synthesizer.open();
            
            System.out.println("Sintetizador encontrado: " + synthesizer.getDeviceInfo().getName());
            System.out.println("Descrição: " + synthesizer.getDeviceInfo().getDescription());

            this.midiChannel = this.synthesizer.getChannels()[0];
        } catch (MidiUnavailableException e) {
            throw new IllegalStateException("Não foi possível inicializar o serviço de áudio.", e);
        }
    }

    public void reproduzir(IElementoSonoro item) {
        if (item == null || item.getNotas() == null || item.getNotas().isEmpty()) {
            return;
        }

        try {
            System.out.println("Tentando tocar notas...");
            for (Nota nota : item.getNotas()) {
                int valorMidi = nota.getValorMidi();
                System.out.println("Enviando NOTE ON para a nota MIDI: " + valorMidi);
                midiChannel.noteOn(valorMidi, VELOCIDADE_PADRAO);
            }

            System.out.println("Aguardando " + DURACAO_NOTA_MS + "ms...");
            Thread.sleep(DURACAO_NOTA_MS);

            for (Nota nota : item.getNotas()) {
                int valorMidi = nota.getValorMidi();
                System.out.println("Enviando NOTE OFF para a nota MIDI: " + valorMidi);
                midiChannel.noteOff(valorMidi);
            }
            System.out.println("Reprodução finalizada.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("A reprodução foi interrompida.");
        }
    }
    
    public void reproduzir(Progressao progressao) {
        if (progressao == null || progressao.getAcordes() == null || progressao.getAcordes().isEmpty()) {
            return;
        }

        System.out.println("Tocando a progressão: " + progressao.getNome());
        try {
            for (IElementoSonoro acorde : progressao.getAcordes()) {
                System.out.println("- Tocando: " + ((model.ElementoMusical)acorde).getNome());
                reproduzir(acorde);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("A reprodução da progressão foi interrompida.");
        }
    }

    public void fechar() {
        if (synthesizer != null && synthesizer.isOpen()) {
            synthesizer.close();
            System.out.println("Serviço de áudio finalizado com sucesso.");
        }
    }
    
    public void pressionarNota(Nota nota) {
        if (nota != null) {
            midiChannel.noteOn(nota.getValorMidi(), VELOCIDADE_PADRAO);
            System.out.println("AUDIO SERVICE: Note ON -> " + nota.getNomeCompleto());
        }
    }

    /**
     * Apenas "solta" a tecla no sintetizador.
     * @param nota A nota a ser solta.
     */
    public void soltarNota(Nota nota) {
        if (nota != null) {
            midiChannel.noteOff(nota.getValorMidi());
            System.out.println("AUDIO SERVICE: Note OFF -> " + nota.getNomeCompleto());
        }
    }
}