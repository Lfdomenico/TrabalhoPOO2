package model;

import java.util.Map;
import java.util.Objects;

public class Nota {
    private int id;
    private String nome;
    private String acidente; //#, b, ##, bb, etc;
    private int oitava;
    
    private static final Map<String, Integer> VALORES_BASE = Map.of("C", 0, "D", 2, "E", 4, "F", 5, "G", 7, "A", 9, "B", 11);
    private static final String[] NOMES_NOTAS_MIDI = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public Nota() {
    }
    
    public Nota(String nome, int oitava) {
        this.nome = nome;
        this.oitava = oitava;
    }
    
    public Nota(String nome, String acidente, int oitava) {
        this.nome = nome;
        this.acidente = acidente;
        this.oitava = oitava;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAcidente() {
        return acidente;
    }

    public void setAcidente(String acidente) {
        this.acidente = acidente;
    }

    public int getOitava() {
        return oitava;
    }

    public void setOitava(int oitava) {
        this.oitava = oitava;
    }
    
    public int getValorMidi(){
        if(nome == null || !VALORES_BASE.containsKey(nome.toUpperCase())) {return -1;} //o nome e pra estar sempre em maiuscula mas por precaucao usar o toUpper
        int valorBase = VALORES_BASE.get(nome.toUpperCase());
        int valorAcidente = 0;
        
        if(acidente != null){
            valorAcidente = switch(acidente){
                case "#" -> 1;
                case "b" -> -1;
                case "##" -> 2;
                case "bb" -> -2;
                default -> 0;
            };
        }
        
        return valorBase + valorAcidente +(oitava + 1) * 12;
    }
    
    public String getNomeCompleto(){
        String acidenteTratado = (this.acidente != null && !this.acidente.isEmpty()) ? this.acidente : "";
        return this.nome + "" + acidenteTratado + this.oitava;
    }
    
    public Nota transpor(int intervalo){
        int valorMidiAtual = this.getValorMidi();
        
        if(valorMidiAtual == -1) {return this;}
        
        int novoValorMidi = valorMidiAtual + intervalo;
        
        if(novoValorMidi < 0 || novoValorMidi >127){throw new IllegalArgumentException("A transposição está fora do alcance MIDI(0-127)");}
        
        int novaOitava = (novoValorMidi/12) -1;
        int indiceNota = novoValorMidi % 12;
        
        String nomeNotaMidi = NOMES_NOTAS_MIDI[indiceNota];
        String novoNome;
        String novoAcidente = "";
        
        if(nomeNotaMidi.length() > 1){
            novoNome = nomeNotaMidi.substring(0,1);
            novoAcidente = nomeNotaMidi.substring(1);
        }
        else{
            novoNome = nomeNotaMidi;
        }
        
        return new Nota(novoNome, novoAcidente, novaOitava);
    }
    
    public static Nota fromMidiValue(int midiValue) {
        if (midiValue < 0 || midiValue > 127) {
            throw new IllegalArgumentException("Valor MIDI inválido: " + midiValue);
        }

        int oitava = (midiValue / 12) - 1;
        int indiceNota = midiValue % 12;
        String nomeNotaCompleto = NOMES_NOTAS_MIDI[indiceNota];

        String nome = nomeNotaCompleto.substring(0, 1);
        String acidente = nomeNotaCompleto.length() > 1 ? nomeNotaCompleto.substring(1) : null;

        Nota nota = new Nota(nome, acidente, oitava);
        nota.setId(0);
        return nota;
    }
    
    @Override
    public boolean equals(Object o) {
        //ve se o objeto e o mesmo
        if (this == o) return true;
        
        //ve se e nulo ou outra classe
        if (o == null || getClass() != o.getClass()) return false;
        
        //covnerte o objeto e compara os atributos
        Nota nota = (Nota) o;
        return this.oitava == nota.oitava &&
               Objects.equals(this.nome, nota.nome) &&
               Objects.equals(this.acidente, nota.acidente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, acidente, oitava);
    }
}
