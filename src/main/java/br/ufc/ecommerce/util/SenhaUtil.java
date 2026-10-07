package br.ufc.ecommerce.util;

import org.mindrot.jbcrypt.BCrypt;

/** Hash de senhas com BCrypt: a senha em texto puro nunca é gravada no banco. */
public class SenhaUtil {

    private SenhaUtil() { }

    public static String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean verificar(String senha, String hash) {
        try {
            return BCrypt.checkpw(senha, hash);
        } catch (IllegalArgumentException e) {
            return false; // hash inválido no banco
        }
    }
}
