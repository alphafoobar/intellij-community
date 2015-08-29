import org.jetbrains.annotations.NotNull;

import java.util.*;

class C {
    {
        Object[] array;

        List l1 = newMethod(array);

        List l2 = newMethod(getObjects());

        System.out.println("l1 = " + l1 + ", l2 = " + l2);
    }

    @NotNull
    private List newMethod(Object[] array) {
        return new ArrayList(Arrays.asList(array));
    }

    String[] getObjects() {
    }
}