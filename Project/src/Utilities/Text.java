package Utilities;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.stream.Stream;

public class Text {
    String fileName;
    FileReader fr;
    BufferedReader in;
    Stream<String> lines;
    Iterator<String> l;
    public boolean hasLine;

   public Text(String fileName) throws FileNotFoundException{
        this.fileName = fileName;
        fr = new FileReader(this.fileName);
        in = new BufferedReader(fr);   
        lines = in.lines();
        l = lines.iterator();
        hasLine = true;
   }

   public String nexline() {

       if(l.hasNext()) {
           String nl = l.next();
           System.out.println("Next line; "+nl);
           return nl;
       }
       else {
           System.out.println("No new line!");
           hasLine = false;
           return null;
       } 
   }    
}