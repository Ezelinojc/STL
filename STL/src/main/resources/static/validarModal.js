function validarDatas() {
				const dataInicial = document.getElementById('dataInicial').value;
				const dataFinal = document.getElementById('dataFinal').value;
				const hoje = new Date().toISOString().split('T')[0]; // Data atual no formato 'YYYY-MM-DD'

				let valido = true;

				// Limpa mensagens de erro
				document.getElementById('dataInicialErro').textContent = '';
				document.getElementById('dataFinalErro').textContent = '';

				// Verifica se a dataInicial é maior que a data atual
				if (dataInicial > hoje) {
					document.getElementById('dataInicialErro').textContent = 'A data inicial não pode ser maior que a data atual.';
					valido = false;
				}

				// Verifica se a dataFinal é maior que a data atual
				if (dataFinal > hoje) {
					document.getElementById('dataFinalErro').textContent = 'A data final não pode ser maior que a data atual.';
					valido = false;
				}

				// Verifica se a dataFinal é menor que a dataInicial
				if (dataFinal < dataInicial) {
					document.getElementById('dataFinalErro').textContent = 'A data final não pode ser menor que a data inicial.';
					valido = false;
				}

				return valido;
			}