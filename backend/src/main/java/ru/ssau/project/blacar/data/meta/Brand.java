package ru.ssau.project.blacar.data.meta;

public enum  Brand {
    LADA,
    TOYOTA,
    BMW,
    AUDI,
    CHEVROLET;

    @Override
    public String toString() {
        return name();
    }
}
