<style>
.reveal section img { background:none; border:none; box-shadow:none; }
</style>

## Getting Started with Spark Streaming

<a href="https://www.catallaxyservices.com">Kevin Feasel</a> (<a href="https://twitter.com/feaselkl">@feaselkl</a>)
<a href="https://csmore.info/on/sparkstreaming">https://CSmore.info/on/sparkstreaming</a>

---

@title[Who Am I?]

@div[left-60]
<table>
	<tr>
		<td><a href="https://csmore.info"><img src="https://www.catallaxyservices.com/media/Logo.png" height="133" width="119" /></a></td>
		<td><a href="https://csmore.info">Catallaxy Services</a></td>
	</tr>
	<tr>
		<td><a href="https://curatedsql.com"><img src="https://www.catallaxyservices.com/media/CuratedSQLLogo.png" height="133" width="119" /></a></td>
		<td><a href="https://curatedsql.com">Curated SQL</a></td>
	</tr>
	<tr>
		<td><a href="https://www.apress.com/us/book/9781484254608"><img src="https://www.catallaxyservices.com/media/PolyBaseRevealed.png" height="153" width="107" /></a></td>
		<td><a href="https://www.apress.com/us/book/9781484254608">PolyBase Revealed</a></td>
	</tr>
</table>
@divend

@div[right-40]
	<br /><br />
	<a href="https://www.twitter.com/feaselkl"><img src="https://www.catallaxyservices.com/media/HeadShot.jpg" height="358" width="315" /></a>
	<br />
	<a href="https://www.twitter.com/feaselkl">@feaselkl</a>
</div>
@divend

---

@title[The Origins Of Spark]

## Agenda

1. **The Origins of Spark**
2. What is Spark Streaming?
3. Our First Streaming Example
4. A Full Program
5. .NET and Spark Streaming

---?image=presentation/assets/background/sparkler.jpg&size=cover&opacity=20

### The Genesis of Spark

Spark started as a research project at the University of California Berkeley’s Algorithms, Machines, People Lab (AMPLab) in 2009.  The project's goal was to develop in-memory cluster computing, avoiding MapReduce's reliance on heavy I/O use.

The first open source release of Spark was 2010, concurrent with a paper from Matei Zaharia, et al.

In 2012, Zaharia, et al release a paper on Resilient Distributed Datasets.

---

### Resilient Distributed Datasets

The Resilient Distributed Dataset (RDD) forms the core of Apache Spark.  It is:

* Immutable – you never change an RDD itself; instead, you apply transformation functions to return a new RDD

---

### Resilient Distributed Datasets

The Resilient Distributed Dataset (RDD) forms the core of Apache Spark.  It is:

* Immutable
* Distributed – executors (akin to data nodes) split up the data set into sizes small enough to fit into those machines’ memory

---

### Resilient Distributed Datasets

The Resilient Distributed Dataset (RDD) forms the core of Apache Spark.  It is:

* Immutable
* Distributed
* Resilient – in the event that one executor fails, the driver (akin to a name node) recognizes this failure and enlists a new executor to finish the job

---

### Resilient Distributed Datasets

The Resilient Distributed Dataset (RDD) forms the core of Apache Spark.  It is:

* Immutable
* Distributed
* Resilient
* Lazy – Executors try to minimize the number of data-changing operations

Add all of this together and you have the key component behind Spark.

---

@title[What is Spark Streaming?]

## Agenda

1. The Origins of Spark
2. **What is Spark Streaming?**
3. Our First Streaming Example
4. A Full Program
5. .NET and Spark Streaming

TODO:  Section 2.

---?image=presentation/assets/background/construction.jpg&size=cover&opacity=20

### Installation Options

We have several options available to install Spark:

* Install stand-alone (Linux, Windows, or Mac)
* Use with a Hadoop distribution like Cloudera or Hortonworks
* Use Databricks Unified Analytics Platform on AWS or Azure
* Use with a Hadoop PaaS solution like Amazon ElasticMapReduce or Azure HDInsight

We will look at the first three in this talk.

---

### Install Spark On Windows

Step 1:  Install the <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">Java Development Kit</a>.  I recommend getting Java Version 8.  Spark is currently not compatible with JDKs after 8.

![Be sure to install JDK 8](presentation/assets/image/JavaSE.png)

---

### Install Spark On Windows

Step 2:  Go to <a href="http://spark.apache.org/downloads.html">the Spark website</a> and download a pre-built Spark binary.


![Instructions to download Apache Spark pre-built for Hadoop.](presentation/assets/image/DownloadSpark.png)

You can unzip this .tgz file using a tool like 7-Zip.

---

### Install Spark On Windows

Step 3:  <a href="https://github.com/steveloughran/winutils/blob/master/hadoop-2.8.3/bin/winutils.exe">Download WinUtils</a>.  This is the 64-bit version and should be 110KB.  There is a 32-bit version which is approximately 43KB; it will **not** work with 64-bit Windows!  Put it somewhere like C:\spark\bin\.

![The winutils readme.](presentation/assets/image/winutils.png)

---

### Install Spark On Windows

Step 4: Create c:\tmp\hive and open up permissions to everybody. 


![Grant rights to everybody on the /tmp/hive directory.](presentation/assets/image/GrantHiveRights.png)

---

### Install Spark On Windows

Step 5:  Create environment variables:

