package com.catallaxyservices.sparkstreaming.helloworld

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{explode, split}
import org.apache.spark.sql.streaming.Trigger

object HelloSparkStreamingDataFrame {

  def main(args: Array[String]): Unit = {
    val spark:SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("HelloSparkStreaming_DataFrame")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.readStream
      .format("socket")
      .option("host","127.0.0.1")
      .option("port","9999")
      .load()

    val count = df
      .select(explode(split(df("value")," "))
      .alias("word"))
      .groupBy("word")
      .count()

    val query = count.writeStream
      .format("console")
      .outputMode("complete")
      .start()
      .awaitTermination()
  }

}
