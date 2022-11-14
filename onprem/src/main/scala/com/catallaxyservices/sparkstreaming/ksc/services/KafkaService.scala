package com.catallaxyservices.sparkstreaming.ksc.services

import com.catallaxyservices.sparkstreaming.ksc.model.Car
import com.catallaxyservices.sparkstreaming.ksc.model.Car._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, SparkSession}

/**
 * KafkaService is a Trait that has one concrete method readFromKafkaTopic.
 * It extends Logging Trait to add loggers.
 */
trait KafkaService {

  /**
   * Reads DataFrame from kafka as dataset of Car
   * @param spark SparkSession
   * @return      Dataset of Car
   */
  def readFromKafkaTopic(spark: SparkSession): Dataset[Car] = {
    import spark.implicits._

    //Connecting to kafka and reading streams.
    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "car")
      .option("startingOffsets", "earliest")
      .load()
      .selectExpr("cast(value as string) as value")
      .select(from_json(col("value"), carSchema).as[Car])
  }
}