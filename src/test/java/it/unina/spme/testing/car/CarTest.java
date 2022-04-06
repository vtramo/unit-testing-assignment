package it.unina.spme.testing.car;

import org.junit.jupiter.api.Test;

import java.util.List;

import static it.unina.spme.testing.IsPalindrome.palindrome;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class CarTest {

    @Test
    public void atLeastOneCarShouldHaveModelNameStartingWithP(){
        // assert that at least one car in Car.getCars() has a model name starting with "P"
    }

    @Test
    public void atLeastOneCarHasAPalindromeModelName(){
        // assert that at least one car in Car.getCars() has a palindrome model name
    }

    @Test
    public void carsShouldContainAtLeastOneGreenVehicle() {
        // assert that at least one of the cars in Car.getCars() contains a vehicle whose fuel
        // is either "Hydrogen" or "Electric"
    }

    @Test
    public void carsShouldNotHaveLessThan50Hp() {
        //assert that Car.getCars() does not contain any Car with less than 50hp
    }

    @Test
    public void carsWithLessPowerfulEnginesShouldBeRepresented(){
        //assert that Car.getCars() contains at least one car with less than 100hp
    }

    @Test
    public void carsWithFossilFuelsShouldBeRepresented() {
        // assert that Car.getCars() contains at least one car for each of the following fuel types:
        //  Gasoline, Diesel, Methane
    }

    @Test
    public void carsShouldHaveReasonableHp(){
        // assert that Car.getCars() contains only cars whose hp property has values in the range [50, 1700]
    }

    @Test
    public void onlyGasolineCarsShouldHaveMoreThan200Hp(){
        //assert that, in Car.getCars(), only gasoline cars are allowed to have more than 200hp
    }

    @Test
    public void carsShouldRepresentAllPossibleFuelTypes(){
        // assert that Car.getCars() contains at least one car for each fuel type in
        // "Methane", "Gasoline", "Diesel", "Electric", "Hydrogen"
        // Help: check out the difference between hasItems() and containsInAnyOrder() in the Hamcrest docs!
    }
}
