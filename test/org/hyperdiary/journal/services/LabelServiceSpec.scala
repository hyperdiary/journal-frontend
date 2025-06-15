package org.hyperdiary.journal.services

import org.apache.jena.riot.{ RDFDataMgr, RDFFormat }
import org.hyperdiary.journal.config.Config
import org.hyperdiary.journal.repository.SolidRepository
import org.hyperdiary.journal.vocabulary.{ DBpedia, PersonalKnowledgeGraph, Wikidata }
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

import java.io.StringWriter

class LabelServiceSpec extends PlaySpec with MockitoSugar {

  "LabelService getLabel" must {

    val testConfig = Config("example")
    val mockSolidRepository = mock[SolidRepository]
    val pkg = PersonalKnowledgeGraph(testConfig)
    val service = new LabelService(mockSolidRepository, pkg)

    "Create a label to a DBpedia resource" in {
      val label = service.createLabel("water skiing", s"${DBpedia.resourceBaseUri}Water_skiing")
      label mustEqual (getModelFromFile("Water_skiing.ttl"))
    }

    "Create a label to a Wikidata resource" in {
      val label = service.createLabel("David Drew", s"${Wikidata.entityBaseUri}Q127700078")
      label mustEqual (getModelFromFile("David_Drew.ttl"))
    }

    "Create a label to a Personal Knowledge Graph resource" in {
      val label = service.createLabel("Peggy", s"${pkg.personBaseUri}I100194424063")
      label mustEqual (getModelFromFile("Peggy.ttl"))
    }

    "Create two labels to a Personal Knowledge Graph resource" in {
      val label = service.createLabel("Peggy", s"${pkg.personBaseUri}I100194424063")
      label mustEqual (getModelFromFile("Peggy.ttl"))
    }

  }

  private def getModelFromFile(path: String) = {
    val uri = getClass.getResource("/" + path).toURI
    val writer = new StringWriter()
    val expectedModel = RDFDataMgr.loadModel(uri.toString)
    RDFDataMgr.write(writer, expectedModel, RDFFormat.TURTLE)
    writer.toString
  }

}
