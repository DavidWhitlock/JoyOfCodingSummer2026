package edu.pdx.cs.joy.whitlock;

import com.google.common.annotations.VisibleForTesting;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
  static final String AIRLINE_NAME_PARAMETER = "airline";
  static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";

  private final Map<String, String> airlines = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing an airline to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );
      if (airlineName != null) {
          log("GET " + airlineName);
          writeAirline(airlineName, response);

      } else {
        // Replace this with a missingRequiredParameter(call)
          log("GET all dictionary entries");
          writeAllDictionaryEntries(response);
      }
  }

  /**
   * Handles an HTTP POST request by creating a new flight in the airline
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );
      if (airlineName == null) {
          missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
          return;
      }

      String flightNumberString = getParameter(FLIGHT_NUMBER_PARAMETER, request );
      if ( flightNumberString == null) {
          missingRequiredParameter( response, FLIGHT_NUMBER_PARAMETER);
          return;
      }

      log("POST " + airlineName + " -> " + flightNumberString);

      this.airlines.put(airlineName, flightNumberString);

      PrintWriter pw = response.getWriter();
      pw.println(Messages.createdFlight(airlineName, flightNumberString));
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);
  }

  /**
   * Handles an HTTP DELETE request by removing all airlines.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/plain");

      log("DELETE all dictionary entries");

      this.airlines.clear();

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allAirlineDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes the definition of the given word to the HTTP response.
   *
   * The text of the message is formatted with {@link TextDumper}
   */
  private void writeAirline(String word, HttpServletResponse response) throws IOException {
    String airline = this.airlines.get(word);

    if (airline == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);

    } else {
      PrintWriter pw = response.getWriter();

      Map<String, String> wordDefinition = Map.of(word, airline);
      TextDumper dumper = new TextDumper(pw);
      dumper.dump(wordDefinition);

      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  /**
   * Writes all of the dictionary entries to the HTTP response.
   *
   * The text of the message is formatted with {@link TextDumper}
   */
  private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
  {
      PrintWriter pw = response.getWriter();
      TextDumper dumper = new TextDumper(pw);
      dumper.dump(airlines);

      response.setStatus( HttpServletResponse.SC_OK );
  }

  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

  @VisibleForTesting
  String getAirline(String word) {
      return this.airlines.get(word);
  }

  @Override
  public void log(String msg) {
    System.out.println(msg);
  }
}
