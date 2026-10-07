/*
 * Validação do formulário no navegador (cadastro e edição de dados).
 * O formulário só é enviado ao servidor se tudo estiver correto.
 * Atenção: as regras precisam ser iguais às de util/Validador.java.
 */
(function () {
  "use strict";

  var form = document.getElementById("formUsuario");
  if (!form) return;

  var caixaErros = document.getElementById("erros-js");
  var senhaObrigatoria = form.dataset.senhaObrigatoria === "true";

  var REGEX_EMAIL = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
  var REGEX_LOGIN = /^[A-Za-z0-9_.]{3,50}$/;

  form.addEventListener("submit", function (evento) {
    var erros = [];
    limparMarcacoes();

    var nome = valor("nome");
    var endereco = valor("endereco");
    var email = valor("email");
    var login = valor("login");
    var senha = form.elements["senha"].value;
    var confirmacao = form.elements["confirmacao"].value;

    if (nome === "") erro(erros, "nome", "Informe o nome.");
    if (endereco === "") erro(erros, "endereco", "Informe o endereço.");

    if (email === "") erro(erros, "email", "Informe o e-mail.");
    else if (!REGEX_EMAIL.test(email)) erro(erros, "email", "E-mail inválido.");

    if (login === "") erro(erros, "login", "Informe o login.");
    else if (!REGEX_LOGIN.test(login))
      erro(erros, "login", "O login deve ter de 3 a 50 caracteres (letras, números, ponto ou sublinhado).");

    if (senhaObrigatoria && senha === "") erro(erros, "senha", "Informe a senha.");
    if (senha !== "") {
      if (senha.length < 6) erro(erros, "senha", "A senha deve ter pelo menos 6 caracteres.");
      if (senha !== confirmacao) erro(erros, "confirmacao", "A confirmação da senha não confere.");
    }

    if (erros.length > 0) {
      evento.preventDefault(); // BLOQUEIA o envio ao servidor
      mostrar(erros);
    }
  });

  function valor(nome) {
    return form.elements[nome].value.trim();
  }

  function erro(lista, campo, mensagem) {
    lista.push(mensagem);
    form.elements[campo].classList.add("invalido");
  }

  function limparMarcacoes() {
    Array.prototype.forEach.call(form.elements, function (el) { el.classList.remove("invalido"); });
    caixaErros.hidden = true;
    caixaErros.textContent = "";
  }

  function mostrar(erros) {
    var ul = document.createElement("ul");
    erros.forEach(function (msg) {
      var li = document.createElement("li");
      li.textContent = msg; // textContent (e não innerHTML) evita injeção de HTML
      ul.appendChild(li);
    });
    caixaErros.appendChild(ul);
    caixaErros.hidden = false;
    caixaErros.scrollIntoView({ behavior: "smooth", block: "center" });
  }
})();
