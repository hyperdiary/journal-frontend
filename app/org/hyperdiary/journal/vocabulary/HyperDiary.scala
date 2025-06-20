package org.hyperdiary.journal.vocabulary

import org.apache.jena.rdf.model.{ Property, Resource, ResourceFactory }

object HyperDiary {

  val uri = "http://hyperdiary.io/terms/"

  val Paragraph: Resource = ResourceFactory.createResource(s"${uri}Paragraph")

  val Entry: Resource = ResourceFactory.createResource(s"${uri}Entry")

  val Label: Resource = ResourceFactory.createResource(s"${uri}Label")

  val Journal: Resource = ResourceFactory.createResource(s"${uri}Journal")

  val Residence: Resource = ResourceFactory.createResource(s"${uri}Residence")

  val hasEntry: Property = ResourceFactory.createProperty(s"${uri}hasEntry")

  val residence: Property = ResourceFactory.createProperty(s"${uri}residence")

  val isLabelFor: Property = ResourceFactory.createProperty(s"${uri}isLabelFor")

}
