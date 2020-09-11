# Getting Started with Spark Streaming

This repository supports my talk entitled [Getting Started with Spark Streaming](https://csmore.info/on/sparkstreaming).

## Demo One:  Hello World

This demo is easiest to run in IntelliJ IDEA, although you can certainly run it via `submit-spark` or in another IDE like Eclipse.

### Requirements

1. Apache Spark must be installed locally.  Instructions for this are available in my talk entitled [Getting Started with Apache Spark](https://csmore.info/on/spark).

2. `ncat` must be installed.  This comes pre-installed on MacOS and Linux.  For Windows, you can find it on [the Nmap website](https://nmap.org/ncat/).

### Process

1. Run ncat to open up a socket on port 9999:  `ncat -kl 9999`

2. Execute `HelloSparkStreaming.scala` or `HelloSparkStreamingDataFrame.scala`.

## Demo Two:  Kafka + Spark Streaming + Cassandra

This demo is easiest to run in IntelliJ IDEA, although you can certainly run it via `submit-spark` or in another IDE like Eclipse.

### Requirements

1. Apache Spark must be installed locally.  Instructions for this are available in my talk entitled [Getting Started with Apache Spark](https://csmore.info/on/spark).

2. `ncat` must be installed.  This comes pre-installed on MacOS and Linux.  For Windows, you can find it on [the Nmap website](https://nmap.org/ncat/).

3. Docker must be installed and should be configured to run Linux-based containers rather than Windows-based containers.

4. Apache Kafka must be installed.  My preferred option is to use [Confluent Platform on Docker](https://docs.confluent.io/current/quickstart/ce-docker-quickstart.html), as this works well on Windows.

5. Cassandra must be installed.  My preferred option is to use [Cassandra in a Docker container](https://medium.com/swlh/building-a-python-data-pipeline-to-apache-cassandra-on-a-docker-container-fc757fbfafdd).

### Process

1. Start up Confluent Platform:

    ```bash
    git clone https://github.com/confluentinc/cp-all-in-one
    cd cp-all-in-one
    git checkout 5.5.1-post
    cd cp-all-in-one/
    docker-compose up -d
    ```

2. Start up Cassandra:

    ```bash
    docker run -p 9042:9042 -p 7000:7000 -p 7001:7001 -p 7199:7199 -p 9160:9160 --name spark-cassandra -d cassandra
    ```

3. Connect to Cassandra.  One option is to use the [Cassandra workbench in Visual Studio Code](https://marketplace.visualstudio.com/items?itemName=kdcro101.vscode-cassandra).  Run the following code:

    ```cassandra
    CREATE  KEYSPACE public
    WITH REPLICATION = {
        'class' : 'SimpleStrategy', 'replication_factor' : 1 }
    };
    CREATE TABLE public.car("Name" text primary key, "Cylinders" int, "Horsepower" int )
    ```

4. Create a new Kafka topic named `car`.

5. Run `Job.scala` in the `ksc` project's `Spark` folder.

6. Load data from `data\cars.json` into the `car` topic.  Here is an example on Windows.

    ```cmd
    kafka-console-producer --broker-list localhost:9092 --topic car < C:\SourceCode\Getting-Started-With-Spark-Streaming\data\cars.json
    ```

7. Run `SELECT * FROM public.car` against Cassandra and notice the data has loaded into the Cassandra table.

## Demo Three:  .NET and Spark Streaming

These examples are derived from the [Microsoft.Spark samples for F#](https://github.com/dotnet/spark/tree/master/examples).

### Requirements

1. Docker must be installed and should be configured to run Linux-based containers rather than Windows-based containers.

2. Apache Kafka must be installed locally if you wish to run the Kafka experiment.  My preferred option is to use [Confluent Platform on Docker](https://docs.confluent.io/current/quickstart/ce-docker-quickstart.html), as this works well on Windows.

### Process

1. Start up Confluent Platform:

    ```bash
    git clone https://github.com/confluentinc/cp-all-in-one
    cd cp-all-in-one
    git checkout 5.5.1-post
    cd cp-all-in-one/
    docker-compose up -d
    ```

2. In the Confluent Control Center (by default, [http://localhost:9021](http://localhost:9021)), navigate to Cluster settings, choose Listener, and ensure that the **advertised.listeners** property has the IP address for your host machine.  For example, if your host machine is at IP address 192.168.1.10, the advertised listener should be at that IP address rather than 127.0.0.1 or localhost.  Otherwise, the .NET Kafka example will not work.

3. Create a topic in Kafka named **Flights** if you wish to run the Kafka demo.

4. Build the Dockerfile in this repository:

    ```bash
    docker build . -t gswss
    ```

5. Choose the demo you want to run.  Both `vi` and `nano` are installed with the image, so pick a text editor and modify the `run_spark_dotnet_demo` file to pick a specific demo.

    ```bash
    docker run --name gswss -it gswss bash
    cd /root
    vi run_spark_dotnet_demo
    ```

6. Run ncat.  To do this, open a new console and run the following:

    ```bash
    docker exec -it gswss /bin/bash
    nc -kl 9999
    ```

    Alternatively, you can load a large file with the following:

    ```bash
    docker exec -it gswss /bin/bash
    nc -kl 9999 < /root/data/WarAndPeace.txt
    ```

7. Execute the `run_spark_dotnet_demo` script to run the chosen demo.

    ```bash
    ./run_spark_dotnet_demo
    ```
