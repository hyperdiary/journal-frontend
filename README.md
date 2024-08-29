# journal-frontend

DBpedia Labels can be download from https://databus.dbpedia.org/dbpedia/generic/labels/ for populating the RDF4J
database

In your home directory create a sub-directory called `solid-config`. Copy the file `css-config.json` into the newly created directory.

Also in your home directory create a sub-directory called `Solid`. This is where the Community Solid Server will store its data.

You can now start the Community Solid Server in Docker:

```commandline
docker compose up
```

To start:
```
sbt run
```
