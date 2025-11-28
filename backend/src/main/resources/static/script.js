// Máscaras de input
function aplicarMascaraCPF(input) {
    input.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length <= 11) {
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
            e.target.value = value;
        }
    });
}

function aplicarMascaraTelefone(input) {
    input.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length <= 11) {
            if (value.length <= 10) {
                value = value.replace(/(\d{2})(\d)/, '($1) $2');
                value = value.replace(/(\d{4})(\d)/, '$1-$2');
            } else {
                value = value.replace(/(\d{2})(\d)/, '($1) $2');
                value = value.replace(/(\d{5})(\d)/, '$1-$2');
            }
            e.target.value = value;
        }
    });
}

function aplicarMascaraCEP(input) {
    input.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length <= 8) {
            value = value.replace(/(\d{5})(\d)/, '$1-$2');
            e.target.value = value;
        }
    });
}

// Aplicar máscaras
document.addEventListener('DOMContentLoaded', function() {
    // Máscaras para cliente
    const cliCpf = document.getElementById('cliCpf');
    const cliTelefone = document.getElementById('cliTelefone');
    const cliCep = document.getElementById('cliCep');
    
    if (cliCpf) aplicarMascaraCPF(cliCpf);
    if (cliTelefone) aplicarMascaraTelefone(cliTelefone);
    if (cliCep) aplicarMascaraCEP(cliCep);

    // Máscaras para prestador
    const preCpf = document.getElementById('preCpf');
    const preTelefone = document.getElementById('preTelefone');
    const preCep = document.getElementById('preCep');
    
    if (preCpf) aplicarMascaraCPF(preCpf);
    if (preTelefone) aplicarMascaraTelefone(preTelefone);
    if (preCep) aplicarMascaraCEP(preCep);
});

// Formatação de data para o formato esperado pelo backend (dd/MM/yyyy)
function formatarDataParaBackend(dataISO) {
    const data = new Date(dataISO);
    const dia = String(data.getDate()).padStart(2, '0');
    const mes = String(data.getMonth() + 1).padStart(2, '0');
    const ano = data.getFullYear();
    return `${dia}/${mes}/${ano}`;
}

// Função para mostrar mensagem
function mostrarMensagem(elementId, mensagem, tipo) {
    const messageDiv = document.getElementById(elementId);
    messageDiv.textContent = mensagem;
    messageDiv.className = `message ${tipo}`;
    
    setTimeout(() => {
        messageDiv.style.display = 'none';
    }, 5000);
}

// Cadastro de Cliente
const formCliente = document.getElementById('formCliente');
if (formCliente) {
    formCliente.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const dados = {};
    
    // Coletar todos os dados do formulário
    for (let [key, value] of formData.entries()) {
        dados[key] = value.trim();
    }
    
    // Formatar data
    if (dados.nascimento) {
        dados.nascimento = formatarDataParaBackend(dados.nascimento);
    }
    
    // Validações básicas
    if (!dados.nome || !dados.cpf || !dados.email || !dados.telefone || !dados.senha) {
        mostrarMensagem('cliMessage', 'Por favor, preencha todos os campos obrigatórios.', 'error');
        return;
    }
    
    // Valores padrão para campos opcionais
    dados.endLogradouro = dados.endLogradouro || '';
    dados.endNumero = dados.endNumero || '';
    dados.endComplemento = dados.endComplemento || '';
    dados.endBairro = dados.endBairro || '';
    dados.endCidade = dados.endCidade || '';
    dados.endUf = dados.endUf || '';
    dados.endCep = dados.endCep || '';
    
    try {
        const response = await fetch('/api/cadastro/cliente', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dados)
        });
        
        const result = await response.json();
        
        if (result.success) {
            mostrarMensagem('cliMessage', result.message, 'success');
            e.target.reset();
        } else {
            mostrarMensagem('cliMessage', result.message, 'error');
        }
    } catch (error) {
        mostrarMensagem('cliMessage', 'Erro ao conectar com o servidor: ' + error.message, 'error');
    }
    });
}

// Cadastro de Prestador
const formPrestador = document.getElementById('formPrestador');
if (formPrestador) {
    formPrestador.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const dados = {};
    
    // Coletar todos os dados do formulário
    for (let [key, value] of formData.entries()) {
        dados[key] = value.trim();
    }
    
    // Formatar data
    if (dados.nascimento) {
        dados.nascimento = formatarDataParaBackend(dados.nascimento);
    }
    
    // Validações básicas
    if (!dados.nome || !dados.cpf || !dados.email || !dados.telefone || !dados.senha) {
        mostrarMensagem('preMessage', 'Por favor, preencha todos os campos obrigatórios.', 'error');
        return;
    }
    
    // Valores padrão para campos opcionais
    dados.areas = dados.areas || '';
    dados.experiencia = dados.experiencia || '';
    dados.certificados = dados.certificados || '';
    dados.endLogradouro = dados.endLogradouro || '';
    dados.endNumero = dados.endNumero || '';
    dados.endComplemento = dados.endComplemento || '';
    dados.endBairro = dados.endBairro || '';
    dados.endCidade = dados.endCidade || '';
    dados.endUf = dados.endUf || '';
    dados.endCep = dados.endCep || '';
    
    try {
        const response = await fetch('/api/cadastro/prestador', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dados)
        });
        
        const result = await response.json();
        
        if (result.success) {
            mostrarMensagem('preMessage', result.message, 'success');
            e.target.reset();
        } else {
            mostrarMensagem('preMessage', result.message, 'error');
        }
    } catch (error) {
        mostrarMensagem('preMessage', 'Erro ao conectar com o servidor: ' + error.message, 'error');
    }
    });
}

