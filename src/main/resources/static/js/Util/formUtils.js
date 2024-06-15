export class AddressForms {
    static STATE_DICTIONARY = {
        "AC": "Acre",
        "AL": "Alagoas",
        "AP": "Amapá",
        "AM": "Amazonas",
        "BA": "Bahia",
        "CE": "Ceará",
        "DF": "Distrito Federal",
        "ES": "Espírito Santo",
        "GO": "Goiás",
        "MA": "Maranhão",
        "MT": "Mato Grosso",
        "MS": "Mato Grosso do Sul",
        "MG": "Minas Gerais",
        "PA": "Pará",
        "PB": "Paraíba",
        "PR": "Paraná",
        "PE": "Pernambuco",
        "PI": "Piauí",
        "RJ": "Rio de Janeiro",
        "RN": "Rio Grande do Norte",
        "RS": "Rio Grande do Sul",
        "RO": "Rondônia",
        "RR": "Roraima",
        "SC": "Santa Catarina",
        "SP": "São Paulo",
        "SE": "Sergipe",
        "TO": "Tocantins"
    };

    static renderStateOptions(selectId) {

        const selectTag = document.getElementById(selectId);
        let states = Object.values(this.STATE_DICTIONARY);
        states.forEach(function (state) {
            let optionTag = document.createElement('option');

            optionTag.text = state;
            optionTag.value = state;
            selectTag.add(optionTag);
        });

    }

    /**
     * @typedef {Object} ViaCepAddress
     * @property {string} bairro
     * @property {string} cep
     * @property {string} complemento
     * @property {string} ddd
     * @property {string} gia
     * @property {string} ibge
     * @property {string} localidade
     * @property {string} logradouro
     * @property {string} siafi
     * @property {string} uf
     */


    /**
     * @param {string} cep
         * @return {Promise<ViaCepAddress>}
     * */
    static async getAddress(cep) {
        try {
            const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
            if (!response.ok) {
                throw new Error('Erro ao buscar o endereço: ' + response.statusText);
            }
            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Erro ao buscar o endereço:', error);
            throw error;
        }
    }

}

