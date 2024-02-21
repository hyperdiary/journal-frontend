package org.hyperdiary.journal.vocabulary

import org.apache.jena.rdf.model.{Property, Resource, ResourceFactory}

object DBpedia {

  private val uri = "http://dbpedia.org/ontology/"

  val subtitle: Property = ResourceFactory.createProperty(s"${uri}subtitle")

}
