// "Replace with collect" "true"
import java.util.*;

public class Collect {
  class Person {
    String getName() {
      return "";
    }
  }

  Set<String> names = new HashSet<>();
  void collectNames(List<Person> persons){
    for (Person person : pers<caret>ons) {
      names.add(person.getName());
    }
  }
}
