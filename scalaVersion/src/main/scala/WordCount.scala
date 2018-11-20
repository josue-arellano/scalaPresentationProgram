/*******
 *  Name:           Josue Arellano, Byron Gil, Roberto Gonzalez
 *  Project:        Programming Language Presentation Demo
 *  Language:       Scala
 *  File:           WordCount.scala
 *  Description:    This file finds the 10 most repeated words in a file. 
 *                  This program shows the readability, writability, functional programming
 *                  features, the Object Oriented features and the functionality for 
 *                  manipulating data.
 *******/

//import just like java
import scala.io.Source
import java.io.FileNotFoundException
import java.io.IOException

object WordCount extends App {
    //tuples are built in
    def calcExTime(start:Long, end:Long): (Long, String) = {
        val total = (end - start) * 1000
        (total, "microseconds")
    }

    println("Welcome to the word count program!")
    print("Enter the file name: ")
    var filename = scala.io.StdIn.readLine()

    //able to use functions from java
    val startTime = System.currentTimeMillis();

    //handle exceptions like in java
    try {
        //built in features for data
        val f = Source.fromFile(filename)
                    .getLines
                    .flatMap(_.split("\\W+"))
                    .foldLeft(Map.empty[String, Int]){
                        (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))}
                        
        val sorted = f.toSeq.sortWith(_._2 > _._2).tail

        //more readable and writable
        val top10 = sorted take 10 //val top10 = sorted.take(10) 

        top10.foreach(println)
        val endTime = System.currentTimeMillis();

        //higher order functions
        def getPrintString(f: (Long, Long) => (Long, String), start: Long, end: Long):String = {
            val totalTime = f(start, end)._1
            val metric = f(start, end)._2
            f"total execution time = $totalTime%,d $metric"
        }

        println("Which metric would you like to display?")
        println(" 1. micro seconds (type 'micro')")
        println(" 2. milli seconds (type 'milli')")
        print("input: ")
        val input: String = scala.io.StdIn.readLine() 

        //Pattern matching
        val str = input match {
            case "micro" => getPrintString(calcExTime, startTime, endTime)
            case "milli" => getPrintString((start: Long,end: Long) => { //anonymous functions
                            val total: Long = (end - start)
                            (total, "milliseconds")
                        }, startTime, endTime)
            case _ => "Wrong input"
        }
        println(str);
    } catch {
        case e: FileNotFoundException => {
            println("File not found")
        }
        case e: IOException => {
            println("IO Exception")
        }
    }
}