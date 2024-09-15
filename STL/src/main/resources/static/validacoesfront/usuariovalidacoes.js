

$(document).ready(function() {
    $('#nome').on('input', function() {
        var nome = $(this).val();
        var padrao = /\d/;

        if (padrao.test(nome)) {
            $('#mensagemErroNome').text('O nome não pode conter números');
        } else {
            $('#mensagemErroNome').text('');
        }
    });
});


$(document).ready(function() {
    $('#email').on('input', function() {
        var email = $(this).val();
        var padrao = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!padrao.test(email)) {
            $('#mensagemErroEmail').text('E-mail inválido');
        } else {
            $('#mensagemErroEmail').text('');
        }
    });
});


$(document).ready(function() {
    $('#telefone').on('input', function() {
        var telefone = $(this).val();
        var padrao = /^9\d{2}-\d{3}-\d{3}$/;

        if (!padrao.test(telefone)) {
            $('#mensagemErroTelefone').text('Telefone inválido. Deve começar com 9 e ter o formato 9XX-XXX-XXX');
        } else {
            $('#mensagemErroTelefone').text('');
        }
    });
});
