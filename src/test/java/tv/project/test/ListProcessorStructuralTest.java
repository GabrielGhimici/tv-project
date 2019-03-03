package tv.project.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import tv.project.ListProcessor;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class ListProcessorStructuralTest {
  private ListProcessor processor;
  @Rule
  public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Before
  public void initializeProcessor() {
    this.processor = new ListProcessor();
  }

  @Test
  public void verifyLengthValidity0Length() {
    assertFalse(this.processor.isValidLength(0));
  }

  @Test
  public void verifyLengthValidity1Length() {
    assertTrue(this.processor.isValidLength(1));
  }

  @Test
  public void verifyLengthValidityGreaterThan1Length() {
    assertTrue(this.processor.isValidLength(34));
  }

  @Test
  public void verifyLengthValidity100Length() {
    assertTrue(this.processor.isValidLength(100));
  }

  @Test
  public void verifyLengthValidityGreaterThan100Length() {
    assertFalse(this.processor.isValidLength(101));
  }

  @Test
  public void verifyElementLengthValidityLessThan0Length() {
    assertFalse(this.processor.isValidNumber(-1));
  }

  @Test
  public void verifyElementLengthValidity0Length() {
    assertTrue(this.processor.isValidNumber(0));
  }

  @Test
  public void verifyElementLengthValidity1Length() {
    assertTrue(this.processor.isValidNumber(1));
  }

  @Test
  public void verifyElementLengthValidityGreaterThan1Length() {
    assertTrue(this.processor.isValidNumber(12345));
  }

  @Test
  public void verifyElementLengthValidity9999999Length() {
    assertTrue(this.processor.isValidNumber(9999999));
  }

  @Test
  public void verifyLengthValidityGreaterThan9999999Length() {
    assertFalse(this.processor.isValidNumber(10000000));
  }

  @Test
  public void processEmptyList() {
    assertEquals(0, this.processor.processList(new ArrayList<>(0)).size());
  }

  @Test
  public void processNonEmptyList() {
    ArrayList<Integer> processedList = this.processor.processList(new ArrayList<>(Arrays.asList(1234, 1236, 12398, 0, 10)));
    assertEquals(5, processedList.size());
    assertEquals(14, processedList.get(0).intValue());
    assertEquals(16, processedList.get(1).intValue());
    assertEquals(1298, processedList.get(2).intValue());
    assertEquals(0, processedList.get(3).intValue());
    assertEquals(0, processedList.get(4).intValue());
  }

  @Test
  public void process1LengthNumber() {
    assertEquals(0, this.processor.processListElement(1));
  }

  @Test
  public void process2LengthNumber() {
    assertEquals(0, this.processor.processListElement(88));
  }

  @Test
  public void processGreaterThan2LengthNumberEL() {
    assertEquals(15, this.processor.processListElement(1235));
  }

  @Test
  public void processGreaterThan2LengthNumberOL() {
    assertEquals(1245, this.processor.processListElement(12345));
  }
}
