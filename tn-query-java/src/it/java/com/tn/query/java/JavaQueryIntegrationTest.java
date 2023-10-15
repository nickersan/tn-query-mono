package com.tn.query.java;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.tn.query.DefaultQueryParser;
import com.tn.query.Mapper;
import com.tn.query.QueryException;
import com.tn.query.QueryParser;

class JavaQueryIntegrationTest
{
  private final QueryParser<Predicate<Target>> queryParser = new DefaultQueryParser<>(
    new JavaPredicateFactory<>(
      List.of(
        Getter.booleanValue("booleanValue", target -> target.booleanValue),
        Getter.byteValue("byteValue", target -> target.byteValue),
        Getter.charValue("charValue", target -> target.charValue),
        Getter.comparableValue("dateValue", target -> target.dateValue),
        Getter.doubleValue("doubleValue", target -> target.doubleValue),
        Getter.floatValue("floatValue", target -> target.floatValue),
        Getter.intValue("intValue", target -> target.intValue),
        Getter.comparableValue("localDateValue", target -> target.localDateValue),
        Getter.comparableValue("localDateTimeValue", target -> target.localDateTimeValue),
        Getter.longValue("longValue", target -> target.longValue),
        Getter.shortValue("shortValue", target -> target.shortValue),
        Getter.comparableValue("stringValue", target -> target.stringValue)
      )
    ),
    List.of(
      Mapper.toBoolean("booleanValue"),
      Mapper.toByte("byteValue"),
      Mapper.toChar("charValue"),
      Mapper.toDate("dateValue"),
      Mapper.toDouble("doubleValue"),
      Mapper.toFloat("floatValue"),
      Mapper.toInt("intValue"),
      Mapper.toLocalDate("localDateValue"),
      Mapper.toLocalDateTime("localDateTimeValue"),
      Mapper.toLong("longValue"),
      Mapper.toShort("shortValue")
    )
  );

