package org.hyperdiary.journal.repository
import org.apache.jena.rdf.model.ModelFactory
import org.hyperdiary.journal.models.{Entry, Paragraph}
import org.hyperdiary.solid.client.SolidClient
import sttp.client3.UriContext

import java.io.ByteArrayInputStream
import javax.inject.{Inject, Singleton}

@Singleton
class LocalSolidRepository @Inject() (solidClient: SolidClient)
    extends SolidRepository {

//  override def getEntry(entryUri: String): Option[Entry] =
//    Some(Entry("Chapter 1: Childhood Memories",
//      List(
//        Paragraph(1, "Gripping the rail tightly I gazed down through the darkness seeing the wake pushing out some thirty feet below as the 10,000-ton cruiser ![HMS Birmingham] nosed slowly ahead to pick up a buoy in ![Scapa Flow]. \"Come on lad - get on with it\", the voice of ![Petty Officer] Rose with a sharp edge as he tied a light rope around my chest. I climbed over the rail and with loudly beating heart, watched by a group of grinning and relieved sailors, clambered down the ship's side towards the great wooden boom that I had to rig for hauling out. It was the winter of 1944 and we had just returned from a \"destroyer sweep\" along the coast of ![Norway]. This meant we had been sent to destroy anything that looked useful or valuable to the ![Nazi occupiers]. I shivered, not just from the cold. I was well aware that if I fell and the heaving line around my chest snapped, I would plummet into the dark Northern waters and the great bronze propellers of this powerful ship would come churning towards me."),
//        Paragraph(2, "How the hell did I find ![myself] in this situation at the tender age of 18. Dear reader I shall explain and return to this wintry scene in due course."),
//        Paragraph(3, "![My father] also joined the ![Armed Forces] in the ![previous World War] and found himself in a tricky situation. He was mad about horses and desperately wanted to learn to ride. A nearby unit of the ![Army Service Corps] (![Territorial Army]) provided the opportunity, so in 1913 he joined up and achieved his equestrian aim. Unfortunately, he also achieved the distinction of being part of what the ![Kaiser] called a \"Contemptible Little Army\" and was sent to ![France] with the ![British Expeditionary Force] as soon as war was declared. He had met and become engaged to ![my mother] before departure and neither of them realised that this war was not to be, as the widely held belief was expressed,\" over by Christmas. In fact, it was to be five years before he finally came home, but he regarded himself as lucky because his unit was transferred to ![Salonika] where they became part of what was to be known as the ![Balkan War]. The ![British] element later became known as the \"Forgotten Army\" in this fierce conflict largely out of the eye of the ![British] public. The war in ![France] meanwhile degenerated into the slaughter of trench warfare. It is a sobering thought that had ![my father] remained in ![France] his chances of survival would have been slim, as an original member of the BEF, and I would not be writing this account.")
//      )))

  override def getEntry(entryUri: String): Option[Entry] = {
    val response = solidClient.getResource(uri"$entryUri", "text/turtle")
    response.body match {
      case Right(entry) =>
        val model = ModelFactory.createDefaultModel()
        model.read(new ByteArrayInputStream(entry.getBytes), entryUri, "TURTLE")
        Entry.fromModel(model)
      case _ => None
    }
  }

  override def getLabelLink(labelText: String): Option[String] = {
    val labelLocalName = labelText.replace(' ','_').replaceAll("[.']","")
    val labelUri = uri"http://krw.localhost:3000/label/$labelLocalName"
    val response = solidClient.getResource(labelUri, "text/turtle")
    response.body match {
      case Right(label) =>
        val model = ModelFactory.createDefaultModel()
        model.read(new ByteArrayInputStream(label.getBytes("UTF-8")), null, "TURTLE")
        val stmt = model.getProperty(
          model.createResource(s"http://krw.hyperdiary.io/label/$labelLocalName"),
          model.createProperty("http://hyperdiary.io/terms/", "isLabelFor")
        )
        Some(stmt.getObject.toString)
      case _ => None
    }
  }
}