package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Progressao extends ElementoMusical{
    private List<Acorde> acordes = new ArrayList<>();

    public Progressao() {
    }
    
    public Progressao(String nome) {
        super(nome);
    }

    public Progressao(List<Acorde> acordes, String nome) {
        super(nome);
        if(acordes != null){
            this.acordes = acordes;
        }
    }
    
    public Progressao(Progressao progressao) {
        super(progressao.getId(), progressao.getNome());
        this.acordes = new ArrayList<>(progressao.getAcordes());
    }
    
    public Progressao(List<Acorde> acordes, int id, String nome) {
        super(id, nome);
        if(acordes != null){
            this.acordes = acordes;
        }
    }
    
    public List<Acorde> getAcordes() {
        return acordes;
    }

    public void setAcordes(List<Acorde> acordes) {
        this.acordes = acordes;
    }
    
    public void adicionarAcorde(Acorde acorde){
        if(acorde != null){
            this.acordes.add(acorde);
        }
    }
    
    public void removerAcorde(int posicaoAcorde){
        if(posicaoAcorde < 0 || posicaoAcorde >= this.acordes.size()){
            throw new IndexOutOfBoundsException("Posição " + posicaoAcorde + "está fora do tamanho atual do acorde de " + this.acordes.size());
        }
        this.acordes.remove(posicaoAcorde);
    }
    
    public void inserirAcorde(Acorde acorde, int posicaoAcorde){
        if(acorde == null){
            return;
        }
        if(posicaoAcorde < 0 || posicaoAcorde > this.acordes.size()){
            throw new IndexOutOfBoundsException("Posição " + posicaoAcorde + "está fora do tamanho atual do acorde de " + this.acordes.size());
        }
        this.acordes.add(posicaoAcorde, acorde);
    }

    @Override
    public String getInfoComplementar() {
        return "";
    }
}
