package tv.project;

public class App {
  public static void main(String[] args) {
    System.out.println("Application initialized...");
    ListProcessor processor = new ListProcessor();
    processor.process();
    System.out.println("Application shut down...");
  }
}
