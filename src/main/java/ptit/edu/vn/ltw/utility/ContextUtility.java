package ptit.edu.vn.ltw.utility;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import ptit.edu.vn.ltw.security.dto.UserPrincipal;

import java.util.Objects;

@UtilityClass
public class ContextUtility {

    public String getUserId(){
        Object objectPrincipal = ObjectUtility.getMethod(() ->
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (!Objects.isNull(objectPrincipal) && objectPrincipal instanceof UserPrincipal principal){
            return ObjectUtility.getMethod(principal::getId);
        }
        return "";
    }
}
