namespace CatallaxyServices.Spark.Examples.StructuredStreaming

open System
open Microsoft.Spark.Sql
open Microsoft.Spark.Sql.Streaming
open CatallaxyServices.Spark.Examples

type Repeater() =
    member this.Run(args : string[])=
        let spark = SparkSession
                        .Builder()
                        .AppName("Spark Streaming .NET Example:  Repeater")
                        .GetOrCreate()

        let lines = spark
                        .ReadStream()
                        .Format("socket")
                        .Option("host", "127.0.0.1")
                        .Option("port", "9999")
                        .Load()

        let query = lines
                        .Select("value")
                        .WriteStream()
                        .Format("console")
                        .Start()

        query.AwaitTermination()
        0

    interface IExample with
        member this.Run (args) = this.Run (args)