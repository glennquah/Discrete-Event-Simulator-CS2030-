//Arrival class as a child class of Event
class Arrival extends Event {

    Arrival(double eventTime, Customer customer) {
        super(eventTime, customer);
    }
    
    @Override
    Boolean haveNextEvent() {
        return true;
    }

    @Override
    Boolean checkNotDuplicate() {
        return true;
    }

    @Override
    Pair<Shop, Event> run(Shop shop) {
        //Check if can Serve
        for (int i = 0; i < shop.getListOfServers().size(); i++) {
            if (shop.getListOfServers().get(i).serveCustomer(super.getEventTime())) {
                Serve ser = new Serve(super.getEventTime(),
                                      super.getCustomer(),
                                      shop.getListOfServers().get(i));
                Pair<Shop, Event> p = new Pair<Shop, Event>(shop, ser);
                return p;
            }
        }
        //If not check if can queue up (Wait)
        for (int j = 0; j < shop.getListOfServers().size(); j++) {
            if (shop.getListOfServers().get(j).getQueue()
                    .checkQueue()) {

                double startTime = shop.getListOfServers()
                    .get(j).getDoneTime(); 
                Customer customerInQueue = super.getCustomer().updateStartTime(startTime);
                Queue newQueue = shop.getListOfServers().get(j).getQueue()
                                     .addQueue(customerInQueue);
                shop = shop.updateQueueForEvent(j + 1,
                                                startTime,
                                                newQueue);
                Server svIfWait = shop.getListOfServers().get(j); 
                Wait wt = new Wait(super.getEventTime(),
                                   customerInQueue,
                                   svIfWait,
                                    true);
                Pair<Shop, Event> p = new Pair<Shop, Event>(shop, wt);
                return p;
            }
        }
        //else leave
        shop = new Shop(shop.getListOfServers(), shop.getStatistic().addCustomerLeft());
        Leave lv = new Leave(super.getEventTime(), super.getCustomer());
        Pair<Shop, Event> p = new Pair<Shop, Event>(shop, lv);
        return p;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s arrives",
                super.getCustomer().getArrivalTime(),
                super.getCustomer().getCustomerID());
    }
}










