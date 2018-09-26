package test;
import java.io.*;
import java.util.*;

public class Apriori {

    public static void main(String[] args) {
        AprioriCalculation ap = new AprioriCalculation();
        ap.aprioriProcess();
    }
}
class AprioriCalculation
{
    Vector<String> candidates=new Vector<String>();
    String configFile="F:\\config.txt";
    String transaFile="F:\\transa.txt";
    String outputFile="F:\\apriori-output.txt";
    int numItems;
    int numTransactions;
    double minSup;
    String oneVal[];
    String itemSep = " ";

 public void aprioriProcess()
    {
        Date d;
        long start, end;
        int itemsetNumber=0;
        //get config
        getConfig();
        System.out.println("Apriori algorithm has started.\n");
        //start timer
        d = new Date();
        start = d.getTime();
        //while not complete
        do
        {
            itemsetNumber++;
            generateCandidates(itemsetNumber);
            calculateFrequentItemsets(itemsetNumber);
            if(candidates.size()!=0)
            {
                System.out.println("Frequent " + itemsetNumber + "-itemsets");
                System.out.println(candidates);
            }
            }while(candidates.size()>1);
        //end timer
        d = new Date();
        end = d.getTime();
        System.out.println("Execution time is: "+((double)(end-start)/1000) + " seconds.");
    }
public static String getInput()
    {
        String input="";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            input = reader.readLine();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return input;
    }
  private void getConfig()
    {
        FileWriter fw;
        BufferedWriter file_out;
        String input="";
        //System.out.println("Default Configuration: ");
       // System.out.println("\tRegular transaction file with '" + itemSep + "' item separator.");
        System.out.println("\tConfig File: " + configFile);
        System.out.println("\tTransa File: " + transaFile);
        System.out.println("\tOutput File: " + outputFile);
        System.out.println("\nPress 'C' to change the item separator, configuration file and transaction files");
        System.out.print("or any other key to continue.  ");
        input=getInput();
 if(input.compareToIgnoreCase("c")==0)
        {
            System.out.print("Enter new transaction filename (return for '"+transaFile+"'): ");
            input=getInput();
            if(input.compareToIgnoreCase("")!=0)
                transaFile=input;

            System.out.print("Enter new configuration filename (return for '"+configFile+"'): ");
            input=getInput();
            if(input.compareToIgnoreCase("")!=0)
                configFile=input;

            System.out.print("Enter new output filename (return for '"+outputFile+"'): ");
            input=getInput();
            if(input.compareToIgnoreCase("")!=0)
                outputFile=input;

            System.out.println("Filenames changed");

            System.out.print("Enter the separating character(s) for items (return for '"+itemSep+"'): ");
            input=getInput();
            if(input.compareToIgnoreCase("")!=0)
                itemSep=input;
        }

        try
        {
             FileInputStream file_in = new FileInputStream(configFile);
             BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
             //number of items
             numItems=Integer.valueOf(data_in.readLine()).intValue();

             //number of transactions
             numTransactions=Integer.valueOf(data_in.readLine()).intValue();

             //minsup
             minSup=(Double.valueOf(data_in.readLine()).doubleValue());

             //output config info to the user
             System.out.print("\nInput configuration: "+numItems+" items, "+numTransactions+" transactions, ");
             System.out.println("minsup = "+minSup+"%");
             System.out.println();
             minSup/=100.0;

            oneVal = new String[numItems];
            System.out.print("Enter 'y' to change the value each row recognizes as a '1':");
            if(getInput().compareToIgnoreCase("y")==0)
            {
                for(int i=0; i<oneVal.length; i++)
                {
                    System.out.print("Enter value for column #" + (i+1) + ": ");
                    oneVal[i] = getInput();
                }
            }
            else
                for(int i=0; i<oneVal.length; i++)
                    oneVal[i]="1";
            fw= new FileWriter(outputFile);
            file_out = new BufferedWriter(fw);
            file_out.write(numTransactions + "\n");
            file_out.write(numItems + "\n******\n");
            file_out.close();
        }
                catch(IOException e)
        {
            System.out.println(e);
        }
    }
 private void generateCandidates(int n)
    {
        Vector<String> tempCandidates = new Vector<String>();
        String str1, str2;
        StringTokenizer st1, st2;
        if(n==1)
        {
            for(int i=1; i<=numItems; i++)
            {
                tempCandidates.add(Integer.toString(i));
            }
        }
        else if(n==2)         {
             for(int i=0; i<candidates.size(); i++)
            {
                st1 = new StringTokenizer(candidates.get(i));
                str1 = st1.nextToken();
                for(int j=i+1; j<candidates.size(); j++)
                {
                    st2 = new StringTokenizer(candidates.elementAt(j));
                    str2 = st2.nextToken();
                    tempCandidates.add(str1 + " " + str2);
                }
            }
        }
        else
        {
            for(int i=0; i<candidates.size(); i++)
            {
                for(int j=i+1; j<candidates.size(); j++)
                {
                    str1 = new String();
                    str2 = new String();
                    st1 = new StringTokenizer(candidates.get(i));
                    st2 = new StringTokenizer(candidates.get(j));

                    for(int s=0; s<n-2; s++)
                    {
                        str1 = str1 + " " + st1.nextToken();
                        str2 = str2 + " " + st2.nextToken();
                    }

                        if(str2.compareToIgnoreCase(str1)==0)
                        tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2.nextToken()).trim());
                }
            }
        }
        candidates.clear();
        candidates = new Vector<String>(tempCandidates);
        tempCandidates.clear();
    }
 private void calculateFrequentItemsets(int n)
    {
        Vector<String> frequentCandidates = new Vector<String>();
                FileInputStream file_in;
        BufferedReader data_in;
        FileWriter fw;
        BufferedWriter file_out;

        StringTokenizer st, stFile;
        boolean match;
        boolean trans[] = new boolean[numItems];
        int count[] = new int[candidates.size()];
                try
        {
                //output file
                fw= new FileWriter(outputFile, true);
                file_out = new BufferedWriter(fw);
                //load the transaction file
                file_in = new FileInputStream(transaFile);
                data_in = new BufferedReader(new InputStreamReader(file_in));
               for(int i=0; i<numTransactions; i++)
                {
                    stFile = new StringTokenizer(data_in.readLine(), itemSep); //read a line from the file to the tokenizer
                    for(int j=0; j<numItems; j++)
                    {
                        trans[j]=(stFile.nextToken().compareToIgnoreCase(oneVal[j])==0); //if it is not a 0, assign the value to true
                    }
                    for(int c=0; c<candidates.size(); c++)
                    {
                        match = false;                        
                        st = new StringTokenizer(candidates.get(c));
                        while(st.hasMoreTokens())
                        {
                            match = (trans[Integer.valueOf(st.nextToken())-1]);
                            if(!match)
                                break;
                        }
                        if(match)
                        count[c]++;
                    }
                }              
            for(int i=0; i<candidates.size(); i++)
                {
                  if((count[i]/(double)numTransactions)>=minSup)
                    {
                        frequentCandidates.add(candidates.get(i));
                        file_out.write(candidates.get(i) + "," + count[i]/(double)numTransactions + "\n");
                    }
                }
                file_out.write("-\n");
                file_out.close();
        }
      catch(IOException e)
        {
            System.out.println(e);
        }
        candidates.clear();
        candidates = new Vector<String>(frequentCandidates);
        frequentCandidates.clear();
    }
}