@div[left-50]
**SPARK_HOME** >> `C:\spark`<br />
**HADOOP_HOME** >> (where winutils is)<br />
**JAVA_HOME** >> (where you installed Java)<br />
**PATH** >> `;%SPARK_HOME%\bin; %JAVA_HOME%\bin;`
@divend

@div[right-50]
![Set environment variables.](presentation/assets/image/EnvironmentVariables.png)
@divend 

---

### Install Spark On Windows

Step 6: Open the `conf` folder and create & modify `log4j.properties`.

![The conf folder, containing log4j.properties](presentation/assets/image/ConfFolder.png)

![Change INFO to ERROR in log4j.properties](presentation/assets/image/log4j.png)

---

### Install Spark On Windows

Step 7:  In the bin folder, run `spark-shell.cmd`.  Type `Ctrl-D` to exit the shell.


![A view of the Spark shell.](presentation/assets/image/SparkShell.png)

---

### Use A Hadoop Distribution

The <a href="https://hortonworks.com/products/sandbox/">Hortonworks Data Platform sandbox</a> and <a href="https://www.cloudera.com/downloads/quickstart_vms/5-13.html">Cloudera QuickStart VM</a> both include Apache Spark and Apache Spark 2.


![A view of Spark running off of a Hortonworks installation.](presentation/assets/image/HadoopDistribution.png)

---

@title[Our First Streaming Example]

## Agenda

1. The Origins of Spark
2. What is Spark Streaming?
3. **Our First Streaming Example**
4. A Full Program
5. .NET and Spark Streaming

---?image=presentation/assets/background/colored-pencils.jpg&size=cover&opacity=20

### Hello World:  DStream

```scala
val ssc = new StreamingContext("local[*]", "HelloSparkStreaming", Seconds(1))
val lines = ssc.socketTextStream("127.0.0.1", 9999, StorageLevel.MEMORY_ONLY)
val wordCounts = lines.flatMap(line => line.split(' '))
	.map(word => word.toLowerCase())
	.countByValueAndWindow(Seconds(30), Seconds(5))
wordCounts
	.transform(rdd => rdd.sortBy(_._2, false))
	.print(10)
```

@[1](Create a context with a 1-second batch size.)
@[2](Open a socket stream to port 9999.)
@[3-5](Count of appearances of a word over a 30-second window, sliding every 5.)
@[6-8](Print the 10 most common words.)


---?image=presentation/assets/background/arrow.jpg&size=cover&opacity=20

### Hello World:  DataFrame

```scala
val spark:SparkSession = SparkSession.builder()
	.master("local[3]")
	.appName("HelloSparkStreaming_DataFrame")
	.getOrCreate()
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
```

@[1-4](Open a new Spark session.)
@[5-9](Open a socket stream to port 9999.)
@[10-14](Count of appearances of a word over the default frame.)
@[15-19](Write outputs to console until stopped.)

---?image=presentation/assets/background/demo.jpg&size=cover&opacity=20

### Demo Time

---

@title[A Full Program]

## Agenda

1. The Origins of Spark
2. What is Spark Streaming?
3. Our First Streaming Example
4. **A Full Program**
5. .NET and Spark Streaming

TODO:  lead-in, images

---?image=presentation/assets/background/restaurant.jpg&size=cover&opacity=20

### Cars:  a Story in Three Services

We will analyze food service inspection data for the city of Durham.  We want to answer a number of questions about this data, including average scores and splits between classic restaurants and food trucks.

---?image=presentation/assets/background/demo.jpg&size=cover&opacity=20

### Demo Time

---

@title[.NET and Spark Streaming]

## Agenda

1. The Origins of Spark
2. What is Spark Streaming?
3. Our First Streaming Example
4. A Full Program
5. **.NET and Spark Streaming**

---?image=presentation/assets/background/frame.jpg&size=cover&opacity=20

### Capabilities

Microsoft.Spark allows us to execute code in .NET DLLs or executables against Spark clusters.  Key functioanlity:

* Both C# and F# are supported.
* Use the DataFrames API for Spark Structured Streaming.
* Import additional libraries using Maven.
* Debugging is possible from within Visual Studio and Visual Studio Code.

---?image=presentation/assets/background/chain.jpg&size=cover&opacity=20

### Limitations

* No support for DStreams.
* Support for Spark versions tends to lag.
* Error handling can be a pain.

---?image=presentation/assets/background/magnifying-glass.jpg&size=cover&opacity=20

### Approach

* Build .NET code in Visual Studio / VS Code.
* Build a Docker container with .NET Core + Java and install Spark.
* Run the `spark-submit` command, sending all necessary parameters.

---?image=presentation/assets/background/demo.jpg&size=cover&opacity=20

### Demo Time

---

@title[What's Next]

TODO:
### What's Next

We've only scratched the surface of Spark Streaming.  From here, check out:

* MLLib, a library for machine learning algorithms built into Spark
* SparkR and sparklyr, two R libraries designed for distributed computing
* GraphX, a distributed graph database 
* Spark Streaming, allowing “real-time” data processing

---

### Wrapping Up

To learn more, go here:  <a href="https://csmore.info/on/sparkstreaming">https://CSmore.info/on/sparkstreaming</a>

And for help, contact me:  <a href="mailto:feasel@catallaxyservices.com">feasel@catallaxyservices.com</a> | <a href="https://www.twitter.com/feaselkl">@feaselkl</a>
