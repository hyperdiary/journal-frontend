package org.hyperdiary.journal.services

import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.{RDFDataMgr, RDFFormat}
import org.apache.jena.vocabulary.RDF
import org.hyperdiary.journal.vocabulary.HyperDiary

import java.io.StringWriter

class LabelService extends BaseService {

  private val model = ModelFactory.createDefaultModel
  private val labelTypeUri = model.createResource()
  private val labelBaseUri = s"$podBaseUri/label/"

  def createLabel(label:String, target: String): String = {
    // TODO check if label exists?
    val labelResource = model.createResource(s"$labelBaseUri$label")
    val targetResource = model.createResource(target)
    labelResource.addProperty(RDF.`type`, HyperDiary.Label)
    labelResource.addProperty(HyperDiary.isLabelFor, targetResource)
    val writer = new StringWriter()
    RDFDataMgr.write(writer,model,RDFFormat.TURTLE)
    writer.toString
  }

}
