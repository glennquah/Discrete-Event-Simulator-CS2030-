import java.util.function.Supplier;
//Server class to describe 1 server to serve a customer

class Server {

    private final double doneTime;
    private final int serverID;
    private final Queue queue;
    private final Supplier<Double> restTimes;

    //Server object
    Server(int serverID, double doneTime, Queue queue, Supplier<Double> restTimes) {
        this.serverID = serverID;
        this.doneTime = doneTime;
        this.queue = queue;
        this.restTimes = restTimes;
    }

    public int getFirstCheckOutID() {
        return 0;
    }

    public Server updateSelfCheckOutQueue(Queue q) {
        return this;
    }
   

    public Server changeServerAfterRest() {
        return new Server(this.serverID,
                          this.doneTime + this.restTimes.get(),
                          this.queue,
                          this.restTimes);
    }

    public Server getServerAfterEvent(int newServerID, double newEndTime, Queue newQueue) {
        return new Server(newServerID,
                          newEndTime,
                          newQueue,
                          this.restTimes);
    }

    public int getCustomerQueueNum(int custID) {
        return this.queue.getQueueNum(custID);
    }
    
    public Queue getQueue() {
        return this.queue;
    }
   
    public int getQSize() {
        return this.queue.getQueueSize();
    }

    public int getServerID() {
        return this.serverID;
    }

    public double getDoneTime() {
        return this.doneTime;
    }

    //To check if the server is still currently serving a customer
    boolean serveCustomer(double startTime) {
        if (startTime >= this.doneTime) {
            return true; /* e.g. if the end time of the customer is 2.0
                            seconds and the start time of the next customer is 2.0,
                            the server can serve the next customer */
        } else {
            return false; /* e.g. if the end time of the customer is 2.0
                             seconds and the start time of the next customer is 1.5,
                             the server cannot serve the next customer */
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s", this.serverID);
    }
}
