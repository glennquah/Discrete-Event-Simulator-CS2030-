class Queue {

    //create another object with list of queue
    // 1. OwnQueue
    // 2. ImList<Queue> (all of the queue of the SS)
    private final int qMax;
    private final ImList<Customer> queueList;

    Queue(int qMax, ImList<Customer> queueList) {
        this.qMax = qMax;
        this.queueList = queueList;
    }

    public int getQueueNum(int custID) {
        for (int i = 0; i < this.queueList.size(); i++) {
            if (custID == this.queueList.get(i).getCustomerID()) {
                return i;
            }
        }
        return 0;
    }

    public ImList<Customer> getQueueList() {
        return this.queueList;
    }

    
    public int getQueueSize() {
        return this.queueList.size();
    }

    public int getQMax() {
        return this.qMax;
    }

    public Queue addQueue(Customer cs) {
        return new Queue(this.qMax, this.queueList.add(cs));
    }

    public Queue updateQueue(Customer cs, int queueNum) {
        ImList<Customer> newQueueList = new ImList<Customer>();
        for (int i = 0; i < this.queueList.size(); i++) {
            newQueueList = newQueueList.add(this.queueList.get(i)
                    .updateStartTime(cs.getStartTime()));
        }
        return new Queue(this.qMax, newQueueList);
    }

    public Queue removeQueue() {
        ImList<Customer> newQueueList = new ImList<Customer>();
        for (int i = 1; i < this.queueList.size(); i++) {
            newQueueList = newQueueList.add(this.queueList.get(i));
        }
        return new Queue(this.qMax, newQueueList);
    }

    public boolean checkQueue() {
        if (this.queueList.size() >= this.qMax) {
            return false;
        } else {
            return true;
        }
    }
}


