package com.saude.mais.agendamento.Entities.User;

public enum Gender {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    INTERSEXO("Intersexo");

    public String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
