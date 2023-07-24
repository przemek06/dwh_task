package comparator;

import pojo.Status;
import java.util.Comparator;

public class StatusComparator implements Comparator<Status> {
    @Override
    public int compare(Status o1, Status o2) {
        int result = Long.compare(o1.getClientId(), o2.getClientId());

        if (result == 0) {
            result = o1.getContactTs().compareTo(o2.getContactTs());
        }

        return result;
    }
}
