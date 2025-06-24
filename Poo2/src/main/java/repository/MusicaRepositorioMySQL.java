package repository;

import model.Musica;
import model.Acorde; // Será necessário para carregar acordes associados, se houver
import controller.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MusicaRepositorioMySQL implements MusicaRepositorio {

    // Se as músicas tiverem acordes associados no BD, você precisará de um AcordeRepositorio
    // private final AcordeRepositorio acordeRepo; 
    // public MusicaRepositorioMySQL(AcordeRepositorio acordeRepo) { this.acordeRepo = acordeRepo; }
    // Por simplicidade neste exemplo, não vou carregar os acordes da música do BD,
    // apenas os dados básicos da música. Se precisar, ajuste o `extrairMusicaDoResultSet`.

    private Musica extrairMusicaDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("MusicaId");
        String titulo = rs.getString("Titulo");
        String artista = rs.getString("Artista");
        int ano = rs.getInt("Ano");
        String tonica = rs.getString("Tonica");
        
        // Aqui você poderia carregar os acordes associados se houver uma tabela de ligação
        // List<Acorde> acordesDaMusica = acordeRepo.findByMusicaId(id);
        // return new Musica(id, titulo, artista, ano, tonica, acordesDaMusica);

        return new Musica(id, titulo, artista, ano, tonica, new ArrayList<>()); // Retorna sem acordes por padrão
    }

    @Override
    public Musica save(Musica musica) {
        String sql;
        if (musica.getId() == 0) { // Nova música
            sql = "INSERT INTO MUSICA (Titulo, Artista, Ano, Tonica) VALUES (?, ?, ?, ?)";
        } else { // Atualizar música existente
            sql = "UPDATE MUSICA SET Titulo = ?, Artista = ?, Ano = ?, Tonica = ? WHERE MusicaId = ?";
        }

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, musica.getTitulo());
            stmt.setString(2, musica.getArtista());
            stmt.setInt(3, musica.getAno());
            stmt.setString(4, musica.getTonica());

            if (musica.getId() != 0) {
                stmt.setInt(5, musica.getId());
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0 && musica.getId() == 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        musica.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar música no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao salvar música no banco de dados.", e);
        }
        return musica;
    }

    @Override
    public List<Musica> searchByTitleOrArtist(String query) {
        List<Musica> musicas = new ArrayList<>();
        // Usa LIKE para buscas parciais e case-insensitive (dependendo da configuração do DB)
        String sql = "SELECT MusicaId, Titulo, Artista, Ano, Tonica FROM MUSICA WHERE Titulo LIKE ? OR Artista LIKE ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    musicas.add(extrairMusicaDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar músicas por título ou artista no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return musicas;
    }

    @Override
    public Optional<Musica> findById(int id) {
        String sql = "SELECT MusicaId, Titulo, Artista, Ano, Tonica FROM MUSICA WHERE MusicaId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extrairMusicaDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar música por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    @Override
    public List<Musica> findAll() {
        List<Musica> musicas = new ArrayList<>();
        String sql = "SELECT MusicaId, Titulo, Artista, Ano, Tonica FROM MUSICA";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                musicas.add(extrairMusicaDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todas as músicas no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return musicas;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM MUSICA WHERE MusicaId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Música com ID " + id + " deletada com sucesso.");
            } else {
                System.out.println("Nenhuma música encontrada com ID " + id + " para deletar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar música por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao deletar música no banco de dados.", e);
        }
    }
}