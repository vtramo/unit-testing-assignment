package it.unina.spme.testing.math;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathUtilsTest {
    @Test
    void testDivisorsOfEight() {
        List<Integer> divisors = MathUtils.getDivisors(8);
        List<Integer> expected = Arrays.asList(1,2,4,8);
        assertEquals(expected,divisors); // first attempt!
    }
}
