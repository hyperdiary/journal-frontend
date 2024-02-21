package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.{Model, Resource}
import org.apache.jena.vocabulary.{DCTerms, RDF}
import org.hyperdiary.journal.vocabulary.HyperDiary

import scala.jdk.CollectionConverters.*

case class Entry(id: String, title: String, paragraphs: List[Paragraph])
object Entry {
  def fromModel(model: Model): Option[Entry] = model
      .listSubjectsWithProperty(RDF.`type`, HyperDiary.Entry)
      .asScala
      .toList
      .headOption
      .flatMap { entry =>
        Some(Entry(getEntryId(entry), getEntryTitle(entry), getParagraphs(model)))
      }

  private def getEntryId(entry: Resource) = {
    if (entry.hasProperty(DCTerms.identifier)) {
      val idObject = entry.getProperty(DCTerms.identifier).getObject
      if (idObject.isLiteral) {
        idObject.asLiteral().getString
      } else {
        // TODO log incorrect object type
        ""
      }
    } else {
      // TODO log missing title statement
      ""
    }
  }

  private def getEntryTitle(entry: Resource): String = {
    if (entry.hasProperty(DCTerms.title)) {
      val titleObject = entry.getProperty(DCTerms.title).getObject
      if (titleObject.isLiteral) {
        titleObject.asLiteral().getString
      } else {
        // TODO log incorrect object type
        ""
      }
    } else {
      // TODO log missing title statement
      ""
    }
  }

  private def getParagraphs(model: Model): List[Paragraph] =
    model
      .listSubjectsWithProperty(RDF.`type`, HyperDiary.Paragraph)
      .asScala
      .toList
      .map { paragraph =>
        val index = paragraph.getURI.split("#P")(1).toInt
        if (paragraph.hasProperty(DCTerms.description)) {
          val descriptionObject =
            paragraph.getProperty(DCTerms.description).getObject
          if (descriptionObject.isLiteral) {
            Paragraph(index, descriptionObject.asLiteral().getString)
          } else {
            // TODO log incorrect object type
            Paragraph(index, "")
          }
        } else {
          // TODO log missing title statement
          Paragraph(index, "")
        }
      }
      .sorted

}
