package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Integration tests for the <code>Student</code> class's main method.
 * These tests extend <code>InvokeMainTestCase</code> which allows them
 * to easily invoke the <code>main</code> method of <code>Student</code>.
 */
class StudentIT extends InvokeMainTestCase {

  @Test
  void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class);
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing name"));
    assertThat(result.getTextWrittenToStandardOut(), is(emptyString()));
  }

  @Test
  void missingGender() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class, "Name");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing gender"));
    assertThat(result.getTextWrittenToStandardOut(), is(emptyString()));
  }

  @Test
  void missingGPA() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class, "Name", "female");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing GPA"));
    assertThat(result.getTextWrittenToStandardOut(), is(emptyString()));
  }

  @Test
  void studentTakingZeroClassesIsPrintedToStandardOut() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class, "Name", "female", "3.45");
    assertThat(result.getTextWrittenToStandardError(), is(emptyString()));
    assertThat(result.getTextWrittenToStandardOut(), containsString("Name has a GPA of 3.45 and is taking 0 classes"));
  }

  @Test
  void negativeGPAPrintsMessageToStandardError() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class, "Dave", "male", "-3.64", "Algorithms", "Operating Systems", "Java");
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid GPA: -3.64.  Valid GPAS are in the range of 0.0-4.0"));
    assertThat(result.getTextWrittenToStandardOut(), is(emptyString()));
  }

  @Test
  void nonNumericGPAPrintsMessageToStandardError() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class, "Dave", "male", "non-numeric", "Algorithms", "Operating Systems", "Java");
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid GPA: non-numeric.  GPA must be a number."));
    assertThat(result.getTextWrittenToStandardOut(), is(emptyString()));
  }
}
