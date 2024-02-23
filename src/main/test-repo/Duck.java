package parks;

class Duck {

    String species;

    String genus;

    String governmentId;

    double age;

    String galaxyOfOrigin;

    String favoriteBreadBrand;

    float wingSpan;

    public void confirmConspiracy()
    {
        System.out.println("We are government drones.");
    }

    public void hop()
    {
        System.out.println("*hop*");
    }

    public void quack()
    {
        System.out.println("*quack*");
    }

    public void quackLoud()
    {
        System.out.println("*QUACK*");
    }

    public void peck()
    {
        System.out.println("*light tapping*");
    }

    public void eat()
    {
        peck();
        peck();
        peck();
        System.out.println("*crunch*");
    }

    public void fly()
    {
        System.out.println("*flap flap flap*");
    }

    public void enterTree()
    {
        System.out.println("*rustle*");
    }

    public String getGovernmentId()
    {
        return this.governmentId;
    }

    public String getGenus()
    {
        return this.genus;
    }

    public String getSpecies()
    {
        return this.species;
    }

    
}