package org.hyperdiary.journal.vocabulary

import org.apache.jena.rdf.model.{ Property, Resource, ResourceFactory }

object HyperDiary {

  private val uri = "http://hyperdiary.io/terms/"

  val Paragraph: Resource = ResourceFactory.createResource(s"${uri}Paragraph")

  val Entry: Resource = ResourceFactory.createResource(s"${uri}Entry")

  val Journal: Resource = ResourceFactory.createResource(s"${uri}Journal")

  val Residence: Resource = ResourceFactory.createResource(s"${uri}Residence")

  val hasEntry: Property = ResourceFactory.createProperty(s"${uri}hasEntry")

  val residence: Property = ResourceFactory.createProperty(s"${uri}residence")

}
