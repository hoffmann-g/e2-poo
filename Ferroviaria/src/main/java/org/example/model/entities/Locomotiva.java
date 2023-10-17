package org.example.model.entities;

import java.util.Objects;

public class Locomotiva extends Car{

    private double pesoMaximo; // inserido no constructor
    private int limiteVagoes = 5;

    public Locomotiva(int id, double pesoMaximo) {
        super(id);
        this.pesoMaximo = pesoMaximo;
    }

    public int getLimiteVagoes() {
        return limiteVagoes;
    }

    @Override
    public String toString() {
        return "L" + Integer.toString(getId());
    }

    /*
     * Metodos 'equals' e 'hashCode' sao necessarios para comparar uma locomotiva
     * com outra utilizando
     * seu ID, ao inves de seu pointer na memoria;
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Locomotiva that = (Locomotiva) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
