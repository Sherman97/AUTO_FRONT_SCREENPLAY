package com.sofka.reservas.model;

public record TestUser(String fullName, String email, String password) {

    public static TestUser collaborator(String suffix) {
        return new TestUser(
            "Colaborador Screenplay " + suffix,
            "colaborador.screenplay." + suffix + "@sofka.com.co",
            "Clave123"
        );
    }

    public static TestUser existingCollaborator() {
        return new TestUser("Pepito", "pepito@sofka.com.co", "123456");
    }
}
