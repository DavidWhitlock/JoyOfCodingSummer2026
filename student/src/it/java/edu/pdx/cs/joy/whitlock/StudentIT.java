package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
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

  @Test
  void invokingMainWithOnlyNamePrintsMissingGenderToStandardError() {
    MainMethodResult result = invokeMain(Student.class, "Dave");

    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing gender"));
  }

  @Test
  void invokingMainWithOnlyNameAndGenderPrintsMissingGpaToStandardError() {
    MainMethodResult result = invokeMain(Student.class, "Dave", "male");

    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing GPA"));
  }

  @Test
  void invokingMainWithBlankNamePrintsValidationMessageToStandardError() {
    MainMethodResult result = invokeMain(Student.class, "", "male", "3.64", "Algorithms");

    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), containsString("Student name is required"));
  }

  @Test
  void invokingMainWithBlankGenderPrintsValidationMessageToStandardError() {
    MainMethodResult result = invokeMain(Student.class, "Dave", "", "3.64", "Algorithms");

    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), containsString("Gender must be male, female, or other"));
  }

  @Test
  void invokingMainWithNonNumericGpaPrintsUserFriendlyMessageToStandardError() {
    MainMethodResult result = invokeMain(Student.class, "Dave", "male", "not-a-number", "Algorithms");

    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), containsString("GPA must be a number"));
    assertThat(result.getTextWrittenToStandardError(), containsString(System.lineSeparator()));
    assertThat(result.getTextWrittenToStandardError(), not(containsString("NumberFormatException")));
  }

  @Test
  void invokingMainWithOutOfRangeGpaPrintsValidationMessageToStandardError() {
    MainMethodResult result = invokeMain(Student.class, "Dave", "male", "4.01", "Algorithms");

    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), containsString("GPA must be between 0.0 and 4.0"));
    assertThat(result.getTextWrittenToStandardError(), not(containsString("IllegalArgumentException")));
  }

  @Test
  void invokingMainWithUnsupportedGenderPrintsValidationMessageToStandardError() {
    MainMethodResult result = invokeMain(Student.class, "Dave", "unknown", "3.64", "Algorithms");

    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), containsString("Gender must be male, female, or other"));
    assertThat(result.getTextWrittenToStandardError(), not(containsString("IllegalArgumentException")));
  }

  @Test
  void invokingMainWithNoClassesPrintsStudentDescriptionToStandardOut() {
    MainMethodResult result = invokeMain(Student.class, "Dave", "male", "3.64");

    assertThat(result.getTextWrittenToStandardOut(),
      equalTo("Dave has a GPA of 3.64 and is taking 0 classes. He says \"This class is too much work\"."
        + System.lineSeparator()));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

  @Test
  void invokingMainWithUpperCaseGenderStillPrintsCorrectPronoun() {
    MainMethodResult result = invokeMain(Student.class, "Lisa", "FEMALE", "3.50", "Java");

    assertThat(result.getTextWrittenToStandardOut(),
      equalTo("Lisa has a GPA of 3.50 and is taking 1 class: Java. She says \"This class is too much work\"."
        + System.lineSeparator()));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

  @Test
  void invokingMainWithOtherGenderStillPrintsTheySay() {
    MainMethodResult result = invokeMain(Student.class, "Pat", "Other", "3.50", "Java");

    assertThat(result.getTextWrittenToStandardOut(),
      equalTo("Pat has a GPA of 3.50 and is taking 1 class: Java. They say \"This class is too much work\"."
        + System.lineSeparator()));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

  @Test
  void invokingMainWithMinimumGpaPrintsStudentDescriptionToStandardOut() {
    MainMethodResult result = invokeMain(Student.class, "Pat", "male", "0.0");

    assertThat(result.getTextWrittenToStandardOut(),
      equalTo("Pat has a GPA of 0.00 and is taking 0 classes. He says \"This class is too much work\"."
        + System.lineSeparator()));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

  @Test
  void invokingMainWithMaximumGpaPrintsStudentDescriptionToStandardOut() {
    MainMethodResult result = invokeMain(Student.class, "Pat", "female", "4.0");

    assertThat(result.getTextWrittenToStandardOut(),
      equalTo("Pat has a GPA of 4.00 and is taking 0 classes. She says \"This class is too much work\"."
        + System.lineSeparator()));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

}
