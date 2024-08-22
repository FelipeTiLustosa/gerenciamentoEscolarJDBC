package model.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import db.DbException;

public class AuthDAO {
    private Connection conn;

    public AuthDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean autenticarAluno(int idAluno, String senha) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT senha FROM Alunos WHERE id_aluno = ?");
            st.setInt(1, idAluno);
            rs = st.executeQuery();
            if (rs.next()) {
                String senhaArmazenada = rs.getString("senha");
                return senhaArmazenada.equals(senha);
            }
            return false;
        } catch (SQLException e) {
            throw new DbException("Erro ao autenticar aluno: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public boolean autenticarProfessor(int idProfessor, String senha) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT senha FROM Professores WHERE id_professor = ?");
            st.setInt(1, idProfessor);
            rs = st.executeQuery();
            if (rs.next()) {
                String senhaArmazenada = rs.getString("senha");
                return senhaArmazenada.equals(senha);
            }
            return false;
        } catch (SQLException e) {
            throw new DbException("Erro ao autenticar professor: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public boolean autenticarCoordenador(int idCoordenador, String senha) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT senha FROM Professores WHERE id_professor = ? AND coordenador = TRUE");
            st.setInt(1, idCoordenador);
            rs = st.executeQuery();
            if (rs.next()) {
                String senhaArmazenada = rs.getString("senha");
                return senhaArmazenada.equals(senha);
            }
            return false;
        } catch (SQLException e) {
            throw new DbException("Erro ao autenticar coordenador: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
