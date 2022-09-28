
/**
 * Write a description of MiniProjectAssignment here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class MiniProjectAssignment {
	public void printNames () {
		FileResource fr = new FileResource();
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (numBorn <= 100) {
				System.out.println("Name " + rec.get(0) +
						   " Gender " + rec.get(1) +
						   " Num Born " + rec.get(2));
			}
		}
	}

	public void totalBirths (FileResource fr) {
		int totalBirths = 0;
		int totalBoys = 0;
		int totalGirls = 0;
		int countNames = 0;
		int countBoysNames = 0;
		int countGirlsNames = 0;
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			countNames++;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				countBoysNames++;
			}
			else {
				totalGirls += numBorn;
				countGirlsNames++;
			}
		}
		
		System.out.println("total births = " + totalBirths);
		System.out.println("female girls = " + totalGirls);
		System.out.println("male boys = " + totalBoys);
		
		System.out.println("Number of all names = " + countNames);
		System.out.println("Number of Boys names = " + countBoysNames);
		System.out.println("Number of Girls names = " + countGirlsNames);
	}
	
	public void testTotalBirths () {
		FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/us_babynames_test/example-small.csv");
		totalBirths(fr);
	}
	
	public int getRank(String name, String gender, FileResource fr) {
	    int rank = 0;
	    boolean isFound = false; 
	    for (CSVRecord rec : fr.getCSVParser(false)) {
	        if (rec.get(1).equals(gender))
	        {
	            rank++;
	            if (rec.get(0).equals(name))
	            {
	                isFound = true;
	                break;
	            }
	        }
	    }
	    
	    if (isFound)
	    {
	        return rank;
	    }
	    else
	    {
	        return -1;
	    }
	}
	
	public void testGetRank () {
		FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/us_babynames_test/yob"+ 2012 +"short.csv");
		int rank = getRank("Frank", "M", fr);
		if (rank != -1)
		{
		    System.out.println("Mason rank is " + rank);
		}
		else
		{
		    System.out.println("Name not found");
		}
	}
	
	public String getName(int rank, String gender, FileResource fr) {
	    
	    //FileResource fr = new FileResource("data/us_babynames_test/yob"+ year +"short.csv");
	    int currentRank = 0;
	    String name = ""; 
	    for (CSVRecord rec : fr.getCSVParser(false)) {
	        if (rec.get(1).equals(gender))
	        {
	            currentRank++;
	            if (currentRank == rank)
	            {
	                name = rec.get(0);
	                break;
	            }
	        }
	    }
	    
	    if (name.isEmpty())
	    {
	        return "not found";
	    }
	    else
	    {
	        return name;
	    }
	}
	
	public void testGetName () {
		FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/us_babynames_test/yob"+ year +"short.csv")
		String name = getName(450, "M", fr);
		System.out.println("Name is " + name);
	}
	
	public String whatIsNameInYear(String name, int year, int newYear, String gender) {
	    
	   FileResource fr = new FileResource("data/us_babynames_by_year/yob"+ year +".csv");
	   int rank = getRank(name, gender, fr);
	   if (rank != -1)
	   {
	       fr = new FileResource("data/us_babynames_by_year/yob"+ newYear +".csv");
	       String NewName = getName(rank, gender, fr);
	       return (name + " born in " + year + " would be " + NewName + " if was born in " + newYear + ".");
	   }
	   else
	   {
	       return "Name is not found";
	   }
	   
	}
	
	public void testWhatIsNameInYear () {
		//FileResource fr = new FileResource();
		String res = whatIsNameInYear("Owen", 1974, 2014, "M");
		System.out.println(res);
	}
	
	public int yearOfHighestRank(String name, String gender) {
	    
	   DirectoryResource dr = new DirectoryResource();
	   int MaxRank = -1;
	   File maxFile = null;
	   // iterate over files
	   for (File f : dr.selectedFiles()) {
	       FileResource fr = new FileResource(f);
	       // use method to get largest in file
	       
	       int rank = getRank(name, gender, fr);
	       
	       maxFile = getLowestOfTwoyear(rank, MaxRank, f, maxFile);
	       MaxRank = getLowestOfTwo(rank, MaxRank);
           }
	   System.out.println("year of max rank = " + maxFile.getName());
           return MaxRank;
	}
	
	public int getLowestOfTwo(int rank, int MaxRank) {
	   
	   if (MaxRank == -1)
	   {
	       MaxRank = rank;
	   }
	   else
	   {
	       if (rank != -1 && rank < MaxRank)
	       {
	           MaxRank = rank;
	       }
	   }
	   
	   return MaxRank;
	}
	public File getLowestOfTwoyear(int rank, int MaxRank, File f, File maxFile) {
	   
	   if (MaxRank == -1)
	   {
	       MaxRank = rank;
	       maxFile = f;
	   }
	   else
	   {
	       if (rank != -1 && rank < MaxRank)
	       {
	           MaxRank = rank;
	           maxFile = f;
	       }
	   }
	   
	   return maxFile;
	}
	
	public void testyearOfHighestRank() {
	    
	   int rank = yearOfHighestRank("Genevieve", "F");
	   if (rank != -1)
	   {
	       System.out.println("Name Rank is " + rank);
	   }
	   else
	   {
	       System.out.println("Name is not found");
	   }
	   
	}
	
	public double getAverageRank(String name, String gender) {
	    
	   DirectoryResource dr = new DirectoryResource();
	   double total = 0;
	   int count = 0;
	   // iterate over files
	   for (File f : dr.selectedFiles()) {
	       FileResource fr = new FileResource(f);
	       // use method to get largest in file
	       
	       int rank = getRank(name, gender, fr);
	       
	       if (rank != -1)
	       {
	           total += rank;
	           count++;
	       }
           }
	   
           if (count == 0)
           {
               return -1;
           }
           else
           {
               return (total/count);
           }
           
	}
	
	public void testgetAverageRank() {
	    
	   double average = getAverageRank("Robert", "M");
	   if (average != -1)
	   {
	       System.out.println("Name average is " + average);
	   }
	   else
	   {
	       System.out.println("Name is not found so no average");
	   }
	   
	}
	
	public int getTotalBirthsRankedHigher(int year, String name, String gender, FileResource fr) {
	    
	   //FileResource fr = new FileResource("data/us_babynames_test/yob"+ year +"short.csv");
	   int total = 0;
	   for (CSVRecord rec : fr.getCSVParser(false)) {
	           if (rec.get(1).equals(gender))
	           {
	               if (rec.get(0).equals(name))
	               {
	                   break;
	               }
	               total += Integer.parseInt(rec.get(2));
	           }
	   }
	   return total;
	}
	
	public void testgetTotalBirthsRankedHigher() {
	    
	   FileResource fr = new FileResource();
	   //FileResource fr = new FileResource("data/us_babynames_test/yob"+ year +"short.csv");
	   int total = getTotalBirthsRankedHigher(2012, "Drew", "M", fr);
	   System.out.println("TotalBirthsRankedHigher = " + total);
	   
	}
}

