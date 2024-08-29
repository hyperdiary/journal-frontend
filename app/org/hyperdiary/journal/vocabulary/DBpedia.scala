package org.hyperdiary.journal.vocabulary

import org.apache.jena.rdf.model.{ Property, Resource, ResourceFactory }

object DBpedia {

  private val baseUri = "http://dbpedia.org"
  private val ontologyBaseUri = s"$baseUri/ontology/"
  val resourceBaseUri = s"$baseUri/resource/"

  val Place: Resource = ResourceFactory.createResource(s"${ontologyBaseUri}Place")

  val subtitle: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}subtitle")

  val parent: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}parent")

  val birthDate: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}birthDate")

  val birthPlace: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}birthPlace")

  val deathDate: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}deathDate")

  val deathPlace: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}deathPlace")

  val sibling: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}sibling")

  val spouse: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}spouse")

  val startDate: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}startDate")

  val endDate: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}endDate")

  val child: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}child")

  val education: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}education")

  val employer: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}employer")

  val militaryUnit: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}militaryUnit")

  val location: Property = ResourceFactory.createProperty(s"${ontologyBaseUri}location")

}
