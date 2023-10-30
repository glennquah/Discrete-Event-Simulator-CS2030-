//Serve class as a child class of Event
class Serve extends Event {

    private final Server server;

    //Serve object
    Serve(double eventTime, Customer customer, Server server) {
        super(eventTime, customer);
        this.server = server;
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
    public String toString() {
        return String.format("%.3f %s serves by %s",
                super.getEventTime(),
                super.getCustomer().getCustomerID(),
                this.server.toString());
    }

    @Override
    Pair<Shop, Event> run(Shop shop) {

        double serviceTime = super.getCustomer().getServiceTime();
        int serverID = this.server.getServerID();

        //If there are people in the queue, update the queue by removing the last person
        if (shop.getListOfServers().get(serverID - 1).getQSize() > 0) {
            Queue moveQueue = shop.getListOfServers().get(serverID - 1).getQueue().removeQueue();
            Double newTime = serviceTime + 
                shop.getListOfServers().get(serverID - 1)
                .getQueue().getQueueList().get(0).getStartTime();
            shop = shop.updateQueueForEvent(serverID,
                                            newTime,
                                            moveQueue);
        } else {
            //If nobody in the queue, change the server timing
            shop = shop.updateQueueForEvent(serverID,
                                            serviceTime + super.getEventTime(),
                                            this.server.getQueue());
        }
        shop = shop.addCustomerServed();
        Done don = new Done(super.getEventTime() + serviceTime,
                            super.getCustomer(),
                            shop.getListOfServers().get(serverID - 1));
        Pair<Shop, Event> p = new Pair<Shop, Event>(shop, don);
        return p;
    }
}
