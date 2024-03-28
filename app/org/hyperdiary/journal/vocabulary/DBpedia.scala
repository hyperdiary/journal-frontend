package org.hyperdiary.journal.vocabulary

import org.apache.jena.rdf.model.{Property, Resource, ResourceFactory}

object DBpedia {

  private val uri = "http://dbpedia.org/ontology/"

  val Place: Resource = ResourceFactory.createResource(s"${uri}Place")

  val subtitle: Property = ResourceFactory.createProperty(s"${uri}subtitle")

  val parent: Property = ResourceFactory.createProperty(s"${uri}parent")

  val birthDate: Property = ResourceFactory.createProperty(s"${uri}birthDate")

  val birthPlace: Property = ResourceFactory.createProperty(s"${uri}birthPlace")

  val deathDate: Property = ResourceFactory.createProperty(s"${uri}deathDate")

  val deathPlace: Property = ResourceFactory.createProperty(s"${uri}deathPlace")

  val sibling: Property = ResourceFactory.createProperty(s"${uri}sibling")

  val spouse: Property = ResourceFactory.createProperty(s"${uri}spouse")

  val startDate: Property = ResourceFactory.createProperty(s"${uri}startDate")

  val endDate: Property = ResourceFactory.createProperty(s"${uri}endDate")


  val child: Property = ResourceFactory.createProperty(s"${uri}child")

  val education: Property = ResourceFactory.createProperty(s"${uri}education")

  val employer: Property = ResourceFactory.createProperty(s"${uri}employer")

  val militaryUnit: Property = ResourceFactory.createProperty(s"${uri}militaryUnit")

  val location: Property = ResourceFactory.createProperty(s"${uri}location")

}
