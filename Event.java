//Event class as a parent class 
abstract class Event {

    protected final double eventTime;
    protected final Customer customer;

    //Create Event class
    Event(double eventTime, Customer customer) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    //To return eventTime
    public double getEventTime() {
        return this.eventTime;
    }

    //To return which customer is in this event
    public Customer getCustomer() {
        return this.customer;
    }

    abstract Boolean haveNextEvent();
    
    abstract Boolean checkNotDuplicate();
      
    abstract Pair<Shop, Event> run(Shop shop);
}
