package it.unina.spme.testing.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static it.unina.spme.testing.IsPalindrome.palindrome;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    List<Car> cars;

    @BeforeEach
    void getCars() {
        cars = Car.getCars();
    }

    @Test
    public void atLeastOneCarShouldHaveModelNameStartingWithP() {
        // assert that at least one car in Car.getCars() has a model name starting with "P"

        for (Car car: cars)
            if (car.getModel().startsWith("P"))
                return;
        fail();
    }

    @Test
    public void atLeastOneCarShouldHaveModelNameStartingWithPWithHamcrest() {
        // assert that at least one car in Car.getCars() has a model name starting with "P" with Hamcrest

        assertThat(cars, hasItem(hasProperty("model", startsWith("P"))));
    }

    @Test
    public void atLeastOneCarHasAPalindromeModelName() {
        // assert that at least one car in Car.getCars() has a palindrome model name

        for (Car car: cars) {
            String model = car.getModel().toLowerCase();

            String reverseModel = new StringBuilder(model)
              .reverse()
              .toString()
              .toLowerCase();

            if (model.equals(reverseModel)) return;
        }
        fail();
    }

    @Test
    public void atLeastOneCarHasAPalindromeModelNameWithHamcrest() {
        // assert that at least one car in Car.getCars() has a palindrome model name with Hamcrest

        assertThat(cars, hasItem(hasProperty("model", is(palindrome()))));
    }

    @Test
    public void carsShouldContainAtLeastOneGreenVehicle() {
        // assert that at least one of the cars in Car.getCars() contains a vehicle whose fuel
        // is either "Hydrogen" or "Electric"

        for (Car car: cars) {
            String fuel = car.getFuel();
            if ("Hydrogen".equals(fuel) || "Electric".equals(fuel))
                return;
        }
        fail();
    }

    @Test
    public void carsShouldContainAtLeastOneGreenVehicleWithHamcrest() {
        // assert that at least one of the cars in Car.getCars() contains a vehicle whose fuel
        // is either "Hydrogen" or "Electric" with Hamcrest

        assertThat(cars, hasItem(hasProperty("fuel", is(in(Set.of("Electric", "Hydrogen"))))));
    }

    @Test
    public void carsShouldNotHaveLessThan50Hp() {
        // assert that Car.getCars() does not contain any Car with less than 50hp

        for (Car car: cars)
            if (car.getHp() < 50)
                fail();
    }

    @Test
    public void carsShouldNotHaveLessThan50HpWithStream() {
        // assert that Car.getCars() does not contain any Car with less than 50hp

        Function<Car, Executable> toExecutableAssertion = car -> ( () -> assertTrue(car.getHp() >= 50) );
        Stream<Executable> assertions = cars.stream().map(toExecutableAssertion);
        assertAll(assertions);
    }

    @Test
    public void carsShouldNotHaveLessThan50HpWithHamcrest() {
        // assert that Car.getCars() does not contain any Car with less than 50hp with Hamcrest

        assertThat(cars, not(hasItem(hasProperty("hp", is(lessThan(50))))));
    }

    @Test
    public void carsWithLessPowerfulEnginesShouldBeRepresented(){
        //assert that Car.getCars() contains at least one car with less than 100hp

        for (Car car: cars)
            if (car.getHp() < 100)
                return;
        fail();
    }

    @Test
    public void carsWithLessPowerfulEnginesShouldBeRepresentedWithHamcrest() {
        //assert that Car.getCars() contains at least one car with less than 100hp with Hamcrest

        assertThat(cars, hasItem(hasProperty("hp", is(lessThan(100)))));
    }


    @Test
    public void carsWithFossilFuelsShouldBeRepresented() {
        // assert that Car.getCars() contains at least one car for each of the following fuel types:
        //  Gasoline, Diesel, Methane

        Set<String> fuelTypes = new HashSet<>(Arrays.asList("Gasoline", "Diesel", "Methane"));
        for (Car car: cars)
            if (fuelTypes.remove(car.getFuel()))
                if (fuelTypes.isEmpty()) return;
        fail();
    }

    @Test
    public void carsWithFossilFuelsShouldBeRepresentedWithHamcrest() {
        // assert that Car.getCars() contains at least one car for each of the following fuel types:
        //  Gasoline, Diesel, Methane with Hamcrest

        assertThat(cars, hasItems(
          hasProperty("fuel", is(equalTo("Gasoline"))),
          hasProperty("fuel", is(equalTo("Diesel"))),
          hasProperty("fuel", is(equalTo("Methane")))
        ));
    }

    @Test
    public void carsShouldHaveReasonableHp() {
        // assert that Car.getCars() contains only cars whose hp property has values in the range [50, 1700]

        for (Car car: cars) {
            int hp = car.getHp();
            if (hp < 50 || hp > 1700) {
                fail();
            }
        }
    }

    @Test
    public void carsShouldHaveReasonableHpWithHamcrest() {
        // assert that Car.getCars() contains only cars whose hp property has values in the range [50, 1700] with Hamcrest

        assertThat(cars, not(hasItem(hasProperty("hp", either(is(lessThan(50))).or(is(greaterThan(1700)))))));
    }


    @Test
    public void onlyGasolineCarsShouldHaveMoreThan200Hp() {
        // assert that, in Car.getCars(), only gasoline cars are allowed to have more than 200hp

        for (Car car: cars) {
            int hp = car.getHp();
            if (hp > 200 && !("Gasoline".equals(car.getFuel()))) {
                fail();
            }
        }
    }

    @Test
    public void onlyGasolineCarsShouldHaveMoreThan200HpWithHamcrest() {
        // assert that, in Car.getCars(), only gasoline cars are allowed to have more than 200hp with Hamcrest

        assertThat(cars, not(hasItem(
          both(hasProperty("hp", is(greaterThan(200))))
            .and(hasProperty("fuel", is(not(equalTo("Gasoline"))))))));
    }

    @Test
    public void carsShouldRepresentAllPossibleFuelTypes() {
        // assert that Car.getCars() contains at least one car for each fuel type in
        // "Methane", "Gasoline", "Diesel", "Electric", "Hydrogen"
        // Help: check out the difference between hasItems() and containsInAnyOrder() in the Hamcrest docs! with hamcrest

        Set<String> fuelTypes = new HashSet<>(Arrays.asList("Methane", "Gasoline", "Diesel", "Electric", "Hydrogen"));
        for (Car car: cars)
            if (fuelTypes.remove(car.getFuel()))
                if (fuelTypes.isEmpty()) return;
        fail();
    }

    @Test
    public void carsShouldRepresentAllPossibleFuelTypesWithHamcrest() {
        // assert that Car.getCars() contains at least one car for each fuel type in
        // "Methane", "Gasoline", "Diesel", "Electric", "Hydrogen"
        // Help: check out the difference between hasItems() and containsInAnyOrder() in the Hamcrest docs!

        assertThat(cars, hasItems(
          hasProperty("fuel", is(equalTo("Gasoline"))),
          hasProperty("fuel", is(equalTo("Diesel"))),
          hasProperty("fuel", is(equalTo("Methane"))),
          hasProperty("fuel", is(equalTo("Electric"))),
          hasProperty("fuel", is(equalTo("Hydrogen")))
        ));
    }
}