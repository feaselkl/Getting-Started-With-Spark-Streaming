FROM mcr.microsoft.com/dotnet/core/sdk:3.1 as core

FROM openjdk:8u265-jre


# IF you have a local copy of the spark gz then reference that as a COPY (which will automatically extract the files) it will
# be a million times faster than ADD from url then extract. Comment out these two lines and uncomment "ADD ./blah"

ADD https://archive.apache.org/dist/spark/spark-2.4.5/spark-2.4.5-bin-hadoop2.7.tgz /tmp
RUN tar xf /tmp/spark-2.4.5-bin-hadoop2.7.tgz -C /usr/local/

#ADD ./spark-2.4.5-bin-hadoop2.7.tgz /usr/local

COPY --from=core /usr/share/dotnet /usr/share/dotnet
CMD "ln -s /usr/share/dotnet/dotnet"

RUN apt-get install \
        ca-certificates \        
        libssl1.1 \
        libstdc++ \
        tzdata

ENV \
    # Enable detection of running in a container
    DOTNET_RUNNING_IN_CONTAINER=true \
    # Set the invariant mode since icu_libs isn't included (see https://github.com/dotnet/announcements/issues/20)
    DOTNET_SYSTEM_GLOBALIZATION_INVARIANT=true

ENV SPARK_HOME=/usr/local/spark-2.4.5-bin-hadoop2.7
ENV PATH=:${PATH}:${SPARK_HOME}/bin:/usr/share/dotnet


RUN apt-get update
RUN apt-get install git --assume-yes
RUN apt-get install ncat vim nano --assume-yes

RUN dotnet --info

ADD data /root/data
ADD dotnet /root/dotnet
COPY run_spark_dotnet_demo /root/run_spark_dotnet_demo

# Create a log4j.properties file to get rid of annoying INFO messages in Spark.
RUN cp /usr/local/spark-2.4.5-bin-hadoop2.7/conf/log4j.properties.template /usr/local/spark-2.4.5-bin-hadoop2.7/conf/log4j.properties
RUN sed -i 's/log4j.rootCategory=INFO/log4j.rootCategory=WARN/g' /usr/local/spark-2.4.5-bin-hadoop2.7/conf/log4j.properties