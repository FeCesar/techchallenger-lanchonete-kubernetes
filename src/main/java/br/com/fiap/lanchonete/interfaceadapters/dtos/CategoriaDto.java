package br.com.fiap.lanchonete.interfaceadapters.dtos;


import lombok.Getter;

@Getter
public class CategoriaDto {
    private Integer id;
    private String nome;
    public CategoriaDto() {
    }
    public CategoriaDto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public static Builder builder() {
        return new Builder();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static class Builder {
        private Integer id;
        private String nome;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }
        public CategoriaDto build() {
            return new CategoriaDto(id, nome);
        }
    }
}