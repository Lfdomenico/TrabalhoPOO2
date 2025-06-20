package model;

import java.util.ArrayList;
import java.util.List;

public class Musica {
    private int id;
    private String titulo;
    private String artista;
    private List<Acorde> acordes = new ArrayList<>();
    

    public Musica() {
    }
    
    public Musica(String titulo, String artista, List<Acorde> acordes) {
        this.titulo = titulo;
        this.artista = artista;
        this.acordes = acordes;
    }
    
    public Musica(int id, String titulo, String artista, List<Acorde> acordes) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.acordes = acordes;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int idMusica) {
        this.id = idMusica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public List<Acorde> getAcordes() {
        return acordes;
    }

    public void setAcordes(List<Acorde> acordes) {
        this.acordes = acordes;
    }
}
