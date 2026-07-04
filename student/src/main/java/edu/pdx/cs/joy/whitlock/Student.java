package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.lang.Human;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a <code>Student</code>.
 */
public class Student extends Human {
  private static final String STUDENT_SAYS = "This class is too much work";

  private final List<String> classes;
  private final double gpa;
  private final String gender;

  /**
   * Creates a new <code>Student</code>
   *
   * @param name
   *        The student's name
   * @param classes
   *        The names of the classes the student is taking.  A student
   *        may take zero or more classes.
   * @param gpa
   *        The student's grade point average
   * @param gender
   *        The student's gender ("male", "female", or "other", case-insensitive)
   */
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);
    this.classes = new ArrayList<>(classes);
    this.gpa = gpa;
    this.gender = gender;
  }

  /**
   * All students say "This class is too much work"
   */
  @Override
  public String says() {
    return STUDENT_SAYS;
  }

  /**
   * Returns a <code>String</code> that describes this
   * <code>Student</code>.
   */
  @Override
  public String toString() {
    return this.getName() + " has a GPA of " + formatGpa(this.gpa)
      + " and is taking " + describeClasses()
      + ". " + subjectPronoun() + " says \"" + this.says() + "\".";
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    System.err.println("Missing required student information");
  }

  private String describeClasses() {
    return switch (this.classes.size()) {
      case 0 -> "0 classes";
      case 1 -> "1 class: " + this.classes.get(0);
      default -> this.classes.size() + " classes: " + joinClasses();
    };
  }

  private String joinClasses() {
    if (this.classes.size() == 2) {
      return this.classes.get(0) + " and " + this.classes.get(1);
    }

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < this.classes.size(); i++) {
      if (i > 0) {
        builder.append(i == this.classes.size() - 1 ? ", and " : ", ");
      }
      builder.append(this.classes.get(i));
    }
    return builder.toString();
  }

  private String subjectPronoun() {
    return switch (this.gender.toLowerCase()) {
      case "male" -> "He";
      case "female" -> "She";
      default -> "They";
    };
  }

  private String formatGpa(double gpa) {
    return new DecimalFormat("0.00").format(gpa);
  }
}