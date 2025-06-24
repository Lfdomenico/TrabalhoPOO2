package model;

import java.util.ArrayList;
import java.util.List;

public class Musica {
    private int id;
    private String titulo;
    private String artista;
    private int ano; // Nova coluna
    private String tonica; // Nova coluna
    private List<Acorde> acordes = new ArrayList<>();
    

    public Musica() {
    }
    

    public Musica(String titulo, String artista, List<Acorde> acordes) {
        this.titulo = titulo;
        this.artista = artista;
        this.acordes = acordes;

        this.ano = 0; 
        this.tonica = null; 
    }

    public Musica(int id, String titulo, String artista, List<Acorde> acordes) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.acordes = acordes;
 
        this.ano = 0; 
        this.tonica = null; 
    }

 
    public Musica(int id, String titulo, String artista, int ano, String tonica, List<Acorde> acordes) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.ano = ano;
        this.tonica = tonica;
        this.acordes = acordes;
    }


    public Musica(String titulo, String artista, int ano, String tonica) {
        this.titulo = titulo;
        this.artista = artista;
        this.ano = ano;
        this.tonica = tonica;
        this.id = 0; 
        this.acordes = new ArrayList<>();
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


    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTonica() {
        return tonica;
    }

    public void setTonica(String tonica) {
        this.tonica = tonica;
    }

    public List<Acorde> getAcordes() {
        return acordes;
    }

    public void setAcordes(List<Acorde> acordes) {
        this.acordes = acordes;
    }

    @Override
    public String toString() {
        return "Musica{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", artista='" + artista + '\'' +
               ", ano=" + ano +
               ", tonica='" + tonica + '\'' +
               '}';
    }
}