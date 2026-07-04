package edu.pdx.cs.joy.whitlock;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  @Test
  void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = new Student(name, new ArrayList<>(), 0.0, "other");
    assertThat(pat.getName(), equalTo(name));
  }

  @Test
  void allStudentsSayThisClassIsTooMuchWork() {
    Student student = new Student("Pat", new ArrayList<>(), 0.0, "other");
    assertThat(student.says(), equalTo("This class is too much work"));
  }

  @Test
  void toStringDescribesMaleStudentTakingThreeClasses() {
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("Operating Systems");
    classes.add("Java");

    Student student = new Student("Dave", classes, 3.64, "male");

    assertThat(student.toString(), equalTo("Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating Systems, and Java. He says \"This class is too much work\"."));
  }

  @Test
  void toStringDescribesStudentTakingZeroClasses() {
    Student student = new Student("Lisa", new ArrayList<>(), 3.42, "female");

    assertThat(student.toString(), equalTo("Lisa has a GPA of 3.42 and is taking 0 classes. She says \"This class is too much work\"."));
  }

  @Test
  void toStringDescribesStudentTakingOneClass() {
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Java");

    Student student = new Student("Pat", classes, 3.50, "other");

    assertThat(student.toString(), equalTo("Pat has a GPA of 3.50 and is taking 1 class: Java. They say \"This class is too much work\"."));
  }

  @Test
  void toStringDescribesStudentTakingTwoClasses() {
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Java");
    classes.add("Operating Systems");

    Student student = new Student("Pat", classes, 3.50, "other");

    assertThat(student.toString(), equalTo("Pat has a GPA of 3.50 and is taking 2 classes: Java and Operating Systems. They say \"This class is too much work\"."));
  }

  @Test
  void studentCanHaveMinimumGpa() {
    Student student = new Student("Pat", new ArrayList<>(), 0.0, "male");

    assertThat(student.toString(), equalTo("Pat has a GPA of 0.00 and is taking 0 classes. He says \"This class is too much work\"."));
  }

  @Test
  void studentCanHaveMaximumGpa() {
    Student student = new Student("Pat", new ArrayList<>(), 4.0, "female");

    assertThat(student.toString(), equalTo("Pat has a GPA of 4.00 and is taking 0 classes. She says \"This class is too much work\"."));
  }

  @Test
  void creatingStudentWithBlankNameThrowsIllegalArgumentException() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
      () -> new Student("", new ArrayList<>(), 3.64, "male"));

    assertThat(ex.getMessage(), equalTo("Student name is required"));
  }

  @Test
  void creatingStudentWithUnsupportedGenderThrowsIllegalArgumentException() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
      () -> new Student("Pat", new ArrayList<>(), 3.64, "unknown"));

    assertThat(ex.getMessage(), equalTo("Gender must be male, female, or other"));
  }

  @Test
  void creatingStudentWithNegativeGpaThrowsIllegalArgumentException() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
      () -> new Student("Pat", new ArrayList<>(), -0.01, "female"));

    assertThat(ex.getMessage(), equalTo("GPA must be between 0.0 and 4.0"));
  }

  @Test
  void creatingStudentWithGpaGreaterThanFourThrowsIllegalArgumentException() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
      () -> new Student("Pat", new ArrayList<>(), 4.01, "other"));

    assertThat(ex.getMessage(), equalTo("GPA must be between 0.0 and 4.0"));
  }

}
