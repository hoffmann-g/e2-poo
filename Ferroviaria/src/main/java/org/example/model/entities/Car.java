package org.example.model.entities;

public abstract class Car {

    private int id;
    private Trem tremAlocado;

    public Car(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    protected Trem getTremAlocado() {
        return tremAlocado;
    }

    protected void setTremAlocado(Trem tremAlocado) {
        this.tremAlocado = tremAlocado;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object o);
}
