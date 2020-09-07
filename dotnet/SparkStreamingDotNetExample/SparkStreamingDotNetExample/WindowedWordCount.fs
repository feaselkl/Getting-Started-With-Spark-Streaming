namespace CatallaxyServices.Spark.Examples.StructuredStreaming

open System
open Microsoft.Spark.Sql
open Microsoft.Spark.Sql.Streaming
open CatallaxyServices.Spark.Examples

type WindowedWordCount() =
    member this.Run(args : string[])=
        match args with
        | ([| windowSizeStr |] | [| windowSizeStr; _ |]) ->
            let windowSize = windowSizeStr |> int64
            let slideSize = if (args.Length = 1) then windowSize else (args.[1] |> int64)
            if (slideSize > windowSize) then
                printfn "<slide duration> must be less than or equal to <window duration>"
            let windowDuration = sprintf "%d seconds" windowSize
            let slideDuration = sprintf "%d seconds" slideSize

            let spark = SparkSession
                            .Builder()
                            .AppName("Spark Streaming .NET Example:  Windowed Word Count")
                            .GetOrCreate()

            let lines = spark
                            .ReadStream()
                            .Format("socket")
                            .Option("host", "127.0.0.1")
                            .Option("port", "9999")
                            .Option("includeTimestamp", true)
                            .Load()

            let words =
                lines.Select(Functions.Explode(Functions.Split(lines.["value"], " "))
                    .Alias("word"), lines.["timestamp"])
            let windowedCounts =
                words.GroupBy(Functions.Window(words.["timestamp"], windowDuration, slideDuration), words.["word"])
                    .Count()
                    .OrderBy("window")

            let query =
                windowedCounts.WriteStream()
                    .OutputMode("complete")
                    .Format("console")
                    .Option("truncate", false)
                    .Start()

            query.AwaitTermination()
            0
        | _ ->
            printfn "Usage: StructuredNetworkWordCountWindowed \
                     <window duration in seconds> [<slide duration in seconds>]"
            1

    interface IExample with
        member this.Run (args) = this.Run (args)