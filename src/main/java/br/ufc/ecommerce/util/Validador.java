package br.ufc.ecommerce.util;

import br.ufc.ecommerce.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validação NO SERVIDOR. A validação em JavaScript (js/validacao.js) melhora a experiência,
 * mas o usuário pode desativá-la; por isso o servidor sempre valida de novo.
 * As regras aqui precisam ser iguais às do JavaScript.
 */
public class Validador {

    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern LOGIN = Pattern.compile("^[A-Za-z0-9_.]{3,50}$");

    private Validador() { }

    public static List<String> validar(Usuario u, String senha, String confirmacao, boolean senhaObrigatoria) {
        List<String> erros = new ArrayList<>();

        if (vazio(u.getNome())) erros.add("Informe o nome.");
        else if (u.getNome().length() > 100) erros.add("O nome deve ter no máximo 100 caracteres.");

        if (vazio(u.getEndereco())) erros.add("Informe o endereço.");
        else if (u.getEndereco().length() > 200) erros.add("O endereço deve ter no máximo 200 caracteres.");

        if (vazio(u.getEmail())) erros.add("Informe o e-mail.");
        else if (!EMAIL.matcher(u.getEmail()).matches() || u.getEmail().length() > 100) erros.add("E-mail inválido.");

        if (vazio(u.getLogin())) erros.add("Informe o login.");
        else if (!LOGIN.matcher(u.getLogin()).matches())
            erros.add("O login deve ter de 3 a 50 caracteres (letras, números, ponto ou sublinhado).");

        boolean informouSenha = !vazio(senha);
        if (senhaObrigatoria && !informouSenha) {
            erros.add("Informe a senha.");
        }
        if (informouSenha) {
            if (senha.length() < 6) erros.add("A senha deve ter pelo menos 6 caracteres.");
            else if (senha.length() > 50) erros.add("A senha deve ter no máximo 50 caracteres.");
            if (!senha.equals(confirmacao)) erros.add("A confirmação da senha não confere.");
        }
        return erros;
    }

    private static boolean vazio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
