package repository;

import model.Escala;
import controller.ConexaoBD; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EscalaRepositorioMySQL implements EscalaRepositorio {

    private Escala extrairEscalaDoResultSet(ResultSet rs) throws SQLException {
        Escala escala = new Escala(
            rs.getInt("EscalaId"),
            rs.getString("Nome"),
            rs.getString("Estrutura"),
            rs.getString("Tipo")
        );
        return escala;
    }

    @Override
    public Escala save(Escala escala) {
        String sql;
        Integer idParaElementoId = (escala.getId() == 0) ? null : escala.getId(); 
        
        if (escala.getId() == 0) {
            sql = "INSERT INTO escala (Estrutura, ElementoId, Tipo, Nome) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE escala SET Estrutura = ?, ElementoId = ?, Tipo = ?, Nome = ? WHERE EscalaId = ?";
        }

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, escala.getEstrutura());
            if (idParaElementoId == null) {
                stmt.setNull(2, java.sql.Types.INTEGER); 
            } else {
                stmt.setInt(2, idParaElementoId);
            }
            stmt.setString(3, escala.getTipo());
            stmt.setString(4, escala.getNome());

            if (escala.getId() != 0) {
                stmt.setInt(5, escala.getId());
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0 && escala.getId() == 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        escala.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar escala no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao salvar escala no banco de dados.", e);
        }
        return escala;
    }

    @Override
    public Optional<Escala> findById(int id) {
        String sql = "SELECT EscalaId, Estrutura, ElementoId, Tipo, Nome FROM escala WHERE EscalaId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extrairEscalaDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar escala por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<Escala> findByNome(String nome) {
        String sql = "SELECT EscalaId, Estrutura, ElementoId, Tipo, Nome FROM escala WHERE Nome = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extrairEscalaDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar escala por Nome exato no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> findEstruturaByNome(String nome) {
        String sql = "SELECT Estrutura FROM escala WHERE Nome = ?"; 
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getString("Estrutura")); 
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar estrutura da escala por Nome no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<Escala> findByEstrutura(String estrutura) {
        List<Escala> escalas = new ArrayList<>();
        String sql = "SELECT EscalaId, Estrutura, ElementoId, Tipo, Nome FROM escala WHERE Estrutura LIKE ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + estrutura + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    escalas.add(extrairEscalaDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar escala por Estrutura no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return escalas;
    }

    @Override
    public List<Escala> findByNameContaining(String nome) {
        List<Escala> escalas = new ArrayList<>();
        String sql = "SELECT EscalaId, Estrutura, ElementoId, Tipo, Nome FROM escala WHERE Estrutura LIKE ? OR Tipo LIKE ? OR Nome LIKE ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + nome + "%");
            stmt.setString(3, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    escalas.add(extrairEscalaDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar escala por nome (Estrutura/Tipo/Nome) no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return escalas;
    }

    @Override
    public List<Escala> findAll() {
        List<Escala> escalas = new ArrayList<>();
        String sql = "SELECT EscalaId, Estrutura, ElementoId, Tipo, Nome FROM escala";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                escalas.add(extrairEscalaDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as escalas no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return escalas;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM escala WHERE EscalaId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Escala com ID " + id + " deletada com sucesso.");
            } else {
                System.out.println("Nenhuma escala encontrada com ID " + id + " para deletar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar escala por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao deletar escala no banco de dados.", e);
        }
    }
}