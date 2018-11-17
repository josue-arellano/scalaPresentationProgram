import scala.io.Source

object WordCount extends App {
    val f = Source.fromFile("kjv.txt")
                .getLines
                .flatMap(_.split("\\W+"))
                .foldLeft(Map.empty[String, Int]){
                    (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))}

    val sorted = f.toSeq.sortWith(_._2 > _._2).tail
    val top10 = sorted.take(10)

    top10.foreach(println)
}