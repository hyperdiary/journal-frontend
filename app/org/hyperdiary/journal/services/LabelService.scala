package org.hyperdiary.journal.services

import org.apache.jena.rdf.model.{ Model, ModelFactory }
import org.apache.jena.riot.{ RDFDataMgr, RDFFormat }
import org.apache.jena.vocabulary.RDF
import org.hyperdiary.journal.repository.SolidRepository
import org.hyperdiary.journal.vocabulary.{ DBpedia, HyperDiary, PersonalKnowledgeGraph, Wikidata }

import java.io.StringWriter
import javax.inject.{ Inject, Singleton }
import scala.jdk.CollectionConverters.*

@Singleton
class LabelService @Inject (solidRepository: SolidRepository, pkg: PersonalKnowledgeGraph) extends BaseService {

  def createLabel(label: String, target: String): Option[String] = {
    // TODO check if label exists?
    val labelModel = createLabelModel(label,target)
    val result = solidRepository.createLabel(labelModel)
    labelModel.close()
    result
//    val writer = new StringWriter()
//    RDFDataMgr.write(writer, labelModel, RDFFormat.TURTLE)
//    labelModel.close()
  }
  
  def createLabelModel(label: String, target: String): Model = {
    val model = getModel
    val labelResource = model.createResource(s"${pkg.labelBaseUri}${sanitise(label)}")
    val targetResource = model.createResource(target)
    labelResource.addProperty(RDF.`type`, HyperDiary.Label)
    labelResource.addProperty(HyperDiary.isLabelFor, targetResource)
    // TODO(RW) check for exceptions?
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
