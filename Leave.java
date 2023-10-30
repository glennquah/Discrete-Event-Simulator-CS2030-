//Leave class as a child class of Event to store leave time
class Leave extends Event {

    Leave(double eventTime, Customer customer) {
        super(eventTime, customer);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves",
                super.getCustomer().getArrivalTime(),
                super.getCustomer().getCustomerID());
    }

    @Override
    Boolean checkNotDuplicate() {
        return true;
    }

    @Override
    Boolean haveNextEvent() {
        return false;
    }


    @Override
    Pair<Shop, Event> run(Shop shop) {
        Leave lv = new Leave(super.getEventTime(), super.getCustomer());
        Pair<Shop, Event> p = new Pair<Shop, Event>(shop, lv);
        return p;
    }
}
