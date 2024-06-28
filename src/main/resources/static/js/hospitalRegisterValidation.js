document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");


    function clearError(id) {
        const errorId = "error-" + id;
        const errorElement = document.getElementById(errorId);
        if (errorElement) {
            errorElement.style.display = "none";
        }
    }

    function displayError(id, message) {
        const errorElement = document.getElementById(id);
        if (errorElement) {
            errorElement.style.display = "block";
            errorElement.textContent = message;
        }
    }

    function validateGenderSelection() {
        const genderOptions = document.querySelectorAll('input[name="registerEntityDto.gender"]');
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
        const genderBackError = document.getElementById("gender-back-error");

        if (errorElement) {
            errorElement.textContent = "";
        }

        if (genderBackError){
            genderBackError.remove();
        }
    }

    function deleteNexSibling(e) {
        const nextSibling = e.target.nextElementSibling;
        if (nextSibling && nextSibling.classList.contains("back-error")) {
            nextSibling.remove();
        }
    }

    const inputs = form.querySelectorAll("input, select");

    const subdomainResult = document.getElementById("subdomain-result");

    const subdomain = document.getElementById("subdomain");

    inputs.forEach(input => {
        input.addEventListener("blur", function() {
            switch (input.id) {
                case "hospitalName":
                    validateRequired(input, "Nome do Hospital");
                    break;
                case "cnpj":
                    if (validateRequired(input, "CNPJ") && !validateCNPJ(input.value)) {
                        displayError("error-cnpj", "CNPJ inválido.");
                    }
                    break;
                case "subdomain":
                    validateRequired(input, "Nome de Subdomínio");
                    subdomainResult.style.display = "none";
                    break;
                case "hospitalStreet":
                    validateRequired(input, "Rua");
                    break;
                case "hospitalNeighborhood":
                    validateRequired(input, "Bairro");
                    break;
                case "hospitalAddressNumber":
                    if (validateRequired(input, "Número") && !validateNumber(input.value)) {
                        displayError("error-hospitalAddressNumber", "Número inválido.");
                    }
                    break;
                case "hospitalCity":
                    validateRequired(input, "Cidade");
                    break;
                case "hospitalState":
                    if (input.value === "") {
                        displayError("error-hospitalState", "Selecione um Estado.");
                    } else {
                        clearError("hospitalState");
                    }
                    break;
                case "hospitalZip":
                    if (validateRequired(input, "CEP") && !validateCEP(input.value)) {
                        displayError("error-hospitalZip", "CEP inválido. Formato aceito: 12345-678 ou 12345678.");
                    }
                    break;
                case "hospitalPrimaryPhone":
                    if (validateRequired(input, "Telefone Principal") && !validatePhoneNumber(input.value)) {
                        displayError("error-hospitalPrimaryPhone", "Telefone principal inválido.");
                    }
                    break;
                case "hospitalSecondaryPhone":
                    if (validateRequired(input, "Telefone Secundário") && !validatePhoneNumber(input.value)) {
                        displayError("error-hospitalSecondaryPhone", "Telefone secundário inválido.");
                    }
                    break;
                case "hospitalEmail":
                    if (validateRequired(input, "Email") && !validateEmail(input.value)) {
                        displayError("error-hospitalEmail", "Email inválido.");
                    }
                    break;
                case "adminFirstName":
                    validateRequired(input, "Primeiro Nome");
                    break;
                case "adminLastName":
                    validateRequired(input, "Sobrenome");
                    break;
                case "adminUsername":
                    validateRequired(input, "Nome de Usuário");
                    break;
                case "adminPassword":
                    if (validateRequired(input, "Senha") && !validateStrongPassword(input.value)) {
                        displayError("error-adminPassword", "Senha inválida. A senha deve conter pelo menos 8 caracteres, uma letra minúscula, uma letra maiúscula, um número e um símbolo especial.");
                    }
                    break;
                case "adminPassword2":
                    if (validateRequired(input, "Senha") && form.querySelector("#adminPassword").value !== input.value) {
                        displayError("error-adminPassword2", "Senhas não podem ser diferentes.");
                    }
                    break;
                case "adminEmail":
                    if (validateRequired(input, "Email") && !validateEmail(input.value)) {
                        displayError("error-adminEmail", "Email inválido.");
                    }
                    break;
                case "adminPhone":
                    if (validateRequired(input, "Celular") && !validateCellPhoneNumber(input.value)) {
                        displayError("error-adminPhone", "Celular inválido.");
                    }
                    break;
                case "adminCpf":
                    if (validateRequired(input, "CPF") && !validateCPF(input.value)) {
                        displayError("error-adminCpf", "CPF inválido.");
                    }
                    break;
                case "adminBirthDate":
                    validateRequired(input, "Data de Nascimento");
                    break;
                default:
                    break;
            }

            clearGenderError();
        });
    });


    subdomain.addEventListener("focus", (e) =>{
        subdomainResult.style.display = "block";
        if (e.target.value === ""){
            subdomainResult.textContent = "www.[SUBDOMAIN].saude-mais.com.br"
        }
    });

    subdomain.addEventListener("input", (e) => {
        subdomainResult.textContent = subdomainResult.textContent.replace(subdomainResult.textContent, `www.${e.target.value}.saude-mais.com.br`.toLocaleLowerCase());
    })

    const elements = document.querySelectorAll("input, select, div");

    elements.forEach(input => {
        input.addEventListener("blur", function (e) {deleteNexSibling(e)});
        input.addEventListener("change", function(e) {deleteNexSibling(e)});
        input.addEventListener("focus", function(ev) {
            deleteNexSibling(ev);
        });
    });

    const genderOptions = document.querySelectorAll('input[name="registerEntityDto.gender"]');

    genderOptions.forEach(input => {
        input.addEventListener("change", function() {
            clearGenderError();
        })
    });




    form.addEventListener("submit", function (e) {
        let isValid = true;
        const inputs = form.querySelectorAll("input, select");

        inputs.forEach((input) => {
            validateRequired(input, "Este Campo")
        });

        isValid = validateGenderSelection();

        if (!isValid){
            window.scrollTo(0, 0);
            e.preventDefault();
        }
    })
});
