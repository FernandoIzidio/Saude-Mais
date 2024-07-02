export class AddressForms {
    static STATE_DICTIONARY = {
        "AC": "ACRE",
        "AL": "ALAGOAS",
        "AP": "AMAPA",
        "AM": "AMAZONAS",
        "BA": "BAHIA",
        "CE": "CEARA",
        "DF": "DISTRITO_FEDERAL",
        "ES": "ESPIRITO_SANTO",
        "GO": "GOIAS",
        "MA": "MARANHAO",
        "MT": "MATO_GROSSO",
        "MS": "MATO_GROSSO_DO_SUL",
        "MG": "MINAS_GERAIS",
        "PA": "PARA",
        "PB": "PARAIBA",
        "PR": "PARANA",
        "PE": "PERNAMBUCO",
        "PI": "PIAUI",
        "RJ": "RIO_DE_JANEIRO",
        "RN": "RIO_GRANDE_DO_NORTE",
        "RS": "RIO_GRANDE_DO_SUL",
        "RO": "RONDONIA",
        "RR": "RORAIMA",
        "SC": "SANTA_CATARINA",
        "SP": "SAO_PAULO",
        "SE": "SERGIPE",
        "TO": "TOCANTINS"
    };

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

