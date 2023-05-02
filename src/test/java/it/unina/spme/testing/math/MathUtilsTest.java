package it.unina.spme.testing.math;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MathUtilsTest {
    @Test
    void testDivisorsOfEight() {
        List<Integer> divisors = MathUtils.getDivisors(8);
        List<Integer> expected = Arrays.asList(1,2,4,8);
        assertEquals(expected,divisors); // first attempt!
    }

    @Test
    void testDivisorsOfEightWithoutHamcrest()  {
        Set<Integer> expectedDivisors = Set.of(1, 2, 4, 8);

        List<Integer> divisors = MathUtils.getDivisors(8);

        if (expectedDivisors.size() != divisors.size()) fail();
        for (Integer divisor: divisors)
            if (!expectedDivisors.contains(divisor))
                fail();
    }

    @Test
    void testDivisorsOfEightWithHamcrest() {
        List<Integer> divisors = MathUtils.getDivisors(8);

        assertThat(divisors, containsInAnyOrder(1, 2, 4, 8));
    }
}