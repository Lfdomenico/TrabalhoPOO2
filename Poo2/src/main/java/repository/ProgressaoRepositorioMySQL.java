package repository;

import model.Acorde;
import model.Progressao;
import controller.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

public class ProgressaoRepositorioMySQL implements ProgressaoRepositorio {

    private final AcordeRepositorio acordeRepositorio;

    public ProgressaoRepositorioMySQL(AcordeRepositorio acordeRepositorio) {
        this.acordeRepositorio = acordeRepositorio;
    }

    private Progressao extrairProgressaoDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("ProgressaoId");
        String nome = rs.getString("Nome"); 
        
        Progressao progressao = new Progressao(new ArrayList<>(), id, nome);
        carregarAcordesDaProgressao(progressao);
        return progressao;
    }

    private void carregarAcordesDaProgressao(Progressao progressao) throws SQLException {
        String sql = "SELECT AcordeId, Ordem FROM progressao_acordes WHERE ProgressaoId = ? ORDER BY Ordem";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, progressao.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int acordeId = rs.getInt("AcordeId");
                    acordeRepositorio.findById(acordeId).ifPresent(progressao::adicionarAcorde);
                }
            }
        }
    }

    private void salvarAcordesDaProgressao(Progressao progressao) throws SQLException {
        String deleteSql = "DELETE FROM progressao_acordes WHERE ProgressaoId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setInt(1, progressao.getId());
            stmt.executeUpdate();
        }

        String insertSql = "INSERT INTO progressao_acordes (ProgressaoId, AcordeId, Ordem) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            
            for (int i = 0; i < progressao.getAcordes().size(); i++) {
                Acorde acorde = progressao.getAcordes().get(i);
                stmt.setInt(1, progressao.getId());
                stmt.setInt(2, acorde.getId());
                stmt.setInt(3, i);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public Progressao save(Progressao progressao) {
        String sql;
        
        if (progressao.getId() == 0) {

            sql = "INSERT INTO progressao (Nome) VALUES (?)"; 
        } else {

            sql = "UPDATE progressao SET Nome = ? WHERE ProgressaoId = ?";
        }

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, progressao.getNome()); 
            
            if (progressao.getId() != 0) {
                stmt.setInt(2, progressao.getId()); 
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar progressão, nenhuma linha afetada.");
            }

            if (progressao.getId() == 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        progressao.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Falha ao salvar progressão, nenhum ID gerado.");
                    }
                }
            }
            
            salvarAcordesDaProgressao(progressao);

        } catch (SQLException e) {
            System.err.println("Erro ao salvar progressão no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao salvar progressão no banco de dados.", e);
        }
        return progressao;
    }

    @Override
    public Optional<Progressao> findById(int id) {
        String sql = "SELECT ProgressaoId, Nome FROM progressao WHERE ProgressaoId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extrairProgressaoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar progressão por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Progressao> findAllByNome(String nome) {
        List<Progressao> progressoes = new ArrayList<>();
        String sql = "SELECT ProgressaoId, Nome FROM progressao WHERE Nome LIKE ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    progressoes.add(extrairProgressaoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar progressões por nome no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return progressoes;
    }


    @Override
    public List<Progressao> findAll() {
        List<Progressao> progressoes = new ArrayList<>();
        String sql = "SELECT ProgressaoId, Nome FROM progressao";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                progressoes.add(extrairProgressaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as progressões no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return progressoes;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM progressao WHERE ProgressaoId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Progressão com ID " + id + " deletada com sucesso.");
            } else {
                System.out.println("Nenhuma progressão encontrada com ID " + id + " para deletar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar progressão por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao deletar progressão no banco de dados.", e);
        }
    }
}