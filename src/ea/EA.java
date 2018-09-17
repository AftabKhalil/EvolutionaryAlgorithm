package ea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.random;
import java.util.Random;

/*
 * @author aftab
 */

 public class EA
{
     private static int Generations = 50;
     private static int noOfInd = 25;
     private static int noOfChld = 20;
     public static void main(String[] args) throws IOException 
    {
        
         File file = new File("result.csv");
         FileWriter fileWriter = new FileWriter(file);  
         BufferedWriter myWriter = new BufferedWriter(fileWriter);
     
         Generation generation = new Generation(noOfInd);
         for (int i = 0; i < noOfInd; i++)
        {
            generation.individual[i] = Individual.getRandomInd();
        }
         
         //UNCOMMENT THE COMMITED LINES TO SHOW THE WHOLE OUTPUT ON CONSOLE
         for (int G = 0; G < Generations; G++) 
        {
             //System.out.println("-----------------------------------------------------------------------");
             //System.out.println("------------------------- Generation # "+G+" --------------------------");
             
             myWriter.write(G+", "+generation.individual[0].fitness);
             myWriter.newLine();       

             //for (int i = 0; i < generation.individual.length; i++) 
            //{
                 //System.out.println(generation.individual[i].x+"\t\t"+generation.individual[i].y+"\t\t"+generation.individual[i].fitness);  
            //}
             //Creating Childrens
             Generation children = generation.createChildren(noOfChld);
             //Selecting the best parents and childrens as new generation
             generation = Generation.chooseBest(generation,children);
        }
         
         //System.out.println("-----------------------------------------------------------------------");
         //System.out.println("------------------------- Generation # "+Generations+" --------------------------");
         //for (int i = 0; i < generation.individual.length; i++) 
        //{
             //System.out.println(generation.individual[i].x+"\t\t"+generation.individual[i].y+"\t\t"+generation.individual[i].fitness);  
        //}
         myWriter.close();
         fileWriter.close();
        
    }
     
     private static class Parent
    {
         public static Individual[] createTwoChilds(Individual parent1,Individual parent2)
        {
            Individual child1 = new Individual(parent1.x,parent2.y).mutate();
            Individual child2 = new Individual(parent2.x,parent1.y).mutate();
            
            return new Individual[]{child1,child2};
        }
    }
     
     private static class Generation
    {
        public Individual[] individual;
        
        public static Generation chooseBest(Generation parents,Generation children)
        {
            Generation all = new Generation(parents.individual.length+children.individual.length);
            int i;
            for (i = 0; i < parents.individual.length; i++) {
                all.individual[i] = parents.individual[i];
            }
            for (int j = 0; j < children.individual.length; j++,i++) {
                all.individual[i] = children.individual[j];
            }
            
             for (i = 0; i < all.individual.length; i++) 
            {
                for (int j = i; j < all.individual.length; j++) 
                {
                    if (all.individual[i].fitness< all.individual[j].fitness)
                    {
                        Individual ind = all.individual[i];
                        all.individual[i] = all.individual[j];
                        all.individual[j] = ind;
                    }   
                }
            }
             
             Generation best = new Generation(parents.individual.length);
             for (i = 0; i < best.individual.length; i++) 
            {
                best.individual[i] = all.individual[i];
            }
             return best;
        }
        
        public Generation(int noOfInd)
        {
            individual = new Individual[noOfInd];
        }
        
        public Generation createChildren(int noOfChld)
        {  
            Generation children = new Generation(noOfChld);
          
            for (int i=0, j=0; i < noOfChld/2; i++) {
              
                int p1 = (int) (random()*10);
                int p2 = (int) (random()*10);

                while(p1 == p2)
                {
                     p1 = (int) (random()*10);
                     p2 = (int) (random()*10);
                }
                
                Individual[] newChilds = Parent.createTwoChilds(this.individual[p1], this.individual[p2]);
                children.individual[j]=newChilds[0];
                j++;
                children.individual[j]=newChilds[1];
                j++;
            }
            return children;
        }
    }
     
     private static class Individual
    {
         private static double getFitness(double x, double y) 
        {
            return 100*(x*x-y)*(x*x-y)+(1-x)*(1-x);
        }
         private static Individual getRandomInd() 
        {
            Random random = new Random();
            int max_x = 2 , min_x = -2;
            int max_y = 3 , min_y = -1;
            
            double x = random.nextDouble() * (random.nextInt(max_x + 1 - min_x) + min_x);
            double y = random.nextDouble() * (random.nextInt(max_y + 1 - min_y) + min_y);
            
            return new Individual(x,y);
        }
         
         public double x;
         public double y;
         public double fitness;
         
         public Individual(double x, double y)
        {
             this.x = x;
             this.y = y;
             this.fitness = Individual.getFitness(x,y);
        }

        private Individual mutate() {
            int pro = (int)(random()*100);
            if(pro<=75){
                int gno = (int)(random()*100);
                if(gno%2==0)
                {
                    double x = this.x;
                    int addsub = (int)(random()*100);
                    if(addsub%2==0)
                    {
                        x = x+0.25;
                        if(x>2)
                        {
                            this.x = 2;
                            return this;
                        }
                        this.x = x;
                        return this;
                    }
                    else
                    {
                        x=x-0.25;
                        if(x<-2)
                        {
                            this.x = -2;
                            return this;
                        }
                        this.x = x;
                        return this;
                    }
                }
                else
                {
                    double y = this.y;
                    int addsub = (int)(random()*100);
                    if(addsub%2==0)
                    {
                        y=y+0.25;
                        if(y>3)
                        {
                            this.y = 3;
                            return this;
                        }
                        this.y = y;
                        return this;
                    }
                    else
                    {
                        y=y-0.25;
                        if(y<-1)
                        {
                            this.y = -1;
                            return this;
                        }
                        this.y = y;
                        return this;
                    }
                }
            }
            else
            {
                return this;
            }
        }
    }
}