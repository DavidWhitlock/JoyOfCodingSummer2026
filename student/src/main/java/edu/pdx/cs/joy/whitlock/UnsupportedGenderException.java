package edu.pdx.cs.joy.whitlock;

public class UnsupportedGenderException extends RuntimeException {
  private final String unsupportedGender;

  public UnsupportedGenderException(String unsupportedGender) {
    this.unsupportedGender = unsupportedGender;
  }

  public String getUnsupportedGender() {
    return unsupportedGender;
  }
}
