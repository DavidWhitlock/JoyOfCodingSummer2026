package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.lang.Human;

import java.util.ArrayList;

/**
 * This class represents a <code>Student</code>.
 */
public class Student extends Human {

  private final double gpa;
  private final ArrayList<String> classes;

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
    this.classes = classes;

    if (gpa < 0.0 || gpa > 4.0) {
      throw new InvalidGPAException(gpa);
    }

    this.gpa = gpa;
  }

  /**
   * All students say "This class is too much work"
   */
  @Override
  public String says() {
    return "This class is too much work";
  }

  /**
   * Returns a <code>String</code> that describes this
   * <code>Student</code>.
   */
  public String toString() {
    return this.getName() + " has a GPA of " + this.gpa + " and is taking " + this.classes.size() + " classes";
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("Missing name");
      return;
    }

    if (args.length == 1) {
      System.err.println("Missing gender");
      return;
    }

    if (args.length == 2) {
      System.err.println("Missing GPA");
      return;
    }

    String name = args[0];
    String gender = args[1];
    double gpa = Double.parseDouble(args[2]);

    Student student = new Student(name, new ArrayList<>(), gpa, gender);
    System.out.println(student);
  }
}