/**
 * the planet body
 */
public class Planet {

  private static final double G = 6.67e-11;

  private static final String imgFileDir = "./images/";

  /**
   * Its current x position
   */
  public double xxPos;

  /**
   * Its current y position
   */
  public double yyPos;

  /**
   * Its current velocity in the x direction
   */
  public double xxVel;

  /**
   * Its current velocity in the y direction
   */
  public double yyVel;

  /**
   * Its mass
   */
  public double mass;

  /**
   * The name of the file that corresponds to the image that depicts the body (for example,
   * jupiter.gif)
   */
  public String imgFileName;

  public Planet(double xP, double yP, double xV, double yV, double m, String img) {
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  }

  public Planet(Planet b) {
    xxPos = b.xxPos;
    yyPos = b.yyPos;
    xxVel = b.xxVel;
    yyVel = b.yyVel;
    mass = b.mass;
    imgFileName = b.imgFileName;
  }

  /**
   * calculates the distance between two Planets.
   *
   * @param target another planet
   * @return the distance
   */
  public double calcDistance(Planet target) {
    double distanceSquare =
        (target.xxPos - this.xxPos) * (target.xxPos - this.xxPos) + (target.yyPos - this.yyPos) * (
            target.yyPos - this.yyPos);
    return Math.sqrt(distanceSquare);
  }

  /**
   * calculates the force exerted on this body by the given body.
   *
   * @param target another planet
   * @return the force
   */
  public double calcForceExertedBy(Planet target) {
    double distance = this.calcDistance(target);
    return (G * this.mass * target.mass) / (distance * distance);
  }

  /**
   * the force exerted in the X directions
   *
   * @param target another planet
   * @return the force exerted in the X directions
   */
  public double calcForceExertedByX(Planet target) {
    double forceExerted = this.calcForceExertedBy(target);
    double dx = target.xxPos - this.xxPos;
    double distance = this.calcDistance(target);
    return forceExerted * dx / distance;
  }

  /**
   * the force exerted in the Y directions
   *
   * @param target another planet
   * @return the force exerted in the Y directions
   */
  public double calcForceExertedByY(Planet target) {
    double forceExerted = this.calcForceExertedBy(target);
    double dy = target.yyPos - this.yyPos;
    double distance = this.calcDistance(target);
    return forceExerted * dy / distance;
  }

  public double calcNetForceExertedByX(Planet[] allBodys) {
    double netForceExertedByX = 0;
    for (Planet planet : allBodys) {
      if (this.equals(planet)) {
        continue;
      }
      double forceExertedByX = this.calcForceExertedByX(planet);
      netForceExertedByX += forceExertedByX;
    }
    return netForceExertedByX;
  }

  public double calcNetForceExertedByY(Planet[] allBodys) {
    double netForceExertedByY = 0;
    for (Planet planet : allBodys) {
      if (this.equals(planet)) {
        continue;
      }
      double forceExertedByY = this.calcForceExertedByY(planet);
      netForceExertedByY += forceExertedByY;
    }
    return netForceExertedByY;
  }

  public void update(double time, double xForce, double yForce) {
    double xAcceleration = xForce / this.mass;
    double yAcceleration = yForce / this.mass;

    double newXVelocity = this.xxVel + xAcceleration * time;
    double newYVelocity = this.yyVel + yAcceleration * time;

    double newXPosition = this.xxPos + newXVelocity * time;
    double newYPosition = this.yyPos + newYVelocity * time;

    this.xxVel = newXVelocity;
    this.yyVel = newYVelocity;
    this.xxPos = newXPosition;
    this.yyPos = newYPosition;
  }

  /**
   * Drawing One Body
   */
  public void draw() {
    StdDraw.picture(this.xxPos, this.yyPos, imgFileDir + this.imgFileName);
  }

}
