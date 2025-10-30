package ptit.edu.vn.ltw.utility;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class ObjectUtility {
    public <T> T getMethod(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception _) {
            return null;
        }
    }
}
