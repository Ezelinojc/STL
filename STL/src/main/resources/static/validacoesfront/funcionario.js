
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
        var telefone = $(this).val().replace(/\D/g, ''); // Remove caracteres não numéricos

        // Limitar o valor a 9 caracteres
        if (telefone.length > 9) {
            telefone = telefone.substring(0, 9); // Limita o valor a 9 dígitos
        }

        $(this).val(telefone); // Atualiza o valor do campo de input

        var padrao = /^9\d{8}$/; // Padrão para 9 dígitos, começando com 9

        if (!padrao.test(telefone)) {
            $('#mensagemErroTelefone').text('Telefone inválido. Deve começar com 9 e conter exatamente 9 dígitos.');
        } else {
            $('#mensagemErroTelefone').text('');
        }
    });
});



