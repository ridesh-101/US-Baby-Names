
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;


public class BabyNames {
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int boysNames = 0;
        int girlsNames = 0;
        int totalGirls = 0;
        
        for(CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if(rec.get(1).equals("M")) {
                totalBoys += numBorn;
                boysNames += 1;
            } else {
                totalGirls += numBorn;
                girlsNames += 1;
            }
        }
        
        System.out.println("totalBirths: " + totalBirths);
        System.out.println("totalBoys: " + totalBoys);
        System.out.println("totalGirls: " + totalGirls);
        System.out.println("Number of Boys Names: " + boysNames);
        System.out.println("Number of Girls Names: " + girlsNames);
        System.out.println("Total Number of Names: " + (boysNames + girlsNames));
    } 
    
    int getRank(int year, String name, String gender) {
        FileResource fr = new FileResource("C:/Users/HP/Desktop/Ridesh Imps/Computer Science/Java/BabyNames/us_babynames_by_year/" + "yob" + year + ".csv");
        int rank = -1;
        int c = 0;
        for(CSVRecord rec : fr.getCSVParser(false)) {
            if(rec.get(1).equals(gender)) {
                c += 1;
            } 
            //System.out.println(" rank: " + rank);
            if(rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                rank = c;
                return rank;
            }  
        }
        
        return -1;
    }
    
    String getName(int year, int rank, String gender) {
       FileResource fr = new FileResource("C:/Users/HP/Desktop/Ridesh Imps/Computer Science/Java/BabyNames/us_babynames_by_year/" + "yob" + year + ".csv");
       int c = 0;
       for(CSVRecord rec : fr.getCSVParser(false)) {
           if(rec.get(1).equals(gender)) {
               c += 1;
               if(c == rank) {
                   return rec.get(0);
               }
           }
          
       }
       return "NO NAME";
    }
    
    int yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int rank = 0;
        int startRank = 0;
        int startYear = -1;
        int curRank = 0, curYear = 0;
        for(File f : dr.selectedFiles()) {
            //FileResource fr = new FileResource(f);
            String fileName = f.getName();
            curYear = Integer.parseInt(fileName.substring(3, 7));
            curRank = getRank(curYear, name, gender);
            System.out.println("curYear: " + curYear + " curRank: " + curRank);
            if(startRank == 0) {
                startRank = curRank;
                startYear = curYear;
            } else if(startRank > curRank && curRank != -1) {
                startRank = curRank;
                startYear = curYear;
            }
            System.out.println("startYear: " + startYear + " startRank: " + startRank);
        }
        if(curRank == -1) {
            return -1;
        }
        return startYear;
    }
    
    double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int curRank = 0, curYear = 0;
        int sum = 0;
        int c = 0;
        for(File f : dr.selectedFiles()) {
            //FileResource fr = new FileResource(f);
            String fileName = f.getName();
            curYear = Integer.parseInt(fileName.substring(3, 7));
            curRank = getRank(curYear, name, gender);
            sum += curRank;
            c += 1;
        }
        if(sum == -1) {
            return -1.0;
        }
        return (double)sum / c;
         
    }
    
    int getTotalBirthsRankedHigher(int year, String name, String gender) {
        FileResource fr = new FileResource("C:/Users/HP/Desktop/Ridesh Imps/Computer Science/Java/BabyNames/us_babynames_by_year/" + "yob" + year + ".csv");
        int sum = 0;
        for(CSVRecord rec : fr.getCSVParser(false)) {
            if(rec.get(1).equals(gender)) {
                sum += Integer.parseInt(rec.get(2));
                if(rec.get(0).equals(name)) {
                    return sum - Integer.parseInt(rec.get(2));
                }
            }
            System.out.println("Sum: " + sum);
        }
        return -1;
    }
    void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        System.out.println(name + " born in " + year + " would be " + newName + " if born in " + newYear);
        
    }
    public void testTotalBirths() {
        FileResource fr = new FileResource();
        totalBirths(fr);
        
    }
    
    void testGetTotalBirthsRankedHigher() {
        System.out.println("getTotalBirthsRankedHigher: " + getTotalBirthsRankedHigher(1990, "Drew", "M"));
    }
    void testGetRank() {
        System.out.println("GetRank: " + getRank(1971, "Frank", "M"));
    }
    
    void testGetName() {
        System.out.println("GetName: " + getName(1982, 450, "M"));
    }
    
    void testWhatIsNameInYear() {
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    void testYearOfHighestRank() {
        System.out.println("Year of Highest Rank: " + yearOfHighestRank("Mich", "M"));
    }
    
    void testGetAverageRank() {
        System.out.println("get Average Rank: " + getAverageRank("Robert", "M"));
    }
}
