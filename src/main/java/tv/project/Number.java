package tv.project;

@SuppressWarnings("WeakerAccess")
public class Number {
  public static int length(int element) {
    int length = 1;
    while (element/10 != 0) {
      length++;
      element = element/10;
    }
    return length;
  }
}
