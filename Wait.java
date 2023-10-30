//Event class a wait class
class Wait extends Event {

    private final Server server;
    private final Boolean duplicate;

    //Wait object
    Wait(double eventTime, Customer customer, Server server, boolean duplicate) {
        super(eventTime, customer);
        this.server = server;
        this.duplicate = duplicate;
    }

    @Override
    Boolean checkNotDuplicate() {
        return this.duplicate;
    }

    @Override
    Boolean haveNextEvent() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s waits at %s",
                super.getEventTime(),
                super.getCustomer().getCustomerID(),
                this.server.toString());
    }

    @Override
    Pair<Shop, Event> run(Shop shop) {
        int custID = super.getCustomer().getCustomerID();
        int queueNo = shop.getListOfServers().get(this.server.getServerID() - 1)
            .getCustomerQueueNum(custID); 
        double startTime = shop.getListOfServers()
            .get(this.server.getServerID() - 1).getDoneTime(); 
        Customer customerInQueue = super.getCustomer().updateStartTime(startTime);
        //If the customer is the first in the queue, serve the customer 
        if (queueNo == 0 && startTime <= super.getEventTime()) {
            double waitTime = startTime - super.getCustomer().getArrivalTime();
            shop = new Shop(shop.getListOfServers(),
                    shop.getStatistic().getWaitTime(waitTime));       
            Serve ser = new Serve(startTime,
                    customerInQueue,
                    shop.getListOfServers().get(this.server.getServerID() - 1));
            Pair<Shop, Event> p = new Pair<Shop, Event>(shop, ser);
            return p;
        } else  {
            //If the customer is not the FIRST in queue, return another wait
            if (shop.getListOfServers().get(this.server.getServerID() - 1)
                    .getFirstCheckOutID() == 0) {
                //if customer is queueing through a HUMAN server
                Queue newQueue = shop.getListOfServers().get(this.server.getServerID() - 1)
                    .getQueue().updateQueue(customerInQueue, queueNo);
                shop = shop.updateQueueForEvent(this.server.getServerID(),
                        shop.getListOfServers().get(this.server.getServerID() - 1).getDoneTime(),
                        newQueue);
                Wait tempWait = new Wait(startTime,
                        customerInQueue,
                        shop.getListOfServers().get(this.server.getServerID() - 1),
                        false);
                Pair<Shop, Event> p = new Pair<Shop, Event>(shop, tempWait);
                return p;
            } else {
                //if customer is queueing through a self check out
                //Go through queuelist of selfcheckout and see which timing is the earliest
                Queue newQueue = shop.getListOfServers().get(this.server.getServerID() - 1)
                    .getQueue().updateQueue(customerInQueue, queueNo);
                Shop tempshop = shop.updateQueueForEvent(this.server.getServerID(),
                        shop.getListOfServers().get(this.server.getServerID() - 1).getDoneTime(),
                        newQueue);
                Pair<Double, Integer> endTimeSelfCheckOut;
                endTimeSelfCheckOut = tempshop.getFastestSelfCheckOutTime();
                double fastestEndTime = endTimeSelfCheckOut.first();
                int selfCheckOutNumber = endTimeSelfCheckOut.second();
                Customer customerInQueueSelfCheckOut = super.getCustomer()
                    .updateStartTime(fastestEndTime);
                newQueue = shop.getListOfServers().get(this.server.getServerID() - 1)
                    .getQueue().updateQueue(customerInQueueSelfCheckOut, queueNo);
                shop = shop.updateQueueForEvent(selfCheckOutNumber + 1,
                        fastestEndTime,
                        newQueue);
                Wait tempWait = new Wait(fastestEndTime,
                        customerInQueueSelfCheckOut,
                        shop.getListOfServers().get(selfCheckOutNumber),
                        false);
                Pair<Shop, Event> p = new Pair<Shop, Event>(shop, tempWait);
                return p;
            }
        }
    }
}
