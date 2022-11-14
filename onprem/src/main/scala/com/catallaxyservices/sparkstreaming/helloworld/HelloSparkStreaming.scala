package com.catallaxyservices.sparkstreaming.helloworld

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.storage.StorageLevel

object HelloSparkStreaming {

  def main(args: Array[String]): Unit = {
    // Create a context with a 1-second batch size
    val ssc = new StreamingContext("local[*]", "HelloSparkStreaming", Seconds(1))
    ssc.sparkContext.setLogLevel("ERROR")

    // Create a socket stream to read data pushed to port 9999
    // This will give us back a microbatch, one per second (based on the choice above)
    val lines = ssc.socketTextStream("127.0.0.1", 9999, StorageLevel.MEMORY_ONLY)

    // For each microbatch, perform a count by word for each line
    // Tally up results over a 30-second window, sliding every 5 seconds
    val wordCounts = lines.flatMap(line => line.split(' '))
      .map(word => word.toLowerCase())
      .countByValueAndWindow(Seconds(30), Seconds(5))

    // Print the ten most common words
    wordCounts
      .transform(rdd => rdd.sortBy(_._2, false))
      .print(10)

    // Begin the process
    ssc.checkpoint("C:/temp/spark_checkpoint")
    ssc.start()
    ssc.awaitTermination()
  }

}
