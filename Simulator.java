import java.util.function.Supplier;

//Simulator class which works as a shop
class Simulator {

    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;

    //Simulator object
    Simulator(int numOfServers,
              int numOfSelfChecks,
              int qmax,
              ImList<Double> arrivalTimes,
              Supplier<Double> serviceTimes,
              Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.restTimes = restTimes;
    }

    /*creating an ImList of customers by running a for loop,
      inputting the customerID, arrivalTimes and serviceTimes*/
    private ImList<Customer> createCustomers() {
        ImList<Customer> listOfCustomers = new ImList<Customer>();
        for (int i = 0; i < this.arrivalTimes.size(); i++) {
            listOfCustomers = listOfCustomers.add(new Customer(
                        (i + 1),
                        arrivalTimes.get(i),
                        this.serviceTimes,
                        0));
        }
        return listOfCustomers;
    }

    //Create Imlist of empty servers, only with serverID
    private ImList<Server> createServers() {
        ImList<Server> listOfServers = new ImList<Server>();
        ImList<Customer> emptyQueueList = new ImList<Customer>();
        //Add human servers
        for (int i = 1; i <= this.numOfServers; i++) {
            listOfServers = listOfServers.add(new Server(i,
                        0, new Queue(this.qmax, emptyQueueList),
                        this.restTimes));
        }
        //Add self check out
        for (int i = 1; i <= this.numOfSelfChecks; i++) {
            listOfServers = listOfServers.add(new SelfCheckOut(this.numOfServers + i,
                        0, new Queue(this.qmax, emptyQueueList), this.numOfServers + 1));
        }
        return listOfServers;
    }

    //Method to get a summary of who came and who left
    String getSummary() {
        ImList<Customer> listOfCustomers = createCustomers(); //Create list of Customers 
        ImList<Server> listOfServers = createServers(); //Create list of Servers
        Statistic stats = new Statistic(0, 0, 0);
        Shop shop = new Shop(listOfServers, stats);
        PQ<Event> pq = new PQ<Event>(new EventComp());
        String summary = "";

        for (int i = 0; i < listOfCustomers.size(); i++) {
            pq = pq.add(new Arrival(listOfCustomers.get(i).getArrivalTime(),
                                    listOfCustomers.get(i)));
        }

        Pair<Event, PQ<Event>> pr;
        Pair<Shop, Event> shopEvent;
 
        while (!pq.isEmpty()) {
            pr = pq.poll();
            if (pr.first().checkNotDuplicate()) {
                summary += "\n" + pr.first().toString();
            }
            if (pr.first().haveNextEvent()) {
                shopEvent = pr.first().run(shop);
                shop = shopEvent.first();
                pq = pr.second().add(shopEvent.second());
            } else {
                shop = pr.first().run(shop).first();
                pq = pr.second();
            }
        }

        summary = summary.trim();
        //to add the final summary of totalserved & totalreject
        summary += "\n" + shop.getStatistic();
        return summary;
    }

    //To return the summary string
    public String simulate() {
        String str = getSummary();
        return str;
    }
}
