package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; 

public class Acorde extends ElementoMusical implements IElementoSonoro {
    private List<Nota> notas = new ArrayList<>();
    private String tipo;
    private Nota tonica;

    public Acorde() {
        super();
        this.notas = new ArrayList<>();
    }


    public Acorde(String tipo, Nota tonica, String nome, List<Nota> notas) {
        super(nome);
        this.tipo = tipo;
        this.tonica = tonica;
        this.notas = (notas != null) ? new ArrayList<>(notas) : new ArrayList<>();
    }


    public Acorde(String tipo, Nota tonica, int id, String nome, List<Nota> notas) {
        super(id, nome);
        this.tipo = tipo;
        this.tonica = tonica;
        this.notas = (notas != null) ? new ArrayList<>(notas) : new ArrayList<>();
    }
    

    public int getElementoId() {
        return super.getId();
    }

    @Override
    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Nota getTonica() {
        return tonica;
    }

    public void setTonica(Nota tonica) {
        this.tonica = tonica;
    }
    
    public Acorde inverter(int inversao){
        if (inversao < 0 || notas.size() < 2){
            return new Acorde(this.tipo,this.tonica, this.id, this.nome, this.notas);
        }
        
        List<Nota> notasInvertidas = new ArrayList<>(this.notas);
        
        for(int i = 0; i < inversao; i++){
            if(notasInvertidas.isEmpty()) break;
            Nota notaMovida = notasInvertidas.remove(0);
            notasInvertidas.add(notaMovida);
        }
        
        String nomeAcordeInvertido = this.getNome() + "(" + inversao + "ª inversão)";
        int idTemporario = 0;
        
        return new Acorde(this.tipo,this.tonica, idTemporario, nomeAcordeInvertido, notasInvertidas); 
    }

    @Override
    public String getInfoComplementar() { 

        if (notas != null && !notas.isEmpty()) {
            String notasStr = notas.stream().map(Nota::getNomeCompleto).collect(Collectors.joining("-"));
            return "Notas: " + notasStr + ", Tipo: " + tipo + ", Tônica: " + (tonica != null ? tonica.getNomeCompleto() : "N/A");
        }
        return "Tipo: " + tipo + ", Tônica: " + (tonica != null ? tonica.getNomeCompleto() : "N/A") + " (Notas não especificadas)";
    }

    @Override
    public String toString() {
        String notasStr = notas.stream().map(Nota::getNome).collect(Collectors.joining("-"));
        return "Acorde [ID=" + getId() + ", Nome='" + getNome() + "', Tipo='" + tipo + "', Tônica=" + (tonica != null ? tonica.getNome() : "N/A") + ", Notas=[" + notasStr + "]]";
    }
}