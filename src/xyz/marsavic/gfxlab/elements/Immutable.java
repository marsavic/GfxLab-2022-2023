package xyz.marsavic.gfxlab.elements;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** A class annotation with this is "purely functional". This means that it effectively does not have any modifiable
 * state, and only provides methods whose result is a function only of its parameters and constructor parameters.
 * - We usually want all these parameters to also be pure or immutable.
 * - We allow a method to write to a buffer passed as a parameter, that is considered to be "a result" of the function.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Immutable {
}
