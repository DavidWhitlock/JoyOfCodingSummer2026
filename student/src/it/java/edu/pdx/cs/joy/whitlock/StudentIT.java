package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
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
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing required student information"));
  }

  @Test
  void invokingMainWithValidArgumentsPrintsStudentDescriptionToStandardOut() {
    MainMethodResult result =
      invokeMain(Student.class, "Dave", "male", "3.64", "Algorithms", "Operating Systems", "Java");

    assertThat(result.getTextWrittenToStandardOut(),
      equalTo("Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating Systems, and Java. He says \"This class is too much work\"."
        + System.lineSeparator()));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

}
