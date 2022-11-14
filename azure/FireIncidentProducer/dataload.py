import os
import asyncio
from azure.eventhub.aio import EventHubProducerClient
from azure.eventhub import EventData
import numpy as np
import pandas as pd

conn_str = os.environ['FIRE_INCIDENTS_EVENT_HUB_CONNECTION_STRING']
df = pd.read_csv('data/Fire_Incidents_2022.csv')
groups = df.groupby(np.arange(len(df.index))//10)

async def run():
    # Create a producer client to send messages to the event hub.
    # Specify a connection string to your event hubs namespace and
    # the event hub name.
    producer = EventHubProducerClient.from_connection_string(conn_str=conn_str, eventhub_name="fireincidents")
    async with producer:
        for (frameno, frame) in groups:
            # Create a batch.
            event_data_batch = await producer.create_batch()
            # Add events to the batch.
            frame.apply(lambda x: event_data_batch.add(EventData(x.to_json())), axis=1)
            # Send the batch of events to the event hub.
            await producer.send_batch(event_data_batch)

loop = asyncio.get_event_loop()
loop.run_until_complete(run())
