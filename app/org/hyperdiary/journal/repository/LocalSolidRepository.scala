package org.hyperdiary.journal.repository
import com.inrupt.client.{ Request, Response }
import com.inrupt.client.auth.Session
import com.inrupt.client.jena.{ JenaBodyHandlers, JenaBodyPublishers }
import com.inrupt.client.openid.OpenIdSession
import com.inrupt.client.solid.SolidSyncClient
import org.apache.jena.rdf.model.{ Model, ModelFactory, RDFNode }
import org.hyperdiary.journal.models.*
import org.hyperdiary.journal.services.BaseService
import org.hyperdiary.journal.vocabulary.HyperDiary

import java.net.URI
import java.net.http.HttpRequest.BodyPublisher
import javax.inject.{ Inject, Singleton }
import scala.jdk.CollectionConverters.*
import scala.util.{ Failure, Success, Try }

@Singleton
class LocalSolidRepository @Inject() extends SolidRepository with BaseService {

  private val session: Session = OpenIdSession.ofClientCredentials(
    new URI("http://localhost:3000/"),
    "journal_frontend_fc81ae65-01c5-4326-bc01-f131f16e9d4d",
    "915b739357468e6734d23dd08594b3a8ed8d6e38f3aa3837c8356eac19fb26a8185fa5dec9432ab138d019722801042960a9171f26679964f2cf048b1be2a7a5",
    "client_secret_basic"
  )

  private val client: SolidSyncClient =
    SolidSyncClient.getClient // .session(session)

  override def getJournal(journalUri: String): Option[Journal] = {
    val request = Request.newBuilder().uri(URI.create(journalUri)).GET().build()
    val response = client.send(request, JenaBodyHandlers.ofModel())
    Journal.fromModel(response.body())
  }

  override def getEntry(entryUri: String): Option[Entry] = {
    val request = Request.newBuilder().uri(URI.create(entryUri)).GET().build()
    val response = client.send(request, JenaBodyHandlers.ofModel())
    Entry.fromModel(response.body())
  }

  override def getLabelLink(labelText: String): Option[String] = {
    val labelLocalName = labelText
      .replace(' ', '_')
      .replaceAll("^\\.", "")
      .replaceAll("[,'()]", "")
      .replaceAll("\\.$", "")
      .replace("(", "")
      .replace(")", "")
      .toLowerCase()
    val labelUri = s"$cssPodUri/label/$labelLocalName"
    val request = Request.newBuilder().uri(URI.create(labelUri)).GET().build()
    Try(client.send(request, JenaBodyHandlers.ofModel())) match {
      case Success(response) =>
        if (response.body().isEmpty) {
          None
        } else {
          val model = response.body()
          val stmt = model.getProperty(
            model.createResource(s"$podBaseUri/label/$labelLocalName"),
            model.createProperty(HyperDiary.uri, "isLabelFor")
          )
          Some(stmt.getObject.toString)
        }
      case Failure(e) =>
        // TODO log error
        None
    }
  }

  override def getPerson(personUri: String): Option[Person] = {
    val request = Request.newBuilder().uri(URI.create(personUri)).GET().build()
    val response = client.send(request, JenaBodyHandlers.ofModel())
    Person.fromModel(response.body())
  }

  override def getResidence(residenceUri: String): Option[Residence] = {
    val request = Request.newBuilder().uri(URI.create(residenceUri)).GET().build()
    val response = client.send(request, JenaBodyHandlers.ofModel())
    Residence.fromModel(response.body())
  }

  override def getPlace(placeUri: String): Option[Place] = {
    val request = Request.newBuilder().uri(URI.create(placeUri)).GET().build()
    val response = client.send(request, JenaBodyHandlers.ofModel())
    Place.fromModel(response.body())
  }

  override def createLabel(labelsModel: Model): Try[String] = Try {
    val labelResource = labelsModel.listSubjects().toList.asScala.head
    val labelModel = labelResource.listProperties().toModel
    val labelUri = labelResource.getURI
    val request = Request
      .newBuilder()
      .uri(URI.create(labelUri))
      .header("Content-Type", "text/turtle")
      .PUT(JenaBodyPublishers.ofModel(labelModel))
      .build()
    val response = client.send(request, Response.BodyHandlers.discarding())
    if (response.statusCode() == 201) {
      labelUri
    } else {
      throw new RuntimeException("oops!") // TODO(RW) improve this!
    }
  }
}
