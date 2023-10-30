class SelfCheckOut extends Server {

    private final int firstCheckOutID;

    SelfCheckOut(int serverID, double doneTime, Queue queue, int firstCheckOutID) {
        super(serverID, doneTime, queue, () -> 0.0);
        this.firstCheckOutID = firstCheckOutID;
    }

    @Override
    public Server changeServerAfterRest() {
        return this;
    }
    
    @Override
    public Server getServerAfterEvent(int newServerID, double newEndTime, Queue newQueue) {
        return new SelfCheckOut(newServerID,
                                newEndTime,
                                newQueue,
                                this.firstCheckOutID);
    }

    @Override
    public int getFirstCheckOutID() {
        return this.firstCheckOutID;
    }

    @Override
    public Server updateSelfCheckOutQueue(Queue q) {
        return new SelfCheckOut(super.getServerID(),
                                super.getDoneTime(),
                                q,
                                this.firstCheckOutID);
    }

    @Override
    public String toString() {
        return String.format("self-check %s", super.getServerID());
    }
}
