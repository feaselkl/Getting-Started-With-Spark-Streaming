package com.catallaxyservices.sparkstreaming

import org.apache.spark._

object wordcount {
  def main(args: Array[String]) {
    // Set up a SparkContext named WordCount that runs locally using
    // all available cores.
    val conf = new SparkConf().setAppName("WordCount")
    conf.setMaster("local[*]")
    val sc = new SparkContext(conf)
    
    // Create an RDD of lines of text in the readme file
    val input = sc.textFile("README.md")
    // Use flatMap to convert this into an RDD of each word in each line
    val words = input.flatMap(line => line.split(' '))
    // Convert these words to lower-case
    val lowerCaseWords = words.map(word => word.toLowerCase())
    // Count up the occurrence of each unique word
    val wordCounts = lowerCaseWords.countByValue()
    
    // Print the first 20 results
    val sample = wordCounts.take(20)
    
    for ((word, count) <- sample) {
      println(word + " " + count)
    }
    
    sc.stop()
  }
  
}