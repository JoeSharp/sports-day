package com.ratracejoe.sportsday.tasks.ui;

import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Has one more item in than")
public class ListSizeComparison<T> implements Question<Boolean> {
  private final List<T> list1;
  private final List<T> list2;

  public ListSizeComparison(List<T> list1, List<T> list2) {
    this.list1 = list1;
    this.list2 = list2;
  }

  @Override
  public Boolean answeredBy(Actor actor) {
    return list1.size() == list2.size() + 1;
  }

  public static <T> ListSizeComparison<T> hasOneMoreItemThan(List<T> list1, List<T> list2) {
    return new ListSizeComparison<>(list1, list2);
  }
}
