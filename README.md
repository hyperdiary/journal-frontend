# HyperDiary Journal Frontend

A [Play Framework](https://www.playframework.com/) (Scala 3) web application for reading and navigating a personal diary stored as RDF data in a [Solid](https://solidproject.org/) Pod.

Journal entries, people, and places are modelled using a custom HyperDiary RDF vocabulary together with standard vocabularies (FOAF, Dublin Core, DBpedia). The label management feature lets you create short-text labels that hyperlink to entities in external knowledge graphs — DBpedia, Wikidata, or your own personal knowledge graph — making journal entries richly cross-referenced.

## Features

- **Journal viewer** — browse journals and read individual entries retrieved from a Solid Pod
- **Person profiles** — rich biographical pages including birth/death dates, family relationships, residences, employers, military units, and education, with optional links to DBpedia/Wikidata entities
- **Place pages** — view place resources stored in the Solid Pod
- **Label management** — create and delete labels that map plain text strings to URIs in a knowledge graph (DBpedia, Wikidata, or HyperDiary personal knowledge graph); a `/label/:label` endpoint returns a ready-to-use HTML hyperlink for any registered label

## Tech stack

| Layer | Technology |
|---|---|
| Web framework | Play Framework 2.9 / Scala 3.3 |
| RDF processing | Apache Jena 4.10 |
| Solid client | Inrupt Java Client SDK 1.1 |
| Solid server | Community Solid Server (Docker) |
| RDF triple store | Elemental (Docker, port 8080) |
| Build tool | sbt |

## Prerequisites

- Java 11+
- sbt
- Docker / Docker Compose

## Infrastructure setup

### 1. Community Solid Server

In your home directory create the following directories:

```
mkdir ~/solid-config
mkdir ~/Solid
```

Copy `css-config.json` from the project root into `~/solid-config/`.

The `~/Solid` directory is where the Community Solid Server will persist its data.

### 2. Start the services

```bash
docker compose up
```

This starts:
- **Community Solid Server** on `http://localhost:3000` — the Solid Pod that stores journals, entries, people, places, and labels as RDF resources
- **Elemental** on `http://localhost:8080` — the XML/RDF triple store

### 3. Populate DBpedia labels (optional)

If you want local DBpedia label lookups you can bulk-load the DBpedia labels dataset into the RDF4J triple store. Labels can be downloaded from [DBpedia Databus](https://databus.dbpedia.org/dbpedia/generic/labels/). See `rdf4jbulkLoad.sh` for the load script.

The application will also query the live DBpedia SPARQL endpoint (`https://dbpedia.org/sparql`) directly if no local store is configured.

## Running the application

```bash
sbt run
```

The application starts on `http://localhost:9000`.

## Routes

| Method | Path | Description |
|---|---|---|
| `GET` | `/` or `/journal` | Home page |
| `GET` | `/journal/:id` | View a journal |
| `GET` | `/journal/:id/entry/:entryId` | View a journal entry |
| `GET` | `/person/:id` | View a person profile |
| `GET` | `/place/:id` | View a place |
| `GET` | `/label` | Label management home |
| `GET` | `/label/:label` | Return an HTML hyperlink for a label |
| `POST` | `/label` | Navigate to create or delete label form |
| `GET/POST` | `/label/create` | Create a new label |
| `GET/POST` | `/label/delete` | Delete a label |

## Converting entries to JATS XML

Journal entry Markdown files can be converted to JATS XML with [pandoc](https://pandoc.org/):

```bash
pandoc -t jats -o entry.xml entry.md
```
