

$(document).ready(function() {
    $('#pregadorAltenativo').on('input', function() {
        var nome = $(this).val();
        var padrao = /\d/;

        if (padrao.test(nome)) {
            $('#mensagemErroPrega').text('O campo não pode conter números');
        } else {
            $('#mensagemErroPrega').text('');
        }
    });
});


$(document).ready(function() {
    $('#descricao').on('input', function() {
        var nome = $(this).val();
        var padrao = /\d/;

        if (padrao.test(nome)) {
            $('#mensagemErroDescr').text('O campo não pode conter números');
        } else {
            $('#mensagemErroPrega').text('');
        }
    });
});


$(document).ready(function() {
    $('#capitulo').on('input', function() {
        var telefone = $(this).val();
        var padrao = /^9\d{2}-\d{3}-\d{3}$/;

        if (!padrao.test(telefone)) {
            $('#mensagemErrocap').text('O campo não deve conter letras');
        } else {
            $('#mensagemErrocap').text('');
        }
    });
});



$(document).ready(function() {
    $('#versiculo').on('input', function() {
        var telefone = $(this).val();
        var padrao = /^9\d{2}-\d{3}-\d{3}$/;

        if (!padrao.test(telefone)) {
            $('#mensagemErrovers').text('O campo não deve conter letras');
        } else {
            $('#mensagemErrovers').text('');
        }
    });
});