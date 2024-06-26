function clearError(id) {
    const errorId = "error-" + id;
    const errorElement = document.getElementById(errorId);
    if (errorElement) {
        errorElement.textContent = "";
    }
}

function validateGenderSelection() {
    const genderOptions = document.querySelectorAll('input[name="userEntityDto.gender"]');
    let isSelected = false;

    genderOptions.forEach(option => {
        if (option.checked) {
            isSelected = true;
        }
    });

    if (!isSelected) {
        displayError("error-genderBio", "Selecione um Sexo Biológico.");
        return false;
    }

    clearGenderError();
    return true;
}


function validateCNPJ(cnpj) {
    cnpj = cnpj.replace(/[^\d]/g, '');

    if (cnpj.length !== 14 || /^(.)\1+$/.test(cnpj)) {
        return false;
    }

    let sum = 0;
    let weight = 5;
    for (let i = 0; i < 12; i++) {
        sum += parseInt(cnpj.charAt(i)) * weight--;
        if (weight < 2) {
            weight = 9;
        }
    }
    let remainder = sum % 11;
    let digit = (remainder < 2) ? 0 : (11 - remainder);
    if (digit !== parseInt(cnpj.charAt(12))) {
        return false;
    }

    sum = 0;
    weight = 6;
    for (let i = 0; i < 13; i++) {
        sum += parseInt(cnpj.charAt(i)) * weight--;
        if (weight < 2) {
            weight = 9;
        }
    }
    remainder = sum % 11;
    digit = (remainder < 2) ? 0 : (11 - remainder);
    if (digit !== parseInt(cnpj.charAt(13))) {
        return false;
    }

    return true;
}


function validateRequired(field, fieldName) {
    const value = field.value.trim();
    const errorId = "error-" + field.id;

    if (value === "") {
        displayError(errorId, `${fieldName} é obrigatório.`);
        return false;
    }
    clearError(field.id);
    return true;
}

function displayError(id, message) {
    const errorElement = document.getElementById(id);
    if (errorElement) {
        errorElement.textContent = message;
    }
}

function validateCPF(cpf) {
    cpf = cpf.replace(/[^\d]/g, '');
    if (cpf.length !== 11 || /^(.)\1+$/.test(cpf)) {
        return false;
    }

    let sum = 0;
    for (let i = 0; i < 9; i++) {
        sum += parseInt(cpf.charAt(i)) * (10 - i);
    }

    let remainder = 11 - (sum % 11);
    if (remainder === 10 || remainder === 11) {
        remainder = 0;
    }

    if (remainder !== parseInt(cpf.charAt(9))) {
        return false;
    }

    sum = 0;
    for (let i = 0; i < 10; i++) {
        sum += parseInt(cpf.charAt(i)) * (11 - i);
    }

    remainder = 11 - (sum % 11);
    if (remainder === 10 || remainder === 11) {
        remainder = 0;
    }

    if (remainder !== parseInt(cpf.charAt(10))) {
        return false;
    }

    return true;
}

function validateCEP(cep) {
    return /^\d{5}-?\d{3}$/.test(cep);
}

function validatePhoneNumber(phone) {
    return /^\(\d{2}\)\s?\d{4}\-\d{4}$/.test(phone) && phone.replace(/\D/g, '').length === 10;
}

function validateCellPhoneNumber(phone) {
    return /^\(\d{2}\)\s?9\d{4}\-\d{4}$/.test(phone) && phone.replace(/\D/g, '').length === 11;
}

function validateStrongPassword(password) {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])[A-Za-z\d!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]{8,}$/;
    return regex.test(password);
}



function validateNumber(number) {
    return /^\d+$/.test(number);
}

function validateEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

function clearGenderError() {
    const errorElement = document.getElementById("error-genderBio");
    if (errorElement) {
        errorElement.textContent = "";
    }
}


