package repository;

import model.Anotacao;
import model.ElementoMusical; 
import controller.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnotacaoRepositorioMySQL implements AnotacaoRepositorio {

    private final ElementoMusicalRepositorio elementoMusicalRepo; 

    public AnotacaoRepositorioMySQL(ElementoMusicalRepositorio elementoMusicalRepo) {
        this.elementoMusicalRepo = elementoMusicalRepo;
    }

    private Anotacao extrairAnotacaoDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("AnotacaoId");
        String texto = rs.getString("Texto");
        Integer elementoId = rs.getInt("ElementoId"); 

        ElementoMusical elementoMusical = null;
        if (!rs.wasNull() && elementoId != null && elementoId != 0) {

            elementoMusical = elementoMusicalRepo.findById(elementoId).orElse(null);
        }

        Anotacao anotacao = new Anotacao(id, texto, elementoMusical);
        return anotacao;
    }

    @Override
    public Anotacao save(Anotacao anotacao) {
        String sql;
        Integer elementoId = (anotacao.getElementoSobre() != null && anotacao.getElementoSobre().getId() != 0) ? anotacao.getElementoSobre().getId() : null;
        
        if (anotacao.getId() == 0) { 
            sql = "INSERT INTO anotacao (Texto, ElementoId) VALUES (?, ?)";
        } else { 
            sql = "UPDATE anotacao SET Texto = ?, ElementoId = ? WHERE AnotacaoId = ?";
        }

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, anotacao.getTexto());
            if (elementoId == null) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, elementoId);
            }

            if (anotacao.getId() != 0) { 
                stmt.setInt(3, anotacao.getId()); 
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0 && anotacao.getId() == 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        anotacao.setId(generatedKeys.getInt(1)); 
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar anotação no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao salvar anotação no banco de dados.", e);
        }
        return anotacao;
    }

    @Override
    public Optional<Anotacao> findById(int id) {
        String sql = "SELECT AnotacaoId, Texto, ElementoId FROM anotacao WHERE AnotacaoId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extrairAnotacaoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar anotação por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Anotacao> findByElementoId(int idElemento) {
        List<Anotacao> anotacoes = new ArrayList<>();
        String sql = "SELECT AnotacaoId, Texto, ElementoId FROM anotacao WHERE ElementoId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idElemento);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    anotacoes.add(extrairAnotacaoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar anotações por ElementoId no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return anotacoes;
    }

    @Override
    public List<Anotacao> findAll() {
        List<Anotacao> anotacoes = new ArrayList<>();
        String sql = "SELECT AnotacaoId, Texto, ElementoId FROM anotacao";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                anotacoes.add(extrairAnotacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as anotações no MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return anotacoes;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM anotacao WHERE AnotacaoId = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Anotação com ID " + id + " deletada com sucesso.");
            } else {
                System.out.println("Nenhuma anotação encontrada com ID " + id + " para deletar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar anotação por ID no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao deletar anotação no banco de dados.", e);
        }
    }
}