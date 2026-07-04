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
  private static final double MIN_GPA = 0.0;
  private static final double MAX_GPA = 4.0;

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
    super(validateName(name));
    validateGpa(gpa);
    this.gender = validateGender(gender);
    this.classes = new ArrayList<>(classes);
    this.gpa = gpa;
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
      + ". " + speechDescription() + " \"" + this.says() + "\".";
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    try {
      Student student = createStudentFromCommandLine(args);
      System.out.println(student);

    } catch (NumberFormatException ex) {
      System.err.println("GPA must be a number");

    } catch (IllegalArgumentException ex) {
      System.err.println(ex.getMessage());
    }
  }

  private static Student createStudentFromCommandLine(String[] args) {
    validateRequiredCommandLineArguments(args);

    String name = args[0];
    String gender = args[1];
    double gpa = Double.parseDouble(args[2]);
    ArrayList<String> classes = new ArrayList<>();
    for (int i = 3; i < args.length; i++) {
      classes.add(args[i]);
    }

    return new Student(name, classes, gpa, gender);
  }

  private static void validateRequiredCommandLineArguments(String[] args) {
    switch (args.length) {
      case 0 -> throw new IllegalArgumentException("Missing required student information");
      case 1 -> throw new IllegalArgumentException("Missing gender");
      case 2 -> throw new IllegalArgumentException("Missing GPA");
      default -> {
      }
    }
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
    return switch (this.gender) {
      case "male" -> "He";
      case "female" -> "She";
      default -> "They";
    };
  }

  private String speechDescription() {
    return switch (this.gender) {
      case "other" -> "They say";
      default -> subjectPronoun() + " says";
    };
  }

  private String formatGpa(double gpa) {
    return new DecimalFormat("0.00").format(gpa);
  }

  private static String validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Student name is required");
    }

    return name;
  }

  private static void validateGpa(double gpa) {
    if (gpa < MIN_GPA || gpa > MAX_GPA) {
      throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
    }
  }

  private static String validateGender(String gender) {
    if (gender == null) {
      throw new IllegalArgumentException("Gender must be male, female, or other");
    }

    String normalizedGender = gender.toLowerCase();
    return switch (normalizedGender) {
      case "male", "female", "other" -> normalizedGender;
      default -> throw new IllegalArgumentException("Gender must be male, female, or other");
    };
  }
}