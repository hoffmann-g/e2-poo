package org.example.model.entities;

import java.util.Objects;

public class Vagao extends Car{

    private double capacidadeCarga; // valor inserido no constructor

    public Vagao(int id, double capacidadeCarga){
        super(id);
        this.capacidadeCarga = capacidadeCarga;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    @Override
    public String toString() {
        return "V" + Integer.toString(getId());
    }

    /*
    Metodos 'equals' e 'hashCode' sao necessarios para comparar um vagao com outro utilizando
    seu ID, ao inves de seu pointer na memoria;
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vagao vagao = (Vagao) o;
        return getId() == vagao.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
