//Done class as a child class of Event
class Done extends Event {

    private final Server server;

    //Done object
    Done(double eventTime, Customer customer, Server server) {
        super(eventTime, customer);
        this.server = server;
    }
    
    @Override
    Boolean checkNotDuplicate() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s done serving by %s",
                super.getEventTime(),
                super.getCustomer().getCustomerID(),
                this.server.toString());
    }

    @Override
    Boolean haveNextEvent() {
        return false;
    }

    @Override
    Pair<Shop, Event> run(Shop shop) {
        Server serverAfterRest = shop.getListOfServers()
            .get(this.server.getServerID() - 1).changeServerAfterRest();
        ImList<Server> listOfServerAfterRest = shop.getListOfServers()
            .set(this.server.getServerID() - 1, serverAfterRest); 
        Shop newShop = new Shop(listOfServerAfterRest, shop.getStatistic()); 
        Pair<Shop, Event> p = new Pair<Shop, Event>(newShop, this);
        return p;
    }
}
