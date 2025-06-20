package model;

import java.util.ArrayList;
import java.util.List;

public class Escala extends ElementoMusical implements IElementoSonoro{
    private List<Nota> notas = new ArrayList<>();
    private String estrutura;

    public Escala() {
    }
    
    public Escala(Escala escala) {
        super(escala.id, escala.nome);
        this.notas = new ArrayList<>(escala.getNotas());
        this.estrutura = escala.estrutura;
    }
    
    public Escala(List<Nota> notas, String estrutura, String nome) {
        super(nome);
        this.notas = notas;
        this.estrutura = estrutura;
    }

    public Escala(List<Nota> notas, String estrutura, int id, String nome) {
        super(id, nome);
        this.notas = notas;
        this.estrutura = estrutura;
    }

    @Override
    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public String getEstrutura() {
        return estrutura;
    }

    public void setEstrutura(String estrutura) {
        this.estrutura = estrutura;
    }

    @Override
    public String getInfoComplementar() { //implementar com o bd, na tabela do bd teria a coluna de qualidade do acorde, extensões, acições e intervalos
        //informacoes complementares da escala - qualidade da escala; extensões e adicoes; lista de intervalos;
        return "";
    }
    
}
