package tv.project.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import tv.project.ListProcessor;

import java.util.ArrayList;
import java.util.InputMismatchException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class ListProcessorFunctionalTest {
  private ListProcessor processor;
  @Rule
  public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Before
  public void initializeProcessor() {
    this.processor = new ListProcessor();
  }

  //equivalence partitioning && boundary values
  @Test
  public void processListLengthNegative() {
    systemInMock.provideLines("-1", "1", "1");
    this.processor.readData();
    assertTrue(this.systemOutRule.getLog().contains("Wrong value! Length must be in (0,100]"));
  }

  @Test
  public void processListLengthGreaterThan100() {
    systemInMock.provideLines("101", "1", "1");
    this.processor.readData();
    assertTrue(this.systemOutRule.getLog().contains("Wrong value! Length must be in (0,100]"));
  }

  @Test(expected = InputMismatchException.class)
  public void processListLengthInvalidInt() {
    systemInMock.provideLines("1hj");
    this.processor.readData();
  }

  @Test
  public void processListNegativeNumber() {
    systemInMock.provideLines("2", "-1", "1", "2");
    this.processor.readData();
    assertTrue(this.systemOutRule.getLog().contains("Wrong value! Number must have min 1 digit and max 7"));
  }

  @Test
  public void processListNumberLengthGreaterThan7() {
    systemInMock.provideLines("2", "12345678", "1", "2");
    this.processor.readData();
    assertTrue(this.systemOutRule.getLog().contains("Wrong value! Number must have min 1 digit and max 7"));
  }

  @Test
  public void processValidListWithMaxNumberOfElements() {
    String elements = "123";
    for (int i=1; i<100; i++) {
      elements = elements.concat("\n123");
    }
    systemInMock.provideLines("100", elements);
    ArrayList<Integer> providedList = this.processor.readData().getValue();
    ArrayList<Integer> parsedList = this.processor.processList(providedList);
    assertEquals(100, parsedList.size());
    for(int i=0; i<100; i++) {
      assertEquals(13, parsedList.get(i).intValue());
    }
  }

  @Test
  public void processValidListWithOneElement() {
    systemInMock.provideLines("1", "1");
    ArrayList<Integer> parsedList = this.processor.processList(this.processor.readData().getValue());
    assertEquals(0, parsedList.get(0).intValue());
  }

  @Test
  public void processValidListWithEvenNumberLength() {
    systemInMock.provideLines("2", "1234", "5435");
    ArrayList<Integer> parsedList = this.processor.processList(this.processor.readData().getValue());
    assertEquals(14, parsedList.get(0).intValue());
    assertEquals(55, parsedList.get(1).intValue());
  }

  @Test
  public void processValidListWithOddNumberLength() {
    systemInMock.provideLines("2", "12334", "54357");
    ArrayList<Integer> parsedList = this.processor.processList(this.processor.readData().getValue());
    assertEquals(1234, parsedList.get(0).intValue());
    assertEquals(5457, parsedList.get(1).intValue());
  }

  @Test
  public void processValidListWithMaxNumberLength() {
    systemInMock.provideLines("2", "9999999", "9999999");
    ArrayList<Integer> parsedList = this.processor.processList(this.processor.readData().getValue());
    assertEquals(999999, parsedList.get(0).intValue());
    assertEquals(999999, parsedList.get(1).intValue());
  }


  @Test
  public void processValidListWithSmallNumberLength() {
    systemInMock.provideLines("2", "1", "57");
    ArrayList<Integer> parsedList = this.processor.processList(this.processor.readData().getValue());
    assertEquals(0, parsedList.get(0).intValue());
    assertEquals(0, parsedList.get(1).intValue());
  }

}
