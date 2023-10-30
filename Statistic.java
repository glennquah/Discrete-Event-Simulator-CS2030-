//Statistic class to showcase the different statistic
//1. Average Waiting Time
//2. Customer Served
//3. Customer left

class Statistic {
    private final double waitTime;
    private final int customerServed;
    private final int customerLeft;
    
    //Statistic object
    Statistic(double waitTime, int customerServed, int customerLeft) {
        this.waitTime = waitTime;
        this.customerServed = customerServed;
        this.customerLeft = customerLeft;
    }

    public Statistic getWaitTime(double time) {
        return new Statistic(this.waitTime + time,
                         this.customerServed,
                         this.customerLeft); 
    }

    public Statistic addCustomerServed() {
        return new Statistic(this.waitTime,
                         this.customerServed + 1,
                         this.customerLeft);
    }

    public Statistic addCustomerLeft() {
        return new Statistic(this.waitTime,
                         this.customerServed,
                         this.customerLeft + 1);
    }
 
    @Override
    public String toString() {
        if (this.customerServed == 0) {
            return String.format("[0.000 0 %s]",
                 this.customerLeft);
        } else {
            return String.format("[%.3f %s %s]",
                 (this.waitTime / this.customerServed),
                 this.customerServed,
                 this.customerLeft);
        }
    }
}
