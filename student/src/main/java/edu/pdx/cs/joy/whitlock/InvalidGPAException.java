package edu.pdx.cs.joy.whitlock;

public class InvalidGPAException extends RuntimeException {
  private final double invalidGPA;

  public InvalidGPAException(double invalidGPA) {
    this.invalidGPA = invalidGPA;
  }

  public double getInvalidGPA() {
    return invalidGPA;
  }
}