document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");

    form.addEventListener("submit", function(event) {
        let isValid = true;

        isValid = validateRequired(form.querySelector("#hospitalName"), "Nome do Hospital") && isValid;

        isValid = validateRequired(form.querySelector("#cnpj"), "CNPJ") && isValid;
        if (isValid && !validateCNPJ(form.querySelector("#cnpj").value)) {
            displayError("error-cnpj", "CNPJ inválido.");
            isValid = false;
        }


        isValid = validateRequired(form.querySelector("#domainName"), "Nome de Domínio") && isValid;
        isValid = validateRequired(form.querySelector("#hospitalStreet"), "Rua") && isValid;
        isValid = validateRequired(form.querySelector("#hospitalNeighborhood"), "Bairro") && isValid;
        isValid = validateRequired(form.querySelector("#hospitalAddressNumber"), "Número") && isValid;
        if (isValid && !validateNumber(form.querySelector("#hospitalAddressNumber").value)) {
            displayError("error-hospitalAddressNumber", "Número inválido.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#hospitalCity"), "Cidade") && isValid;

        const hospitalState = form.querySelector("#hospitalState");
        if (hospitalState.value === "") {
            displayError("error-hospitalState", "Selecione um Estado.");
            isValid = false;
        } else {
            clearError("hospitalState");
        }

        isValid = validateRequired(form.querySelector("#hospitalZip"), "CEP") && isValid;
        if (isValid && !validateCEP(form.querySelector("#hospitalZip").value)) {
            displayError("error-hospitalZip", "CEP inválido. Formato aceito: 12345-678 ou 12345678.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#hospitalPrimaryPhone"), "Telefone Principal") && isValid;
        if (isValid && !validatePhoneNumber(form.querySelector("#hospitalPrimaryPhone").value)) {
            displayError("error-hospitalPrimaryPhone", "Telefone principal inválido.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#hospitalSecondaryPhone"), "Telefone Secundário") && isValid;
        if (isValid && !validatePhoneNumber(form.querySelector("#hospitalSecondaryPhone").value)) {
            displayError("error-hospitalSecondaryPhone", "Telefone secundário inválido.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#hospitalEmail"), "Email") && isValid;
        if (isValid && !validateEmail(form.querySelector("#hospitalEmail").value)) {
            displayError("error-adminEmail", "Email inválido.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#adminFirstName"), "Primeiro Nome") && isValid;
        isValid = validateRequired(form.querySelector("#adminLastName"), "Sobrenome") && isValid;

        isValid = validateGenderSelection() && isValid;


        isValid = validateRequired(form.querySelector("#adminUsername"), "Nome de Usuário") && isValid;


        isValid = validateRequired(form.querySelector("#adminPassword"), "Senha") && isValid;
        if (isValid && !validateStrongPassword(form.querySelector("#adminPassword").value)) {
            displayError("error-adminPassword", "Senha inválida. A senha deve conter pelo menos 8 caracteres, uma letra minúscula, uma letra maiúscula, um número e um símbolo especial.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#adminPassword2"), "Senha") && isValid;
        if (isValid && form.querySelector("#adminPassword").value !== form.querySelector("#adminPassword2").value){
            displayError("error-adminPassword2", "Senhas não podem ser diferentes.");
            isValid = false;
        }


        isValid = validateRequired(form.querySelector("#adminEmail"), "Email") && isValid;
        if (isValid && !validateEmail(form.querySelector("#adminEmail").value)) {
            displayError("error-adminEmail", "Email inválido.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#adminPhone"), "Celular") && isValid;
        if (isValid && !validateCellPhoneNumber(form.querySelector("#adminPhone").value)) {
            displayError("error-adminPhone", "Celular inválido.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#adminCpf"), "CPF") && isValid;
        if (isValid && !validateCPF(form.querySelector("#adminCpf").value)) {
            displayError("error-adminCpf", "CPF inválido.");
            isValid = false;
        }

        isValid = validateRequired(form.querySelector("#adminBirthDate"), "Data de Nascimento") && isValid;

        if (!isValid) {
            event.preventDefault();
        }
    });



    const inputs = document.querySelectorAll("input, select");
    inputs.forEach(input => {
        input.addEventListener("change", function() {
            clearError(this.id);
        });
    });

    const genderOptions = document.querySelectorAll('input[name="userEntityDto.gender"]');
    genderOptions.forEach(option => {
        option.addEventListener("change", function() {
            clearGenderError();
        });
    });

    const elements = document.querySelectorAll("input, select, div");

    elements.forEach(input => {
        input.addEventListener("change", function() {
            const nextSibling = this.nextElementSibling;
            if (nextSibling && nextSibling.classList.contains("back-error")) {
                nextSibling.remove();
            }
        });
    });

});
