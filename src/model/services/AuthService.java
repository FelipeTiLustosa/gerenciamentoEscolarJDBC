package model.services;

public class AuthService {

    private AuthDAO authDAO;

    public AuthService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public boolean loginAluno(int idAluno, String senha) {
        return authDAO.autenticarAluno(idAluno, senha);
    }

    public boolean loginProfessor(int idProfessor, String senha) {
        return authDAO.autenticarProfessor(idProfessor, senha);
    }

    public boolean loginCoordenador(int idCoordenador, String senha) {
        return authDAO.autenticarCoordenador(idCoordenador, senha);
    }
}
