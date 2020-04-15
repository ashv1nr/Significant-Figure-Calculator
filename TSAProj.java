
import static java.lang.System.*;
import java.awt.*;
import java.util.*;

public class TSAProj
{
	public static void main(String[] args)
	{
		SigFigs godSquad = new SigFigs();
      godSquad.input();
      godSquad.process();
      godSquad.output();
      godSquad.showFrame();
      //godSquad.varCheck();
   }
}

class SigFigs extends JDrawingFrame
{

String num;
double guess;
int count=0;
int currentIndex=-1;
int mostRecentNonZero=-1;
int nextNonZero;
int z;
int firstNonZero;
int firstNonZeroFinder=-1;
int withDecimalCounter=0;
int notNumberIdentifier=0;
int itsNotANumber=0;

	void input()
   {
   
    
   
     Scanner scan = new Scanner(in);
   
     out.println("Please input a positive number.");
     num = scan.nextLine();
   
     for(int q = 0; q<=num.length()-1; q = q+1)
     {
       if((!num.substring(q,q+1).equalsIgnoreCase("0")) && (!num.substring(q,q+1).equalsIgnoreCase("1")) && (!num.substring(q,q+1).equalsIgnoreCase("2")) && (!num.substring(q,q+1).equalsIgnoreCase("3")) && (!num.substring(q,q+1).equalsIgnoreCase("4")) && (!num.substring(q,q+1).equalsIgnoreCase("5")) && (!num.substring(q,q+1).equalsIgnoreCase("6")) && (!num.substring(q,q+1).equalsIgnoreCase("7")) && (!num.substring(q,q+1).equalsIgnoreCase("8")) && (!num.substring(q,q+1).equalsIgnoreCase("9")) && (!num.substring(q,q+1).equalsIgnoreCase(",")) && (!num.substring(q,q+1).equalsIgnoreCase(".")))
       {
         notNumberIdentifier = notNumberIdentifier+1;
       }
     }  
       
       if(notNumberIdentifier==0)
       {
         out.println("Guess how many significant figures are in your number.");
         guess = scan.nextDouble();
       }
       else
       {
         guess = -1;
       }
     
   
   
   }
   
  
   
   
   void process()
   {
    
    
     
     for(int x = 0; x<=num.length()-1; x = x+1)
     {
      
    
        if(notNumberIdentifier!=0)
        {
          itsNotANumber= notNumberIdentifier;
          x = num.length();
        }
        
           else if((!num.substring(x,x+1).equalsIgnoreCase("0")) && (!num.substring(x,x+1).equalsIgnoreCase(",")) && (!num.substring(x,x+1).equalsIgnoreCase(".")))
           {
             count = count +1;
             currentIndex = currentIndex +1;
             mostRecentNonZero = currentIndex;
           }
         
              else if(num.substring(x,x+1).equalsIgnoreCase(","))
              {
                currentIndex = currentIndex +1;
              }
           
                 else if(num.substring(x,x+1).equalsIgnoreCase("."))
                 {
                   currentIndex = currentIndex +1;
                   if((num.indexOf("1") == -1) && (num.indexOf("2") == -1) && (num.indexOf("3") == -1) && (num.indexOf("4") == -1) && (num.indexOf("5") == -1) && (num.indexOf("6") == -1) && (num.indexOf("7") == -1) && (num.indexOf("8") == -1) && (num.indexOf("9") == -1)) 
                   {
                     count = 0;
                     x = num.length();
                   }
                   else
                   {
                   FOUND_IT:
                   for(int w = 0; w<=num.length()-1; w = w+1)
                   {
                     firstNonZeroFinder = firstNonZeroFinder +1;
                     if((!num.substring(w,w+1).equalsIgnoreCase("0")) && (!num.substring(w,w+1).equalsIgnoreCase(",")) && (!num.substring(w,w+1).equalsIgnoreCase(".")))
                     {
                       firstNonZero = firstNonZeroFinder;
                       break FOUND_IT;
                     }
                   } 
                   for(int v = firstNonZero; v<=num.length()-1; v = v +1)
                   {
                     if((num.substring(v,v+1).equalsIgnoreCase(",")) || (num.substring(v,v+1).equalsIgnoreCase(".")))
                     {
                       withDecimalCounter = withDecimalCounter +0;
                     }
                     else
                     {
                       withDecimalCounter = withDecimalCounter +1;
                       count = withDecimalCounter;
                     } 
                   } 
                   x = num.length();
                  }
                 }
           
                    else if(num.substring(x,x+1).equalsIgnoreCase("0"))
                    {
                      currentIndex = currentIndex +1;
                      z = currentIndex;
                      OUTER_LOOP:
                      for(int y = currentIndex; y<=num.length()-1; y = y +1)
                      {
                        if(num.substring(y,y+1).equalsIgnoreCase("0"))
                        {
                          z = z +1;
                        }
                        else if(num.substring(y,y+1).equalsIgnoreCase(","))
                        {
                          z = z +1;
                        }
                        else
                        {
                          break OUTER_LOOP;
                        }
                      }  
                      nextNonZero = z;
                      if((mostRecentNonZero == -1) || (nextNonZero == num.length()))
                      {
                        count = count +0;
                      }
                      else if((currentIndex > mostRecentNonZero) && (currentIndex < nextNonZero))
                      {
                        count = count +1;
                      }
                    }
                
   
     }

  
   
   }
   
   
   
   
   void output()
   {
     
     
     
     if(itsNotANumber!=0)
     {
       setFontSize(15);
       setColor(Color.WHITE);
       pen.drawString(num + "    is not a positive number. Only postive numbers like the examples below have significant figures.", 10, 100);
       setFontSize(20);
       setColor(Color.YELLOW);
       pen.drawString("Examples of numbers to input:", 90, 200);
       setColor(Color.WHITE);
       pen.drawString("7", 90, 225);
       pen.drawString("1002", 90, 250);
       pen.drawString("000456.000", 90, 275);
       pen.drawString("97,367,000,380", 90, 300);
       pen.drawString("249,080,040,727.69979004700", 90, 325);
     }
     else
     {
       setFontSize(20);
     
       if(guess == count)
       {
         setColor(Color.GREEN);
         pen.drawString(" YES! The number " + num + " does have  " + count + "  significant figures.", 10,100);
       } 
       else
       {
         setColor(Color.RED);
         pen.drawString("Actually, the number " + num + " has  " + count + "  significant figures.", 10,100);
       }
     
       setFontSize(17);
       setColor(Color.WHITE);
       pen.drawString("Remember the rules of Significant Figures:", 50,200);
       setColor(Color.YELLOW);
       pen.drawString("1. All non-zeros are significant.", 75,225);
       pen.drawString("2. Sandwich-zeros(a zero between two non-zeros) are significant.", 75,250);
       pen.drawString("3. Trailing-zeros(zeros with no non-zeros after it) are only significant if there is a decimal point.", 75,275);
       pen.drawString("4. Leading-zeros(zeros with no non-zeros before it) are never significant.", 75,300);
     }   
  
  
  
   }
   
   
   
   
   void varCheck()
   {
   
   
   
     out.println("count: " + count);
     out.println("current index: " + currentIndex);
     out.println("most recent non zero: " + mostRecentNonZero);
     out.println("next non zero: " + nextNonZero);
     out.println("z: " + z);
   
   
   
   }





}

