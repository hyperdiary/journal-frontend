package org.hyperdiary.journal.vocabulary

import org.apache.jena.rdf.model.{Property, Resource, ResourceFactory}
import org.apache.jena.vocabulary.RDF.Init

object HyperDiary {

  private val uri = "http://hyperdiary.io/terms/"

  val Paragraph: Resource = ResourceFactory.createResource(s"${uri}Paragraph")

  val Entry: Resource = ResourceFactory.createResource(s"${uri}Entry")

}