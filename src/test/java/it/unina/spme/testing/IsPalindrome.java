package it.unina.spme.testing;

import org.hamcrest.*;

public class IsPalindrome extends TypeSafeMatcher<String>{

    @Override
    public void describeTo(Description description) {
        description.appendText("a palindrome string");

    }

    @Override
    protected boolean matchesSafely(String item) {
        StringBuilder sb = new StringBuilder(item).reverse();
        return sb.toString().equalsIgnoreCase(item);
    }

    public static IsPalindrome palindrome() {
        return new IsPalindrome();
    }

}
