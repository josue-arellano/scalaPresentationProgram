import scala.io.Source
import java.io.FileNotFoundException
import java.io.IOException

object WordCount extends App {
    def calcExTime(start:Long, end:Long): (Long, String) = {
        val total = end - start
        (total, "milliseconds")
    }
    //able to use functions from java
    val startTime = System.currentTimeMillis();
    try {
        //built in features for data analysis
        val f = Source.fromFile("kjv.txt")
                    .getLines
                    .flatMap(_.split("\\W+"))
                    .foldLeft(Map.empty[String, Int]){
                        (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))}
                        
        val sorted = f.toSeq.sortWith(_._2 > _._2).tail

        //more readable and writable
        val top10 = sorted take 10 //this is equivalent to: 'val top10 = sorted.take(10)'

        top10.foreach(println)
        val endTime = System.currentTimeMillis();

        //functional program, so I implemented as pure functions
        //Function expecting another function that takes two arguments as an argument
        def getPrintString(f: (Long, Long) => (Long, String), start: Long, end: Long):String = {
            val totalTime = f(start, end)._1
            val metric = f(start, end)._2
            f"total execution time = $totalTime%,d $metric"
        }


        //formatted strings are easier to write
        println(getPrintString(calcExTime, startTime, endTime))
        println(getPrintString((start: Long,end: Long) => {
            val total: Long = (end - start) * 1000
            (total, "microseconds")
        }, startTime, endTime))
    } catch {
        case e: FileNotFoundException => {
            println("File not found")
        }
        case e: IOException => {
            println("IO Exception")
        }
    }
}