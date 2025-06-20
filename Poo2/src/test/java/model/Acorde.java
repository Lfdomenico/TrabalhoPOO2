package model;

import java.util.ArrayList;
import java.util.List;

public class Acorde extends ElementoMusical implements IElementoSonoro{
    private List<Nota> notas = new ArrayList<>();
    private String tipo;
    private Nota tonica;

    public Acorde() {
    }

    public Acorde(String tipo, Nota tonica, String nome, List<Nota> notas) {
        super(nome);
        this.tipo = tipo;
        this.tonica = tonica;
        if(notas != null){
            this.notas = new ArrayList<>(notas);
        }
        else{
            this.notas = new ArrayList<>();
        }
    }

    public Acorde(String tipo, Nota tonica, int id, String nome, List<Nota> notas) {
        super(id, nome);
        this.tipo = tipo;
        this.tonica = tonica;
        this.notas = notas;
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
        
        return new Acorde(this.tipo,this.tonica, idTemporario, nomeAcordeInvertido, notasInvertidas); //so pra tirar o erro
    }

    @Override
    public String getInfoComplementar() { //implementar com o bd, na tabela do bd teria a coluna de qualidade do acorde, extensões, acições e intervalos
        //informacoes complementares do acorde - qualidade do acorde; extensões e adicoes; lista de intervalos;
        return "";
    }
}