  @Test
  void shouldMatchBoolean()
  {
    Target target = new Target();
    target.booleanValue = true;

    assertTrue(this.queryParser.parse("booleanValue = true").test(target));
    assertFalse(this.queryParser.parse("booleanValue = false").test(target));
    assertFalse(this.queryParser.parse("booleanValue = X").test(target));

    assertFalse(this.queryParser.parse("booleanValue != true").test(target));
    assertTrue(this.queryParser.parse("booleanValue != false").test(target));
    assertTrue(this.queryParser.parse("booleanValue != X").test(target));

    assertFalse(this.queryParser.parse("booleanValue > true").test(target));
    assertTrue(this.queryParser.parse("booleanValue > false").test(target));
    assertTrue(this.queryParser.parse("booleanValue > X").test(target));

    assertTrue(this.queryParser.parse("booleanValue >= true").test(target));
    assertTrue(this.queryParser.parse("booleanValue >= false").test(target));
    assertTrue(this.queryParser.parse("booleanValue >= X").test(target));

    assertFalse(this.queryParser.parse("booleanValue < true").test(target));
    assertFalse(this.queryParser.parse("booleanValue < false").test(target));
    assertFalse(this.queryParser.parse("booleanValue < X").test(target));

    assertTrue(this.queryParser.parse("booleanValue <= true").test(target));
    assertFalse(this.queryParser.parse("booleanValue <= false").test(target));
    assertFalse(this.queryParser.parse("booleanValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("booleanValue ≈ true").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("booleanValue !≈ false").test(target));

    assertTrue(this.queryParser.parse("booleanValue ∈ [true, false]").test(target));
    assertTrue(this.queryParser.parse("booleanValue ∈ [true]").test(target));
    assertFalse(this.queryParser.parse("booleanValue ∈ [false]").test(target));
    assertFalse(this.queryParser.parse("booleanValue ∈ X").test(target));
  }

  @Test
  void shouldMatchByte()
  {
    Target target = new Target();
    target.byteValue = 1;

    assertTrue(this.queryParser.parse("byteValue = 1").test(target));
    assertFalse(this.queryParser.parse("byteValue = 2").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue = X").test(target));

    assertFalse(this.queryParser.parse("byteValue != 1").test(target));
    assertTrue(this.queryParser.parse("byteValue != 2").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue != X").test(target));

    assertTrue(this.queryParser.parse("byteValue > 0").test(target));
    assertFalse(this.queryParser.parse("byteValue > 1").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue > X").test(target));

    assertTrue(this.queryParser.parse("byteValue >= 0").test(target));
    assertTrue(this.queryParser.parse("byteValue >= 1").test(target));
    assertFalse(this.queryParser.parse("byteValue >= 2").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue >= X").test(target));

    assertTrue(this.queryParser.parse("byteValue < 2").test(target));
    assertFalse(this.queryParser.parse("byteValue < 1").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue < X").test(target));

    assertTrue(this.queryParser.parse("byteValue <= 2").test(target));
    assertTrue(this.queryParser.parse("byteValue <= 1").test(target));
    assertFalse(this.queryParser.parse("byteValue <= 0").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue ≈ 1").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue !≈ 2").test(target));

    assertTrue(this.queryParser.parse("byteValue ∈ [1, 2]").test(target));
    assertTrue(this.queryParser.parse("byteValue ∈ [1]").test(target));
    assertFalse(this.queryParser.parse("byteValue ∈ [2]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("byteValue ∈ X").test(target));
  }

  @Test
  void shouldMatchChar()
  {
    Target target = new Target();
    target.charValue = 'b';

    assertTrue(this.queryParser.parse("charValue = b").test(target));
    assertFalse(this.queryParser.parse("charValue = c").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue = bc").test(target));

    assertFalse(this.queryParser.parse("charValue != b").test(target));
    assertTrue(this.queryParser.parse("charValue != c").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue != ab").test(target));

    assertTrue(this.queryParser.parse("charValue > a").test(target));
    assertFalse(this.queryParser.parse("charValue > b").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue > ab").test(target));

    assertTrue(this.queryParser.parse("charValue >= a").test(target));
    assertTrue(this.queryParser.parse("charValue >= b").test(target));
    assertFalse(this.queryParser.parse("charValue >= c").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue >= ab").test(target));

    assertTrue(this.queryParser.parse("charValue < c").test(target));
    assertFalse(this.queryParser.parse("charValue < b").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue < ab").test(target));

    assertTrue(this.queryParser.parse("charValue <= c").test(target));
    assertTrue(this.queryParser.parse("charValue <= b").test(target));
    assertFalse(this.queryParser.parse("charValue <= a").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue <= ab").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue ≈ b").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue !≈ c").test(target));

    assertTrue(this.queryParser.parse("charValue ∈ [a, b, c]").test(target));
    assertTrue(this.queryParser.parse("charValue ∈ [b]").test(target));
    assertFalse(this.queryParser.parse("charValue ∈ [a]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("charValue ∈ ab").test(target));
  }

  @Test
  void shouldMatchDate()
  {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2021, Calendar.FEBRUARY, 5, 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    Target target = new Target();
    target.dateValue = calendar.getTime();

    assertTrue(this.queryParser.parse("dateValue = 2021-02-05").test(target));
    assertFalse(this.queryParser.parse("dateValue = 2021-10-06").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue = X").test(target));

    assertFalse(this.queryParser.parse("dateValue != 2021-02-05").test(target));
    assertTrue(this.queryParser.parse("dateValue != 2021-10-06").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue != X").test(target));

    assertTrue(this.queryParser.parse("dateValue > 2021-02-04").test(target));
    assertFalse(this.queryParser.parse("dateValue > 2021-02-05").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue > X").test(target));

    assertTrue(this.queryParser.parse("dateValue >= 2021-02-04").test(target));
    assertTrue(this.queryParser.parse("dateValue >= 2021-02-05").test(target));
    assertFalse(this.queryParser.parse("dateValue >= 2021-02-06").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue >= X").test(target));

    assertTrue(this.queryParser.parse("dateValue < 2021-02-06").test(target));
    assertFalse(this.queryParser.parse("dateValue < 2021-02-05").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue < X").test(target));

    assertTrue(this.queryParser.parse("dateValue <= 2021-02-05").test(target));
    assertTrue(this.queryParser.parse("dateValue <= 2021-02-06").test(target));
    assertFalse(this.queryParser.parse("dateValue <= 2021-02-04").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue ≈ 2021-02-05").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue !≈ 2021-02-06").test(target));

    assertTrue(this.queryParser.parse("dateValue ∈ [2021-02-04, 2021-02-05, 2021-02-06]").test(target));
    assertTrue(this.queryParser.parse("dateValue ∈ [2021-02-05]").test(target));
    assertFalse(this.queryParser.parse("dateValue ∈ [2021-02-04]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue ∈ X").test(target));
  }

  @Test
  void shouldMatchDateTimeWithMinutes()
  {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2021, Calendar.FEBRUARY, 5, 10, 15, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    Target target = new Target();
    target.dateValue = calendar.getTime();

    assertTrue(this.queryParser.parse("dateValue = 2021-02-05T10:15").test(target));
    assertFalse(this.queryParser.parse("dateValue = 2021-02-05T10:16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue = X").test(target));

    assertFalse(this.queryParser.parse("dateValue != 2021-02-05T10:15").test(target));
    assertTrue(this.queryParser.parse("dateValue != 2021-10-05T10:14").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue != X").test(target));

    assertTrue(this.queryParser.parse("dateValue > 2021-02-05T10:14").test(target));
    assertFalse(this.queryParser.parse("dateValue > 2021-02-05T10:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue > X").test(target));

    assertTrue(this.queryParser.parse("dateValue >= 2021-02-05T10:14").test(target));
    assertTrue(this.queryParser.parse("dateValue >= 2021-02-05T10:15").test(target));
    assertFalse(this.queryParser.parse("dateValue >= 2021-02-05T10:16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue >= X").test(target));

    assertTrue(this.queryParser.parse("dateValue < 2021-02-05T10:16").test(target));
    assertFalse(this.queryParser.parse("dateValue < 2021-02-05T10:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue < X").test(target));

    assertTrue(this.queryParser.parse("dateValue <= 2021-02-05T10:16").test(target));
    assertTrue(this.queryParser.parse("dateValue <= 2021-02-05T10:15").test(target));
    assertFalse(this.queryParser.parse("dateValue <= 2021-02-05T10:14").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue ≈ 2021-02-05T10:15").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue !≈ 2021-02-05T10:16").test(target));

    assertTrue(this.queryParser.parse("dateValue ∈ [2021-02-05T10:14, 2021-02-05T10:15, 2021-02-05T10:16]").test(target));
    assertTrue(this.queryParser.parse("dateValue ∈ [2021-02-05T10:15]").test(target));
    assertFalse(this.queryParser.parse("dateValue ∈ [2021-02-05T10:14]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue ∈ X").test(target));
  }

  @Test
  void shouldMatchDateTimeWithSeconds()
  {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2021, Calendar.FEBRUARY, 5, 10, 15, 16);
    calendar.set(Calendar.MILLISECOND, 0);

    Target target = new Target();
    target.dateValue = calendar.getTime();

    assertTrue(this.queryParser.parse("dateValue = 2021-02-05T10:15:16").test(target));
    assertFalse(this.queryParser.parse("dateValue = 2021-02-05T10:15:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue = X").test(target));

    assertFalse(this.queryParser.parse("dateValue != 2021-02-05T10:15:16").test(target));
    assertTrue(this.queryParser.parse("dateValue != 2021-10-05T10:15:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue != X").test(target));

    assertTrue(this.queryParser.parse("dateValue > 2021-02-05T10:15:15").test(target));
    assertFalse(this.queryParser.parse("dateValue > 2021-02-05T10:15:16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue > X").test(target));

    assertTrue(this.queryParser.parse("dateValue >= 2021-02-05T10:15:15").test(target));
    assertTrue(this.queryParser.parse("dateValue >= 2021-02-05T10:15:16").test(target));
    assertFalse(this.queryParser.parse("dateValue >= 2021-02-05T10:15:17").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue >= X").test(target));

    assertTrue(this.queryParser.parse("dateValue < 2021-02-05T10:15:17").test(target));
    assertFalse(this.queryParser.parse("dateValue < 2021-02-05T10:15:16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue < X").test(target));

    assertTrue(this.queryParser.parse("dateValue <= 2021-02-05T10:15:17").test(target));
    assertTrue(this.queryParser.parse("dateValue <= 2021-02-05T10:15:16").test(target));
    assertFalse(this.queryParser.parse("dateValue <= 2021-02-05T10:15:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue ≈ 2021-02-05T10:15:16").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue !≈ 2021-02-05T10:17").test(target));

    assertTrue(this.queryParser.parse("dateValue ∈ [2021-02-05T10:15:15, 2021-02-05T10:15:16, 2021-02-05T10:15:17]").test(target));
    assertTrue(this.queryParser.parse("dateValue ∈ [2021-02-05T10:15:16]").test(target));
    assertFalse(this.queryParser.parse("dateValue ∈ [2021-02-05T10:15:17]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue ∈ X").test(target));
  }

  @Test
  void shouldMatchDateTimeWithMilliseconds()
  {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2021, Calendar.FEBRUARY, 5, 10, 15, 16);
    calendar.set(Calendar.MILLISECOND, 17);

    Target target = new Target();
    target.dateValue = calendar.getTime();

    assertTrue(this.queryParser.parse("dateValue = 2021-02-05T10:15:16.17").test(target));
    assertFalse(this.queryParser.parse("dateValue = 2021-02-05T10:15:16.18").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue = X").test(target));

    assertFalse(this.queryParser.parse("dateValue != 2021-02-05T10:15:16.17").test(target));
    assertTrue(this.queryParser.parse("dateValue != 2021-10-05T10:15:16.16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue != X").test(target));

    assertTrue(this.queryParser.parse("dateValue > 2021-02-05T10:15:16.16").test(target));
    assertFalse(this.queryParser.parse("dateValue > 2021-02-05T10:15:16.17").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue > X").test(target));

    assertTrue(this.queryParser.parse("dateValue >= 2021-02-05T10:15:16.16").test(target));
    assertTrue(this.queryParser.parse("dateValue >= 2021-02-05T10:15:16.17").test(target));
    assertFalse(this.queryParser.parse("dateValue >= 2021-02-05T10:15:16.18").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue >= X").test(target));

    assertTrue(this.queryParser.parse("dateValue < 2021-02-05T10:15:16.18").test(target));
    assertFalse(this.queryParser.parse("dateValue < 2021-02-05T10:15:16.17").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue < X").test(target));

    assertTrue(this.queryParser.parse("dateValue <= 2021-02-05T10:15:16.17").test(target));
    assertTrue(this.queryParser.parse("dateValue <= 2021-02-05T10:15:16.18").test(target));
    assertFalse(this.queryParser.parse("dateValue <= 2021-02-05T10:15:16.16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue ≈ 2021-02-05T10:15:16.17").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue !≈ 2021-02-05T10:15:16.18").test(target));

    assertTrue(this.queryParser.parse("dateValue ∈ [2021-02-05T10:15:16.16, 2021-02-05T10:15:16.17, 2021-02-05T10:15:16.18]").test(target));
    assertTrue(this.queryParser.parse("dateValue ∈ [2021-02-05T10:15:16.17]").test(target));
    assertFalse(this.queryParser.parse("dateValue ∈ [2021-02-05T10:15:16.16]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("dateValue ∈ X").test(target));
  }

  @Test
  void shouldMatchDouble()
  {
    Target target = new Target();
    target.doubleValue = 1.2;

    assertTrue(this.queryParser.parse("doubleValue = 1.2").test(target));
    assertFalse(this.queryParser.parse("doubleValue = 1.3").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue = X").test(target));

    assertFalse(this.queryParser.parse("doubleValue != 1.2").test(target));
    assertTrue(this.queryParser.parse("doubleValue != 1.3").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue != X").test(target));

    assertTrue(this.queryParser.parse("doubleValue > 1.1").test(target));
    assertFalse(this.queryParser.parse("doubleValue > 1.2").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue > X").test(target));

    assertTrue(this.queryParser.parse("doubleValue >= 1.1").test(target));
    assertTrue(this.queryParser.parse("doubleValue >= 1.2").test(target));
    assertFalse(this.queryParser.parse("doubleValue >= 1.3").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue >= X").test(target));

    assertTrue(this.queryParser.parse("doubleValue < 1.3").test(target));
    assertFalse(this.queryParser.parse("doubleValue < 1.2").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue < X").test(target));

    assertTrue(this.queryParser.parse("doubleValue <= 1.3").test(target));
    assertTrue(this.queryParser.parse("doubleValue <= 1.2").test(target));
    assertFalse(this.queryParser.parse("doubleValue <= 1.1").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue ≈ 1.2").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue !≈ 1.3").test(target));

    assertTrue(this.queryParser.parse("doubleValue ∈ [1.1, 1.2, 1.3]").test(target));
    assertTrue(this.queryParser.parse("doubleValue ∈ [1.2]").test(target));
    assertFalse(this.queryParser.parse("doubleValue ∈ [1.1]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("doubleValue ∈ X").test(target));
  }

  @Test
  void shouldMatchFloat()
  {
    Target target = new Target();
    target.floatValue = 1.2F;

    assertTrue(this.queryParser.parse("floatValue = 1.2").test(target));
    assertFalse(this.queryParser.parse("floatValue = 1.3").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue = X").test(target));

    assertFalse(this.queryParser.parse("floatValue != 1.2").test(target));
    assertTrue(this.queryParser.parse("floatValue != 1.3").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue != X").test(target));

    assertTrue(this.queryParser.parse("floatValue > 1.1").test(target));
    assertFalse(this.queryParser.parse("floatValue > 1.2").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue > X").test(target));

    assertTrue(this.queryParser.parse("floatValue >= 1.1").test(target));
    assertTrue(this.queryParser.parse("floatValue >= 1.2").test(target));
    assertFalse(this.queryParser.parse("floatValue >= 1.3").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue >= X").test(target));

    assertTrue(this.queryParser.parse("floatValue < 1.3").test(target));
    assertFalse(this.queryParser.parse("floatValue < 1.2").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue < X").test(target));

    assertTrue(this.queryParser.parse("floatValue <= 1.3").test(target));
    assertTrue(this.queryParser.parse("floatValue <= 1.2").test(target));
    assertFalse(this.queryParser.parse("floatValue <= 1.1").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue ≈ 1.2").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue !≈ 1.3").test(target));

    assertTrue(this.queryParser.parse("floatValue ∈ [1.1, 1.2, 1.3]").test(target));
    assertTrue(this.queryParser.parse("floatValue ∈ [1.2]").test(target));
    assertFalse(this.queryParser.parse("floatValue ∈ [1.1]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("floatValue ∈ X").test(target));
  }

  @Test
  void shouldMatchInt()
  {
    Target target = new Target();
    target.intValue = 10;

    assertTrue(this.queryParser.parse("intValue = 10").test(target));
    assertFalse(this.queryParser.parse("intValue = 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue = X").test(target));

    assertFalse(this.queryParser.parse("intValue != 10").test(target));
    assertTrue(this.queryParser.parse("intValue != 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue != X").test(target));

    assertTrue(this.queryParser.parse("intValue > 9").test(target));
    assertFalse(this.queryParser.parse("intValue > 10").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue > X").test(target));

    assertTrue(this.queryParser.parse("intValue >= 9").test(target));
    assertTrue(this.queryParser.parse("intValue >= 10").test(target));
    assertFalse(this.queryParser.parse("intValue >= 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue >= X").test(target));

    assertTrue(this.queryParser.parse("intValue < 11").test(target));
    assertFalse(this.queryParser.parse("intValue < 10").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue < X").test(target));

    assertTrue(this.queryParser.parse("intValue <= 11").test(target));
    assertTrue(this.queryParser.parse("intValue <= 10").test(target));
    assertFalse(this.queryParser.parse("intValue <= 9").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue ≈ 10").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue !≈ 11").test(target));

    assertTrue(this.queryParser.parse("intValue ∈ [9, 10, 11]").test(target));
    assertTrue(this.queryParser.parse("intValue ∈ [10]").test(target));
    assertFalse(this.queryParser.parse("intValue ∈ [9]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("intValue ∈ X").test(target));
  }

  @Test
  void shouldMatchLocalDate()
  {
    LocalDate localDate = LocalDate.of(2021, Month.FEBRUARY, 5);

    Target target = new Target();
    target.localDateValue = localDate;

    assertTrue(this.queryParser.parse("localDateValue = 2021-02-05").test(target));
    assertFalse(this.queryParser.parse("localDateValue = 2021-10-06").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue = X").test(target));

    assertFalse(this.queryParser.parse("localDateValue != 2021-02-05").test(target));
    assertTrue(this.queryParser.parse("localDateValue != 2021-10-06").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue != X").test(target));

    assertTrue(this.queryParser.parse("localDateValue > 2021-02-04").test(target));
    assertFalse(this.queryParser.parse("localDateValue > 2021-02-05").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue > X").test(target));

    assertTrue(this.queryParser.parse("localDateValue >= 2021-02-04").test(target));
    assertTrue(this.queryParser.parse("localDateValue >= 2021-02-05").test(target));
    assertFalse(this.queryParser.parse("localDateValue >= 2021-02-06").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue >= X").test(target));

    assertTrue(this.queryParser.parse("localDateValue < 2021-02-06").test(target));
    assertFalse(this.queryParser.parse("localDateValue < 2021-02-05").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue < X").test(target));

    assertTrue(this.queryParser.parse("localDateValue <= 2021-02-05").test(target));
    assertTrue(this.queryParser.parse("localDateValue <= 2021-02-06").test(target));
    assertFalse(this.queryParser.parse("localDateValue <= 2021-02-04").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue ≈ 2021-02-06").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue !≈ 2021-02-07").test(target));

    assertTrue(this.queryParser.parse("localDateValue ∈ [2021-02-04, 2021-02-05, 2021-02-06]").test(target));
    assertTrue(this.queryParser.parse("localDateValue ∈ [2021-02-05]").test(target));
    assertFalse(this.queryParser.parse("localDateValue ∈ [2021-02-04]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateValue ∈ X").test(target));
  }

  @Test
  void shouldMatchLocalDateTimeWithMinutes()
  {
    LocalDateTime localDateTime = LocalDateTime.of(2021, Month.FEBRUARY, 5, 10, 15);

    Target target = new Target();
    target.localDateTimeValue = localDateTime;

    assertTrue(this.queryParser.parse("localDateTimeValue = 2021-02-05T10:15").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue = 2021-02-05T10:16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue = X").test(target));

    assertFalse(this.queryParser.parse("localDateTimeValue != 2021-02-05T10:15").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue != 2021-10-05T10:14").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue != X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue > 2021-02-05T10:14").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue > 2021-02-05T10:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue > X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:14").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:15").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue >= X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue < 2021-02-05T10:16").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue < 2021-02-05T10:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue < X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:16").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:15").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:14").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue ≈ 2021-02-05T10:16").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue !≈ 2021-02-05T10:15").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:14, 2021-02-05T10:15, 2021-02-05T10:16]").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:15]").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:14]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue ∈ X").test(target));
  }

  @Test
  void shouldMatchLocalDateTimeWithSeconds()
  {
    LocalDateTime localDateTime = LocalDateTime.of(2021, Month.FEBRUARY, 5, 10, 15, 16);

    Target target = new Target();
    target.localDateTimeValue = localDateTime;

    assertTrue(this.queryParser.parse("localDateTimeValue = 2021-02-05T10:15:16").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue = 2021-02-05T10:15:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue = X").test(target));

    assertFalse(this.queryParser.parse("localDateTimeValue != 2021-02-05T10:15:16").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue != 2021-10-05T10:15:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue != X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue > 2021-02-05T10:15:15").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue > 2021-02-05T10:15:16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue > X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:15:15").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:15:16").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:15:17").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue >= X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue < 2021-02-05T10:15:17").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue < 2021-02-05T10:15:16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue < X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:15:17").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:15:16").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:15:15").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue ≈ 2021-02-05T10:15:16").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue !≈ 2021-02-05T10:15:15").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:15:15, 2021-02-05T10:15:16, 2021-02-05T10:15:17]").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:15:16]").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:15:17]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue ∈ X").test(target));
  }

  @Test
  void shouldMatchLocalDateTimeWithMilliseconds()
  {
    LocalDateTime localDateTime = LocalDateTime.of(2021, Month.FEBRUARY, 5, 10, 15, 16, 170_000_000);

    Target target = new Target();
    target.localDateTimeValue = localDateTime;

    assertTrue(this.queryParser.parse("localDateTimeValue = 2021-02-05T10:15:16.17").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue = 2021-02-05T10:15:16.18").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue = X").test(target));

    assertFalse(this.queryParser.parse("localDateTimeValue != 2021-02-05T10:15:16.17").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue != 2021-10-05T10:15:16.16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue != X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue > 2021-02-05T10:15:16.16").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue > 2021-02-05T10:15:16.17").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue > X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:15:16.16").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:15:16.17").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue >= 2021-02-05T10:15:16.18").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue >= X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue < 2021-02-05T10:15:16.18").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue < 2021-02-05T10:15:16.17").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue < X").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:15:16.17").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:15:16.18").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue <= 2021-02-05T10:15:16.16").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue ≈ 2021-02-05T10:15:16.18").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue !≈ 2021-02-05T10:15:16.17").test(target));

    assertTrue(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:15:16.16, 2021-02-05T10:15:16.17, 2021-02-05T10:15:16.18]").test(target));
    assertTrue(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:15:16.17]").test(target));
    assertFalse(this.queryParser.parse("localDateTimeValue ∈ [2021-02-05T10:15:16.16]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("localDateTimeValue ∈ X").test(target));
  }

  @Test
  void shouldMatchLong()
  {
    Target target = new Target();
    target.longValue = 10;

    assertTrue(this.queryParser.parse("longValue = 10").test(target));
    assertFalse(this.queryParser.parse("longValue = 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue = X").test(target));

    assertFalse(this.queryParser.parse("longValue != 10").test(target));
    assertTrue(this.queryParser.parse("longValue != 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue != X").test(target));

    assertTrue(this.queryParser.parse("longValue > 9").test(target));
    assertFalse(this.queryParser.parse("longValue > 10").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue > X").test(target));

    assertTrue(this.queryParser.parse("longValue >= 9").test(target));
    assertTrue(this.queryParser.parse("longValue >= 10").test(target));
    assertFalse(this.queryParser.parse("longValue >= 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue >= X").test(target));

    assertTrue(this.queryParser.parse("longValue < 11").test(target));
    assertFalse(this.queryParser.parse("longValue < 10").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue < X").test(target));

    assertTrue(this.queryParser.parse("longValue <= 11").test(target));
    assertTrue(this.queryParser.parse("longValue <= 10").test(target));
    assertFalse(this.queryParser.parse("longValue <= 9").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue ≈ 11").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue !≈ 10").test(target));

    assertTrue(this.queryParser.parse("longValue ∈ [9, 10, 11]").test(target));
    assertTrue(this.queryParser.parse("longValue ∈ [10]").test(target));
    assertFalse(this.queryParser.parse("longValue ∈ [9]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("longValue ∈ X").test(target));
  }

  @Test
  void shouldMatchShort()
  {
    Target target = new Target();
    target.shortValue = 10;

    assertTrue(this.queryParser.parse("shortValue = 10").test(target));
    assertFalse(this.queryParser.parse("shortValue = 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue = X").test(target));

    assertFalse(this.queryParser.parse("shortValue != 10").test(target));
    assertTrue(this.queryParser.parse("shortValue != 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue != X").test(target));

    assertTrue(this.queryParser.parse("shortValue > 9").test(target));
    assertFalse(this.queryParser.parse("shortValue > 10").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue > X").test(target));

    assertTrue(this.queryParser.parse("shortValue >= 9").test(target));
    assertTrue(this.queryParser.parse("shortValue >= 10").test(target));
    assertFalse(this.queryParser.parse("shortValue >= 11").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue >= X").test(target));

    assertTrue(this.queryParser.parse("shortValue < 11").test(target));
    assertFalse(this.queryParser.parse("shortValue < 10").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue < X").test(target));

    assertTrue(this.queryParser.parse("shortValue <= 11").test(target));
    assertTrue(this.queryParser.parse("shortValue <= 10").test(target));
    assertFalse(this.queryParser.parse("shortValue <= 9").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue <= X").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue ≈ 11").test(target));

    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue !≈ 10").test(target));

    assertTrue(this.queryParser.parse("shortValue ∈ [9, 10, 11]").test(target));
    assertTrue(this.queryParser.parse("shortValue ∈ [10]").test(target));
    assertFalse(this.queryParser.parse("shortValue ∈ [9]").test(target));
    assertThrows(QueryException.class, () -> this.queryParser.parse("shortValue ∈ X").test(target));
  }

  @Test
  void shouldMatchString()
  {
    Target target = new Target();
    target.stringValue = "BB";

    assertTrue(this.queryParser.parse("stringValue = BB").test(target));
    assertFalse(this.queryParser.parse("stringValue = AB").test(target));

    assertFalse(this.queryParser.parse("stringValue != BB").test(target));
    assertTrue(this.queryParser.parse("stringValue != AB").test(target));

    assertTrue(this.queryParser.parse("stringValue > AB").test(target));
    assertFalse(this.queryParser.parse("stringValue > BB").test(target));

    assertTrue(this.queryParser.parse("stringValue >= AB").test(target));
    assertTrue(this.queryParser.parse("stringValue >= BB").test(target));
    assertFalse(this.queryParser.parse("stringValue >= BC").test(target));

    assertTrue(this.queryParser.parse("stringValue < BC").test(target));
    assertFalse(this.queryParser.parse("stringValue < BB").test(target));

    assertTrue(this.queryParser.parse("stringValue <= BC").test(target));
    assertTrue(this.queryParser.parse("stringValue <= BB").test(target));
    assertFalse(this.queryParser.parse("stringValue <= AB").test(target));

    assertTrue(this.queryParser.parse("stringValue ≈ B*").test(target));
    assertTrue(this.queryParser.parse("stringValue ≈ *B").test(target));
    assertTrue(this.queryParser.parse("stringValue ≈ *B*").test(target));
    assertFalse(this.queryParser.parse("stringValue ≈ *A*").test(target));
    assertFalse(this.queryParser.parse("stringValue ≈ *A*").test(target));
    assertFalse(this.queryParser.parse("stringValue ≈ *A*").test(target));

    assertFalse(this.queryParser.parse("stringValue !≈ B*").test(target));
    assertFalse(this.queryParser.parse("stringValue !≈ *B").test(target));
    assertFalse(this.queryParser.parse("stringValue !≈ *B*").test(target));
    assertTrue(this.queryParser.parse("stringValue !≈ *A*").test(target));
    assertTrue(this.queryParser.parse("stringValue !≈ *A*").test(target));
    assertTrue(this.queryParser.parse("stringValue !≈ *A*").test(target));

    assertTrue(this.queryParser.parse("stringValue ∈ [AB, BB, BC]").test(target));
    assertTrue(this.queryParser.parse("stringValue ∈ [BB]").test(target));
    assertFalse(this.queryParser.parse("stringValue ∈ [AB]").test(target));
  }

  @Test
  void shouldMatchUnknownField()
  {
    assertThrows(QueryException.class, () -> this.queryParser.parse("unknown = anything"));
  }

  @Test
  void shouldMatchAnd()
  {
    Target target = new Target();
    target.stringValue = "Testing";
    target.intValue = 123;

    assertTrue(this.queryParser.parse("stringValue = Testing && intValue = 123").test(target));
    assertFalse(this.queryParser.parse("stringValue = X && intValue = 123").test(target));
    assertFalse(this.queryParser.parse("stringValue = Testing && intValue = 0").test(target));
  }

  @Test
  void shouldMatchOr()
  {
    Target target = new Target();
    target.stringValue = "Testing";
    target.intValue = 123;

    assertTrue(this.queryParser.parse("stringValue = Testing || intValue = 123").test(target));
    assertTrue(this.queryParser.parse("stringValue = X || intValue = 123").test(target));
    assertTrue(this.queryParser.parse("stringValue = Testing || intValue = 0").test(target));
    assertFalse(this.queryParser.parse("stringValue = X || intValue = 0").test(target));
  }

  @Test
  void shouldMatchMultipleLogicalOperatorsWithParenthesis()
  {
    Target target = new Target();
    target.booleanValue = true;
    target.stringValue = "Testing";
    target.intValue = 123;

    assertTrue(this.queryParser.parse("booleanValue = true || (stringValue = X && intValue = 1)").test(target));
    assertTrue(this.queryParser.parse("booleanValue = false || (stringValue = Testing && intValue = 123)").test(target));
    assertFalse(this.queryParser.parse("booleanValue = false || (stringValue = X && intValue = 1)").test(target));
  }

  static class Target
  {
    boolean booleanValue;
    byte byteValue;
    char charValue;
    Date dateValue;
    double doubleValue;
    float floatValue;
    int intValue;
    public LocalDate localDateValue;
    public LocalDateTime localDateTimeValue;
    long longValue;
    short shortValue;
    String stringValue;
  }
}
