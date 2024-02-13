package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.Model
import org.apache.jena.vocabulary.{DCTerms, RDF}
import org.hyperdiary.journal.vocabulary.HyperDiary

import scala.jdk.CollectionConverters.*

case class Entry(title: String, paragraphs: List[Paragraph])
object Entry {
  def fromModel(model: Model): Option[Entry] = {
    val paragraphs = model
      .listSubjectsWithProperty(RDF.`type`, HyperDiary.Paragraph)
      .asScala
      .toList
      .map { resource =>
        {
          val index = resource.getURI.split("#P")(1).toInt
          val text = resource
            .getProperty(DCTerms.description)
            .getObject
            .asLiteral()
            .getString
          Paragraph(index, text)
        }
      }
      .sorted
    val title = model
      .listSubjectsWithProperty(RDF.`type`, HyperDiary.Entry)
      .asScala
      .toList
      .head
      .getProperty(DCTerms.title)
      .getObject
      .asLiteral()
      .getString
    Some(Entry(title, paragraphs))
  }

}
