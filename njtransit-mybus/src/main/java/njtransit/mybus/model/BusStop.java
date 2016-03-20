package njtransit.mybus.model;

/**
 * simple bus stop POJO
 *
 * @author codywang
 */
public class BusStop {

  private final int stopId;
  private final String stopName;

  public BusStop(final int stopId, final String stopName) {
    this.stopId = stopId;
    this.stopName = stopName;
  }

  public int getStopId() {
    return this.stopId;
  }

  public String getStopName() {
    return this.stopName;
  }
}
