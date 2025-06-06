package org.hyperdiary.journal.services

import org.apache.jena.rdf.model.{ Model, ModelFactory }
import org.apache.jena.vocabulary.RDF
import org.hyperdiary.journal.LabelAlreadyExists
import org.hyperdiary.journal.repository.SolidRepository
import org.hyperdiary.journal.vocabulary.{ DBpedia, HyperDiary, PersonalKnowledgeGraph, Wikidata }

import javax.inject.{ Inject, Singleton }
import scala.jdk.CollectionConverters.*
import scala.util.{ Failure, Try }

@Singleton
class LabelService @Inject (solidRepository: SolidRepository, pkg: PersonalKnowledgeGraph) extends BaseService {

  def createLabel(label: String, target: String): Try[String] =
    solidRepository.getLabelLink(label) match {
      case None =>
        for {
          labelModel <- createLabelModel(label, target)
          result     <- solidRepository.createLabel(labelModel)
          _          <- Try(labelModel.close())
        } yield result
      case Some(_) => Failure(LabelAlreadyExists())
    }

  def deleteLabel(labelName: String): Try[Unit] =
    solidRepository.deleteLabel(s"${pkg.labelBaseUri}$labelName")

  def getHtmlHyperlink(label: String): String = {
    solidRepository.getLabelLink(label) match {
      case Some(link) =>
        val patchedLink = updateHtmlLink(link, pkg.baseUri) // TODO this is a temporary fix until deployed
        s"<a href=\"$patchedLink\">$label</a>"
      case _ => s"<span style=\"color:red;\">$label</span>"
    }
  }

  private def createLabelModel(label: String, target: String): Try[Model] = Try {
    val model = getModel
    val labelResource = model.createResource(s"${pkg.labelBaseUri}${sanitise(label)}")
    val targetResource = model.createResource(target)
    labelResource.addProperty(RDF.`type`, HyperDiary.Label).addProperty(HyperDiary.isLabelFor, targetResource)
    model
  }

  private def getModel: Model = {
    val model = ModelFactory.createDefaultModel
    val prefixes =
      Map(
        "hd"     -> HyperDiary.uri,
        "label"  -> pkg.labelBaseUri,
        "person" -> pkg.personBaseUri,
        "dbr"    -> DBpedia.resourceBaseUri,
        "wd"     -> Wikidata.entityBaseUri
      )
    model.setNsPrefixes(prefixes.asJava)
  }

  private def sanitise(label: String) = label.toLowerCase().replace(' ', '_')

}
