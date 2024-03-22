package org.hyperdiary.journal.vocabulary

import org.apache.jena.rdf.model.{Property, Resource, ResourceFactory}

object DBpedia {

  private val uri = "http://dbpedia.org/ontology/"

  val subtitle: Property = ResourceFactory.createProperty(s"${uri}subtitle")

  val parent: Property = ResourceFactory.createProperty(s"${uri}parent")

  val birthDate: Property = ResourceFactory.createProperty(s"${uri}birthDate")

  val birthPlace: Property = ResourceFactory.createProperty(s"${uri}birthPlace")

  val deathDate: Property = ResourceFactory.createProperty(s"${uri}deathDate")

  val deathPlace: Property = ResourceFactory.createProperty(s"${uri}birthPlace")


}
