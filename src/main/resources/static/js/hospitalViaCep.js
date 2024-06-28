import { AddressForms } from "./Util/formUtils.js";
import { renderErrorFriendly } from "./Util/ErrorFriendly.js";

document.addEventListener("DOMContentLoaded", event => {
    AddressForms.renderStateOptions("hospitalState");

    const cepInput = document.getElementById("hospitalZip");

    cepInput.addEventListener("change", async event => {
        try {
            const address = await AddressForms.getAddress(event.target.value);
            document.getElementById("hospitalStreet").value = address.logradouro;
            document.getElementById("hospitalCity").value = address.localidade;
            document.getElementById("hospitalState").value = AddressForms.STATE_DICTIONARY[address.uf];
            document.getElementById("hospitalNeighborhood").value = address.bairro;

        } catch (err) {
            renderErrorFriendly("hospitalZip", "Erro ao buscar cep", "div");
        }
    });
});
