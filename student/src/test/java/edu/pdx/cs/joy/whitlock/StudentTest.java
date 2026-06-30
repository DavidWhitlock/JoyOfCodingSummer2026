package edu.pdx.cs.joy.whitlock;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  private static @NonNull Student createStudentNamed(String name) {
    return new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
  }


  @Test
  void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = createStudentNamed(name);
    assertThat(pat.getName(), equalTo(name));
  }

  @Test
  void allStudentsSayThisClassIsTooMuchWork() {
    Student student = createStudentNamed("Pat");
    assertThat(student.says(), equalTo("This class is too much work"));
  }

  @Disabled
  @Test
  void daveStudent() {
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("Operating Systems");
    classes.add("Java");

    Student dave = new Student("Dave", classes, 3.64, "male");
    assertThat(dave.toString(), equalTo("Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating Systems, and Java. He says \"This class is too much work\"."));
  }

  @Test
  void toStringContainsStudentName() {
    String name = "Pat";
    Student student = createStudentNamed(name);
    assertThat(student.toString(), containsString(name));
  }

  @Test
  void toStringContainsGPA() {
    double gpa = 3.64;
    Student student = new Student("Name", new ArrayList<>(), gpa, "male");
    assertThat(student.toString(), containsString(" has a GPA of " + gpa));
  }

  @Test
  void negativeGPAThrowsInvalidRangeException() {
    double invalidGPA = -1.0;
    try {
      new Student("Name", new ArrayList<>(), invalidGPA, "male");
      fail("Should have thrown an InvalidGPAException");

    } catch (InvalidGPAException e) {
      assertThat(e.getInvalidGPA(), equalTo(invalidGPA));
    }
  }
}
