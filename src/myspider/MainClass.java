/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myspider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author hasib
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    static String url;
    static Scanner scan;
    static Document doc;
    static Elements links,imgSources;
    static final String agent="Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0";
    
    public static void main(String[] args) throws IOException {
        //Setting up proxyhost
        System.setProperty("http.proxyhost", "127.0.0.1");
        System.setProperty("http.proxyport", "3123");
        
        
        System.out.println("Welcome to my web spider. . . . ");
        System.out.println("You have to enter an URL which will be scraped by this program. . . . . ");
        System.out.println("Library used : Jsoup");
        System.out.println("Moving on . . . . . ");
        
        scan=new Scanner(System.in);
        
        System.out.print("Enter your URL : ");
        url=scan.nextLine();
        
        if(url.subSequence(0, 8)!="https://") {
            url="https://"+url;
        }
        //System.out.println("You have entered : "+url);
        
        System.out.println("Scraping data from URL. . . . . . . . ");
        doc=Jsoup.connect(url).userAgent(agent).get();
        links=doc.select("a[href]");
        imgSources=doc.getElementsByTag("img");
        
        int i=1;
        
        System.out.println("Displaying all the links found in site : ");
        System.out.println("");
        for(Element e:links) {
            System.out.println(i+". "+e.attr("href"));
            i++;
        }
        
        i=1;
        
        
        System.out.println("");
        System.out.println("");
        System.out.println("Displaying all the image links found in site :");
        System.out.println("");
        for(Element e:imgSources) {
            System.out.println(i+". "+e.attr("abs:src"));
            i++;
        }
        
        System.out.print("Do you want to save this in file? Press Y for yes, else n for No ");
        String choice=scan.next();
        
        if(choice.equals("Y")||choice.equals("y")) {
            writeToFile(links, imgSources);
        }
        else {
            System.out.println("Terminating Program. Happy crawling . . . . .");
        }
    }

    private static void writeToFile(Elements links, Elements imgSources) throws IOException  {
        System.out.print("Enter file name for links:");
        String nameFilename=scan.next();
        File nameFile=new File("src/"+nameFilename+".txt");
        
        System.out.print("Enter file name for Image sources:");
        String urlFileName=scan.next();
        File urlFile=new File("src/"+urlFileName+".txt");
        
        if(!nameFile.exists()) nameFile.createNewFile();
        if(!urlFile.exists()) urlFile.createNewFile();
        
        FileWriter nfout=new FileWriter(nameFile,true);
        FileWriter ufout=new FileWriter(urlFile,true);
        
        BufferedWriter nbw=new BufferedWriter(nfout);
        BufferedWriter ubw=new BufferedWriter(ufout);
        
        int i=1;
        for(Element e:links) {
            nbw.write(i+". "+e.attr("href"));
            nbw.newLine();
            i++;
        }
        nbw.close();
        
        i=1;
        for(Element e:imgSources) {
            ubw.write(i+". "+e.attr("abs:src"));
            ubw.newLine();
            i++;
        }
        ubw.close();
        
        System.out.println("Write complete. You may now check your src folder of your project.");
        System.out.println("Thank you. . . . .");
    }
    
}
