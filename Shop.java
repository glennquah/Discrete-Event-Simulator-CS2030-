class Shop {

    private final ImList<Server> listOfServers;
    private final Statistic statistic;

    Shop(ImList<Server> listOfServers,
            Statistic statistic) {
        this.listOfServers = listOfServers;
        this.statistic = statistic;
    }

    public ImList<Server> getListOfServers() {
        return this.listOfServers;
    }

    public Statistic getStatistic() {
        return this.statistic;
    }

    public Shop addCustomerServed() {
        return new Shop(this.listOfServers, this.statistic.addCustomerServed());
    }

    public Shop updateQueueForEvent(int serverNum, double startTime, Queue q) {

        ImList<Server> tempList = new ImList<Server>();
        for (int i = 0; i < this.listOfServers.size(); i++) {
            tempList = tempList.add(this.listOfServers.get(i));
        }

        if (tempList.get(serverNum - 1).getFirstCheckOutID() != 0) {
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getFirstCheckOutID() != 0) {
                    tempList = tempList.set(i, tempList.get(i).updateSelfCheckOutQueue(q));
                }
            }
        }

        Server svAfterWaiting = tempList.get(serverNum - 1).getServerAfterEvent(serverNum,
                startTime,
                q);
        tempList = tempList.set(serverNum - 1, svAfterWaiting);

        return new Shop(tempList, this.statistic);
    }
    
    public Pair<Double, Integer> getFastestSelfCheckOutTime() {     
        double fastestTime = Double.MAX_VALUE;
        int serverNumber = -1;
        for (int i = 0; i < this.listOfServers.size(); i++) {
            if (this.listOfServers.get(i).getFirstCheckOutID() != 0) {
                double doneTime = this.listOfServers.get(i).getDoneTime();
                if (doneTime < fastestTime) {
                    fastestTime = doneTime;
                    serverNumber = i;
                }
            }
        }
        return new Pair<Double, Integer>(fastestTime, serverNumber);
    }    
}
