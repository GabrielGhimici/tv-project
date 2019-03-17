package tv.project;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("WeakerAccess")
public class ListProcessor {
  public ListProcessor() {}
  public void process() {
    try {
      Pair<Integer, ArrayList<Integer>> data = this.readData();
      ArrayList<Integer> processedList;
      System.out.println("List length: " + data.getKey());
      System.out.println("Initial list: " + data.getValue());
      processedList = this.processList(data.getValue());
      System.out.println("Processed list: " + processedList);
    } catch (InputMismatchException e) {
      System.out.println("An invalid data type was provided...");
    }
  }

  public Pair<Integer, ArrayList<Integer>> readData() throws InputMismatchException {
    Scanner input = new Scanner(System.in);
    int listLength;
    ArrayList<Integer> list = new ArrayList<>();
    listLength = input.nextInt();
    while (!this.isValidLength(listLength)) {
      System.out.print("Wrong value! Length must be in (0,100] ");
      listLength = input.nextInt();
    }
    for (int i=0; i < listLength; i++) {
      int tempNumber;
      tempNumber = input.nextInt();
      while (!this.isValidNumber(tempNumber)) {
        System.out.print("Wrong value! Number must have min 1 digit and max 7 ");
        tempNumber = input.nextInt();
      }
      list.add(tempNumber);
    }
    return new Pair<>(listLength, list);
  }

  public boolean isValidLength(int length) {
    return length > 0 && length <= 100;
  }

  public boolean isValidNumber(int number) {
    return number >= 0 && number <= 9999999;
  }

  public ArrayList<Integer> processList(ArrayList<Integer> list) {
    ArrayList<Integer> result = new ArrayList<>();
    for(int i: list) {
      result.add(this.processListElement(i));
    }
    return result;
  }

  public int processListElement(int element) {
    int elementLength = Number.length(element);
    if (elementLength == 1 || elementLength == 2) {
      return 0;
    } else {
      return this.removeData(element);
    }
  }

  public int removeData(int element) {
    int elementLength = Number.length(element);
    int halfLength;
    if (elementLength % 2 == 0) {
      halfLength = elementLength/2 - 1;
    } else {
      halfLength = elementLength/2;
    }
    return this.calculateHalf(element, halfLength, true) * (int)Math.pow(10, halfLength) +
      this.calculateHalf(element, halfLength, false);
  }

  public int calculateHalf(int number, int halfLength, boolean isFirst) {
    if (isFirst) {
      return number / (int) Math.pow(10, Number.length(number) - halfLength);
    } else {
      return number % (int) Math.pow(10, halfLength);
    }
  }
}
