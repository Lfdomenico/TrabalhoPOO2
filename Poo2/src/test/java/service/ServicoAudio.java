package service;

import model.IElementoSonoro;
import model.Nota;
import model.Progressao;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * Serviço responsável por reproduzir sons a partir de objetos do modelo.
 * Atua como uma fachada para a API Java Sound (javax.sound.midi).
 */
public class ServicoAudio {

    private final Synthesizer synthesizer;
    private final MidiChannel midiChannel;
    private static final int VELOCIDADE_PADRAO = 100;
    private static final int DURACAO_NOTA_MS = 2000; // Aumentei a duração para 2 segundos

    public ServicoAudio() {
        try {
            this.synthesizer = MidiSystem.getSynthesizer();
            this.synthesizer.open();
            
            // --- LINHAS DE DEBUG ADICIONADAS ---
            System.out.println("DEBUG: Sintetizador encontrado: " + synthesizer.getDeviceInfo().getName());
            System.out.println("DEBUG: Descrição: " + synthesizer.getDeviceInfo().getDescription());
            // ------------------------------------

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
            System.out.println("DEBUG: Tentando tocar notas...");
            for (Nota nota : item.getNotas()) {
                int valorMidi = nota.getValorMidi();
                System.out.println("DEBUG: Enviando NOTE ON para a nota MIDI: " + valorMidi);
                midiChannel.noteOn(valorMidi, VELOCIDADE_PADRAO);
            }

            System.out.println("DEBUG: Aguardando " + DURACAO_NOTA_MS + "ms...");
            Thread.sleep(DURACAO_NOTA_MS);

            for (Nota nota : item.getNotas()) {
                int valorMidi = nota.getValorMidi();
                System.out.println("DEBUG: Enviando NOTE OFF para a nota MIDI: " + valorMidi);
                midiChannel.noteOff(valorMidi);
            }
            System.out.println("DEBUG: Reprodução finalizada.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("A reprodução foi interrompida.");
        }
    }
    
    /**
     * Toca uma progressão de acordes em sequência.
     * @param progressao A progressão a ser tocada.
     */
    public void reproduzir(Progressao progressao) {
        if (progressao == null || progressao.getAcordes() == null || progressao.getAcordes().isEmpty()) {
            return;
        }

        System.out.println("Tocando a progressão: " + progressao.getNome());
        try {
            for (IElementoSonoro acorde : progressao.getAcordes()) {
                System.out.println("- Tocando: " + ((model.ElementoMusical)acorde).getNome());
                // Reutiliza o método de reproduzir para cada acorde
                reproduzir(acorde);
                // Pequena pausa entre os acordes para distingui-los
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("A reprodução da progressão foi interrompida.");
        }
    }

    /**
     * Libera os recursos do sintetizador. Essencial para ser chamado
     * ao final da aplicação para não deixar o dispositivo de áudio "preso".
     */
    public void fechar() {
        if (synthesizer != null && synthesizer.isOpen()) {
            synthesizer.close();
            System.out.println("Serviço de áudio finalizado com sucesso.");
        }
    }
}