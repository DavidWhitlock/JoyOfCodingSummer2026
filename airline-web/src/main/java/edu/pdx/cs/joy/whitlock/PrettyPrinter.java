package edu.pdx.cs.joy.whitlock;

import com.google.common.annotations.VisibleForTesting;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

public class PrettyPrinter {
  private final Writer writer;

  @VisibleForTesting
  static String formatWordCount(int count )
  {
    return String.format( "Dictionary on server contains %d words", count );
  }


  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  public void dump(Airline airline) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {

      Collection<Flight> flights = airline.getFlights();
      pw.println(airline.getName() + "with " + flights.size() + " flights\n");

      for (Flight flight : flights) {
        pw.println("  Flight number " + flight.getNumber());
      }

      pw.flush();
    }

  }
}
