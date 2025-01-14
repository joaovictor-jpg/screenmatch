package br.com.jota.screenmetch.screenmetch.models.enums;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime"),
    ADVETURE("Adventure", "Aventura");

    private String categoriaOmdb;
    private String categoriaPTBR;

    Categoria(String categoriaOmdb, String categoriaPTBR) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPTBR = categoriaPTBR;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromStringPtBr(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPTBR.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
