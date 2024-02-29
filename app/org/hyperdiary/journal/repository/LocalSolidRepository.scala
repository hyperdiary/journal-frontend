package org.hyperdiary.journal.repository
import com.inrupt.client.Request
import com.inrupt.client.auth.Session
import com.inrupt.client.jena.JenaBodyHandlers
import com.inrupt.client.openid.OpenIdSession
import com.inrupt.client.solid.SolidSyncClient
import org.apache.jena.rdf.model.{Model, ModelFactory}
import org.hyperdiary.journal.models.{Entry, Journal}

import java.io.ByteArrayInputStream
import java.net.URI
import javax.inject.{Inject, Singleton}

@Singleton
class LocalSolidRepository @Inject() extends SolidRepository {

//  private val session: Session = OpenIdSession.ofClientCredentials(
//    URI.create(System.getenv("MY_SOLID_IDP")),
//    System.getenv("MY_SOLID_CLIENT_ID"),
//    System.getenv("MY_SOLID_CLIENT_SECRET"),
//    System.getenv("MY_AUTH_FLOW")
//  )

  private val client: SolidSyncClient = SolidSyncClient.getClient //.session(session)

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
    val labelLocalName = labelText.replace(' ', '_').replaceAll("^\\.", "").replaceAll("[,'()]", "").replaceAll("\\.$", "").replace("(", "").replace(")", "")
    val labelUri = s"http://krw.localhost:3000/label/$labelLocalName"
    val request = Request.newBuilder().uri(URI.create(labelUri)).GET().build()
    val response = client.send(request, JenaBodyHandlers.ofModel())
    val model: Model = response.body()
    val stmt = model.getProperty(
      model.createResource(s"http://krw.hyperdiary.io/label/$labelLocalName"),
      model.createProperty("http://hyperdiary.io/terms/", "isLabelFor")
    )
    Some(stmt.getObject.toString)
  }
}
