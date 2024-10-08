package application;

import db.DB;
import model.dao.AlunoDAO;
import model.dao.DaoFactory;
import model.entities.Alunos;
import model.services.AuthDAO;
import model.services.AuthService;

import java.sql.Connection;
import java.time.LocalDate;

public class Program {
    public static <AlunosDAO> void main(String[] args) {
        AlunoDAO alunosDAO = DaoFactory.criarAlunoDAO();

        // Cria um novo objeto Alunos
       // Alunos novoAluno = new Alunos(null, "Pedro Lustosa", LocalDate.of(2000, 5, 15), "Rua A, 123", "11987654321", "joao.silva@example.com");

        // Insere o novo aluno no banco de dados
        //alunosDAO.inserir(novoAluno);

        // Exibe o ID gerado
        //System.out.println("Inserção realizada com sucesso! ID do novo aluno: " + novoAluno.getIdAluno());

        alunosDAO.buscarTodos();
        alunosDAO.buscarAlunosPorCurso(5);

        alunosDAO.buscarPorId(5);
        DB.closeConnection(DB.getConnection());

        // Estabelecer a conexão com o banco de dados
        Connection conn = null;

            conn = DB.getConnection();

            // Criar o DAO e o serviço de autenticação
            AuthDAO authDAO = new AuthDAO(conn);
            AuthService authService = new AuthService(authDAO);

            // Dados de teste
            int idAlunoTeste = 4;  // Substitua pelo ID do aluno existente no banco de dados
            String senhaTeste = "senha";  // Substitua pela senha correta do aluno

            // Realizar o teste de login
            boolean loginBemSucedido = authService.loginAluno(idAlunoTeste, senhaTeste);

            if (loginBemSucedido) {
                System.out.println("Login do aluno foi bem-sucedido!");
            } else {
                System.out.println("Falha no login do aluno.");
            }

            DB.closeConnection(conn);

    }
}