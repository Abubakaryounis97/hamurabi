
import java.util.InputMismatchException;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi 
{         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    public static void main(String [] args) 
    { 
        new Hammurabi().playGame();
    }


    void playGame() 
    {    // initialize variables
        int population = 100; 
        int acres = 1000;
        int bushels=2800;
        int landPrice = 19; // initial price of land
        int year = 1; // start at year 1
        //starting conditions 
        System.out.println(" O great Hammurabi!");
        System.out.println("You are in the year 1 of your 10 year reign.");
        System.out.println("in the previous year 0 people starved to death");
        System.out.println("in the previous year 5 people have entered the city");
        System.out.println("the population is now 100.");
        System.out.println("we harvested 3000 bushels at 3 bushels pe acre");
        System.out.println("rats destroyed 200 bushels of grain");
        System.out.println("you have 2800 bushels of grain left.");
        System.out.println("city own 1000 acres of land in the city.");
        System.out.println("the price of land is 19 bushels per acre.");
         System.out.println("--------------------------------------------------");

        //////////hammurabi decisions
        while ( year <=10)
        {
      
        System.out.println("The price of land is now " + landPrice + " bushels per acre.");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");
        // step 1: ask how many acres to buy
        int acresToBuy = askHowManyAcresToBuy(landPrice, bushels);
        acres = acres + acresToBuy; 
        bushels = bushels - acresToBuy * landPrice;
        System.out.println("You have bought " + acresToBuy + " acres of land.");
        System.out.println("You now have " + acres + " acres of land.");
        System.out.println("You now have " + bushels + " bushels");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");
        
        // step 2: ask how many acres to sell
       int acresToSell = askHowManyAcresToSell(acres, bushels);
        acres = acres - acresToSell;
        bushels = bushels + acresToSell * landPrice;
        System.out.println("You have sold " + acresToSell + " acres of land.");
        System.out.println("You now have " + acres + " acres of land.");
        System.out.println("You now have " + bushels + " bushels");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");
        
        // step 3: ask how many people to feed
        int bushelsToFeed = askHowManyPeopleToFeed(bushels);
        bushels = bushels - bushelsToFeed; // subtract bushels fed to people
        System.out.println("You have fed " + bushelsToFeed + " bushels to the people.");
        System.out.println("You now have " + bushels + " bushels left.");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");
        // step 4 acres to plant
        int acresToPlant = askHowManyAcresToPlant(acres, population, bushels);
        bushels = bushels + (acresToPlant * landPrice); 
        System.out.println("You have planted " + acresToPlant + " acres of land.");
        System.out.println("You now have " + bushels + " bushels.");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");
     
        // deaths from plague
        int deaths = plagueDeaths(population);
        population = population - deaths;
        System.out.println("Unfortunately, " + deaths + " people died from the plague this year.");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");

        // deaths from starvation
        int deathsStarvation = starvationDeaths(population, bushelsToFeed);
        population = population - deathsStarvation;
        System.out.println("Unfortunately, " + deathsStarvation + " people died from starvation this year.");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");

        // check for uprising
        boolean uprising = uprising(population, deathsStarvation);
        if (uprising) 
            {
            System.out.println("The people have risen up against you, Hammurabi! You have been overthrown!");
            System.out.println("--------------------------------------------------");
            return; // end the game
            }
        // calculate immigrants
        int immigrants = immigrants(population, acres, bushels);
        population = population + immigrants;
            System.out.println("Great Hammurabi, you have " + immigrants + " immigrants this year.");
            System.out.println("Bushels: " + bushels);
            System.out.println("--------------------------------------------------");
        // harvest
        int harvest = harvest(acresToPlant, bushels);
            bushels = bushels + harvest; // add harvest to bushels
        System.out.println("You have harvested " + harvest + " bushels of grain.");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");
        /// rats
        int grainEatenByRats = grainEatenByRats(bushels);
            bushels = bushels - grainEatenByRats; // subtract grain eaten by rats
        System.out.println("Unfortunately, rats have eaten " + grainEatenByRats + " bushels of grain.");
        System.out.println("You now have " + bushels + " bushels of grain left.");
        System.out.println("Bushels: " + bushels);
        System.out.println("--------------------------------------------------");
         // update land price for next year
        landPrice = costOfLand();
        ////// summary
        printSummary(year, deaths, population, acres, bushels, landPrice, deathsStarvation, immigrants, grainEatenByRats);
        year++;
        } 
        System.out.println("GAME OVER");
        // end of for loop for 10 years
    }

    int getNumber(String message)
    {
        while (true) 
        {
            System.out.print(message);
            try {
                return scanner.nextInt(); // reads the intergers after the message
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }
    int askHowManyAcresToBuy (int price, int bushels) 
    {
    while (true) {
        int acresToBuy = getNumber("How many acres do you want to buy? ");
        int cost = acresToBuy * price;

        if (acresToBuy < 0) {
            System.out.println("You can't buy negative land!");
        } else if (cost > bushels) {
            System.out.println("You don't have enough grain. You need " + cost + " bushels, but only have " + bushels + ".");
        } else {
            return acresToBuy;
        }
    }
    }
    int askHowManyAcresToSell(int acres, int bushels) 
    {    while(true)
        {
            int acresTosell= getNumber("How many acres do you want to sell? ");
            if (acresTosell<0)
            {
                System.out.println("not possible to sell negative acres!");
            }
            else if (acresTosell > acres)
            {
                System.out.println("You don't have that many acres to sell. You have " + acres + " acres.");
            } 
            else {
                    return acresTosell;
            }
        }

        
    }

    int askHowManyPeopleToFeed(int bushels) // Each person needs at least 20 bushels of grain per year to survive
    {  
        while(true)
        {
        int bushelsTofeed= getNumber("How many bushels do you want to feed the people? ");
            if (bushelsTofeed < 0) 
            { System.out.println("can not feed negative bushels!");
            } 
             else if (bushelsTofeed > bushels) 
            {
            System.out.println("You don't have enough bushels. You have " + bushels + " bushels.");
             } 
            else 
            {
            return bushelsTofeed;
            }
        }
    }

    int askHowManyAcresToPlant(int acres, int population, int bushels)
    {
    while (true)
    {
        { 
            int acresToPlant = getNumber("How many acres do you want to plant? ");
            if (acresToPlant < 0)
            {
                System.out.println("You can't plant negative acres!");
            }
            else if (acresToPlant > acres)
            {
                System.out.println("You don't have that many acres to plant. You own " + acres + " acres.");
            }
        else if (acresToPlant > population * 10)
            {
            System.out.println("You can't plant more than 10 acres per person. You have " + population + " people, so you can plant a maximum of " + (population * 10) + " acres.");
            }
        else if (acresToPlant * 2 > bushels)
            {
            System.out.println("You don't have enough bushels to plant that many acres. You have " + bushels + " bushels, and it takes 2 bushels per acre.");
            }
        else
            {   
            return acresToPlant;
            }
       
        }
    }
}

    int plagueDeaths (int population) 
    {
        int deaths = rand.nextInt(population / 15); // up to 15% of the population can die
        return deaths;
    }


    int starvationDeaths(int population, int bushelsToFeed)
    {
        int bushelsNeeded = population * 20; // each person needs 20 bushels to survive
        if (bushelsToFeed< bushelsNeeded) 
        {
            int deathsStarvation= population-(bushelsToFeed / 20);
            return deathsStarvation;
        }
        else
        {
            return 0; // nobody dies from starvation
        }
    }

    boolean uprising (int population, int deathsStarvation)
    {
        int Limit = ((population*45)/100);
        if (deathsStarvation > Limit)
        {
            return true; // uprising occurs
        }
        else
        {
            return false; // no uprising
        }
   
}

    int immigrants ( int population, int acres, int bushels)
    {
        int immigrants= (20*acres + bushels) / (100 * population)+1;
        return immigrants; // returns the number of
    }

    int harvest ( int acresToPlant, int bushels)
{
    int newbushels = acresToPlant * 3; // each acre produces 3 bushels
    return newbushels;

}

    int grainEatenByRats (int bushels)
{
    int perEaten = rand.nextInt(21)+10;
     if (rand.nextInt(100) < 40)
     {
        int eaten = (perEaten * bushels) / 100; // rats eat 10-30% of the grain
        return eaten;
     }
     else 
     {
        return 0; // no grain eaten by rats
     }


}

    int costOfLand()
{
    return (rand.nextInt(24) + 17); // random price between 17 and 36 bushels per acre
}

void printSummary(int year,int deaths, int population, int acres, int bushels, int landPrice, int deathsStarvation, int immigrants, int grainEatenByRats)
{
    System.out.println("Year: " + year);
    System.out.println("deaths from starvation: " + deathsStarvation);
    System.out.println("plauge deaths:"+ deaths);
    System.out.println("immigrants: " + immigrants);
    System.out.println("population: " + population);
    System.out.println("acres left: " + acres);
    System.out.println("bushels left: " + bushels);
    System.out.println("grain eaten by rats: " + grainEatenByRats);
    System.out.println("land price: " + landPrice);

    System.out.println("--------------------------------------------------");

}
}