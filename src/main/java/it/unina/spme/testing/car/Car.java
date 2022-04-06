package it.unina.spme.testing.car;


import java.util.ArrayList;
import java.util.List;

public class Car {
    private String model;
    private int hp;
    private String fuel;

    Car(String model, int hp, String fuel){
        this.model = model;
        this.hp = hp;
        this.fuel = fuel;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public String getFuel() {
        return fuel;
    }
    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    static List<Car> getCars(){
        List<Car> list = new ArrayList<Car>();
        list.add(new Car("Trueno", 230,"Gasoline"));
        list.add(new Car("MX-30", 150, "Electric"));
        list.add(new Car("Nexo",  160, "Hydrogen"));
        list.add(new Car("Focus", 90, "Diesel"));
        list.add(new Car("Panda",80, "Methane"));
        list.add(new Car("Jesko",1603, "Gasoline"));
        list.add(new Car("Civic",220, "Gasoline"));
        return list;
    }
}