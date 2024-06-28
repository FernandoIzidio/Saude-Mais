document.addEventListener("DOMContentLoaded", function() {
    const subdomainInput = document.getElementById("subdomain");
    let cpfInput = document.getElementById("adminCpf");
    let cnpjInput = document.getElementById("cnpj");
    let cep = document.getElementById("hospitalZip");
    let cellphone = document.getElementById("adminPhone");
    let primaryPhone = document.getElementById("hospitalPrimaryPhone");
    let secondaryPhone = document.getElementById("hospitalSecondaryPhone");

    cpfInput.addEventListener("input", function() {
        let value = this.value.replace(/\D/g, "");
        value = value.replace(/^(\d{3})(\d)/, "$1.$2");
        value = value.replace(/^(\d{3})\.(\d{3})(\d)/, "$1.$2.$3");
        value = value.replace(/\.(\d{3})(\d)/, ".$1-$2");
        this.value = value;
    });

    cnpjInput.addEventListener("input", function() {
        let value = this.value.replace(/\D/g, "");
        value = value.replace(/^(\d{2})(\d)/, "$1.$2");
        value = value.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
        value = value.replace(/\.(\d{3})(\d)/, ".$1/$2");
        value = value.replace(/(\d{4})(\d)/, "$1-$2");
        this.value = value;
    });

    cep.addEventListener("input", function() {
        let value = this.value.replace(/\D/g, "");
        value = value.replace(/^(\d{5})(\d)/, "$1-$2");
        this.value = value;
    });

    cellphone.addEventListener("input", function() {
        let value = this.value.replace(/\D/g, "");
        value = value.replace(/^(\d{2})(\d)/, "($1) $2");
        value = value.replace(/(\d{4,5})(\d)/, "$1-$2");
        this.value = value;
    });

    primaryPhone.addEventListener("input", function() {
        let value = this.value.replace(/\D/g, "");
        value = value.replace(/^(\d{2})(\d)/, "($1) $2");
        value = value.replace(/(\d{4})(\d)/, "$1-$2");
        this.value = value;
    });

    secondaryPhone.addEventListener("input", function() {
        let value = this.value.replace(/\D/g, "");
        value = value.replace(/^(\d{2})(\d)/, "($1) $2");
        value = value.replace(/(\d{4})(\d)/, "$1-$2");
        this.value = value;
    });


    subdomainInput.addEventListener("input", function() {
        let value = this.value;

        value = value.replace(/[^\w-]/g, "");

        if (value.length > 70) {
            value = value.slice(0, 70);
        }

        this.value = value;
    });

});
