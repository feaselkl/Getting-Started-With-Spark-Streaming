namespace CatallaxyServices.Spark.Examples.StructuredStreaming

open System
open Microsoft.Spark.Sql
open Microsoft.Spark.Sql.Streaming
open CatallaxyServices.Spark.Examples

type KafkaReader() =
    member this.Run(args : string[])=
        match args with
        | [| bootstrapServers; topics |] ->
            let spark = SparkSession
                            .Builder()
                            .AppName("Spark Streaming .NET Example:  Kafka Reader")
                            .GetOrCreate()

            let lines =
                spark.ReadStream()
                    .Format("kafka")
                    .Option("kafka.bootstrap.servers", bootstrapServers)
                    .Option("subscribe", topics)
                    .Load()
                    .SelectExpr("CAST(value AS STRING)")

            let words =
                lines
                    .Select(Functions.Explode(Functions.Split(lines.["value"], ","))
                    .Alias("word"))
            let wordCounts = words.GroupBy("word").Count()

            let query = wordCounts
                            .WriteStream()
                            .OutputMode("complete")
                            .Format("console")
                            .Start()

            query.AwaitTermination()

            0
        | _ ->
            printfn "Usage: StructuredKafkaWordCount <bootstrap-servers> <topics>"
            1

    interface IExample with
        member this.Run (args) = this.Run (args)