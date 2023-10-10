package com.tn.query;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

class MapperTest
{
  @Test
  void shouldMapToBoolean()
  {
    assertEquals(Boolean.TRUE, Mapper.toBoolean("test").map("true"));
    assertEquals(Boolean.FALSE, Mapper.toBoolean("test").map("false"));
    assertEquals(Boolean.FALSE, Mapper.toBoolean("test").map(null));
  }

  @Test
  void shouldMapToByte()
  {
    assertEquals((byte)1, Mapper.toByte("test").map("1"));
  }

  @Test
  void shouldMapToChar()
  {
    assertEquals('x', Mapper.toChar("test").map("x"));
  }

  @Test
  void shouldMapToDate()
  {
    assertEquals(new GregorianCalendar(2022, Calendar.APRIL, 24).getTime(), Mapper.toDate("test").map("2022-04-24"));
    assertEquals(new GregorianCalendar(2022, Calendar.APRIL, 24, 18, 46).getTime(), Mapper.toDate("test").map("2022-04-24T18:46"));
    assertEquals(new GregorianCalendar(2022, Calendar.APRIL, 24, 18, 46, 12).getTime(), Mapper.toDate("test").map("2022-04-24T18:46:12"));
  }

  @Test
  void shouldMapToDouble()
  {
    assertEquals(12.34, Mapper.toDouble("test").map("12.34"));
  }

  @Test
  void shouldMapToEnum()
  {
    assertEquals(Result.SUCCESS, Mapper.toEnum("test", Result.class).map("SUCCESS"));
  }

  @Test
  void shouldMapToEnumWithInvalidValue()
  {
    assertThrows(QueryException.class, () -> Mapper.toEnum("test", Result.class).map("UNKNOWN"));
  }

  @Test
  void shouldMapToFloat()
  {
    assertEquals(12.34F, Mapper.toFloat("test").map("12.34"));
  }

  @Test
  void shouldMapToInt()
  {
    assertEquals(12, Mapper.toInt("test").map("12"));
  }

  @Test
  void shouldMapToLocalDate()
  {
    assertEquals(LocalDate.of(2022, Month.APRIL, 24), Mapper.toLocalDate("test").map("2022-04-24"));
  }

  @Test
  void shouldMapToLocalDateTime()
  {
    assertEquals(LocalDateTime.of(2022, Month.APRIL, 24, 18, 46), Mapper.toLocalDateTime("test").map("2022-04-24T18:46"));
    assertEquals(LocalDateTime.of(2022, Month.APRIL, 24, 18, 46, 12), Mapper.toLocalDateTime("test").map("2022-04-24T18:46:12"));
    assertEquals(LocalDateTime.of(2022, Month.APRIL, 24, 18, 46, 12, 532 * 1_000_000), Mapper.toLocalDateTime("test").map("2022-04-24T18:46:12.532"));
  }

  @Test
  void shouldMapToLong()
  {
    assertEquals(12L, Mapper.toLong("test").map("12"));
  }

  @Test
  void shouldMapToShort()
  {
    assertEquals((short)12, Mapper.toShort("test").map("12"));
  }

  @Test
  void shouldMapToString()
  {
    assertEquals("test", Mapper.toString("test").map("test"));
    assertEquals("test", Mapper.toString("test").map("'test'"));
    assertEquals("test", Mapper.toString("test").map("\"test\""));
    assertEquals("test's", Mapper.toString("test").map("\"test's\""));
  }

  private enum Result
  {
    SUCCESS,
    FAILURE
  }
}
