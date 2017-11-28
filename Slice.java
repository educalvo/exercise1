package nl.ru.ai.exercise1;
class Slice
{
  public int from;
  public int upto;
  /**
   * Creates a new slice
   * @param f
   * @param u
   */
  public Slice(int from, int upto)
  {
    this.from=from;
    this.upto=upto;
  }
  /**
   * Checks if a slice is valid
   * @return true if valid, false otherwise
   */
  public boolean isValid()
  {
    return 0<=from&&from<=upto;
  }
}