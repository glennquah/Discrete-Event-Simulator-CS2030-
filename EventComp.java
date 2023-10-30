//EventComp class for comparing which event should come first
import java.util.Comparator;

class EventComp implements Comparator<Event> {

    public int compare(Event e1, Event e2) {
        //To compare time different between both event
        double timeDiff = e1.getEventTime() - e2.getEventTime();
        if (timeDiff < 0) {
            return -1; 
        } else if (timeDiff > 0) {
            return 1;
        } else {
            return e1.getCustomer().getCustomerID() - e2.getCustomer().getCustomerID();
        }
    }
}

