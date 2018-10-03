package test;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NaiveBayesian {

	public static void main(String[] args) {
		String[] outlook= {"sunny","overcast","rainy"};
		String[] temperature= {"hot","mild","cool"};
		String[] humidity= {"high","normal"};
		String[] windy= {"FALSE","TRUE"};
		double [] outlookprob=new double[2*outlook.length];
		double [] tempprob=new double[2*temperature.length];
		double [] humidprob=new double[2*humidity.length];
		double [] windyprob=new double[2*windy.length];
		int correctlyClassified=0,incorrectlyClassified=0;
		String classify;
		File f1=new File("F:\\weather.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f1));
			 String st; 
			 st=br.readLine();
			 ArrayList<String[]> list=new ArrayList<String[]>();

			  while ((st = br.readLine()) != null) 
			  {
				  String[] items = st.split(",");
				  list.add(items);
			  }
			  double countneg=0;
			  double countpos=0;
			  System.out.println("Total Instances="+list.size());
			  
			  for(String[] lists:list)
			  {
				  if(lists[lists.length-1].equals("no"))
					  countneg++;
				  else
				  {
					  countpos++; 
				  }
					
			  }
			  System.out.println("Total yes="+countpos);
			  System.out.println("Total no="+countneg);
			  String yes="yes";
			  String no="no";
			  int probout=0;
			  countpos=countpos/list.size();
			  countneg=countneg/list.size();
			  int flag=0;
			  NumberFormat formatter = new DecimalFormat("#0.00");
			  System.out.println("yes="+formatter.format(countpos));
			  System.out.println("no="+formatter.format(countneg));
			  for(int i=0;i<outlook.length;i++)
			  {
				  for(String []lists:list)
				  {
					  if(lists[0].contains(outlook[i])&&lists[lists.length-1].contains(no))
					  {
						  outlookprob[2*i]++;
					  }
					  else if(lists[0].contains(outlook[i])&&lists[lists.length-1].contains(yes))
					  {
						  outlookprob[(2*i)+1]++;
					  }
					  
				  }
				  if(outlookprob[2*i]==0||outlookprob[2*i+1]==0)
					  flag=1;
			  }
				  for(int i=0;i<temperature.length;i++)
				  {
					  for(String[] lists:list)
					  {
						  if(lists[1].contains(temperature[i])&&lists[lists.length-1].contains(no))
						  {
							 tempprob[2*i]++; 
						  }
						  else if(lists[1].contains(temperature[i])&&lists[lists.length-1].contains(yes))
						  {
							  tempprob[2*i+1]++;
						  }
						  
					  }
					  if(tempprob[2*i]==0||tempprob[2*i+1]==0)
						  flag=1;
				  }
				  
			  for(int i=0;i<humidity.length;i++)
			  {
				  for(String[] lists:list)
				  {
					  if(lists[2].contains(humidity[i])&&lists[lists.length-1].contains(no))
					  {
						 humidprob[2*i]++; 
					  }
					  else if(lists[2].contains(humidity[i])&&lists[lists.length-1].contains(yes))
					  {
						  humidprob[2*i+1]++;
					  } 
					 
				  }
				  if(humidprob[2*i]==0||humidprob[2*i+1]==0)
					  flag=1; 
			  }
			  for(int i=0;i<windy.length;i++)
			  {
				  for(String[] lists:list)
				  {
					  if(lists[3].contains(windy[i])&&lists[lists.length-1].contains(no))
					  {
						 windyprob[2*i]++; 
					  }
					  else if(lists[3].contains(windy[i])&&lists[lists.length-1].contains(yes))
					  {
						  windyprob[2*i+1]++;
					  }  
				  }
				  if(windyprob[2*i]==0||windyprob[2*i+1]==0)
					  flag=1;
			  }
			if(flag==1)
			{
				for(int i=0;i<outlookprob.length;i++)
					outlookprob[i]++;
				for(int i=0;i<tempprob.length;i++)
					tempprob[i]++;
				for(int i=0;i<humidprob.length;i++)
					humidprob[i]++;
				for(int i=0;i<windyprob.length;i++)
					windyprob[i]++;	
			}
			 System.out.println("Outlook");
			  for(int i=0;i<outlookprob.length;i=i+2)
				  System.out.println(outlook[i/2]+"   "+outlookprob[i]+"\t"+outlookprob[i+1]);  
			  System.out.println("\nTemperature");
			  for(int i=0;i<tempprob.length;i=i+2)
				  System.out.println(temperature[i/2]+"   "+tempprob[i]+"\t"+tempprob[i+1]);
			  System.out.println("\nHumidity");
			  for(int i=0;i<humidprob.length;i=i+2)
				  System.out.println(humidity[i/2]+"   "+humidprob[i]+"\t"+humidprob[i+1]);
			  System.out.println("\nWindy");
			  for(int i=0;i<windyprob.length;i=i+2)
				  System.out.println(windy[i/2]+"   "+windyprob[i]+"\t"+windyprob[i+1]);
			int posout=-1,postemp=-1,poshumid=-1,poswind=-1;
			for(String[] lists:list)
			{
				String outlookparam=lists[0];
				String tempparam=lists[1];
				String humidityparam=lists[2];
				String windyparam=lists[3];
				String classifyParam=lists[4];	
				for(int i=0;i<outlook.length;i++)
				{
					if(outlook[i].equals(outlookparam))
						posout=i;}
				for(int i=0;i<temperature.length;i++)
				{
					if(temperature[i].equals(tempparam))
						postemp=i;}
				for(int i=0;i<humidity.length;i++)
				{
					if(humidity[i].equals(humidityparam))
						poshumid=i;}
				for(int i=0;i<windy.length;i++)
				{
					if(windy[i].equals(windyparam))
						poswind=i;}
				double outneg=0,outpos=0;
				for(int i=0;i<outlookprob.length;i++)
				{
					if(i%2==0)
						outneg=outlookprob[i]+outneg;
					else
						outpos=outlookprob[i]+outpos;}
				double tempneg=0,temppos=0;
				for(int i=0;i<tempprob.length;i++)
				{
					if(i%2==0)
						tempneg=tempprob[i]+tempneg;
					else
						temppos=tempprob[i]+temppos;}
				double humidneg=0,humidpos=0;
				for(int i=0;i<humidprob.length;i++)
				{
					if(i%2==0)
						humidneg=humidprob[i]+humidneg;
					else
						humidpos=humidprob[i]+humidpos;}
				double windyneg=0,windypos=0;
				for(int i=0;i<windyprob.length;i++)
				{
					if(i%2==0)
						windyneg=windyprob[i]+windyneg;
					else
						windypos=windyprob[i]+windypos;}
				double prob_of_pos=(countpos/list.size())*(outlookprob[posout*2+1]/outpos)*(tempprob[postemp*2+1]/temppos)*(humidprob[poshumid*2+1]/humidpos)*(windyprob[poswind*2+1]/windypos);
				double prob_of_neg=(countneg/list.size())*(outlookprob[posout*2]/outneg)*(tempprob[postemp*2]/tempneg)*(humidprob[poshumid*2+1]/humidneg)*(windyprob[poswind*2+1]/windyneg);
				if(prob_of_pos>prob_of_neg)
				{classify="yes";	}
				else
				{classify="no";}
				if(classify.contains(classifyParam))
					correctlyClassified++;
				else
					incorrectlyClassified++;
			}
			System.out.println("Correctly Classified"+"\t"+correctlyClassified);
			System.out.println("Incorrectly Classified"+"\t"+incorrectlyClassified);
			System.out.println("Enter the parameter of the statement");
			Scanner scan=new Scanner(System.in);
			System.out.println("Enter the value of outlook");
			String outlookparam=scan.next();
			System.out.println("Enter the value of Temperature");
			String tempparam=scan.next();
			System.out.println("Enter the value of Humidity");
			String humidityparam=scan.next();
			System.out.println("Enter the value of Windy");
			String windyparam=scan.next();
			for(int i=0;i<outlook.length;i++)
			{
				if(outlook[i].equals(outlookparam))
					posout=i;}
			for(int i=0;i<temperature.length;i++)
			{
				if(temperature[i].equals(tempparam))
					postemp=i;}
			for(int i=0;i<humidity.length;i++)
			{
				if(humidity[i].equals(humidityparam))
					poshumid=i;}
			for(int i=0;i<windy.length;i++)
			{
				if(windy[i].equals(windyparam))
					poswind=i;}
			double outneg=0,outpos=0;
			for(int i=0;i<outlookprob.length;i++)
			{
				if(i%2==0)
					outneg=outlookprob[i]+outneg;
				else
					outpos=outlookprob[i]+outpos;}
			double tempneg=0,temppos=0;
			for(int i=0;i<tempprob.length;i++)
			{
				if(i%2==0)
					tempneg=tempprob[i]+tempneg;
				else
					temppos=tempprob[i]+temppos;}
			double humidneg=0,humidpos=0;
			for(int i=0;i<humidprob.length;i++)
			{
				if(i%2==0)
					humidneg=humidprob[i]+humidneg;
				else
					humidpos=humidprob[i]+humidpos;}
			double windyneg=0,windypos=0;
			for(int i=0;i<windyprob.length;i++)
			{
				if(i%2==0)
					windyneg=windyprob[i]+windyneg;
				else
					windypos=windyprob[i]+windypos;}
			//System.out.println("Outlook Positive"+outpos+"Outlook Negative"+outneg+"\n"+"Temperature Positive"+temppos+"Temperature Negative"+tempneg+"\n"+"Humidity Positive"+humidpos+"Humidity Negative"+humidneg+"\n"+"Windy Positive"+windypos+"Windy Negative"+windyneg);
			double prob_of_pos=(countpos/list.size())*(outlookprob[posout*2+1]/outpos)*(tempprob[postemp*2+1]/temppos)*(humidprob[poshumid*2+1]/humidpos)*(windyprob[poswind*2+1]/windypos);
			double prob_of_neg=(countneg/list.size())*(outlookprob[posout*2]/outneg)*(tempprob[postemp*2]/tempneg)*(humidprob[poshumid*2+1]/humidneg)*(windyprob[poswind*2+1]/windyneg);
			System.out.println("Probability of playing outside"+"\t"+prob_of_pos);
			System.out.println("Probability of not playing outside"+"\t"+prob_of_neg);
			if(prob_of_pos>prob_of_neg)
			{
				System.out.println("Classify it as YES");
			}
			else
			{
				System.out.println("Classify it as NO");
			}
			  } 
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO Auto-generated method stub
		}}
