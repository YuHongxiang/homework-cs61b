/**
 * simulate a universe specified in one of the data files.
 */
public class NBody {

  private static String backgroundPath = "./images/starfield.jpg";

  /**
   * return the radius of the universe in that file
   *
   * @param fileName file name
   * @return the radius of the universe in that file
   */
  public static double readRadius(String fileName) {
    In in = new In(fileName);
    int N = in.readInt();
    return in.readDouble();
  }

  /**
   * return an array of Bodys corresponding to the bodies in the file
   *
   * @param fileName file name
   * @return an array of Bodys corresponding to the bodies in the file
   */
  public static Planet[] readPlanets(String fileName) {
    In in = new In(fileName);
    int planetSize = in.readInt();
    in.readDouble();
    Planet[] planets = new Planet[planetSize];
    int index = 0;
    while (!in.isEmpty()) {
      if (index >= planetSize) {
        break;
      }
      double xxPos = in.readDouble();
      double yyPos = in.readDouble();
      double xxVel = in.readDouble();
      double yyVel = in.readDouble();
      double mass = in.readDouble();
      String imgFileName = in.readString();
      Planet planet = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
      planets[index] = planet;
      index++;
    }
    return planets;
  }

  public static void main(String[] args) {
    // Collecting All Needed Input
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    Planet[] planets = readPlanets(filename);
    double radius = readRadius(filename);

    StdDraw.setScale(-radius, radius);

    StdDraw.enableDoubleBuffering();

    double timer = 0.0;

    while (timer < T) {
      double[] xForces = new double[planets.length];
      double[] yForces = new double[planets.length];
      for (int i = 0; i < planets.length; i++) {
        xForces[i] = planets[i].calcNetForceExertedByX(planets);
        yForces[i] = planets[i].calcNetForceExertedByY(planets);
      }

      for (int i = 0; i < planets.length; i++) {
        planets[i].update(dt, xForces[i], yForces[i]);
      }

      /* Clears the drawing window. */
      StdDraw.clear();

      // Drawing the Background
      StdDraw.picture(0, 0, backgroundPath);

      // Drawing More than One Body
      for (Planet planet : planets) {
        planet.draw();
//
      }

      StdDraw.show();
      StdDraw.pause(100);

      timer += dt;
    }

    StdOut.printf("%d\n", planets.length);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < planets.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
    }



  }

}
