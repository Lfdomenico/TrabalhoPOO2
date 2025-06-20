package repository;

import model.Acorde;
import model.Nota;
import controller.ConexaoBD; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Collections;

public class AcordeRepositorioMySQL implements AcordeRepositorio {

    private Acorde extrairAcordeDoResultSet(ResultSet rs) throws SQLException {
        int tonicaId = rs.getInt("TonicaId");

        Nota tonica = new Nota(rs.getString("Nome"), null, 0); 
        if (!rs.wasNull()) {
            tonica.setId(tonicaId); 
        }

        List<Nota> notasDoAcorde = new ArrayList<>(); 
        String estruturaNotasStr = rs.getString("EstruturaNotas");
        if (estruturaNotasStr != null && !estruturaNotasStr.isEmpty()) {
            Arrays.stream(estruturaNotasStr.split("-"))
                  .forEach(notaStr -> notasDoAcorde.add(new Nota(notaStr, null, 0))); 
        }

        Acorde acorde = new Acorde(
            rs.getString("Tipo"),
            tonica,
            rs.getInt("AcordeId"),
            rs.getString("Nome"),
            notasDoAcorde 
        );
        return acorde;
    }

    @Override
    public Acorde save(Acorde acorde) {
        String sql;
        Integer tonicaId = (acorde.getTonica() != null && acorde.getTonica().getId() != 0) ? acorde.getTonica().getId() : null;
        String estruturaNotasStr = acorde.getNotas().stream().map(Nota::getNome).collect(Collectors.joining("-")); 

        if (acorde.getId() == 0) { 
            sql = "INSERT INTO acorde (Nome, Tipo, TonicaId, EstruturaNotas) VALUES (?, ?, ?, ?)";
        } else { 
            sql = "UPDATE acorde SET Nome = ?, Tipo = ?, TonicaId = ?, EstruturaNotas = ? WHERE AcordeId = ?";
        }

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, acorde.getNome());
            stmt.setString(2, acorde.getTipo());
            if (tonicaId == null) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, tonicaId);
            }
            stmt.setString(4, estruturaNotasStr);

            if (acorde.getId() != 0) { 
                stmt.setInt(5, acorde.getId()); 
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0 && acorde.getId() == 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        acorde.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar acorde no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao salvar acorde no banco de dados.", e);
        }
        return acorde;
    }

    @Override
    public Optional<Acorde> findById(int id) {
        String sql = "SELECT AcordeId, Nome, Tipo, TonicaId, EstruturaNotas FROM acorde WHERE AcordeId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extrairAcordeDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar acorde por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Acorde> findByNomeAndTipo(String nome, String tipo) {
        String sql = "SELECT AcordeId, Nome, Tipo, TonicaId, EstruturaNotas FROM acorde WHERE Nome = ? AND Tipo = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extrairAcordeDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar acorde por Nome e Tipo no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Acorde> findByNameContaining(String nome) {
        List<Acorde> acordes = new ArrayList<>();
        String sql = "SELECT AcordeId, Nome, Tipo, TonicaId, EstruturaNotas FROM acorde WHERE Nome LIKE ? OR Tipo LIKE ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    acordes.add(extrairAcordeDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar acordes por nome no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return acordes;
    }
    
    @Override
    public List<Acorde> findByTonica(Nota tonica) {
        List<Acorde> acordes = new ArrayList<>();
        String sql = "SELECT AcordeId, Nome, Tipo, TonicaId, EstruturaNotas FROM acorde WHERE TonicaId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tonica.getId()); 
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    acordes.add(extrairAcordeDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar acordes por Tonica no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return acordes;
    }

    @Override
    public List<Acorde> findByTipo(String tipo) {
        List<Acorde> acordes = new ArrayList<>();
        String sql = "SELECT AcordeId, Nome, Tipo, TonicaId, EstruturaNotas FROM acorde WHERE Tipo = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    acordes.add(extrairAcordeDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar acordes por Tipo no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return acordes;
    }

    @Override
    public List<String> findAllTipos() {
        List<String> tipos = new ArrayList<>();
        String sql = "SELECT DISTINCT Tipo FROM acorde ORDER BY Tipo"; 
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tipos.add(rs.getString("Tipo"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os tipos de acordes no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return tipos;
    }


    @Override
    public List<Acorde> findAll() {
        List<Acorde> acordes = new ArrayList<>();
        String sql = "SELECT AcordeId, Nome, Tipo, TonicaId, EstruturaNotas FROM acorde";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                acordes.add(extrairAcordeDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os acordes no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return acordes;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM acorde WHERE AcordeId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Acorde com ID " + id + " deletado com sucesso.");
            } else {
                System.out.println("Nenhum acorde encontrado com ID " + id + " para deletar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar acorde por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao deletar acorde no banco de dados.", e);
        }
    }
}